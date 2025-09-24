package main.java.com.tienda.validation;

public class InputValidator {
    // Validacion para campos vacios
    public static String requireNonEmpty(String input, String fieldName){
        if (input == null || input.trim().isEmpty()){
            throw new ValidationException(fieldName + "Cannot be empty");
        }
        return input.trim();
    }

    // Validacion numeros int
    public static int parsePositiveInt(String input, String fieldName){
        try {
            int value = Integer.parseInt(requireNonEmpty(input, fieldName));
            if (value < 0){
                throw new ValidationException(fieldName + " Must be >= 0");
            }
            return value;
        } catch (NumberFormatException e){
            throw new ValidationException(fieldName + " Must be a valid integer");
        }
    }

    // Validacion numeros double
    public static double parsePositiveDouble(String input, String fieldName){
        try {
            double value = Double.parseDouble(requireNonEmpty(input, fieldName));
            if (value < 0){
                throw new ValidationException(fieldName + " Must be >= 0.0");
            }
            return value;
        } catch (NumberFormatException e){
            throw new ValidationException(fieldName + " Must be a valid decimal");
        }
    }
}
