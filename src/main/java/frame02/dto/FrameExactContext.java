package frame02.dto;

import frame02.emums.FrameExactType;
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
    private FramRuleDto framRuleDto;
    private FrameCompressDto frameCompressDto;
}
