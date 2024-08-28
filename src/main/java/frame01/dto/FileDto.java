package frame01.dto;

import frame01.emums.FileSource;
import frame01.emums.FileType;
import lombok.Data;

/**
 * @author lishichao
 * @version 1.0
 * @desc TODO
 * @date 2024/6/4 10:40 上午
 */
@Data
public class FileDto {
    private String fileName;
    private FileSource fileSource;
    private FileType fileType;
    private String fileUrl;
}
