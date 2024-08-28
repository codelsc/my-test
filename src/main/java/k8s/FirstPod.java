package k8s;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Deployment;
import io.kubernetes.client.openapi.models.V1DeploymentList;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.util.Config;

import java.io.IOException;
import java.util.List;

public class FirstPod {

    public static void main(String[] args) throws IOException {
        // 创建 Kubernetes API 客户端
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);

        // 获取 CoreV1Api 实例
        CoreV1Api api = new CoreV1Api();

        // 替换为您想要查询的命名空间和 Deployment 名称
        String namespace = "aidev";

        // 根据标签选择器获取最新 Pod
        V1PodList podList = null;
        try {
            podList = api.listNamespacedPod(namespace, null, null, null, null, "service-name=my-service09-23", null, null, null, null,null,null);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
        List<V1Pod> pods = podList.getItems();

        // 找到最新的 Pod（根据时间戳）
        V1Pod latestPod = pods.stream()
                .sorted((p1, p2) -> p2.getMetadata().getCreationTimestamp().compareTo(p1.getMetadata().getCreationTimestamp()))
                .findFirst()
                .orElse(null);

        if (latestPod != null) {
            System.out.println("Latest Pod: " + latestPod.getMetadata().getName());
        } else {
            System.out.println("No Pods found");
        }
    }
}
