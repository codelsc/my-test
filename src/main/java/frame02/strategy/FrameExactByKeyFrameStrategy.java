package frame02.strategy;

import frame02.FrameExactCallbackStrategy;
import frame02.HandleCallback;
import frame02.dto.FrameExactContext;
import frame02.dto.FrameExactInfo;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * @author lishichao
 * @version 1.0
 * @desc TODO
 * @date 2024/6/4 10:51 上午
 */
public class FrameExactByKeyFrameStrategy implements FrameExactCallbackStrategy {

    @Override
    public void doExactFrame(FrameExactContext exactContext, HandleCallback callback, Integer framStep) {
        callback.handle(Arrays.asList());
    }
}
