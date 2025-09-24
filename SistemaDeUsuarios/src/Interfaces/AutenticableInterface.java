package Interfaces;

import Model.User;

public interface AutenticableInterface {
    User authenticate(String email, String password);
}
