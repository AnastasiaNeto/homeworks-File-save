import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
       /* 1. Создать три экземпляра класса GameProgress.
          2.Сохранить сериализованные объекты GameProgress в папку savegames из предыдущей задачи.
          3. Созданные файлы сохранений из папки savegames запаковать в архив zip.
          4. Удалить файлы сохранений, лежащие вне архива.
        */

        List<File> fileList = Arrays.asList(
                new File("D://Games//savegames//save0.dat"),
                new File("D://Games//savegames//save1.dat"),
                new File("D://Games//savegames//save2.dat")
        );
        GameProgress[] newList = {
                new GameProgress(300, 95, 20, 203),
                new GameProgress(550, 54, 11, 516),
                new GameProgress(480, 83, 14, 544)
        };
        saveGame(newList);
        zipFiles(newList);
        for (File x : fileList) {
            deleteFile(x);
        }
    }

    public static void saveGame(GameProgress[] newList) {
        for (int i = 0; i < newList.length; i++) {
            try (FileOutputStream fos = new FileOutputStream("D://Games//savegames//save" + i + ".dat");
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(newList[i]);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    public static void zipFiles(GameProgress[] newList) {
        try (ZipOutputStream zout = new ZipOutputStream(
                new FileOutputStream("D://Games//savegames//zip.zip"))) {
            for (int i = 0; i < newList.length; i++) {
                FileInputStream fis = new FileInputStream("D://Games//savegames//save" + i + ".dat");
                ZipEntry entry = new ZipEntry("save" + i + ".dat");
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public static void deleteFile(File file) {
        if (file.delete()) {
            System.out.println("Файл " + file.getName() + " удален");
        } else {
            System.out.println("Файл " + file.getName() + " не удален");
        }
    }
}
