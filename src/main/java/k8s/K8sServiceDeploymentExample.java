package k8s;

import io.kubernetes.client.custom.IntOrString;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.apis.NetworkingV1Api;
import io.kubernetes.client.openapi.models.*;
import io.kubernetes.client.util.Config;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class K8sServiceDeploymentExample {
    public static void main(String[] args) throws Exception {
        // 1. 创建 Kubernetes 客户端
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);

        // 设置参数
        String appName = "lsc-service";
        String namespace = "default";
        String domainName = "ingress-test-l.csnd-dev.com"; // 这里是您想要设置的域名
        int servicePort = 80; // 服务端口
        int nodePort = 30303; // NodePort 端口，您可以选择
        String containerImage = "nginx:latest"; // 容器镜像

        // 2. 创建 Deployment
        AppsV1Api appsV1Api = new AppsV1Api();
        V1Deployment deployment = new V1Deployment()
                .apiVersion("apps/v1")
                .kind("Deployment")
                .metadata(new V1ObjectMeta().name(appName).namespace(namespace))
                .spec(new V1DeploymentSpec()
                        .replicas(1)
                        .selector(new V1LabelSelector().matchLabels(Map.of("app", appName)))
                        .template(new V1PodTemplateSpec()
                                .metadata(new V1ObjectMeta().labels(Map.of("app", appName)))
                                .spec(new V1PodSpec()
                                        .containers(List.of(new V1Container()
                                                                .name("my-container")
                                                                .image(containerImage)
                                                                .ports(List.of(new V1ContainerPort().containerPort(servicePort)))
//                                                .livenessProbe(new V1Probe()
//                                                        .exec(new io.kubernetes.client.openapi.models.V1ExecAction()
//                                                                .command(List.of("curl", "-f", "http://localhost/healthz")))
//                                                        .initialDelaySeconds(20)
//                                                        .periodSeconds(10))
//                                                .readinessProbe(new V1Probe()
//                                                        .exec(new io.kubernetes.client.openapi.models.V1ExecAction()
//                                                                .command(List.of("curl", "-f", "http://localhost/healthz")))
//                                                        .initialDelaySeconds(5)
//                                                        .periodSeconds(10))
                                                        )
                                                )
                                )
                ));

        /**
         * 在指定命名空间中创建一个新的 Kubernetes 服务（deployment）对象。
         *
         * @param namespace         要在哪个命名空间中创建服务的名称。命名空间用于隔离和组织 Kubernetes 资源。
         * @param body              要创建的服务的 deployment 对象，包含服务的所有配置，包括元数据（metadata）、规范（spec）等。
         * @param pretty            可选的参数，决定返回的对象是否采取易读的格式。如果为 true，返回的 JSON 输出将会是格式化的，方便调试和查看。
         *                           默认为 null，通常情况下可以忽略。
         * @param dryRun            用于指示操作是否只进行模拟而不实际创建资源。可以用“All”、“None”等值，或具体的验证行为。
         *                           默认为 null，这意味着将实际执行创建操作。
         * @param fieldManager      用于标识对资源字段进行修改的客户端名称。该参数有助于 Kubernetes 区分字段更改的来源，并在多个客户端环境中管理字段。
         *                           例子: "clientA"。
         * @param fieldValidation   用于指定字段验证的级别。可以是 "Strict"（严格验证）、"Warn"（警告模式），或 "Ignore"（忽略验证）。
         *                           这会影响请求的合法性检查，默认为 null，表示使用 API 的默认验证策略。
         *
         * @return V1Service       返回创建的 V1Service 对象，包含服务器端记录的详细信息，如分配的名称、状态等。
         * @throws ApiException    如果在 API 请求过程中发生错误，例如权限问题、请求格式不正确等，将抛出 ApiException。
         */
        appsV1Api.createNamespacedDeployment(namespace, deployment, null, null, null, null);

        // 3. 创建 NodePort Service
        CoreV1Api coreV1Api = new CoreV1Api();
        V1Service service = new V1Service()
                .apiVersion("v1")
                .kind("Service")
                .metadata(new V1ObjectMeta().name(appName).namespace(namespace))
                .spec(new V1ServiceSpec()
//                        .type("LoadBalancer")
                        .type("NodePort") // 使用 NodePort 类型
                        .selector(Map.of("app", appName))
                        .ports(List.of(new V1ServicePort()
                                .port(servicePort)
                                .targetPort(new IntOrString(servicePort))
//                                .nodePort(nodePort)
                        )));

        /**
         * 在指定命名空间中创建一个新的 Kubernetes 服务（Service）对象。
         *
         * @param namespace         要在哪个命名空间中创建服务的名称。命名空间用于隔离和组织 Kubernetes 资源。
         * @param body              要创建的服务的 V1Service 对象，包含服务的所有配置，包括元数据（metadata）、规范（spec）等。
         * @param pretty            可选的参数，决定返回的对象是否采取易读的格式。如果为 true，返回的 JSON 输出将会是格式化的，方便调试和查看。
         *                           默认为 null，通常情况下可以忽略。
         * @param dryRun            用于指示操作是否只进行模拟而不实际创建资源。可以用“All”、“None”等值，或具体的验证行为。
         *                           默认为 null，这意味着将实际执行创建操作。
         * @param fieldManager      用于标识对资源字段进行修改的客户端名称。该参数有助于 Kubernetes 区分字段更改的来源，并在多个客户端环境中管理字段。
         *                           例子: "clientA"。
         * @param fieldValidation   用于指定字段验证的级别。可以是 "Strict"（严格验证）、"Warn"（警告模式），或 "Ignore"（忽略验证）。
         *                           这会影响请求的合法性检查，默认为 null，表示使用 API 的默认验证策略。
         *
         * @return V1Service       返回创建的 V1Service 对象，包含服务器端记录的详细信息，如分配的名称、状态等。
         * @throws ApiException    如果在 API 请求过程中发生错误，例如权限问题、请求格式不正确等，将抛出 ApiException。
         */
        coreV1Api.createNamespacedService(namespace, service, null, null, null, null);

        // 自动分配的 nodePort
        V1Service getService = coreV1Api.readNamespacedService(appName, namespace, null);
        Integer autoAllocatedNodePort = getService.getSpec().getPorts().get(0).getNodePort();
        System.out.println("Auto-allocated NodePort: " + autoAllocatedNodePort);
        // 4. 创建 Ingress
//        NetworkingV1Api networkingV1Api = new NetworkingV1Api();
//        V1Ingress ingress = new V1Ingress()
//                .apiVersion("networking.k8s.io/v1")
//                .kind("Ingress")
//                .metadata(new V1ObjectMeta().name(appName + "-ingress").namespace(namespace))
//                .spec(new V1IngressSpec()
//                        .rules(List.of(new V1IngressRule()
//                                .host(domainName) // 设置指定的域名
//                                .http(new V1HTTPIngressRuleValue()
//                                        .paths(List.of(new V1HTTPIngressPath()
//                                                .path("/")
//                                                .pathType("Prefix")
//                                                .backend(new V1IngressBackend()
//                                                        .service(new V1IngressServiceBackend()
//                                                                .name(appName)
//                                                                .port(new V1ServiceBackendPort().number(servicePort))))))))));
//
//        networkingV1Api.createNamespacedIngress(namespace, ingress, null, null, null, null);
//
//        System.out.println("Deployment, Service, and Ingress created successfully!");
    }
}
/**
 * 在 Kubernetes 中，服务的成功部署通常涉及多个资源，包括 Deployment、Service 和 Ingress。要确保这些资源都处于工作状态，您可以检查以下几个关键状态：
 *
 * Deployment 状态:
 *
 * 您的 Deployment 是否成功部署了所需数量的 Pod。
 * 检查 Pod 的状态是否是 Running，这意味着容器已经启动并在正常运行。
 * 您可以通过以下步骤验证 Deployment 的状态：
 *
 * AppsV1Api appsV1Api = new AppsV1Api();
 * V1DeploymentStatus deploymentStatus = appsV1Api.readNamespacedDeploymentStatus(appName, namespace, null).getStatus();
 * int availableReplicas = deploymentStatus.getAvailableReplicas() != null ? deploymentStatus.getAvailableReplicas() : 0;
 * int desiredReplicas = deploymentStatus.getReplicas();
 * if (availableReplicas == desiredReplicas) {
 *     System.out.println("Deployment is ready.");
 * } else {
 *     System.out.println("Deployment is not ready yet.");
 * }
 * Service 状态:
 *
 * Kubernetes Service 不需要明确的状态检查，因为它本质上是一个入口点，可以访问后台的 Pod。您可以确认 Service 是否创建成功并且能够连接。
 * 验证 Service 是否存在：
 *
 * CoreV1Api coreV1Api = new CoreV1Api();
 * V1Service service = coreV1Api.readNamespacedService(appName, namespace, null);
 * if (service != null) {
 *     System.out.println("Service is created successfully.");
 * } else {
 *     System.out.println("Service creation failed.");
 * }
 * Ingress 状态:
 *
 * 检查 Ingress 是否有效，如果其后端 Pod 所在的 Service 可正确访问。
 * 验证 Ingress 是否创建成功：
 *
 * NetworkingV1Api networkingV1Api = new NetworkingV1Api();
 * V1Ingress ingress = networkingV1Api.readNamespacedIngress(appName + "-ingress", namespace, null);
 * if (ingress != null) {
 *     System.out.println("Ingress is created successfully.");
 * } else {
 *     System.out.println("Ingress creation failed.");
 * }
 * 结合这些状态检查后，您可以说您的服务已成功部署。如果都通过了上述检查，您的 Deployment、Service 和 Ingress 都应该是有效的且可以正常工作。您可以将这些检查逻辑组合在一起，形成一个完整的检查周期，确保每个组件都已经就绪。
 */
