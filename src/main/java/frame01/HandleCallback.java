package frame01;

import frame01.dto.FrameExactInfo;

/**
 * @author lishichao
 * @version 1.0
 * @desc TODO
 * @date 2024/6/4 8:26 下午
 */
public interface HandleCallback {
    void onFrameExtracted(FrameExactInfo frameInfo);
}
