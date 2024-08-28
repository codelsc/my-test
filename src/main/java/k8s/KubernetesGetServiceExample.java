package k8s;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Service;
import io.kubernetes.client.util.Config;

public class KubernetesGetServiceExample {

    public static void main(String[] args) {
        // 请替换为您要查询的 Service 名称和命名空间
        String serviceName = "lsc-service"; // 例如 "my-service"
        String namespace = "default";       // 例如 "default"

        try {
            // 创建 Kubernetes API 客户端
            ApiClient client = Config.defaultClient();
            io.kubernetes.client.openapi.Configuration.setDefaultApiClient(client);

            // 创建 CoreV1Api 的实例
            CoreV1Api api = new CoreV1Api();

            // 获取指定命名空间下的 Service 信息
            try {
                V1Service service = api.readNamespacedService(serviceName, namespace, null);
                System.out.println(service);
            } catch (ApiException apiException){
                System.out.println(apiException.getCode());
            }

            // 打印 Service 的详细信息
//            System.out.println("Service Name: " + service.getMetadata().getName());
//            System.out.println("Namespace: " + service.getMetadata().getNamespace());
//            System.out.println("Labels: " + service.getMetadata().getLabels());
//            System.out.println("Annotations: " + service.getMetadata().getAnnotations());
//            System.out.println("Cluster IP: " + service.getSpec().getClusterIP());
//            System.out.println("Ports: " + service.getSpec().getPorts());
            System.out.println("--------------------------------------------------");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
