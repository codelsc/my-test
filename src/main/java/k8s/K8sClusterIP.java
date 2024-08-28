package k8s;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Node;
import io.kubernetes.client.openapi.models.V1NodeList;
import io.kubernetes.client.util.Config;

public class K8sClusterIP {
    public static void main(String[] args) throws Exception {
        // 创建 Kubernetes API 客户端
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);

        CoreV1Api api = new CoreV1Api();
        V1NodeList nodes = api.listNode(null, null, null, null, null, null, null, null, null, null, null);

        // 打印每个节点的 IP 地址
        for (V1Node node : nodes.getItems()) {
            String nodeIP = node.getStatus().getAddresses().stream()
                    .filter(address -> "InternalIP".equals(address.getType()))
                    .findFirst()
                    .map(address -> address.getAddress())
                    .orElse("N/A");
            System.out.println("Node IP: " + nodeIP);
        }
    }
}
