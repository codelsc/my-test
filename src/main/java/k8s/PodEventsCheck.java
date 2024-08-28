package k8s;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.CoreV1EventList;
import io.kubernetes.client.util.Config;

public class PodEventsCheck {
    public static void main(String[] args) throws Exception {
        // 进行 API 客户端实例化
        ApiClient client = Config.defaultClient();
        io.kubernetes.client.openapi.Configuration.setDefaultApiClient(client);

        CoreV1Api api = new CoreV1Api(client);

        // 替换为你的命名空间和 Pod 名称
        String namespace = "aidev"; // 你想要查询的命名空间
        String podName = "my-service08-22-675984c8c-6hpn4"; // 你的 Pod 名称
        String fieldSelector = "involvedObject.kind=Pod,involvedObject.name=" + podName;

        // 获取 Pod 所在命名空间的所有 Events
        CoreV1EventList eventList = api.listNamespacedEvent(namespace, null, null, null, fieldSelector, null, null, null, null, false, null, null);

        // 遍历 Events 列表并筛选 Pod 相关的 Events
        for (var event : eventList.getItems()) {
            if (event.getInvolvedObject().getName().contains(podName) &&
                    event.getInvolvedObject().getKind().equals("Pod")) {
                System.out.println("Reason: " + event.getReason());
                System.out.println("Message: " + event.getMessage());
                System.out.println("----");
            }
        }
    }
}
