package frame02;

import frame02.dto.FileDto;
import frame02.dto.FrameExactContext;
import frame02.dto.FrameExactInfo;
import frame02.template.FrameExactTemplate;
import frame02.template.FrameExactTemplateImpl;

import java.util.Arrays;
import java.util.List;

/**
 * @author lishichao
 * @version 1.0
 * @desc TODO
 * @date 2024/6/4 10:55 上午
 */
public class Test02 {
    public static void main(String[] args) throws Exception {
        Long taskId = 1000L;
        Thread t = new Thread(() -> {
            frameJob(taskId);
        });
        t.setName("frameJob-" + taskId);
        t.start();
    }

    public static void frameJob(Long taskId) {
        try {
            // 修改状态为【进行中】
            List<String> videoPathList = Arrays.asList("");
            videoPathList.forEach(videoPath -> {
                // ThreadLocal.set()
                FileDto fileDto = new FileDto();
                fileDto.setFileUrl(videoPath);
                FrameExactContext context = new FrameExactContext();
                context.setVideoFileDto(fileDto);

                FrameExactCallbackStrategy strategy = null;
                MyCallback callback = new MyCallback();
                FrameExactTemplate template = new FrameExactTemplateImpl(strategy);
                int framStep = 10;
                template.exactFrames(context, callback, framStep);
            });

            // 修改状态为【已完成】
        } catch (Exception e){
            // 修改状态为【失败】
        }
    }

    static class MyCallback implements HandleCallback {

        @Override
        public void handle(List<FrameExactInfo> frameInfo) {

            // ThreadLocal.get()

            // 上传minio

            // 保存mysql

        }
    }

}
