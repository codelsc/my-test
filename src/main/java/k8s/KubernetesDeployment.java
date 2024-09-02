package k8s;

import io.kubernetes.client.custom.IntOrString;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.*;
import io.kubernetes.client.util.Config;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

//import io.kubernetes.client.openapi.models.V1VirtualService;
//import io.kubernetes.client.openapi.models.NetworkingV1alpha3Api;

public class KubernetesDeployment {

    public static void main(String[] args) throws IOException, ApiException {
        // 创建 Kubernetes 客户端
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);

        // 创建 AppsV1Api 和 CoreV1Api 实例
        AppsV1Api appsV1Api = new AppsV1Api();
        CoreV1Api coreV1Api = new CoreV1Api();

        // 创建 Deployment，镜像更换为 Nginx
        V1Deployment deployment = new V1Deployment()
                .apiVersion("apps/v1")
                .kind("Deployment")
                .metadata(new V1ObjectMeta().name("nginx-deployment"))
                .spec(new V1DeploymentSpec()
                        .replicas(1)
                        .selector(new V1LabelSelector().matchLabels(Map.of("app", "nginx")))
                        .template(new V1PodTemplateSpec()
                                .metadata(new V1ObjectMeta().labels(Map.of("app", "nginx")))
                                .spec(new V1PodSpec()
                                        .containers(List.of(new V1Container()
                                                .name("nginx-container")
                                                .image("nginx:latest") // 使用 Nginx 镜像
                                                .ports(Collections.singletonList(new V1ContainerPort().containerPort(80)))))))); // 修改端口为 80

        // 部署 Deployment
        appsV1Api.createNamespacedDeployment("default", deployment, null, null, null, null);

        // 创建Service
        V1Service service = new V1Service()
                .apiVersion("v1")
                .kind("Service")
                .metadata(new V1ObjectMeta().name("nginx-service"))
                .spec(new V1ServiceSpec()
                        .type("NodePort") // 指定Service类型为NodePort
                        .ports(Arrays.asList(new V1ServicePort() // 定义端口映射
                                .port(80) // Service端口（集群内部访问使用）
                                .targetPort(new IntOrString(80)) // 指向Pod容器的端口
                                .nodePort(30880) // 可选：指定NodePort端口，如果不指定则由系统分配
                        ))
                        .selector(Map.of("app", "nginx")) // 通过标签选择器指定要暴露的Pod
                );
        coreV1Api.createNamespacedService("default", service, null, null, null, null);

//        NetworkingV1Api networkingV1Api = new NetworkingV1Api();
//        V1Ingress ingress = new V1Ingress()
//                .apiVersion("networking.k8s.io/v1")
//                .kind("Ingress")
//                .metadata(new V1ObjectMeta().name("my-ingress").namespace("default"))
//                .spec(new V1IngressSpec()
//                        .rules(List.of(new V1IngressRule()
//                                .host("internal.example.com") // 内网域名
//                                .http(new V1HTTPIngressRuleValue()
//                                        .paths(List.of(new V1HTTPIngressPath()
//                                                .path("/")
//                                                .pathType("Prefix")
//                                                .backend(new V1IngressBackend()
//                                                        .service(new V1IngressServiceBackend()
//                                                                .name("my-service")
//                                                                .port(new V1ServiceBackendPort().number(80))))))))));
//
//        networkingV1Api.createNamespacedIngress("default", ingress, null, null, null, null);
//        // 创建 Service
//        V1Service service = new V1Service()
//                .apiVersion("v1")
//                .kind("Service")
//                .metadata(new V1ObjectMeta().name("nginx-service"))
//                .spec(new V1ServiceSpec()
//                        .type("ClusterIP")
//                        .selector(Map.of("app", "nginx"))
//                        .ports(List.of(new V1ServicePort().port(80).targetPort(new IntOrString(80))))); // 端口为 80
//
//        // 部署 Service
//        coreV1Api.createNamespacedService("default", service, null, null, null, null);
//        // 创建 VirtualService（假设你在使用 Istio，这里是简单的示例）
//        V1VirtualService virtualService = new V1VirtualService()
//                .apiVersion("networking.istio.io/v1alpha3")
//                .kind("VirtualService")
//                .metadata(new V1ObjectMeta().name("my-app-virtualservice"))
//                .spec(new V1VirtualServiceSpec()
//                        .hosts(List.of("my-app.example.com"))
//                        .http(List.of(new HTTPRoute()
//                                .route(List.of(new Destination()
//                                        .host("my-app-service.default.svc.cluster.local")
//                                        .port(new PortSelector().number(8080)))))));
//
//        // 部署 VirtualService
//        NetworkingV1alpha3Api networkingApi = new NetworkingV1alpha3Api();
//        networkingApi.createNamespacedVirtualService("default", virtualService, null, null, null);

        System.out.println("Deployment, Service and VirtualService created.");
    }
}