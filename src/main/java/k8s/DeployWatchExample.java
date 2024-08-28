package k8s;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Deployment;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.Watch;
import okhttp3.Call;

public class DeployWatchExample {

    public static void main(String[] args) throws Exception {
        // 初始化 API 客户端
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);

        // 创建 AppsV1Api 实例
        AppsV1Api appsV1Api = new AppsV1Api();

        // 监视 Pod 的变化
        String fieldSelector = null;
        String labelSelector = null;


        Call call2 = appsV1Api.listNamespacedDeploymentCall(
                "aidev", null, null, null, fieldSelector, labelSelector,
                null, null, null, null, null, true, null);

        Watch<V1Deployment> watch2 = Watch.createWatch(
                client,
                call2,
                new com.google.gson.reflect.TypeToken<Watch.Response<V1Deployment>>() {
                }.getType());

        System.out.println("Watching for changes:");

        for (Watch.Response<V1Deployment> item : watch2) {
            String lastResourceVersion = item.object.getMetadata().getResourceVersion(); // 更新 resourceVersion
            System.out.printf("Deploy resourceVersion: %s; Type: %s; Name: %s; UpdatedReplicas: %s; ReadyReplicas: %s;" +
                            " AvailableReplicas: %s; Replicas: %s; UnavailableReplica: %s%n",
                    lastResourceVersion,
                    item.type,
                    item.object.getMetadata().getName(),
                    item.object.getStatus().getUpdatedReplicas(),
                    item.object.getStatus().getReadyReplicas(),
                    item.object.getStatus().getAvailableReplicas(),
                    item.object.getStatus().getReplicas(),
                    item.object.getStatus().getUnavailableReplicas()
            );
//            System.out.println("Conditions:" + lastResourceVersion +  ">>>>" + item.object.getStatus().getConditions());
//            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + item.type + ":" + item.object.getMetadata().getName() + ":" + item.object);
//            System.out.println("-------------------------------------");
        }
    }
}