package frame01;

import java.io.InputStream;

/**
 * @author lishichao
 * @version 1.0
 * @desc TODO
 * @date 2024/6/4 8:28 下午
 */
public interface FrameExactCallbackStrategy {
    void doExactFrame(InputStream inputStream, HandleCallback callback);
}
