package zipTest;

import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author lishichao
 * @version 1.0
 * @desc TODO
 * @date 2024/7/11 09:15
 */
public class ZipTest {
    public static void main(String[] args) throws Exception{
        FileOutputStream fos = new FileOutputStream("example.zip");
        ZipOutputStream zos = new ZipOutputStream(fos);

        // 添加第一个文件条目
        ZipEntry entry1 = new ZipEntry("document.txt");
        zos.putNextEntry(entry1);
        byte[] content1 = "This is a text file.".getBytes();
        zos.write(content1);
        zos.closeEntry();

        // 添加包含反斜杠的文件条目
        ZipEntry entry2 = new ZipEntry("folder1\uF05Cfile2.txt");

        zos.putNextEntry(entry2);
        byte[] content2 = "This is another file.".getBytes();
        zos.write(content2);
        zos.closeEntry();

        zos.close();
        fos.close();
    }
}
