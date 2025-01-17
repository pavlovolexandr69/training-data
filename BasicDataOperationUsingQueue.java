import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Клас BasicDataOperationUsingQueue надає методи для виконання основних операцiй з даними типу Character.
 */
public class BasicDataOperationUsingQueue {
    static final String PATH_TO_DATA_FILE = "list/char.data";

    Character charValueToSearch;
    Character[] charArray;
    Queue<Character> charQueue;

    public static void main(String[] args) {  
        BasicDataOperationUsingQueue basicDataOperationUsingQueue = new BasicDataOperationUsingQueue(args);
        basicDataOperationUsingQueue.doDataOperation();
    }

    BasicDataOperationUsingQueue(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException("Вiдсутнє значення для пошуку");
        }

        String valueToSearch = args[0];
        this.charValueToSearch = valueToSearch.charAt(0);

        charArray = Utils.readArrayFromFile(PATH_TO_DATA_FILE);

        charQueue = new PriorityQueue<>(Arrays.asList(charArray));
    }

    private void doDataOperation() {
        searchArray();
        findMinAndMaxInArray();

        sortArray();

        searchArray();
        findMinAndMaxInArray();

        searchQueue();
        findMinAndMaxInQueue();
        peekAndPollQueue();

        Utils.writeArrayToFile(charArray, PATH_TO_DATA_FILE + ".sorted");
    }

    private void sortArray() {
        long startTime = System.nanoTime();
        charArray = Arrays.stream(charArray)
                        .sorted()
                        .toArray(Character[]::new);
        Utils.printOperationDuration(startTime, "сортування масиву Character");
    }

    private void searchArray() {
        long startTime = System.nanoTime();
        boolean found = Arrays.stream(charArray)
                            .anyMatch(c -> c.equals(charValueToSearch));
        Utils.printOperationDuration(startTime, "пошук в масивi Character");

        System.out.println("Значення '" + charValueToSearch + "' " + (found ? "знайдено" : "не знайдено") + " в масиві.");
    }

    private void findMinAndMaxInArray() {
        if (charArray == null || charArray.length == 0) {
            System.out.println("Масив порожнiй або не iнiцiалiзований.");
            return;
        }

        long startTime = System.nanoTime();

        Character min = Arrays.stream(charArray)
                            .min(Character::compareTo)
                            .orElse(null);
        Character max = Arrays.stream(charArray)
                            .max(Character::compareTo)
                            .orElse(null);

        Utils.printOperationDuration(startTime, "пошук мiнiмального i максимального значення в масивi Character");

        System.out.println("Мiнiмальне значення в масивi: " + min);
        System.out.println("Максимальне значення в масивi: " + max);
    }

    private void searchQueue() {
        long startTime = System.nanoTime();
        boolean found = charQueue.stream()
                                .anyMatch(c -> c.equals(charValueToSearch));
        Utils.printOperationDuration(startTime, "пошук в Queue Character");

        System.out.println("Значення '" + charValueToSearch + "' " + (found ? "знайдено" : "не знайдено") + " в Queue.");
    }

    private void findMinAndMaxInQueue() {
        if (charQueue == null || charQueue.isEmpty()) {
            System.out.println("Queue порожнiй або не iнiцiалiзований.");
            return;
        }

        long startTime = System.nanoTime();

        Character min = charQueue.stream()
                                .min(Character::compareTo)
                                .orElse(null);
        Character max = charQueue.stream()
                                .max(Character::compareTo)
                                .orElse(null);

        Utils.printOperationDuration(startTime, "пошук мiнiмального i максимального значення в Queue");

        System.out.println("Мiнiмальне значення в Queue: " + min);
        System.out.println("Максимальне значення в Queue: " + max);
    }

    private void peekAndPollQueue() {
        if (charQueue == null || charQueue.isEmpty()) {
            System.out.println("Queue порожнiй або не iнiцiалiзований.");
            return;
        }

        Character firstElement = charQueue.peek();
        System.out.println("Перший елемент у черзi: " + firstElement);

        firstElement = charQueue.poll();
        System.out.println("Забрати перший елемент у черзi: " + firstElement);

        firstElement = charQueue.peek();
        System.out.println("Перший елемент у черзi: " + firstElement);
    }
}

class Utils {
    static void printOperationDuration(long startTime, String operationName) {
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("\n>>>>>>>>>> Час виконання операцiї '" + operationName + "'': " + duration + " наносекунд");
    }

    static Character[] readArrayFromFile(String pathToFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            return br.lines()
                     .map(line -> line.charAt(0))
                     .toArray(Character[]::new);
        } catch (IOException e) {
            throw new RuntimeException("Помилка читання даних з файлу: " + pathToFile, e);
        }
    }

    static void writeArrayToFile(Character[] charArray, String pathToFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToFile))) {
            for (Character value : charArray) {
                writer.write(value.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
