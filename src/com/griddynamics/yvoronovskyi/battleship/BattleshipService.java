package com.griddynamics.yvoronovskyi.battleship;

public class BattleshipService {
    private final BattleshipField firstBattleshipField;
    private final BattleshipField secondBattleshipField;
    private int currGamer = Constants.FIRST_PLAYER_TURN;
    public BattleshipService() {
        firstBattleshipField = new BattleshipField();
        secondBattleshipField = new BattleshipField();
    }

    public void makeFleet() {
        System.out.printf(Constants.PLACE_SHIP_PATTERN, Constants.FIRST_PLAYER_TURN_NAME);
        System.out.println(Constants.LINE_SEPARATOR);
        firstBattleshipField.makeFleet();
        System.out.printf(Constants.PLACE_SHIP_PATTERN, Constants.SECOND_PLAYER_TURN_NAME);
        System.out.println(Constants.LINE_SEPARATOR);
        secondBattleshipField.makeFleet();
    }

    public void startGame() {
        while (!firstBattleshipField.isLoser() || !firstBattleshipField.isLoser()) {
            makeTurn();
        }
        System.out.println(Constants.CONGRATULATIONS);
    }

    private void makeTurn() {
        if (currGamer == Constants.FIRST_PLAYER_TURN) {
            System.out.printf(Constants.MAKE_TURN_PATTERN, Constants.FIRST_PLAYER_TURN);
            firstBattleshipField.makeTurn(secondBattleshipField);
        } else {
            System.out.printf(Constants.MAKE_TURN_PATTERN, Constants.SECOND_PLAYER_TURN);
            secondBattleshipField.makeTurn(firstBattleshipField);
        }
        currGamer = currGamer == Constants.FIRST_PLAYER_TURN
                ? Constants.SECOND_PLAYER_TURN
                : Constants.FIRST_PLAYER_TURN;
    }
}