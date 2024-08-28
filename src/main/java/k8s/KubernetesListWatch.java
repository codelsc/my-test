package k8s;

import com.google.gson.reflect.TypeToken;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.openapi.models.V1PodStatus;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.Watch;
import java.io.IOException;

public class KubernetesListWatch {

    public static void main(String[] args) {
        try {
            // 创建 API 客户端
            ApiClient client = Config.defaultClient();
            Configuration.setDefaultApiClient(client);

            // 创建 CoreV1Api 实例
            CoreV1Api api = new CoreV1Api();

            // 首先列出所有 Pods
            V1PodList podList = api.listNamespacedPod("default", null, null, null, null, null, null, null, null, null, null, null);
            for (V1Pod pod : podList.getItems()) {
                System.out.println("Pod name: " + pod.getMetadata().getName());
                // 获取 Pod 的状态
                V1PodStatus podStatus = pod.getStatus();
                if (podStatus != null) {
                    // 获取 Pod 的阶段（Phase）
                    String phase = podStatus.getPhase();
                    System.out.println("Pod status phase: " + phase);

                    // 获取容器状态
                    if (podStatus.getContainerStatuses() != null) {
                        podStatus.getContainerStatuses().forEach(containerStatus -> {
                            String containerName = containerStatus.getName();
                            String containerState = containerStatus.getState().toString();
                            System.out.println("Container name: " + containerName + ", state: " + containerState);
                        });
                    }
                } else {
                    System.out.println("Pod status is not available.");
                }
            }

            // 使用 Watch 监视 Pods 的变化
            Watch<V1Pod> watch = Watch.createWatch(
                    client,
                    api.listNamespacedPodCall("default", null, null, null, null, null, null, null, null, true, null, null, null),
                    new TypeToken<Watch.Response<V1Pod>>(){}.getType());

            for (Watch.Response<V1Pod> watchResponse : watch) {
                System.out.println("Received event: " + watchResponse.type + " Pod: " + watchResponse.object.getMetadata().getName());
            }
        } catch (IOException | ApiException e) {
            e.printStackTrace();
        }
    }
}