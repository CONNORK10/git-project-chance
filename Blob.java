import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class Blob {
    String fileName, fileContents, hash;

    public Blob(String inputFile) throws IOException {
        fileName = inputFile;
        fileContents = getFileContents(inputFile);
        hash = hashContents();
        copyFileToObjects();
        writeToIndexFile();
    }

    // Efficiently read file contents using StringBuilder and correct newline character
    public String getFileContents(String inputFile) throws IOException {
        StringBuilder fileContents = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContents.append(line).append("\n");  // Append each line with newline character
            }
        }
        return fileContents.toString().trim();  // Trim to remove the last unnecessary newline
    }

    // Ensure the parent directory exists before creating the file
    public void copyFileToObjects() throws IOException {
        File file = new File("git" + File.separator + "objects" + File.separator + hash);
        file.getParentFile().mkdirs();  // Ensure that the directory exists
        try (Writer writer = new FileWriter(file)) {
            writer.write(fileContents);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //modified the method to apply to part one
    public void writeToIndexFile(String type, String path) throws IOException {
        File index = new File("index");
        try (FileWriter writer = new FileWriter(index, true)) {
            writer.write(type + " " + hash + " " + path + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // added StandardCharsets.UTF_8 to avoid UnsupportedEncodingException
    public String hashContents() {
        String sha1 = "";
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(fileContents.getBytes(StandardCharsets.UTF_8));
            sha1 = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sha1;
    }

    // Convert bytes to hexadecimal
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    public String getHash() {
        return hash;
    }
}