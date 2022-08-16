package com.griddynamics.yvoronovskyi.battleship;

public class BattleshipApplication {

    public static void main(String[] args) {

        BattleshipService battleshipService = new BattleshipService();
        battleshipService.makeFleet();
        battleshipService.startGame();
    }
}
