package Day21;

import AOCutil.AOC;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main{

        public static void main(String[] args) {
                String[] input = AOC.input("src/Day21/sampleinput.txt");

                char[][] chars = AOC.convertStringToChar(input);

                Keypad kp = createKeypads();

                ArrayList<ArrayList<Character>> inputs = convertInput(chars);

                int p1 = calculateComplexity(kp, inputs);

                System.out.println("Day 21 Part 1: " + p1);


        }

        private static int calculateComplexity(Keypad kp, ArrayList<ArrayList<Character>> inputs) {
                int sum = 0;

                for(ArrayList<Character> input : inputs){
                        int number = getNumericPart(input);
                        ArrayList<ArrayList<Character>> result =  kp.getChainedInput(input);
                        sum += result.getFirst().size() * number;
                        System.out.println(result.getFirst().size() + " * " + number);
                }

                return sum;
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

}

