//package k8s;
//
//import io.kubernetes.client.openapi.ApiClient;
//import io.kubernetes.client.openapi.Configuration;
//import io.kubernetes.client.openapi.apis.AppsV1Api;
//import io.kubernetes.client.openapi.models.V1ContainerPort;
//import io.kubernetes.client.openapi.models.V1Deployment;
//import io.kubernetes.client.openapi.models.V1DeploymentBuilder;
//import io.kubernetes.client.openapi.models.V1DeploymentSpec;
//import io.kubernetes.client.openapi.models.V1DeploymentSpecBuilder;
//import io.kubernetes.client.openapi.models.V1PodTemplateSpec;
//import io.kubernetes.client.openapi.models.V1PodTemplateSpecBuilder;
//import io.kubernetes.client.util.Config;
//
//import java.util.Collections;
//import java.util.HashMap;
//
//public class KubeDeploymentExample {
//
//    public static void main(String[] args) {
//        try {
//            // 加载kubeconfig文件，配置访问Kubernetes集群
//            ApiClient client = Config.defaultClient();
//            Configuration.setDefaultApiClient(client);
//
//            // 创建AppsV1Api实例
//            AppsV1Api api = new AppsV1Api();
//
//            // 创建一个Deployment
//            V1Deployment deployment = new V1DeploymentBuilder()
//                    .withNewMetadata()
//                    .withName("my-nginx-deployment")
//                    .endMetadata()
//                    .withNewSpec()
//                    .withReplicas(3)
//                    .withNewTemplate(new V1PodTemplateSpecBuilder()
//                            .withNewMetadata()
//                            .withLabels(new HashMap<String, String>() {{
//                                put("app", "nginx");
//                            }})
//                            .endMetadata()
//                            .withNewSpec()
//                            .addNewContainer()
//                            .withName("nginx")
//                            .withImage("nginx:1.17.1")
//                            .withPorts(Collections.singletonList(new V1ContainerPort().containerPort(80)))
//                            .endContainer()
//                            .endSpec()
//                            .endTemplate()
//                            .endSpec()
//                            .build();
//
//            // 应用Deployment到Kubernetes集群
//            api.createNamespacedDeployment("default", deployment, null, null, null);
//
//            System.out.println("Deployment created successfully");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
