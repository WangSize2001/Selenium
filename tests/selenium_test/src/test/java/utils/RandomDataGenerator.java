package utils;

import java.util.Random;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Random data generation utility class
 */
public class RandomDataGenerator {
    private static final Random random = new Random();
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    
    /**
     * Generate random name
     */
    public static String generateRandomName() {
        String[] firstNames = {"John", "Jane", "Michael", "Emma", "William", "Olivia", "James", "Sophia", "Robert", "Emily"};
        return firstNames[random.nextInt(firstNames.length)];
    }
    
    /**
     * Generate random last name
     */
    public static String generateRandomLastName() {
        String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Miller", "Davis", "Garcia", "Rodriguez", "Wilson"};
        return lastNames[random.nextInt(lastNames.length)];
    }
    
    /**
     * Generate random email
     */
    public static String generateRandomEmail() {
        String name = generateRandomName().toLowerCase();
        String domain = "example.com";
        long timestamp = System.currentTimeMillis();
        return name + timestamp + "@" + domain;
    }
    
    /**
     * Generate random integer from 1 to specified maximum value
     */
    public static int generateRandomInt(int max) {
        return random.nextInt(max) + 1;
    }
    
    /**
     * Generate random gender
     */
    public static String generateRandomGender() {
        String[] genders = {"male", "female", "other"};
        return genders[random.nextInt(genders.length)];
    }
    
    /**
     * Generate random date (within the last year)
     */
    public static String generateRandomDate() {
        LocalDate now = LocalDate.now();
        int daysToSubtract = random.nextInt(365);
        LocalDate randomDate = now.minusDays(daysToSubtract);
        return randomDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
    
    /**
     * Generate random hexadecimal color value
     */
    public static String generateRandomColor() {
        String[] colors = {"#FF0000", "#00FF00", "#0000FF", "#FFFF00", "#FF00FF", "#00FFFF", "#000000", "#FFFFFF"};
        return colors[random.nextInt(colors.length)];
    }
    
    /**
     * Generate random option (1, 2 or 3)
     */
    public static String generateRandomOption() {
        String[] options = {"option", "option 1", "option 2", "option 3"};
        return options[random.nextInt(options.length)];
    }
    
    /**
     * Generate random string
     */
    public static String generateRandomString(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return builder.toString();
    }
}