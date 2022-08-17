package com.griddynamics.yvoronovskyi.battleship;

import java.util.regex.Pattern;
public class Constants {
    public static final Pattern COORDINATES_PATTERN = Pattern.compile("([A-Za-z][0-9])\\s([A-Za-z][0-9])");
    public static final String PLACE_SHIP_PATTERN = "Player %s, place your ships on the game field";
    public static final String MAKE_TURN_PATTERN = "Player %s, it's your turn:";
    public static final String CONGRATULATIONS = "You sank the last ship. You won. Congratulations!";
    public static final String NEXT_PLAYER_TURN = "Player, it's your turn:";
    public static final String FIRST_PLAYER_TURN_NAME = "1";
    public static final String SECOND_PLAYER_TURN_NAME = "2";
    public static final String EMPTY_CELL = "~";
    public static final String SHIP = "O";
    public static final String HIT = "X";
    public static final String MISS = "M";
    public static final String PRINT_ALL = "al";
    public static final String PRINT_SHOTS = "sh";
    public static final String LINE_SEPARATOR = System.lineSeparator();
    public static final String LINE_DELIMETER = "---------------------";
    public static final String DELIMETER = " ";
    public static final String EMPTY_DELIMETER = "";
    public static final String HIT_SHIP_MESSAGE = "You hit a ship!";
    public static final String SANK_SHIP_MESSAGE = "You sank a ship!";
    public static final String MISS_MESSAGE = "You missed!";
    public static final String WRONG_SHIP_LENGTH_MESSAGE = "Error! Wrong length of the Submarine! Try again:";
    public static final String WRONG_SHIP_LOCATION_MESSAGE = "Error! Wrong ship location! Try again:";
    public static final String WRONG_COORDINATES_MESSAGE = "Error! You entered the wrong coordinates! Try again:";
    public static final String WRONG_TOO_CLOSE_LOCATION_MESSAGE = "Error! You placed it too close to another one. Try again:";
    public static final String WRONG_COORDINATES_FORMAT = "Please enter coordinates in correct format";
    public static final int FIRST_PLAYER_TURN = 1;
    public static final int SECOND_PLAYER_TURN = 2;
    public static final int FIRST_INDEX = 0;
    public static final int ROW_SIZE = 10;
    public static final int COLUMN_SIZE = 10;
}
