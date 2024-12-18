package Day17;

import java.util.ArrayList;
import java.util.List;

public class Part2 {

    public static long combo(int operand, long[] Registers) {
        return switch (operand) {
            case 4 -> Registers[0];
            case 5 -> Registers[1];
            case 6 -> Registers[2];
            default -> operand;
        };
    }

    public static List<Integer> runProgram(List<Integer> program, long A) {

        long[] Registers = new long[]{A, 0L, 0L};

        int pointer = 0;
        List<Integer> output = new ArrayList<>();

        while (pointer < program.size()) {
            int opcode = program.get(pointer);
            int operand = program.get(pointer + 1);
            pointer += 2;

            switch (opcode) {
                case 0:
                    Registers[0] = Registers[0] / (1L << combo(operand, Registers));
                    break;
                case 1:
                    Registers[1] = Registers[1] ^ operand;
                    break;
                case 2:
                    Registers[1] = combo(operand, Registers) % 8;
                    break;
                case 3:
                    if(Registers[1] != 0){
                        pointer = operand;
                    }
                    break;
                case 4:
                    Registers[1] = Registers[1] ^ Registers[2];
                    break;
                case 5:
                    output.add((int) (combo(operand, Registers) % 8));
                    break;
                case 6:
                    Registers[1] = Registers[0] / (1L << combo(operand, Registers));
                    break;
                case 7:
                    Registers[2] = Registers[0] / (1L << combo(operand, Registers));
                    break;
            }
        }
        return output;
    }


    public static long reverseEngineer(List<Integer> loop, List<Integer> target, long midA) {
        if (target.isEmpty()) {
            return midA;
        }

        for (int next3Bits = 0; next3Bits < 8; next3Bits++) {
            long possibleA = midA * 8 + next3Bits;

            List<Integer> output = runProgram(loop, possibleA);
            if (!output.isEmpty() && output.get(output.size() - 1).equals(target.get(target.size() - 1))) {
                long result = reverseEngineer(loop, target.subList(0, target.size() - 1), possibleA);
                if (result != -1) {
                    return result;
                }
            }
        }
        return -1;
    }
}
