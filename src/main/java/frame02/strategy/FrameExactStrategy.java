package frame02.strategy;

import frame02.HandleCallback;
import frame02.dto.FrameExactContext;
import frame02.dto.FrameExactInfo;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author lishichao
 * @version 1.0
 * @desc TODO
 * @date 2024/6/4 10:46 上午
 */
public interface FrameExactStrategy {
    List<FrameExactInfo> doExactFrame(FrameExactContext exactContext);
    void doExactFrame(FrameExactContext exactContext, HandleCallback callback);
    CompletableFuture<List<FrameExactInfo>> doExactFrameAsync(FrameExactContext exactContext);
}
