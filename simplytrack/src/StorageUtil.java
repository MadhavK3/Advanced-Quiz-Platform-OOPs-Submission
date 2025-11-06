import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

// to keep data centralized and consistent
// abstraction is used
public class StorageUtil {
//    base foldr constant name
    private static final String DIR = "data";

//    static block to perform setup logic before other static methods runs
    static {
//        creating data directory for saving exported files
        try { Files.createDirectories(Paths.get(DIR)); } catch (Exception e) { System.err.println("Storage dir error: "+e.getMessage()); }
    }

//    another static method for writing data into files
    public static void writeLines(String filename, List<String> lines) throws IOException {
        Path p = Paths.get(DIR, filename);
//        write files in UTF-8 encoding, overwrite contents by default for updation
        Files.write(p, lines, StandardCharsets.UTF_8);
    }

    public static List<String> readLines(String filename) {
        Path p = Paths.get(DIR, filename);
        try {
            if (!Files.exists(p)) return new ArrayList<>();
            return Files.readAllLines(p, java.nio.charset.StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
