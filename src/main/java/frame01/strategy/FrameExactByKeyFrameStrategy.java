package frame01.strategy;

import frame01.dto.FrameExactInfo;

import java.io.InputStream;
import java.util.List;

/**
 * @author lishichao
 * @version 1.0
 * @desc TODO
 * @date 2024/6/4 10:51 上午
 */
public class FrameExactByKeyFrameStrategy extends AbstractFrameExactStrategy {

    @Override
    public List<FrameExactInfo> doExactFrame(InputStream inputStream) {
        // 实现帧提取的逻辑，按关键帧提取
        return null;
    }
}
