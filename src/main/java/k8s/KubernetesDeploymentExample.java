package k8s;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.models.*;
import io.kubernetes.client.util.Config;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;

public class KubernetesDeploymentExample {
    public static void main(String[] args) {
        try {
            // 加载自定义的 kubeconfig 文件
//            InputStream kubeConfigStream = KubeDeploymentExample.class.getClassLoader().getResourceAsStream("config");
//
//            if (kubeConfigStream == null) {
//                throw new RuntimeException("Kubeconfig file not found!");
//            }
//            ApiClient client = Config.fromConfig(kubeConfigStream);

            // 创建Kubernetes API客户端
//            ApiClient client = Config.defaultClient();
            ApiClient client = Config.fromConfig("./kubeconfig");
            Configuration.setDefaultApiClient(client);

            // 创建Deployment的元数据
            V1ObjectMeta metadata = new V1ObjectMeta();
            metadata.setName("example-deployment");
            metadata.setNamespace("default");

            // 创建容器
            V1Container container = new V1Container();
            container.setName("example-container");
            container.setImage("nginx:1.14.2"); // 设置容器镜像
            container.setPorts(Collections.singletonList(new V1ContainerPort().containerPort(80))); // 设置容器端口

            // 创建一个标签集合
            HashMap<String, String> labels = new HashMap<>();
            labels.put("app", "example-app");

            // 创建Pod模板
            V1PodTemplateSpec template = new V1PodTemplateSpec();
            // 这里使用setLabels方法设置标签
            V1ObjectMeta v1ObjectMeta = new V1ObjectMeta();
            v1ObjectMeta.setLabels(labels);
            template.setMetadata(v1ObjectMeta);

            // 设置Pod的spec，包括容器
            V1PodSpec podSpec = new V1PodSpec();
            podSpec.setContainers(Collections.singletonList(container));
            template.setSpec(podSpec);

            // 创建Deployment的规格
            V1DeploymentSpec spec = new V1DeploymentSpec();
            spec.setReplicas(3);
            V1LabelSelector v1LabelSelector = new V1LabelSelector();
            v1LabelSelector.setMatchLabels(labels);
            spec.setSelector(v1LabelSelector);
            spec.setTemplate(template);

            // 创建Deployment对象
            V1Deployment deployment = new V1Deployment();
            deployment.setMetadata(metadata);
            deployment.setSpec(spec);

            // 创建AppsV1Api实例
            AppsV1Api apiInstance = new AppsV1Api();

            // 创建Deployment
            apiInstance.createNamespacedDeployment(
                    "default", // 命名空间
                    deployment, // Deployment对象
                    null, // pretty
                    null, // exact
                    null,  // dryRun
                    null
            );
            System.out.println("Deployment created successfully!");

        } catch (IOException | ApiException e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
    }
}
