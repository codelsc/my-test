package k8s;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.openapi.models.V1NodeList;
import io.kubernetes.client.openapi.models.V1Node;

public class KubernetesMasterNodeIp {

    public static void main(String[] args) throws Exception {
        // 创建 Kubernetes API 客户端
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);

        // 创建 CoreV1Api 实例，与 Kubernetes API 交互
        CoreV1Api api = new CoreV1Api();

        // 获取所有节点
        V1NodeList nodeList = api.listNode(null, null, null, null, null, null, null, null, null, null, null);

        for (V1Node node : nodeList.getItems()) {
            System.out.println("Node Name: " + node.getMetadata().getName());
            // 假设你关心的是节点的某个内部IP
            if (node.getStatus() != null && node.getStatus().getAddresses() != null) {
                for (io.kubernetes.client.openapi.models.V1NodeAddress addr : node.getStatus().getAddresses()) {
                    if ("InternalIP".equals(addr.getType())) {
                        System.out.println("Internal IP: " + addr.getAddress());
                    }
                }
            }
        }
    }
}
