package Day17;

import AOCutil.AOC;

import java.util.ArrayList;

public class Main {

    public static long RegisterA;
    public static long RegisterB;
    public static long RegisterC;

    static int[] program;

    static int pointer = 0;

    static long[] inputValues;

    static ArrayList<Long> output = new ArrayList<>();

    public static void main(String[] args) {
        String output = solveProgram("input");
        System.out.println("Day 17 Part 1: " + output);

        long beginValue = 1;

        setupProgram("input");

        long result = bruteForce(beginValue);
    }

    public static long bruteForce(long beginValue){
        long i = beginValue;
        int difference = 10;
        while(difference != 0){
            difference = iterateProgram(i);

            System.out.println("A = " + i + " diff = " + difference + " output length = " + output.size() + " output: " + generateOutput());
            i = adjust(i, difference);
        }
        return i;
    }

    private static long adjust(long i, int difference) {
        if(output.size() < program.length){
            i *= 10;
        } else if (output.size() > program.length) {
            i /= 10;
        } else{
            i++;
        }

        return i;
    }

    public static void setupProgram(String path){
        String[] input = AOC.input("src/Day17/" + path + ".txt");
        parseInput(input);

        inputValues = new long[]{RegisterB, RegisterC};
    }

    public static int iterateProgram(long registerValue){
        resetProgram(registerValue);

        runProgram();

        int diff = compareOutput();

        return diff;
    }

    private static int compareOutput() {
        int diffSum = 0;
        int programLength = program.length;
        int outputLength = output.size();
        for (int i = 0; i < Math.max(programLength,outputLength); i++) {
            if(i >= programLength){
                diffSum += output.get(i);
            } else if (i >= outputLength) {
                diffSum += program[i];
            }else {
                diffSum += Math.abs(program[i] - output.get(i));
            }
        }
        return diffSum;
    }

    private static void resetProgram(long registerValue) {
        RegisterA = registerValue;
        RegisterB = inputValues[0];
        RegisterC = inputValues[1];

        pointer = 0;
        output.clear();
    }

    public static String solveProgram(String path){
        String[] input = AOC.input("src/Day17/" + path + ".txt");
        parseInput(input);

        printInformation();

        runProgram();

        String output = generateOutput();

        System.out.println(output);
        return output;
    }

    private static String generateOutput() {
        StringBuilder string = new StringBuilder("Output: ");
        for(Long nr : output){
            string.append(nr);
            string.append(",");
        }
        return string.toString();
    }

    private static void runProgram() {
        while(doInstruction()){
        }
    }

    private static boolean doInstruction() {
        //System.out.println("pointer = " + pointer);
        int opcode = program[pointer];
        int operand = program[pointer + 1];

        runInstruction(opcode,operand);

        pointer += 2;
        return pointer < program.length - 1;
    }

    private static void runInstruction(int opcode, int operand) {
        switch(opcode){
            case 0:
                adv(operand);
                break;
            case 1:
                bxl(operand);
                break;
            case 2:
                bst(operand);
                break;
            case 3:
                jnz(operand);
                break;
            case 4:
                bxc(operand);
                break;
            case 5:
                out(operand);
                break;
            case 6:
                bdv(operand);
                break;
            case 7:
                cdv(operand);
                break;
        }
    }

    private static long combo(int operand) {
        return switch (operand) {
            case 4 -> RegisterA;
            case 5 -> RegisterB;
            case 6 -> RegisterC;
            default -> operand;
        };
    }

    private static void adv(int operand) {
        RegisterA = division(operand);
    }

    private static int division(int operand) {
        long numerator = RegisterA;
        int denominator = (int) Math.pow(2,combo(operand));
        System.out.println("RegisterB = " + RegisterB);;
        System.out.println("operand = " + operand);
        System.out.println("combo(operand) = " + combo(operand));
        System.out.println("denominator = " + denominator);

        long result = numerator/denominator;
        //System.out.println("division result = " + result);
        //TODO truncate
        return (int)result;
    }

    private static void bxl(int operand) {
        RegisterB = bitwiseXOR(RegisterB,operand);
    }

    private static void bst(int operand) {
        //TODO keep lowest 3 bits
        RegisterB = combo(operand)%8;
    }

    private static void jnz(int operand) {
        if(RegisterA==0){
        }else{
            //System.out.println("setting pointer to " + operand);
            pointer = operand - 2;
        }
    }

    private static void bxc(int operand) {
        RegisterB = bitwiseXOR(RegisterB,RegisterC);
    }

    private static void out(int operand) {
        //System.out.println("operand = " + operand);
        long value = combo(operand)%8;
        //System.out.println("value = " + value + " and RegB = " + RegisterB%8);
        //System.out.println("value = " + value);
        //System.out.println("outputting: " + value);
        output.add(value);
    }

    private static void bdv(int operand) {
        RegisterB = (int) division(operand);
    }

    private static void cdv(int operand) {
        RegisterC = (int) division(operand);
    }

    private static void printInformation(){
        System.out.println(registerToString() + "\n" + programToString());
    }

    private static String registerToString(){
        return "Register A: " + RegisterA + "\n" + "Register B: " + RegisterB + "\n" + "Register C: " + RegisterC + "\n";
    }

    private static String programToString(){
        String programString = "Program: ";
        for(int step : program){
            programString += step + ",";
        }
        return programString;
    }

    private static void parseInput(String[] input) {
        RegisterA = Integer.parseInt(input[0].split(" ")[2]);
        RegisterB = Integer.parseInt(input[1].split(" ")[2]);
        RegisterC = Integer.parseInt(input[2].split(" ")[2]);
        String programString = input[4].split(" ")[1];
        String[] nrs = programString.split(",");
        program = new int[nrs.length];
        for (int i = 0; i < program.length; i++) {
            program[i] = Integer.parseInt(nrs[i]);
        }
        pointer = 0;
        output.clear();
    }

    private static long bitwiseXOR(long value1, long value2){
        return value1^value2;
    }
}






