package com.example.passwordvalidation.service;

//Validar que la contrasena tenga:
//        Al menos una mayuscula
//        Al menos una minuscula
//        Al menos seis caracteres como minimo
//        Como maximo doce caracteres
//        Al menos un caracter especial
//        Al menos un digito
//        Validar composicion de la clave


public class PasswordService {
    public static final int MINIMUM_NUMBER_CHARACTERS = 6;
    public static final int MAXIMUM_NUMBER_CHARACTERS = 12;
    public static final String ALPHABET = "[a-zA-Z0-9]+";
    //    public boolean hasOneCapitalLetter(String password) {
//        IntStream passwordCharacters=password.chars();
//
//        boolean hasCapitalLetters = passwordCharacters.anyMatch(Character::isUpperCase);
//
//        return hasCapitalLetters;
//    }

    public Boolean validatePassword(String password) {
        if (!hasOneCapitalLetter(password)) return false;
        if (!hasOneLowerCase(password)) return false;
        if (!hasSixCharactesAsMinium(password))return false;
        if (!hasMaximumTwelveCharacters(password)) return false;
        if (!hasOneSpecialCharacter(password)) return false;
        if (!hasOneDigit(password)) return false;

        return true;
    }

    public Boolean hasOneCapitalLetter(String password) {
        for (int index = 0; index < password.length(); index++) {
            if (Character.isUpperCase(password.charAt(index))) return true;
        }
        return false;
    }

    public Boolean hasOneLowerCase(String password) {
        for (int index = 0; index < password.length(); index++) {
            if (Character.isLowerCase(password.charAt(index))) return true;
        }
        return false;
    }

    public Boolean hasSixCharactesAsMinium(String password) {
        return password.length() >= MINIMUM_NUMBER_CHARACTERS;
    }

    public Boolean hasMaximumTwelveCharacters(String password) {
        return password.length() <= MAXIMUM_NUMBER_CHARACTERS;
    }

    public Boolean hasOneSpecialCharacter(String password) {
        return !password.matches(ALPHABET);
    }

    public Boolean hasOneDigit(String password) {
        for (int index = 0; index < password.length(); index++) {
            if (Character.isDigit(password.charAt(index))) return true;
        }
        return false;
    }
}
