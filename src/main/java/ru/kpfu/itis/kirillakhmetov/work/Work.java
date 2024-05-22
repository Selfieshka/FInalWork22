package ru.kpfu.itis.kirillakhmetov.work;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Work implements Runnable {
    private final File file;
    private final PrintWriter pw;
    private final List<FileInfo> filesInfo;

    public Work(File file, PrintWriter pw, List<FileInfo> filesInfo) {
        this.file = file;
        this.pw = pw;
        this.filesInfo = filesInfo;
    }

    @Override
    public void run() {
        FileInfo fileContent = null;
        try {
            fileContent = MyReader.readFromFile(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        synchronized (pw) {
            pw.print("Прочитали файл :" + file.getName() + " " +
                    "количество байт данных: " + fileContent.k + " " +
                    "количество считанных символов: " + fileContent.s.length() + " " +
                    "контрольное число: " + fileContent.d + " " +
                    "номер части: " + fileContent.p + "\n");
        }

        synchronized (filesInfo) {
            filesInfo.add(fileContent);
        }
    }
}
