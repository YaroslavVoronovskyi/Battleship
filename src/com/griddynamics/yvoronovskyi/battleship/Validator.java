package com.griddynamics.yvoronovskyi.battleship;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Validator {
    public static boolean validateCoordinates(String coordinates) {
        Pattern pattern = Constants.COORDINATES_PATTERN;
        Matcher matcher = pattern.matcher(coordinates);
        return matcher.matches();
    }
}
