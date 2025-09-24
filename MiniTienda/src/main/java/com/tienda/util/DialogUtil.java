package main.java.com.tienda.util;

import  javax.swing.*;
public class DialogUtil {
    private DialogUtil(){}

    public static String prompt(String message){
        return JOptionPane.showInputDialog(
                null,
                message,
                "inventario",
                JOptionPane.QUESTION_MESSAGE
        );
    }

    public static void info(String message){
        JOptionPane.showMessageDialog(null, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void error(String message){
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
