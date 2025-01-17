import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Клас BasicDataOperationUsingQueue надає методи для виконання основних операцiй з даними типу Character.
 * 
 * <p>Цей клас зчитує данi з файлу "list/Character.data", сортує їх та виконує пошук значення в масивi та черзi.</p>
 * 
 * <p>Основнi методи:</p>
 * <ul>
 *   <li>{@link #main(String[])} - Точка входу в програму.</li>
 *   <li>{@link #doDataOperation()} - Виконує основнi операцiї з даними.</li>
 *   <li>{@link #sortArray()} - Сортує масив Character.</li>
 *   <li>{@link #searchArray()} - Виконує пошук значення в масивi Character.</li>
 *   <li>{@link #findMinAndMaxInArray()} - Знаходить мiнiмальне та максимальне значення в масивi Character.</li>
 *   <li>{@link #searchQueue()} - Виконує пошук значення в черзi Character.</li>
 *   <li>{@link #findMinAndMaxInQueue()} - Знаходить мiнiмальне та максимальне значення в черзi Character.</li>
 *   <li>{@link #peekAndPollQueue()} - Виконує операцiї peek та poll з чергою Character.</li>
 * </ul>
 * 
 * <p>Конструктор:</p>
 * <ul>
 *   <li>{@link #BasicDataOperationUsingQueue(String[])} - iнiцiалiзує об'єкт з значенням для пошуку.</li>
 * </ul>
 * 
 * <p>Константи:</p>
 * <ul>
 *   <li>{@link #PATH_TO_DATA_FILE} - Шлях до файлу з даними.</li>
 * </ul>
 * 
 * <p>Змiннi екземпляра:</p>
 * <ul>
 *   <li>{@link #charValueToSearch} - Значення Character для пошуку.</li>
 *   <li>{@link #charArray} - Масив Character.</li>
 *   <li>{@link #charQueue} - Черга Character.</li>
 * </ul>
 */
public class BasicDataOperationUsingQueue {
    static final String PATH_TO_DATA_FILE = "list/Character.data";

    Character charValueToSearch;
    Character[] charArray;
    Queue<Character> charQueue;

    public static void main(String[] args) {  
        BasicDataOperationUsingQueue basicDataOperationUsingQueue = new BasicDataOperationUsingQueue(args);
        basicDataOperationUsingQueue.doDataOperation();
    }

    /**
     * Конструктор, який iнiцiалiзує об'єкт з значенням для пошуку.
     * 
     * @param args Аргументи командного рядка, де перший аргумент - значення для пошуку.
     */
    BasicDataOperationUsingQueue(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException("Вiдсутнє значення для пошуку");
        }

        String valueToSearch = args[0];
        this.charValueToSearch = valueToSearch.charAt(0);

        charArray = Utils.readArrayFromFile(PATH_TO_DATA_FILE);

        charQueue = new PriorityQueue<>(Arrays.asList(charArray));
    }

    /**
     * Виконує основнi операцiї з даними.
     */
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

    /**
     * Сортує масив об'єктiв Character та виводить початковий i вiдсортований масиви.
     */
    private void sortArray() {
        long startTime = System.nanoTime();

        Arrays.sort(charArray);

        Utils.printOperationDuration(startTime, "сортування масиву Character");
    }

    /**
     * Метод для пошуку значення в масивi Character.
     */
    private void searchArray() {
        long startTime = System.nanoTime();
        
        int index = Arrays.binarySearch(this.charArray, charValueToSearch);
        
        Utils.printOperationDuration(startTime, "пошук в масивi Character");

        if (index >= 0) {
            System.out.println("Значення '" + charValueToSearch + "' знайдено в масивi за iндексом: " + index);
        } else {
            System.out.println("Значення '" + charValueToSearch + "' в масивi не знайдено.");
        }
    }

    private void findMinAndMaxInArray() {
        if (charArray == null || charArray.length == 0) {
            System.out.println("Масив порожнiй або не iнiцiалiзований.");
            return;
        }

        long startTime = System.nanoTime();

        Character min = charArray[0];
        Character max = charArray[0];

        for (Character value : charArray) {
            if (value < min) {
                min = value;
            }
            if (value > max) {
                max = value;
            }
        }

        Utils.printOperationDuration(startTime, "пошук мiнiмального i максимального значення в масивi Character");

        System.out.println("Мiнiмальне значення в масивi: " + min);
        System.out.println("Максимальне значення в масивi: " + max);
    }

    /**
     * Метод для пошуку значення в черзi Character.
     */
    private void searchQueue() {
        long startTime = System.nanoTime();

        boolean isFound = this.charQueue.contains(charValueToSearch);

        Utils.printOperationDuration(startTime, "пошук в Queue Character");

        if (isFound) {
            System.out.println("Значення '" + charValueToSearch + "' знайдено в Queue");
        } else {
            System.out.println("Значення '" + charValueToSearch + "' в Queue не знайдено.");
        }
    }

    private void findMinAndMaxInQueue() {
        if (charQueue == null || charQueue.isEmpty()) {
            System.out.println("Queue порожнiй або не iнiцiалiзований.");
            return;
        }

        long startTime = System.nanoTime();

        Character min = Collections.min(charQueue);
        Character max = Collections.max(charQueue);

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

/**
 * Клас Utils мiститить допомiжнi методи для роботи з даними типу Character.
 */
class Utils {
    static void printOperationDuration(long startTime, String operationName) {
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("\n>>>>>>>>>> Час виконання операцiї '" + operationName + "'': " + duration + " наносекунд");
    }

    static Character[] readArrayFromFile(String pathToFile) {
        List<Character> tempList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                tempList.add(line.charAt(0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tempList.toArray(new Character[0]);
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
