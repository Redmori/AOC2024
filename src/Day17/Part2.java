package Day17;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part2 {
    private static final String REG_A = "A";
    private static final String REG_B = "B";
    private static final String REG_C = "C";

    public static long comboOperand(int operand, Map<String, Long> regs) {
        return switch (operand) {
            case 4 -> regs.get(REG_A);
            case 5 -> regs.get(REG_B);
            case 6 -> regs.get(REG_C);
            default -> operand;
        };
    }

    public static List<Integer> runProgram(List<Integer> program, long initialA) {
        Map<String, Long> regs = new HashMap<>();
        regs.put(REG_A, initialA);
        regs.put(REG_B, 0L);
        regs.put(REG_C, 0L);

        long[] Registers = new long[]{initialA, 0L, 0L};

        int pointer = 0;
        List<Integer> output = new ArrayList<>();

        while (pointer < program.size()) {
            int opcode = program.get(pointer);
            int operand = program.get(pointer + 1);
            pointer += 2;

            switch (opcode) {
                case 0:
                    regs.put(REG_A, regs.get(REG_A) / (1L << comboOperand(operand, regs)));
                    break;
                case 1:
                    regs.put(REG_B, regs.get(REG_B) ^ operand);
                    break;
                case 2:
                    regs.put(REG_B, comboOperand(operand, regs) % 8);
                    break;
                case 3:
                    if (regs.get(REG_A) != 0) {
                        pointer = operand;
                    }
                    break;
                case 4:
                    regs.put(REG_B, regs.get(REG_B) ^ regs.get(REG_C));
                    break;
                case 5:
                    output.add((int) (comboOperand(operand, regs) % 8));
                    break;
                case 6:
                    regs.put(REG_B, regs.get(REG_A) / (1L << comboOperand(operand, regs)));
                    break;
                case 7:
                    regs.put(REG_C, regs.get(REG_A) / (1L << comboOperand(operand, regs)));
                    break;
            }
        }

        return output;
    }


    public static long reverseEngineer(List<Integer> loop, List<Integer> target, long aSoFar) {
        if (target.isEmpty()) {
            return aSoFar;
        }

        for (int next3Bits = 0; next3Bits < 8; next3Bits++) {
            long candidateA = aSoFar * 8 + next3Bits;

            // Extend register A by 3 bits and see if we output the last token in the target
            List<Integer> programOutput = runProgram(loop, candidateA);
            if (!programOutput.isEmpty() && programOutput.get(programOutput.size() - 1).equals(target.get(target.size() - 1))) {
                try {
                    return reverseEngineer(loop, target.subList(0, target.size() - 1), candidateA);
                } catch (IllegalStateException e) {
                    continue;
                }
            }
        }

            throw new IllegalStateException("No valid value for register A found");
    }
}
