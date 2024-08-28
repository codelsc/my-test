package k8s;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.Watch;

public class PodWatcher {

    public static void main(String[] args) throws Exception{
        ApiClient client = Config.defaultClient();
        //GO
//        Watch<V1Pod> watch = Watch.createWatch(
//             client,
//             new CoreV1Api(),
//             null, // 这里可以指定namespace，null表示所有namespace
//             null, // labelSelector, 过滤特定标签的Pods
//             true, // watch是否应该返回资源列表，然后仅监听后续变化
//             new Watcher<V1Pod>() {
//                 @Override
//                 public void eventReceived(Action action, V1Pod resource) {
//                     System.out.printf("%s: %s\n", action, resource.getMetadata().getName());
//                 }
//
//                 @Override
//                 public void onClose(Throwable t) {
//                     // 处理关闭事件
//                 }
//             });
            // Watch是阻塞的，直到关闭
            // 这里可以添加额外的代码，但通常不需要
    }
}
