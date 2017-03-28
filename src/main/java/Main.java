import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.io.*;


public class Main
{

    public static void main(String argv[])
    {


        //moved instantiating this object and converting it to hashmap out of traverse method
        File file = new File("/Users/simonread/pictures/oldpic");
        Map<String, File> hashcodeToFileCrossRef = new HashMap<String, File>();

        file.

        //look at implementing more try/catch and throws

        //all of these methods are static because 1.main is and 2.do not require object to be instantiated

    }

    private static void traverse (File file,Map<String,File> collection)
            {

        //what about duplicates (I think I have covered types below)?
        Iterator it = FileUtils.iterateFiles(file, new String[] {"jpg"} ,true);
            //while (it.hasNext())
                //{
                    //if (file.isDirectory())
                    //{
                    //traverse(file, collection);
                    //}
                    //else
                        {
                        for (File photo : file.listFiles())
                            {
                            recordPhotoWithoutDuplication(collection, photo, generateHashCodeFromPhotoData(photo));
                            }
                        for (Map.Entry<String, File> element : collection.entrySet())
                            {
                            copyPicToNewLocation(element);
                            }
                        }
                }
            //}


    private static void recordPhotoWithoutDuplication(Map<String, File> fileMap, File photo, String hex)
    {
        if (fileMap.containsKey(hex))
        {
            System.out.println("duplicate");
            return;
        }
        insertIntoHashcodeToFileCrossRef(fileMap, photo, hex);
    }

    public static String generateHashCodeFromPhotoData(File file)
    {

        try {
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                messageDigest.update(Files.readAllBytes(file.toPath()));
                byte[] hash = messageDigest.digest();
                return DatatypeConverter.printHexBinary(hash).toUpperCase();
            }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    return null;
                }
    }

    private static void copyPicToNewLocation(Map.Entry<String, File> element) {
        System.out.println("key: " + element.getKey() + " filename: " + element.getValue().getName());
        try {
            File newFile = new File("/Users/simonread/pictures/newpic/" + element.getValue().getName());
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

    //below is for persistence of directory conetnts as an array

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