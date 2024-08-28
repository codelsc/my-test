package k8s;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.models.V1Deployment;
import io.kubernetes.client.openapi.models.V1ReplicaSet;
import io.kubernetes.client.util.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KubernetesReplicaSet {
    public static void main(String[] args) {
        try {
            // 连接到 Kubernetes 集群
            ApiClient client = Config.defaultClient();
            Configuration.setDefaultApiClient(client);

            // 初始化 AppsV1Api
            AppsV1Api appsV1Api = new AppsV1Api();

            String namespace = "aidev"; // 指定命名空间
            String deploymentName = "my-service09-23"; // 指定 Deployment 名称

            // 获取 Deployment
            V1Deployment deployment = appsV1Api.readNamespacedDeployment(deploymentName, namespace, null);

            if (deployment == null) {
                System.out.println("未找到指定的Deployment.");
                return;
            }

            // 获取命名空间下的所有 ReplicaSets
            List<V1ReplicaSet> replicaSets = appsV1Api.listNamespacedReplicaSet(namespace, null, null, null, null, "service-name=my-service09-23", null, null, null, null, null, null).getItems();

            V1ReplicaSet newReplicaSet = null;
            List<V1ReplicaSet> oldReplicaSets = new ArrayList<>();

            // 识别 New ReplicaSet 和 Old ReplicaSets
            for (V1ReplicaSet replicaSet : replicaSets) {
                String ownerUid = replicaSet.getMetadata().getOwnerReferences().get(0).getUid();
                if (ownerUid.equals(deployment.getMetadata().getUid())) {
                    String revision = replicaSet.getMetadata().getAnnotations().get("deployment.kubernetes.io/revision");
                    String currentRevision = deployment.getMetadata().getAnnotations().get("deployment.kubernetes.io/revision");

                    if (currentRevision.equals(revision)) {
                        newReplicaSet = replicaSet;
                    } else {
                        oldReplicaSets.add(replicaSet);
                    }
                }
            }

            System.out.println("New ReplicaSet: " + newReplicaSet);
            System.out.println("Old ReplicaSets: " + oldReplicaSets);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}