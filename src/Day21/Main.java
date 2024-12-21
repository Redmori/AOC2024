package Day21;

import AOCutil.AOC;

import java.util.ArrayList;

public class Main{

        public static void main(String[] args) {
                String[] input = AOC.input("src/Day21/input.txt");

                char[][] chars = AOC.convertStringToChar(input);

                Keypad kp = createKeypads();

                ArrayList<ArrayList<Character>> inputs = convertInput(chars);

                long p1 = calculateComplexity(kp, inputs);

                System.out.println("Day 21 Part 1: " + p1);

                Keypad kp2 = createManyKeypads(7);

                ArrayList<ArrayList<Character>> inputs2 = convertInput(chars);

                long p2 = calculateComplexity(kp2, inputs2);

                System.out.println("Day 21 Part 2: " + p2);


                ArrayList<ArrayList<Character>> inputs3 = convertInput(chars);

                //TEMP first one only
                long p3 = fastComplexity(inputs3, 15);

                System.out.println("Day 21 Part 3: " + p3);

        }

        private static long fastComplexity(ArrayList<ArrayList<Character>> inputs, int numberOfKeypads) {
                long sum = 0;
                Keypad kp = createManyKeypads(0);
                for(ArrayList<Character> input : inputs){
                        int number = getNumericPart(input);
                        ArrayList<Character> intermediateInput = kp.getInput(input).getFirst();
                        for (int i = 0; i < numberOfKeypads; i++) {
                                intermediateInput = getInput(intermediateInput);
                        }
                        sum += intermediateInput.size() * (long) number;
                }
                return sum;
        }

        private static ArrayList<Character> getInput(ArrayList<Character> output) {
                System.out.println("output.size() = " + output.size());
                
                int[] pointer = new int[]{0,2};
                int[] avoid = new int[]{0,0};

                ArrayList<Character> input = new ArrayList<>();

                while(output.size() > 0){
                        int[] token = getTokenLocation(output.getFirst());

                        int dy = token[0] - pointer[0];
                        int dx = token[1] - pointer[1];

                        boolean horizontalPrio = false;
                        boolean verticalPrio = false;
                        if(avoid[1] == pointer[1] && avoid[0] == token[0]){
                                verticalPrio = true;
                        }
                        if(avoid[0] == pointer[0] && avoid[1] ==  token[1]){
                                horizontalPrio = true;
                        }

                        if(verticalPrio){
                                if (dy < 0) {
                                        for (int i = 0; i < Math.abs(dy); i++) {
                                                input.add('^');
                                        }
                                }
                                if (dy > 0) {
                                        for (int i = 0; i < Math.abs(dy); i++) {
                                                input.add('v');
                                        }
                                }
                                if (dx < 0) {
                                        for (int i = 0; i < Math.abs(dx); i++) {
                                                input.add('<');
                                        }
                                }
                                if (dx > 0) {
                                        for (int i = 0; i < Math.abs(dx); i++) {
                                                input.add('>');
                                        }
                                }
                        } else if (horizontalPrio) {
                                if (dx < 0) {
                                        for (int i = 0; i < Math.abs(dx); i++) {
                                                input.add('<');
                                        }
                                }
                                if (dx > 0) {
                                        for (int i = 0; i < Math.abs(dx); i++) {
                                                input.add('>');
                                        }
                                }
                                if (dy < 0) {
                                        for (int i = 0; i < Math.abs(dy); i++) {
                                                input.add('^');
                                        }
                                }
                                if (dy > 0) {
                                        for (int i = 0; i < Math.abs(dy); i++) {
                                                input.add('v');
                                        }
                                }
                        }else {
                                if (dx < 0) {
                                        for (int i = 0; i < Math.abs(dx); i++) {
                                                input.add('<');
                                        }
                                }
                                if (dy < 0) {
                                        for (int i = 0; i < Math.abs(dy); i++) {
                                                input.add('^');
                                        }
                                }
                                if (dy > 0) {
                                        for (int i = 0; i < Math.abs(dy); i++) {
                                                input.add('v');
                                        }
                                }
                                if (dx > 0) {
                                        for (int i = 0; i < Math.abs(dx); i++) {
                                                input.add('>');
                                        }
                                }
                        }
                        input.add('A');
                        pointer = token;

                        output.removeFirst();
                }

                return input;
        }

        private static int[] getTokenLocation(Character token) {
                switch(token){
                        case '^':
                                return new int[]{0,1};
                        case '<':
                                return new int[]{1,0};
                        case 'v':
                                return new int[]{1,1};
                        case '>':
                                return new int[]{1,2};
                        case 'A':
                                return new int[]{0,2};
                }
                return new int[]{-1,-1};
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

