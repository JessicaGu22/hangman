import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class WordProvider {

    private static List<String> wordList = new ArrayList<>();

    static {
        try {
            File file = new File("wordlist.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                wordList.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("wordlist.txt not found!");
            e.printStackTrace();
        }
    }

    public static String getWord() {
        if (wordList.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return wordList.get(random.nextInt(wordList.size()));
    }
}