import java.io.IOException;

public class GitTester {
    public static void main(String[] args) throws IOException {
        Git.initRepo();
        Git.initRepo(); // Should output "Git Repository already exists"
        Git.deleteRepo(); // Should output "Git Repository deleted"

        //String fileName = "inputText.txt";
        String file = "practice.txt";
        Blob b = new Blob (file);
        //Blob blob = new Blob(fileName);
    }
}