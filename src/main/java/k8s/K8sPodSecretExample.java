package k8s;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.*;
import io.kubernetes.client.util.Config;

import java.io.IOException;
import java.util.Base64;
import java.util.Collections;

public class K8sPodSecretExample {

    public static void main(String[] args) throws IOException {
        // 1. 设置 Kubernetes 客户端
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        CoreV1Api coreV1Api = new CoreV1Api();

        String imagePullSecret = "myregistrykey";
        String namespace = "default";
        String username = "admin";
        String password = "xwsk123456";
        String registry = "docker-host.csnd.com";

        // 创建 Secret 对象
        V1Secret secret = new V1Secret();
        secret.setApiVersion("v1");
        secret.setKind("Secret");
        secret.setMetadata(new V1ObjectMeta().name(imagePullSecret).namespace(namespace));
        secret.setType("kubernetes.io/dockerconfigjson");

        // 填充 Docker Config JSON
        String auth = encodeAuth(username, password);
        String dockerConfigJson = "{ \"auths\": { \"" + registry + "\": { \"username\": \"" + username + "\", \"password\": \"" + password + "\", \"email\": \"your-email@example.com\", \"auth\": \"" + auth + "\" } } }";
//        byte[] encodedDockerConfigJson = Base64.getEncoder().encode(dockerConfigJson.getBytes());

        secret.setData(Collections.singletonMap(".dockerconfigjson", dockerConfigJson.getBytes()));

        // 创建 Secret
        try {
            coreV1Api.createNamespacedSecret(namespace, secret, null, null, null, null);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }

        // 4. 创建 Pod
        V1Pod pod = new V1Pod();
        pod.setApiVersion("v1");
        pod.setKind("Pod");
        pod.setMetadata(new V1ObjectMeta().name("example-pod").namespace(namespace));

        V1Container container = new V1Container();
        container.setName("example-container");
        container.setImage("docker-host.csnd.com/nginx:1.24.0");  // 替换为你的镜像地址

        // 设置容器
        pod.setSpec(new V1PodSpec().containers(Collections.singletonList(container))
                .imagePullSecrets(Collections.singletonList(new V1LocalObjectReference().name(imagePullSecret))));

        // 5. 创建 Pod
        try {
            coreV1Api.createNamespacedPod(namespace, pod, null, null, null, null);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    private static String encodeAuth(String username, String password) {
        String auth = username + ":" + password;
        return Base64.getEncoder().encodeToString(auth.getBytes());
    }
}