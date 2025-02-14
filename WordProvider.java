import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class WordProvider {

    private static List<String> wordList = new ArrayList<>();

    static {
        try {
            InputStream inputStream = WordProvider.class.getClassLoader().getResourceAsStream("wordlist.txt");
            if (inputStream == null) {
                System.err.println("wordlist.txt not found in classpath!");
                return;
            }

            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNextLine()) {
                wordList.add(scanner.nextLine());
            }
            scanner.close();
        } catch (Exception e) {
            System.err.println("Error loading wordlist!");
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