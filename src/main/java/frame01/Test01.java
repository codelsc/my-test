package frame01;

import frame01.dto.FileDto;
import frame01.dto.FrameExactContext;
import frame01.dto.FrameExactInfo;
import frame01.emums.FrameExactType;
import frame01.strategy.FrameExactByTimerStrategy;
import frame01.strategy.FrameExactStrategy;
import frame01.template.FrameExactTemplate;
import frame01.template.FrameExactTemplateImpl;

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
        context.setFrameExactType(FrameExactType.BY_TIMER);
        context.setFrameCompressRate(0.5);

        FrameExactStrategy strategy = new FrameExactByTimerStrategy();
        FrameExactTemplate template = new FrameExactTemplateImpl(strategy);

        List<FrameExactInfo> frameInfos = template.exactFrames(context);

        // 处理frameInfos，略
    }

}
