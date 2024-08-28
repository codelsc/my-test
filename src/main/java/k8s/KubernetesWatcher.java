package k8s;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.kubernetes.client.WatcherException;

public class KubernetesWatcher {
    public static void main(String[] args) throws Exception {
        KubernetesClient client = new DefaultKubernetesClient();
        client.pods().inAnyNamespace().watch(new Watcher<Pod>() {
            @Override
            public boolean reconnecting() {
                System.out.println("Watcher reconnecting");
                return Watcher.super.reconnecting();
            }

            @Override
            public void eventReceived(Action action, Pod pod) {
                // 处理Pod状态变化
                System.out.println("Received event: " + action + " for pod: " + pod.getMetadata().getName());
            }

            @Override
            public void onClose() {
                System.out.println("Watcher closed");
            }

            @Override
            public void onClose(WatcherException e) {
                System.out.println("Watcher closed e");
            }
        });
    }
}
