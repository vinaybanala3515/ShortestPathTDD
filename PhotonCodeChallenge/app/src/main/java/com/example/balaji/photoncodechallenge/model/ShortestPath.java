package com.example.balaji.photoncodechallenge.model;


import java.util.HashMap;
import java.util.LinkedHashMap;

public class ShortestPath {
    public HashMap<String, Integer> entire;
    public int[][] inputMatrix;

    public ShortestPath() {
        this.entire = new LinkedHashMap<>();
    }

    public int[][] createMatrix(String stringMatrix) {
        if (stringMatrix != null && (!stringMatrix.equals(""))) {
            String[] rows = stringMatrix.split(";");
            int[][] matrix = new int[rows.length][rows[0].split(",").length];
            for (int i = 0; i < rows.length; i++) {
                String[] cols = rows[i].split(",");
                for (int j = 0; j < cols.length; j++) {
                    if (Character.isDigit(cols[j].charAt(0))) {
                        matrix[i][j] = Integer.parseInt(cols[j]);
                    } else if (cols[j].charAt(0) == '-' && Character.isDigit(cols[j].charAt(1))) {
                        matrix[i][j] = Integer.parseInt(cols[j]);
                    } else {
                        return null;
                    }
                }
            }
            return matrix;
        } else {
            return null;
        }

    }

    public String shortestRoute(String stringMatrix) {
        inputMatrix = createMatrix(stringMatrix);
        //System.out.println(displayMatrix(inputMatrix));
        if (inputMatrix == null) {
            return displayResult(null, 0, false);
        } else {
            String path = "";
            int value = 0;
            if (inputMatrix.length == 1) {
                path = "";
                value = 0;
                for (int i = 0; i < inputMatrix[0].length; i++) {
                    if (value + inputMatrix[0][i] <= 50) {
                        path = path.concat(String.valueOf(1));
                        value = value + inputMatrix[0][i];
                    } else {
                        return displayResult(path, value, false);
                    }
                }
                return displayResult(path, value, true);
            } else if (inputMatrix[0].length == 1) {
                path = "";
                value = inputMatrix[0][0];
                for (int i = 0; i < inputMatrix.length; i++) {
                    if (inputMatrix[i][0] < value) {
                        path = String.valueOf(i + 1);
                        value = inputMatrix[i][0];
                    }
                }
                return displayResult(path, value, true);
            } else {
                for (int i = 0; i < inputMatrix.length; i++) {
                    get(inputMatrix, "", 0, i, 0);
                }
                path = "";
                value = 50;
                int keyLength = 0;
                for (String temp : entire.keySet()) {
                    if (keyLength <= temp.length()) {
                        keyLength = temp.length();
                    }
                }

                for (String temp : entire.keySet()) {
                    //System.out.println("Key : " + temp + " value : " + entire.get(temp));
                    if (temp.length() == keyLength && entire.get(temp) < value) {
                        path = temp;
                        value = entire.get(temp);
                    }
                }
                return displayResult(path, value, true);
            }
        }
    }

    public String displayResult(String path, int value, boolean check) {
        String fullPath = "[";
        if (inputMatrix != null) {
            if (path.length() < inputMatrix[0].length) {
                check = false;
            }
        }

        if (path != null && check) {

            for (int i = 0; i < path.length(); i++) {
                fullPath = fullPath.concat(String.valueOf(path.charAt(i))).concat(" ");
            }
            fullPath = fullPath.trim().concat("]");
            return ("Yes").concat("\n").concat(String.valueOf(value).concat("\n")).concat(fullPath);
        } else if (path != null && !check) {
//            if (path.length() < inputMatrix[0].length) {
//                check = false;
//            }
            for (int i = 0; i < path.length(); i++) {
                fullPath = fullPath.concat(String.valueOf(path.charAt(i))).concat(" ");
            }
            fullPath = fullPath.trim().concat("]");
            return ("No").concat("\n").concat(String.valueOf(value).concat("\n")).concat(fullPath);
        } else {
            return "Invalid Matrix";
        }

    }

    public void get(int[][] input, String key, int val, int row, int column) {
        if (val + input[row][column] <= 50) {
            if (column == input[0].length - 1) {
                entire.put(key.concat(String.valueOf(row + 1)), val + input[row][column]);
            } else {
                if (input.length > 2) {
                    if (row == 0) {
                        get(input, key.concat(String.valueOf(row + 1)), val + input[row][column], input.length - 1, column + 1);
                        get(input, key.concat(String.valueOf(row + 1)), val + input[row][column], row, column + 1);
                        get(input, key.concat(String.valueOf(row + 1)), val + input[row][column], row + 1, column + 1);
                    } else if (row == input.length - 1) {
                        get(input, key.concat(String.valueOf(row + 1)), val + input[row][column], row - 1, column + 1);
                        get(input, key.concat(String.valueOf(row + 1)), val + input[row][column], row, column + 1);
                        get(input, key.concat(String.valueOf(row + 1)), val + input[row][column], 0, column + 1);
                    } else {
                        get(input, key.concat(String.valueOf(row + 1)), val + input[row][column], row - 1, column + 1);
                        get(input, key.concat(String.valueOf(row + 1)), val + input[row][column], row, column + 1);
                        get(input, key.concat(String.valueOf(row + 1)), val + input[row][column], row + 1, column + 1);
                    }
                } else {
                    if (row == 0) {
                        get(input, key.concat(String.valueOf(row + 1)), val + input[row][column], input.length - 1, column + 1);
                        get(input, key.concat(String.valueOf(row + 1)), val + input[row][column], row, column + 1);
                    } else if (row == input.length - 1) {
                        get(input, key.concat(String.valueOf(row + 1)), val + input[row][column], row - 1, column + 1);
                        get(input, key.concat(String.valueOf(row + 1)), val + input[row][column], row, column + 1);
                    } else {
                        get(input, key.concat(String.valueOf(row + 1)), val + input[row][column], row - 1, column + 1);
                        get(input, key.concat(String.valueOf(row + 1)), val + input[row][column], row, column + 1);
                    }
                }
            }
        } else {
            entire.put(key, val);
        }
    }

    public String displayMatrix(int[][] sample) {
        String fullMatrix = "";
        if(sample!=null){
            for (int i = 0; i < sample.length; i++) {
                for (int j = 0; j < sample[0].length; j++) {
                    fullMatrix = fullMatrix.concat(String.valueOf(sample[i][j])).concat(" ");
                }
                fullMatrix = fullMatrix.concat("\n");
            }
            return fullMatrix;
        }
        else{
            return "Invalid Matrix";
        }

    }

}
