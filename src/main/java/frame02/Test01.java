package frame02;

import frame02.dto.FileDto;
import frame02.dto.FrameExactContext;
import frame02.dto.FrameExactInfo;
import frame02.emums.FrameExactType;
import frame02.strategy.FrameExactByTimerStrategy;
import frame02.strategy.FrameExactStrategy;
import frame02.template.FrameExactTemplate;
import frame02.template.FrameExactTemplateImpl;

import java.io.IOException;
import java.util.List;

/**
 * @author lishichao
 * @version 1.0
 * @desc TODO
 * @date 2024/6/4 10:55 上午
 */
public class Test01 {
    public static void main(String[] args) throws IOException {
        FileDto fileDto = new FileDto();
        // 设置fileDto属性

        FrameExactContext context = new FrameExactContext();
        context.setVideoFileDto(fileDto);
//        context.setFrameExactType(FrameExactType.BY_TIMER);
//        context.setFrameCompressRate(0.5);

        FrameExactStrategy strategy = new FrameExactByTimerStrategy();
        FrameExactTemplate template = new FrameExactTemplateImpl(strategy);

        List<FrameExactInfo> frameInfos = template.exactFrames(context);

        // 处理frameInfos，略
    }

}
