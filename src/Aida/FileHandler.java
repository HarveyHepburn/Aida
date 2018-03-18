package Aida;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileHandler {
    static String folder = "logs";
    static SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    FileHandler() {
        File logs = new File(Paths.get(folder).toString());
        if (!logs.exists()) {
            logs.mkdir();
        }
    }

    boolean saveFile(String content, String title) {
        try {
            String time = timeFormat.format(new Date());
            String t = Encrypter.encrypt(title);
            return save(time + t, content);
        } catch (Exception e) {
            return false;
        }
    }

    boolean saveEdit(String content, int id) {
        return save(list.get(id), content);
    }

    boolean saveEdit(String content, int id, String title) {
        try {
            String nName = list.get(id).substring(0, 14) + Encrypter.encrypt(title);
            nName = nName.replace('/', '_');
            File file = new File(Paths.get(folder + "/" + list.get(id)).toString());
            File file2 = new File(Paths.get(folder + "/" + nName).toString());
            if (!file2.exists())
                file.renameTo(file2);
            return save(nName, content);
        } catch (Exception e) {
            return false;
        }
    }

    boolean save(String fileName, String content) {
        try {
            fileName = fileName.replace('/', '_');
            FileWriter writer = new FileWriter(Paths.get(folder + "/" + fileName).toString());
            PrintWriter printWriter = new PrintWriter(writer);
            printWriter.print(Encrypter.encrypt(content));
            printWriter.flush();
            printWriter.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    List<String> list;

    List<String> readChapterList() {
        List<String> chapterList = new ArrayList<>();
        list = new ArrayList<>();
        try {
            Files.walk(Paths.get(folder + "/"))
                    .filter(Files::isRegularFile)
                    .forEach(file -> {
                        try {
                            if(file.getFileName().toString().substring(0,12).chars().allMatch(Character::isDigit)){
                                list.add(file.getFileName().toString());
                                String name = file.getFileName().toString();
                                name = name.substring(14, name.length()).replace('_', '/');
                                chapterList.add(Encrypter.decrypt(name));
                            }
                        } catch (Exception e) {
                        }
                    });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return chapterList;
    }

    String readChapter(int id) {
        String content;
        try {
            content = Encrypter.decrypt(new String(Files.readAllBytes(Paths.get(folder + "/" + list.get(id)))));
        } catch (Exception e) {
            content = "";
        }
        return content;
    }
}
