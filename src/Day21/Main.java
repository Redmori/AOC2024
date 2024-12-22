package Day21;

import AOCutil.AOC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Main{

        public static void main(String[] args) {
                String[] input = AOC.input("src/Day21/input.txt");

                char[][] chars = AOC.convertStringToChar(input);

                Keypad kp = createKeypads();

                ArrayList<ArrayList<Character>> inputs = convertInput(chars);

                long p1 = calculateComplexity(kp, inputs);

                System.out.println("Day 21 Part 1: " + p1);

                ArrayList<ArrayList<Character>> inputs4 = convertInput(chars);

                long p4 = fasterComplexity(inputs4, 25);

                System.out.println("Day 21 Part 4: " + p4);


        }

        private static long fasterComplexity(ArrayList<ArrayList<Character>> inputs, int numberOfKeypads) {
                long sum = 0;
                Keypad kp = createManyKeypads(0);
                for(ArrayList<Character> input : inputs){
                        int number = getNumericPart(input);
                        ArrayList<Character> intermediateInput = kp.getInput(input).getFirst();
                        String convertedInput = intermediateInput.stream()
                                .map(String::valueOf)
                                .collect(Collectors.joining());
                        long smartResult = smartInput(convertedInput, numberOfKeypads);
                        sum += smartResult * (long) number;
                }
                return sum;
        }

        public static String[] seqs = new String[26];

        public static HashMap<String, Long>[] memo;

        static {
                memo = new HashMap[26];
                for (int i = 0; i < memo.length; i++) {
                        memo[i] = new HashMap<>();
                }
        }

        public static long smartInput(String output, int depth) {
                //System.out.println(output + " at depth " + depth);
                seqs[depth] += output;
                long sum = 0;
                if(depth == 0)
                        return output.length();
                //String lastCharAtDepth = lastChar[depth];

                output = "A" + output;

                //lastChar[depth] = output.substring(output.length() - 1);

                for (int i = 0; i < output.length() - 1; i++) {
                        String segment = output.substring(i, i + 2);
                        if(memo[depth].containsKey(segment)) {
                                sum += memo[depth].get(segment);
                        }else {
                                String path = getPath(segment);
                                long addition = smartInput(path, depth - 1);
                                sum += addition;
                                memo[depth].put(segment, addition);
                        }
                }
                return sum;
        }


        private static String getPath(String move){

            switch(move){
                    case "<^":
                            return ">^A";
                    case "<A":
                            return ">>^A";
                    case "<<":
                            return "A";
                    case "<v":
                            return ">A";
                    case "<>":
                            return ">>A";
                    case "^^":
                            return "A";
                    case "^A":
                            return ">A";
                    case "^<":
                            return "v<A";
                    case "^v":
                            return "vA";
                    case "^>":
                            return "v>A";
                    case "A^":
                            return "<A";
                    case "AA":
                            return "A";
                    case "A<":
                            return "v<<A";
                    case "Av":
                            return "<vA";
                    case "A>":
                            return "vA";
                    case "v^":
                            return "^A";
                    case "vA":
                            return "^>A";
                    case "v<":
                            return "<A";
                    case "vv":
                            return "A";
                    case "v>":
                            return ">A";
                    case ">^":
                            return "<^A";
                    case ">A":
                            return "^A";
                    case "><":
                            return "<<A";
                    case ">v":
                            return "<A";
                    case ">>":
                            return "A";
            }
                System.out.println("error in reading path");
            return "";
        }


        private static long calculateComplexity(Keypad kp, ArrayList<ArrayList<Character>> inputs) {
                long sum = 0;

                for(ArrayList<Character> input : inputs){
                        int number = getNumericPart(input);
                        ArrayList<ArrayList<Character>> result =  kp.getChainedInput(input);
                        sum += getShortestAray(result) * number;
                        System.out.println(result.getFirst().size() + " * " + number);
                }

                return sum;
        }

        private static long getShortestAray(ArrayList<ArrayList<Character>> arrays){
                long shortest = Long.MAX_VALUE;
                for(ArrayList<Character> a : arrays){
                        if(a.size() < shortest){
                                shortest = a.size();
                        }
                }
                return shortest;
        }

        private static int getNumericPart(ArrayList<Character> input) {
                StringBuilder numericString = new StringBuilder();

                for (Character c : input) {
                        if (Character.isDigit(c)) {
                                numericString.append(c);
                        }
                }

                return Integer.parseInt(numericString.toString());
        }


        private static ArrayList<ArrayList<Character>> convertInput(char[][] chars) {
                ArrayList<ArrayList<Character>> inputs = new ArrayList<>();

                for(char[] row : chars){
                        ArrayList<Character> newSequence = new ArrayList<>();
                        for(char c : row){
                                newSequence.add(c);
                        }
                        inputs.add(newSequence);
                }
                return inputs;
        }


        public static Keypad createKeypads(){
                Keypad kp = new Keypad();
                Keypad kp2 = new Keypad(kp);
                Keypad kp3 = new Keypad(kp2);

                return kp3;
        }

        public static Keypad createManyKeypads(int amount){
            Keypad previousKP = new Keypad();
                for (int i = 0; i < amount; i++) {
                    previousKP = new Keypad(previousKP);
                }

                return previousKP;
        }

}

