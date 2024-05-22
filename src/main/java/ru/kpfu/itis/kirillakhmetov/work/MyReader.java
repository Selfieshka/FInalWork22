package ru.kpfu.itis.kirillakhmetov.work;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MyReader {
    public static FileInfo readFromFile(File file) throws IOException {
        FileInfo fileInfo = null;

        try (FileInputStream fis = new FileInputStream(file);
             DataInputStream dis = new DataInputStream(fis)) {

            int k = dis.readInt();

            byte[] bytes = new byte[k];
            for (int i = 0; i < k; i++) {
                bytes[i] = (byte) dis.read();
            }
            String s = new String(bytes, StandardCharsets.UTF_8);

            int d = dis.readInt();
            int p = dis.readInt();

            fileInfo = new FileInfo(k, s, d, p);

            if (s.length() != d) {
                System.out.println("Количество символов не совпадает с контрольным числом");
            }
        }

        return fileInfo;
    }
}
