package frame02.dto;

import frame02.emums.FrameCompressType;
import lombok.Data;

/**
 * @author lishichao
 * @version 1.0
 * @desc TODO
 * @date 2024/6/5 4:30 下午
 */
@Data
public class FrameCompressDto {
    FrameCompressType frameCompressType;
    Double frameCompressRate;
}
