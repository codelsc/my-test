package frame01.template;

import frame01.FrameExactCallbackStrategy;
import frame01.HandleCallback;
import frame01.dto.FileDto;
import frame01.dto.FrameExactContext;
import frame01.dto.FrameExactInfo;
import frame01.strategy.FrameExactStrategy;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author lishichao
 * @version 1.0
 * @desc TODO
 * @date 2024/6/4 10:54 上午
 */
public class FrameExactTemplateImpl implements FrameExactTemplate {
//    @Autowired
//    private Map<String, FrameExactStrategy> frameExactStrategyMap;
    private FrameExactStrategy strategy;


    public FrameExactTemplateImpl(FrameExactStrategy strategy) {
        this.strategy = strategy;
    }

    private FrameExactCallbackStrategy callbackStrategy;

    public FrameExactTemplateImpl(FrameExactCallbackStrategy callbackStrategy) {
        this.callbackStrategy = callbackStrategy;
    }

    @Override
    public List<FrameExactInfo> exactFrames(FrameExactContext exactContext) {
        InputStream videoStream = getVideoStream(exactContext.getVideoFileDto());
        // 辅助策略执行帧抽取
        List<FrameExactInfo> frames = strategy.doExactFrame(videoStream);
        // 压缩及上传略
        return frames;
    }

    @Override
    public void exactFrames(FrameExactContext exactContext, HandleCallback callback) {
        InputStream videoStream = getVideoStream(exactContext.getVideoFileDto());
        // 辅助策略执行帧抽取
        callbackStrategy.doExactFrame(videoStream, callback);
    }

    @Override
    public InputStream getVideoStream(FileDto videoFileDto) {
        // 获取视频流
        return null;
    }
}
