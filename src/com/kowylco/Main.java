package com.kowylco;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws Exception {
	// write your code here
        String[] inputArray  = new String[3];
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String inputStr = "";

        System.out.println("""
                Калькулятор умеет выполнять операции сложения, вычитания, умножения и деления с двумя числами: a + b, a - b, a * b, a / b.
                Данные передаются в одну строку
                Калькулятор принимает на вход арабские или римские числа от 1 до 10 включительно, не более.
                Калькулятор умеет работать только с арабскими или римскими цифрами одновременно
                Введите выражение""");

        try {
            inputStr = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String operation = Operation.getOperation(inputStr);
        inputArray[0] = inputStr.substring(0,inputStr.indexOf(operation));
        inputArray[1] = operation;
        inputArray[2] = inputStr.substring(inputStr.indexOf(operation) + 1);

        System.out.println(Calc.calculate(inputArray));
    }
}

class Calc {
    public static String calculate(String[] inputArray) throws Exception {
        int intResult;
        Numbers numbers = new Numbers(inputArray);
        intResult = switch (inputArray[1]) {
            case "+" -> numbers.numberOne + numbers.numberTwo;
            case "-" -> numbers.numberOne - numbers.numberTwo;
            case "/" -> numbers.numberOne / numbers.numberTwo;
            case "*" -> numbers.numberOne * numbers.numberTwo;
            default -> throw new IllegalStateException("Unexpected value: " + inputArray[1]);
        };

        if (numbers.isRoman) {
            if (intResult < 0) {
                throw new Exception("в римской системе нет отрицательных чисел");
            } else if (intResult == 0) {
                return "0";
            }
            return Numbers.intToRoman(intResult);
        } else return Integer.toString(intResult);
    }
}

class Operation {
    public static boolean isValidOperation (String inputStr) throws Exception {
        String[] operators = new String[]{"+", "-", "/", "*"};
        int posOperator = -1;
        int sumPosOperators = 0;
        for (String operator : operators) {
            if (inputStr.contains(operator) && inputStr.indexOf(operator) == inputStr.lastIndexOf(operator)) {
                posOperator = inputStr.indexOf(operator);
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
        for (String operator : operators) {
            if (inputStr.contains(operator)) {
                posOperator = inputStr.indexOf(operator);
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
    Numbers(String[] inData) throws Exception {
        try {
            numberOne = Integer.parseInt(inData[0]);
            numberTwo = Integer.parseInt(inData[2]);
        }
        catch (NumberFormatException exception) {
            numberOne = this.parseRoman(inData[0]);
            numberTwo = this.parseRoman(inData[2]);
            isRoman = true;
        }
        if (numberOne > 10 || numberTwo >10) {
            throw new Exception("Калькулятор должен принимать на вход числа от 1 до 10 включительно, не более");
        }
    }
    public int parseRoman(String inStr) {
        return switch (inStr.toUpperCase()) {
            case "I" -> 1;
            case "II" -> 2;
            case "III" -> 3;
            case "IV" -> 4;
            case "V" -> 5;
            case "VI" -> 6;
            case "VII" -> 7;
            case "VIII" -> 8;
            case "IX" -> 9;
            case "X" -> 10;
            default -> throw new IllegalStateException("Unexpected value: " + inStr);
        };
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