import java.util.*;


public class Main {

    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();
    public static final int NUMBER_OF_ROUTES = 1000;
    public static final String LETTERS = "RLRFR";
    public static final int LENGTH_OF_ROUTE = 100;

    public static void main(String[] args) {
        String[] routes = new String[NUMBER_OF_ROUTES];
        for (int i = 0; i < routes.length; i++) {
            routes[i] = generateRoute(LETTERS, LENGTH_OF_ROUTE);
        }

        for (String route : routes) {
            new Thread(() -> {
                int rNumber = 0;
                for (int i = 0; i < route.length(); i++) {
                    if (route.charAt(i) == 'R') {
                        rNumber++;
                    }
                }
                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(rNumber)) {
                        sizeToFreq.put(rNumber, sizeToFreq.get(rNumber) + 1);
                    } else {
                        sizeToFreq.put(rNumber, 1);
                    }
                }
            }).start();
        }

        Map.Entry<Integer, Integer> max = sizeToFreq
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get();
        System.out.println("Самое частое количество повторений " + max.getKey()
                + " (встретилось " + max.getValue() + " раз)");

        System.out.println("Другие размеры: ");

        sizeToFreq
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(e -> System.out.println(" - " + e.getKey() + " (" + e.getValue() + " раз)"));
    }


    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}
