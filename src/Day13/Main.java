package Day13;

import AOCutil.AOC;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("hello world");

        String[] input = AOC.input("src/Day13/input.txt");

        ArrayList<Equation> equations = generateEquations(input);

        int sum = evaluateEquations(equations);
        System.out.println("Day 13 Part 1: " + sum);


        ArrayList<Equation> equationsP2 = generateEquationsP2(input);
        long sum2 = evaluateEquationsP2(equationsP2);
        System.out.println("Day 13 Part 2: " + sum2);
            }

    private static long evaluateEquationsP2(ArrayList<Equation> equations) {
        long sum = 0;
        for(Equation equation : equations){
            long[] evaluationResults = equation.evaluate();
            long A = evaluationResults[0];
            long B = evaluationResults[1];
            //System.out.println("A=" + A + " B=" + B);
            if(A < 0L || B < 0L)
                continue;
            sum += 3L*A + B;
        }
        return sum;
    }


    private static ArrayList<Equation> generateEquationsP2(String[] input) {
        ArrayList<Equation> equations = new ArrayList<>();

        for(int i = 0; i < input.length; i += 4){
            int[] eqp1 = getConstants(input[i]);
            int[] eqp2 = getConstants(input[i+1]);
            int[] eqp3 = getResults(input[i+2]);

            Equation newEquation = new Equation(eqp1[0], eqp2[0], eqp1[1], eqp2[1], eqp3[0] + 10000000000000L, eqp3[1] + 10000000000000L);
            equations.add(newEquation);
            //System.out.println(newEquation);
        }
        return equations;
    }


    private static int evaluateEquations(ArrayList<Equation> equations){
        int sum = 0;
        for(Equation equation : equations){
            long[] evaluationResults = equation.evaluate();
            long A = evaluationResults[0];
            long B = evaluationResults[1];
            //System.out.println("A=" + A + " B=" + B);
            if(A > 100 || B > 100 || A == -1)
                continue;;
            sum += 3L*A + B;
        }
        return sum;
    }

    private static ArrayList<Equation> generateEquations(String[] input) {
        ArrayList<Equation> equations = new ArrayList<>();

        for(int i = 0; i < input.length; i += 4){
            int[] eqp1 = getConstants(input[i]);
            int[] eqp2 = getConstants(input[i+1]);
            int[] eqp3 = getResults(input[i+2]);

            Equation newEquation = new Equation(eqp1[0], eqp2[0], eqp1[1], eqp2[1], eqp3[0], eqp3[1]);
            equations.add(newEquation);
        }
        return equations;
    }

    private static int[] getConstants(String input){
        int[] c = new int[2];
        String[] split1 = input.split("\\+");
        c[0] = Integer.parseInt(split1[1].split(",")[0]);
        c[1] = Integer.parseInt(split1[2]);
        return c;
    }

    private static int[] getResults(String input){
        int[] d = new int[2];
        String[] split1 = input.split("=");
        d[0] = Integer.parseInt(split1[1].split(",")[0]);
        d[1] = Integer.parseInt(split1[2]);
        return d;
    }


}
