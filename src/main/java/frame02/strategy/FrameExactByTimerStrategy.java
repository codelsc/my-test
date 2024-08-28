package frame02.strategy;

import frame02.HandleCallback;
import frame02.dto.FrameExactContext;
import frame02.dto.FrameExactInfo;

import java.io.InputStream;
import java.util.List;

/**
 * @author lishichao
 * @version 1.0
 * @desc TODO
 * @date 2024/6/4 10:50 上午
 */
public class FrameExactByTimerStrategy extends AbstractFrameExactStrategy {

    @Override
    public List<FrameExactInfo> doExactFrame(FrameExactContext exactContext) {
        // 实现帧提取的逻辑，按时间抽帧
        return null;
    }

    @Override
    public void doExactFrame(FrameExactContext exactContext, HandleCallback callback) {

    }
}