package frame02.template;

import frame02.FrameExactCallbackStrategy;
import frame02.HandleCallback;
import frame02.dto.FileDto;
import frame02.dto.FrameExactContext;
import frame02.dto.FrameExactInfo;
import frame02.strategy.FrameExactStrategy;

import java.io.InputStream;
import java.util.List;

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
        // 辅助策略执行帧抽取
        List<FrameExactInfo> frames = strategy.doExactFrame(exactContext);
        // 压缩及上传略
        return frames;
    }

    @Override
    public void exactFrames(FrameExactContext exactContext, HandleCallback callback, Integer framStep) {
        // 辅助策略执行帧抽取
        callbackStrategy.doExactFrame(exactContext, callback, framStep);
    }
}
