package csvtest;

import java.io.*;

public class CSVFileWriter {

    public static void main(String[] args) {
        String content1 = "filename,class,x1,y1,x2,y2,x3,y3.....xn,yn" +"\n";
        String content2 = "1.jpg,car,1,2,3,4,4,5\n";
        String content3 = "2.jpg,person,5,3,53,4,34, , , , \n";
        byte[] csvByteArray = new byte[0];
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            outputStream.write(content1.getBytes());
            outputStream.write(content2.getBytes());
            outputStream.write(content3.getBytes());

            csvByteArray = outputStream.toByteArray();
            System.out.println("CSV content converted to byte array successfully.");

            // 使用 csvByteArray 进行后续操作
            try (FileOutputStream stream = new FileOutputStream("output2.csv")) {
                stream.write(csvByteArray);
                System.out.println("Byte array written to file successfully.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while converting the CSV content to byte array.");
            e.printStackTrace();
        }
    }
}