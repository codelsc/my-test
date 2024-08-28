package frame02;

import frame02.dto.FrameExactInfo;

import java.util.List;

/**
 * @author lishichao
 * @version 1.0
 * @desc TODO
 * @date 2024/6/4 8:26 下午
 */
public interface HandleCallback {
    void handle(List<FrameExactInfo> frameInfoList);
}
