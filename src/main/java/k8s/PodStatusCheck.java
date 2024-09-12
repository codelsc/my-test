package k8s;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.util.Config;


public class PodStatusCheck {
    public static void main(String[] args) throws Exception {
        // 使用 Kubeconfig 文件进行初始化
        ApiClient client = Config.defaultClient();
        io.kubernetes.client.openapi.Configuration.setDefaultApiClient(client);

        CoreV1Api api = new CoreV1Api(client);

        // 获取特定命名空间下的 Pod 列表
        String namespace = "aidev"; // 替换为你想要查询的命名空间
        V1PodList podList = api.listNamespacedPod(namespace, null, null, null,
                null, null, null, null, null,
                false, null, null);

        // 遍历 Pod 列表并检查状态
        for (V1Pod pod : podList.getItems()) {
            String podName = pod.getMetadata().getName();
            System.out.println("Pod Name: " + podName);

            // 检查 Pod 的状态
            if (pod.getStatus() != null && pod.getStatus().getPhase().equals("Failed")) {
                System.out.println("Pod " + podName + " failed.");
                if (pod.getStatus().getContainerStatuses() != null) {
                    for (var containerStatus : pod.getStatus().getContainerStatuses()) {
                        if (containerStatus.getState().getTerminated() != null) {
                            System.out.println("Container Name: " + containerStatus.getName());
                            System.out.println("Reason: " + containerStatus.getState().getTerminated().getReason());
                        }
                    }
                }
            }
        }
    }
}