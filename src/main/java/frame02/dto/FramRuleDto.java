package frame02.dto;

import frame02.emums.FrameExactType;
import lombok.Data;

/**
 * @author lishichao
 * @version 1.0
 * @desc TODO
 * @date 2024/6/5 4:23 下午
 */
@Data
public class FramRuleDto {
    FrameExactType frameExactType;
    // 抽帧频率
    Long framRate;
}
