package frame01.template;

import frame01.HandleCallback;
import frame01.dto.FileDto;
import frame01.dto.FrameExactContext;
import frame01.dto.FrameExactInfo;

import java.io.InputStream;
import java.util.List;

/**
 * @author lishichao
 * @version 1.0
 * @desc TODO
 * @date 2024/6/4 10:53 上午
 */
public interface FrameExactTemplate {
    List<FrameExactInfo> exactFrames(FrameExactContext exactContext);

    void exactFrames(FrameExactContext exactContext, HandleCallback callback);

    InputStream getVideoStream(FileDto videoFileDto);
}
