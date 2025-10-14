package util;

public class ValidatorUtil {

    public static boolean isValidISBN(String isbn) {
        return isbn != null && isbn.matches("\\d{10}|\\d{13}");
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public static boolean isPositiveNumber(int number) {
        return number > 0;
    }

    public static void validateNotNull(Object obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }
}
