import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String argv[]) {
        File file = new File("d:/oldpic");
        Map<String, File> hashcodeToFileCrossRef = new HashMap<String, File>();
        for (File photo : file.listFiles()) {
            recordPhotoWithoutDuplication(hashcodeToFileCrossRef, photo, generateHashCodeFromPhotoData(photo));
        }
        for (Map.Entry<String, File> element : hashcodeToFileCrossRef.entrySet()) {
            copyPicToNewLocation(element);
        }
    }

    private static void recordPhotoWithoutDuplication(Map<String, File> fileMap, File photo, String hex) {
        if (fileMap.containsKey(hex)) {
            System.out.println("duplicate");
            return;
        }
        insertIntoHashcodeToFileCrossRef(fileMap, photo, hex);
    }

    public static String generateHashCodeFromPhotoData(File file) {

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(Files.readAllBytes(file.toPath()));
            byte[] hash = messageDigest.digest();
            return DatatypeConverter.printHexBinary(hash).toUpperCase();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static void copyPicToNewLocation(Map.Entry<String, File> element) {
        System.out.println("key: " + element.getKey() + " filename: " + element.getValue().getName());
        try {
            File newFile = new File("d:/newpic/" + element.getValue().getName());
            Files.copy(element.getValue().toPath(),
                    newFile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("copy failed for file:" + element.getValue().getName());
        }
    }

    private static void insertIntoHashcodeToFileCrossRef(Map<String, File> fileMap, File photo, String hex) {
        fileMap.put(hex, photo);
        System.out.printf("hex=%s\n", hex);
    }

    /* *previous code that created an array first but above does not require it*
    String dirPath = "d:/pic";
    File dir = new File(dirPath);
    File[] files = dir.listFiles();
    if (files.length == 0) {
        System.out.println("The directory is empty");
    } else {
        for (File aFile : files) {
            System.out.println(aFile.getName() + " - " + aFile.length());
        }
    }
    */

}