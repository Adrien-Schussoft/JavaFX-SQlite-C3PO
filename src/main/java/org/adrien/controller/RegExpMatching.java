package org.adrien.controller;

import java.util.regex.Pattern;

public class RegExpMatching {

    private static final String REG_EXP = "^[A-Za-z-éèêôû\\s]+$";
    private static Pattern inputPattern = Pattern.compile(REG_EXP);

    public static boolean isValidInput(String input){
        return inputPattern.matcher(input).matches();
    }
}
