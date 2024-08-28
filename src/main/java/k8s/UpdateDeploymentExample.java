package k8s;

import io.kubernetes.client.custom.V1Patch;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.models.*;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.openapi.apis.AppsV1Api;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class UpdateDeploymentExample {
    public static void main(String[] args) {
        try {
            // 创建 API 客户端
            ApiClient client = Config.defaultClient();
            Configuration.setDefaultApiClient(client);

            // 创建 AppsV1Api 实例
            AppsV1Api appsV1Api = new AppsV1Api();

            String namespace = "default"; // 设置命名空间
            String deploymentName = "lsc-service"; // 设置部署名称

            // 获取现有的 Deployment
            V1Deployment deployment = appsV1Api.readNamespacedDeployment(deploymentName, namespace, null);

//            String containerImage = "nginx:latest"; // 容器镜像
//            String appName = "lsc-service";
//            String domainName = "ingress-test-l.csnd-dev.com"; // 这里是您想要设置的域名
//            int servicePort = 80; // 服务端口
//            V1Deployment deployment = new V1Deployment()
//                    .apiVersion("apps/v1")
//                    .kind("Deployment")
//                    .metadata(new V1ObjectMeta().name(appName).namespace(namespace))
//                    .spec(new V1DeploymentSpec()
//                            .replicas(1)
//                            .selector(new V1LabelSelector().matchLabels(Map.of("app", appName)))
//                            .template(new V1PodTemplateSpec()
//                                            .metadata(new V1ObjectMeta().labels(Map.of("app", appName)))
//                                            .spec(new V1PodSpec()
//                                                            .containers(List.of(new V1Container()
//                                                                            .name("my-container")
//                                                                            .image(containerImage)
//                                                                            .ports(List.of(new V1ContainerPort().containerPort(servicePort)))
//                                                            )
//                                            ))
//                            ));

            // 更新 Deployment 的 Pod 模板 (例如，添加环境变量)
            V1PodTemplateSpec template = deployment.getSpec().getTemplate();
            // 在这里做必要的修改，例如更新容器的标签、环境变量等
            // e.g., template.getSpec().getContainers().get(0).setImage("new-image:tag");

            // 只更改 Deployment 的副本数
//            V1DeploymentSpec spec = deployment.getSpec();
//            spec.setReplicas(spec.getReplicas() + 1); // 增加副本数
//            deployment.setSpec(spec);

            V1PodTemplateSpec v1PodTemplateSpec = deployment.getSpec().getTemplate();
            // 更新标签
            v1PodTemplateSpec.getMetadata().putLabelsItem("new-label-key", "new-label-value3"); // 更新或添加标签
            // 应用更新
            // 创建补丁
//            String patchStr = "[{\"op\": \"replace\", \"path\": \"/spec/replicas\", \"value\": 5}]"; // 修改副本数为5
//            V1Patch patch = new V1Patch(patchStr, V1PatchType.JSON_PATCH);
//            V1Deployment updatedDeployment = appsV1Api.patchNamespacedDeployment(deploymentName, namespace, patch, null, null, null, null, null);
            V1Deployment updatedDeployment = appsV1Api.replaceNamespacedDeployment(deploymentName, namespace, deployment, null, null, null, null);

            System.out.println("Updated Deployment: " + updatedDeployment.getMetadata().getName());

        } catch (ApiException e) {
            System.err.println("Exception when calling AppsV1Api#readNamespacedDeployment");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}