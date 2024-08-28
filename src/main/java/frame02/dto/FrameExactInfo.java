package frame02.dto;

import lombok.Data;

/**
 * @author lishichao
 * @version 1.0
 * @desc TODO
 * @date 2024/6/4 10:44 上午
 */
@Data
public class FrameExactInfo {
    private String frameName;
    private byte[] frameBytes;
    private int frameNo;
    private long frameLocationSec;
    private String frameUrl;
}
