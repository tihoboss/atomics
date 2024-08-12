import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;


public class Main {
    private static AtomicInteger length3 = new AtomicInteger(0);
    private static AtomicInteger length4 = new AtomicInteger(0);
    private static AtomicInteger length5 = new AtomicInteger(0);

    public static void main(String[] args) {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            int length = 3 + random.nextInt(3);
            texts[i] = generateText("abc", length);
        }
        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (String text : texts) {
            executor.execute(() -> checkPalindrome(text));
            executor.execute(() -> checkOneLetter(text));
            executor.execute(() -> checkAbc(text));
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        printResult();
    }
    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void checkPalindrome(String text) {
        if (isPalindrome(text)||checkOneLetter(text)||checkAbc(text)) {
            int length = text.length();
            switch (length) {
                case 3:
                    length3.incrementAndGet();
                    break;
                case 4:
                    length4.incrementAndGet();
                    break;
                case 5:
                    length5.incrementAndGet();
                    break;
                default:
                    break;
            }
        }
    }

    public static boolean isPalindrome(String text) {
        int left = 0;
        int right = text.length() - 1;
        while (left < right) {
            if (text.charAt(left) != text.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
    public static boolean checkOneLetter(String text){
        char firstChar = text.charAt(0);
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != firstChar) {
                return false;
            }
        }
        return true;
    }
    public static boolean checkAbc(String text){
        for(int i =1;i<text.length();i++){
            if(text.charAt(i)<text.charAt(i-1)) return false;
        }
        return true;
    }
    public static void printResult(){
        System.out.println("Красивых слов с длиной 3: "+length3.get()+" шт");
        System.out.println("Красивых слов с длиной 4: "+length4.get()+" шт");
        System.out.println("Красивых слов с длиной 5: "+length5.get()+" шт");
    }
}