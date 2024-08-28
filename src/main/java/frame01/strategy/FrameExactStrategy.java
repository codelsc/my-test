package frame01.strategy;

import frame01.dto.FrameExactInfo;

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
    List<FrameExactInfo> doExactFrame(InputStream inputStream);
    CompletableFuture<List<FrameExactInfo>> doExactFrameAsync(InputStream inputStream);
}
