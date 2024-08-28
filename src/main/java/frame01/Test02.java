package frame01;

import frame01.dto.FileDto;
import frame01.dto.FrameExactContext;
import frame01.dto.FrameExactInfo;
import frame01.emums.FrameExactType;
import frame01.strategy.FrameExactByTimerStrategy;
import frame01.strategy.FrameExactStrategy;
import frame01.template.FrameExactTemplate;
import frame01.template.FrameExactTemplateImpl;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
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
//                ThreadLocal.set()
                FileDto fileDto = new FileDto();
                fileDto.setFileUrl(videoPath);
                FrameExactContext context = new FrameExactContext();
                context.setVideoFileDto(fileDto);

                FrameExactCallbackStrategy strategy = null;
                MyCallback callback = new MyCallback();
                FrameExactTemplate template = new FrameExactTemplateImpl(strategy);
                template.exactFrames(context, callback);
            });

            // 修改状态为【已完成】
        } catch (Exception e){
            // 修改状态为【失败】
        }
    }

    static class MyCallback implements HandleCallback{

        @Override
        public void onFrameExtracted(FrameExactInfo frameInfo) {

            // ThreadLocal.get()

            // 上传minio

            // 保存mysql

        }
    }

}
