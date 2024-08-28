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

public class PodWatchExample {

    public static void main(String[] args) throws Exception {
        // 初始化 API 客户端
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);

        // 创建 CoreV1Api 实例
        CoreV1Api api = new CoreV1Api();
        // 创建 AppsV1Api 实例
        AppsV1Api appsV1Api = new AppsV1Api();
        // 列出所有 Pod
//        V1PodList podList = api.listPodForAllNamespaces(
//                null, null, null, null, null, null, null, null, null, null, null);
//
//        System.out.println("Listing Pods:");
//        podList.getItems().forEach(pod ->
//                System.out.println(pod.getMetadata().getName()));

        // 监视 Pod 的变化
        String fieldSelector = null;
        String labelSelector = null;
//        Call call = api.listPodForAllNamespacesCall(
//                null, null, fieldSelector, labelSelector, null, null,
//                null, null, true, null, true, null);
        Call call = api.listNamespacedPodCall(
                "aidev", null, null, null, fieldSelector, labelSelector,
                null, null, null, null, null, true, null);

        Watch<V1Pod> watch = Watch.createWatch(
                client,
                call,
                new com.google.gson.reflect.TypeToken<Watch.Response<V1Pod>>() {
                }.getType());

        System.out.println("Watching for changes:");
        for (Watch.Response<V1Pod> item : watch) {
//            String lastResourceVersion = item.object.getMetadata().getResourceVersion(); // 更新 resourceVersion
//            System.out.printf("Pod resourceVersion: %s; Type: %s; Name: %s; phase: %s%n",
//                    lastResourceVersion,
//                    item.type,
//                    item.object.getMetadata().getName(),
//                    item.object.getStatus().getPhase());
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + item.type + ":" + item.object.getMetadata().getName() + ":" + item.object);
            System.out.println("-------------------------------------");
        }
    }
}