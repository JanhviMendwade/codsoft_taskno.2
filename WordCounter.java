import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

public class WordCounter {
    private static final Set<String> STOP_WORDS = new HashSet<>();
    static {
        STOP_WORDS.add("the");
        STOP_WORDS.add("is");
        STOP_WORDS.add("in");
        STOP_WORDS.add("at");
        STOP_WORDS.add("of");
        STOP_WORDS.add("on");
        STOP_WORDS.add("and");
        STOP_WORDS.add("a");
        STOP_WORDS.add("to");
        // Add more stop words as needed
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String text = "";
        boolean fromFile = false;

        System.out.println("Do you want to enter text manually or provide a file? (enter/file): ");
        String inputMethod = scanner.nextLine().trim().toLowerCase();

        if ("file".equals(inputMethod)) {
            System.out.println("Please provide the file path: ");
            String filePath = scanner.nextLine().trim();
            try {
                text = readFile(filePath);
                fromFile = true;
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + filePath);
                return;
            }
        } else {
            System.out.println("Please enter your text: ");
            text = scanner.nextLine().trim();
        }

        if (text.isEmpty()) {
            System.out.println("No text provided.");
            return;
        }

        String[] words = splitIntoWords(text);
        Map<String, Integer> wordCount = countWords(words);

        int totalWords = words.length;
        int uniqueWords = wordCount.size();

        System.out.println("Total word count: " + totalWords);
        System.out.println("Unique word count: " + uniqueWords);

        if (fromFile) {
            System.out.println("Words and their frequencies:");
            for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }

        scanner.close();
    }

    private static String readFile(String filePath) throws FileNotFoundException {
        StringBuilder text = new StringBuilder();
        Scanner fileScanner = new Scanner(new File(filePath));
        while (fileScanner.hasNextLine()) {
            text.append(fileScanner.nextLine()).append("\n");
        }
        fileScanner.close();
        return text.toString();
    }

    private static String[] splitIntoWords(String text) {
        StringTokenizer tokenizer = new StringTokenizer(text, " \t\n\r\f,.:;?![]'");
        String[] words = new String[tokenizer.countTokens()];
        int index = 0;
        while (tokenizer.hasMoreTokens()) {
            words[index++] = tokenizer.nextToken().toLowerCase();
        }
        return words;
    }

    private static Map<String, Integer> countWords(String[] words) {
        Map<String, Integer> wordCount = new HashMap<>();
        for (String word : words) {
            if (!STOP_WORDS.contains(word)) {
                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            }
        }
        return wordCount;
    }
}
