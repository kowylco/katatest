package com.kowylco;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {
	// write your code here
        String[] inputArray  = new String[3];
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String inputStr = "";
        try {
            inputStr = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String operation = Operation.getOperation(inputStr);
        inputArray[0] = inputStr.substring(0,inputStr.indexOf(operation));
        inputArray[1] = operation;
        inputArray[2] = inputStr.substring(inputStr.indexOf(operation) + 1);
        System.out.println(inputStr);
        System.out.println(Arrays.toString(inputArray));
        
        Numbers numbers = new Numbers(inputArray);
        int intResult;
        switch (inputArray[1]) {
            case "+":
                intResult = numbers.numberOne + numbers.numberTwo;
                break;
            case "-":
                intResult = numbers.numberOne - numbers.numberTwo;
                break;
            case "/":
                intResult = numbers.numberOne / numbers.numberTwo;
                break;
            case "*":
                intResult = numbers.numberOne * numbers.numberTwo;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + inputArray[1]);
        }
        System.out.println(intResult);
        if (numbers.isRoman) {
            System.out.println(Numbers.intToRoman(intResult));
        }
    }
}

class Operation {
    public static boolean isValidOperation (String inputStr) throws Exception {
        String[] operators = new String[]{"+", "-", "/", "*"};
        int posOperator = -1;
        int sumPosOperators = 0;
        for (int i = 0; i < operators.length; i++){
            if (inputStr.contains(operators[i]) && inputStr.indexOf(operators[i]) == inputStr.lastIndexOf(operators[i])) {
                posOperator = inputStr.indexOf(operators[i]);
                sumPosOperators = sumPosOperators + posOperator;
            }
        }
        if (posOperator == sumPosOperators) {
            return true;
        } else throw new Exception("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
    }
    public static String getOperation(String inputStr) throws Exception {
        String[] operators = new String[]{"+", "-", "/", "*"};
        int posOperator = -1;
        Operation.isValidOperation(inputStr);
        for (int i = 0; i < operators.length; i++){
            if (inputStr.contains(operators[i])) {
                posOperator = inputStr.indexOf(operators[i]);
            }
        }
        if (posOperator != -1) {
            return String.valueOf(inputStr.charAt(posOperator));
        } else throw new Exception("строка не является математической операцией");
    }
}

class Numbers {
    int numberOne;
    int numberTwo;
    boolean isRoman = false;
    Numbers(String[] inData) {
        try {
            numberOne = Integer.parseInt(inData[0]);
            numberTwo = Integer.parseInt(inData[2]);
        }
        catch (NumberFormatException exception) {
            numberOne = this.parseRoman(inData[0]);
            numberTwo = this.parseRoman(inData[2]);
            isRoman = true;
        }
    }
    public int parseRoman(String inStr) {
        int outInt;
        switch (inStr.toUpperCase()) {
            case "I":
                outInt = 1;
                break;
            case "II":
                outInt = 2;
                break;
            case "III":
                outInt = 3;
                break;
            case "IV":
                outInt = 4;
                break;
            case "V":
                outInt = 5;
                break;
            case "VI":
                outInt = 6;
                break;
            case "VII":
                outInt = 7;
                break;
            case "VIII":
                outInt = 8;
                break;
            case "IX":
                outInt = 9;
                break;
            case "X":
                outInt = 10;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + inStr);
        }
        return outInt;
    }
    public static String intToRoman(int inInt) {
        RomanNumber[] romanNumbers = RomanNumber.values();
        int romanNumbersLength = romanNumbers.length - 1;
        StringBuilder resultRoman = new StringBuilder();
        while (inInt > 0 && romanNumbersLength > 0) {
            if (romanNumbers[romanNumbersLength].getValue() <= inInt) {
                resultRoman.append(romanNumbers[romanNumbersLength]);
                inInt -= romanNumbers[romanNumbersLength].getValue();
            } else {
                romanNumbersLength = romanNumbersLength - 1;
            }
        }
        return resultRoman.toString();
    }
}

enum RomanNumber {
    I(1), IV(4), V(5), IX(9), X(10), XL(40), L(50), XC(90), C(100);

    private final int value;

    RomanNumber(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}