package com.griddynamics.yvoronovskyi.battleship;

import java.awt.*;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BattleshipField {
    private final String[][] fieldMap;
    private final String[][] shipIdMap;
    private final String[][] occupationMap;
    private final Map<String, Integer> shipsFleet;
    private int shipId;
    private String currentShipId = Constants.EMPTY_DELIMETER;

    public BattleshipField() {
        fieldMap = new String[Constants.ROW_SIZE][Constants.COLUMN_SIZE];
        shipIdMap = new String[Constants.ROW_SIZE][Constants.COLUMN_SIZE];
        shipId = 0;
        occupationMap = new String[Constants.ROW_SIZE][Constants.COLUMN_SIZE];
        for (int rowIndex = Constants.FIRST_INDEX; rowIndex < Constants.ROW_SIZE; rowIndex++) {
            for (int columnIndex = Constants.FIRST_INDEX; columnIndex < Constants.COLUMN_SIZE; columnIndex++) {
                fieldMap[rowIndex][columnIndex] = Constants.EMPTY_CELL;
                occupationMap[rowIndex][columnIndex] = Constants.EMPTY_CELL;
                shipIdMap[rowIndex][columnIndex] = Constants.EMPTY_CELL;
            }
        }
        this.shipsFleet = Stream.of(ShipType.values())
                .collect(Collectors.toMap(ShipType::getName, ShipType::getSize));
    }

    public String printDesk(String typePrint) {
        StringBuilder outStr = new StringBuilder();
        StringBuilder border = new StringBuilder(Constants.DELIMETER);
        for (int index = 1; index < Constants.COLUMN_SIZE + 1; index++) {
            border.append(Constants.DELIMETER);
            border.append(index);
        }
        border.append(Constants.LINE_SEPARATOR);
        outStr.append(border);
        for (int rowIndex = Constants.FIRST_INDEX; rowIndex < Constants.ROW_SIZE; rowIndex++) {
            outStr.append(Character.toString(rowIndex + 65));
            for (int columnIndex = Constants.FIRST_INDEX; columnIndex < Constants.COLUMN_SIZE; columnIndex++) {
                outStr.append(Constants.DELIMETER);
                String ch = fieldMap[rowIndex][columnIndex];
                if (typePrint.equals(Constants.PRINT_SHOTS)) {
                    ch = (ch.equals(Constants.SHIP)) ? Constants.EMPTY_CELL : ch;
                }
                outStr.append(ch);
            }
            outStr.append(Constants.LINE_SEPARATOR);
        }

        return outStr.toString();
    }

    public boolean isLoser() {
        for (int rowIndex = Constants.FIRST_INDEX; rowIndex < Constants.ROW_SIZE; rowIndex++) {
            for (int columnIndex = Constants.FIRST_INDEX; columnIndex < Constants.COLUMN_SIZE; columnIndex++) {
                if (Constants.SHIP.equals(fieldMap[rowIndex][columnIndex])) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isOcupated(int x, int y) {
        return (occupationMap[x][y].equals(Constants.EMPTY_CELL));
    }

    public boolean isShip(Point point) {
        currentShipId = Constants.EMPTY_DELIMETER;
        String ship = fieldMap[point.x][point.y];
        if (ship.equals(Constants.SHIP)) {
            currentShipId = shipIdMap[point.x][point.y];
        }
        return (ship.equals(Constants.SHIP) || ship.equals(Constants.HIT));
    }

    private boolean isShipAlive() {
        for (int rowIndex = Constants.FIRST_INDEX; rowIndex < Constants.ROW_SIZE; rowIndex++) {
            for (int columnIndex = Constants.FIRST_INDEX; columnIndex < Constants.COLUMN_SIZE; columnIndex++) {
                if (currentShipId.equals(shipIdMap[rowIndex][columnIndex])) {
                    return true;
                }
            }
        }
        return false;
    }

    public void makeFleet() {
        System.out.println(this.printDesk(Constants.PRINT_ALL));

        for (Map.Entry<String, Integer> entry : shipsFleet.entrySet()) {
            makeShip(entry.getKey(), entry.getValue());
        }
    }

    private void makeShip(String shipType, int desk) {
        boolean isShipCoordinateValid = false;
        System.out.println("Enter the coordinates of the " + shipType + "(" + desk + "cells):");
        while (!isShipCoordinateValid) {
            String coordinates = ConsoleReader.getString();
            isShipCoordinateValid = Validator.validateCoordinates(coordinates);
            if (!isShipCoordinateValid) {
                System.out.println(Constants.WRONG_COORDINATES_FORMAT);
                continue;
            }
            Pattern pattern = Constants.COORDINATES_PATTERN;
            Matcher matcher = pattern.matcher(coordinates);
            matcher.matches();
            String firstCoordinate = matcher.group(1);
            String secondCoordinate = matcher.group(2);

            int firstX = (int) Double.parseDouble(String.valueOf(getMapPoints(firstCoordinate).getX()));
            int firstY = (int) Double.parseDouble(String.valueOf(getMapPoints(firstCoordinate).getY()));
            int secondX = (int) Double.parseDouble(String.valueOf(getMapPoints(secondCoordinate).getX()));
            int secondY = (int) Double.parseDouble(String.valueOf(getMapPoints(secondCoordinate).getY()));

            isShipCoordinateValid = (firstX == secondX || firstY == secondY);
            if (!isShipCoordinateValid) {
                System.out.println(Constants.WRONG_SHIP_LOCATION_MESSAGE);

            }
            int sDesk = (firstX == secondX)
                    ? Math.abs(firstY - secondY)
                    : Math.abs(firstX - secondX);
            isShipCoordinateValid = sDesk + 1 == desk;
            if (!isShipCoordinateValid) {
                System.out.println(Constants.WRONG_SHIP_LENGTH_MESSAGE);
                continue;
            }
            isShipCoordinateValid = setShipOnMap(firstX, firstY, secondX, secondY, desk);
            if (!isShipCoordinateValid) {
                System.out.println(Constants.WRONG_TOO_CLOSE_LOCATION_MESSAGE);
                continue;
            }
            System.out.println(this.printDesk(Constants.PRINT_ALL));
        }
    }

    public boolean setShipOnMap(int firstX, int firstY, int secondX, int secondY, int desk) {
        boolean isHorizontal = firstX == secondX;
        int begin;
        if (isHorizontal) {
            begin = Math.min(firstY, secondY);
            for (int index = begin; index < begin + desk; index++) {
                if (!isOcupated(firstX, index)) {
                    return false;
                }
            }
            shipId++;
            for (int index = begin; index < begin + desk; index++) {
                makeOccupation(firstX, index, Constants.EMPTY_DELIMETER + shipId);
            }
        } else {
            begin = Math.min(firstX, secondX);
            for (int index = begin; index < begin + desk; index++) {
                if (!isOcupated(index, firstY)) {
                    return false;
                }
            }
            shipId++;
            for (int index = begin; index < begin + desk; index++) {
                makeOccupation(index, firstY, Constants.EMPTY_DELIMETER + shipId);
            }
        }
        return true;
    }

    private int getPrevious(int point) {
        return (point == 0) ? point : point - 1;
    }

    private int getNext(int point) {
        return (point == 9) ? point : point + 1;
    }

    private void makeOccupation(int x, int y, String shipId) {
        fieldMap[x][y] = Constants.SHIP;
        shipIdMap[x][y] = shipId;
        for (int xIndex = getPrevious(x); xIndex <= getNext(x); xIndex++) {
            for (int yIndex = getPrevious(y); yIndex <= getNext(y); yIndex++) {
                occupationMap[xIndex][yIndex] = Constants.SHIP;
            }
        }
    }

    public Point getMapPoints(String mapPoints) {
        char[] coordinate = mapPoints.toCharArray();
        int x = Character.getNumericValue(coordinate[0]);
        int y = (coordinate.length > 2)
                ? Character.getNumericValue(coordinate[1] + coordinate[2])
                : Character.getNumericValue(coordinate[1]);
        return new Point(x - 10, y - 1);
    }

    public void makeTurn(BattleshipField otherGameField) {
        System.out.println(otherGameField.printDesk(Constants.PRINT_SHOTS));
        System.out.println(Constants.LINE_DELIMETER);
        System.out.println(this.printDesk(Constants.PRINT_ALL));
        System.out.println(Constants.NEXT_PLAYER_TURN);
        Point point = new Point();
        boolean isShipCoordinateValid = false;
        while (!isShipCoordinateValid) {
            point = getMapPoints(ConsoleReader.getString());
            isShipCoordinateValid = point.x >= 0 && point.x < 10 && point.y >= 0 && point.y < 10;
            if (!isShipCoordinateValid) {
                System.out.println(Constants.WRONG_COORDINATES_MESSAGE);
            }
        }
        currentShipId = Constants.EMPTY_DELIMETER;
        boolean pointWithShip = otherGameField.isShip(point);
        otherGameField.markPoint(point, pointWithShip ? Constants.HIT : Constants.MISS);
        if (pointWithShip) {
            System.out.println(otherGameField.isShipAlive()
                    ? Constants.HIT_SHIP_MESSAGE
                    : Constants.SANK_SHIP_MESSAGE);
        } else {
            System.out.println(Constants.MISS_MESSAGE);
        }
    }

    public void markPoint(Point point, String s) {
        fieldMap[point.x][point.y] = s;
        shipIdMap[point.x][point.y] = s;
    }
}
