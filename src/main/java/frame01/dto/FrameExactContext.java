package frame01.dto;

import frame01.emums.FrameExactType;
import lombok.Data;

/**
 * @author lishichao
 * @version 1.0
 * @desc TODO
 * @date 2024/6/4 10:43 上午
 */
@Data
public class FrameExactContext {
    private FileDto videoFileDto;
    private FrameExactType frameExactType;
    private double frameCompressRate;
}
