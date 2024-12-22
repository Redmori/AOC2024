package Day21;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class KeypadTest {

    @Test
    public void testNumKeypadDiagonal(){
        ArrayList<ArrayList<Character>> input = prepareKeypad(new char[]{'2','4','8'});
        System.out.println("testNumKeypadDiagonal " + input);
        assertEquals(8, input.size());
    }

    @Test
    public void testNumKeypadEasy(){
        ArrayList<ArrayList<Character>> input = prepareKeypad(new char[]{'3','2','1'});
        System.out.println("testNumKeypadEasy " + input);
        assertEquals(1, input.size());
    }

    @Test
    public void testNumKeypadAvoidX(){
        ArrayList<ArrayList<Character>> input = prepareKeypad(new char[]{'1','A'});
        System.out.println("testNumKeypadAvoidX " + input);
        assertEquals(1, input.size());
    }

    @Test
    public void testNumKeypadAvoidX1(){
        ArrayList<ArrayList<Character>> input = prepareKeypad(new char[]{'1'});
        System.out.println("testNumKeypadAvoidX1 " + input);
        assertEquals(1, input.size());
        ArrayList<Character> expected = new ArrayList<>(Arrays.asList('^', '<', '<', 'A'));
        assertEquals(expected, input.getFirst());
    }

    @Test
    public void testNumKeypadNoMove(){
        ArrayList<ArrayList<Character>> input = prepareKeypad(new char[]{'A','A','A'});
        System.out.println("testNumKeypadNoMove " + input);
        assertEquals(1, input.size());
    }


    @Test
    public void testArrowKeypad(){
        ArrayList<ArrayList<Character>> input = prepareArrowpad(new char[]{'v','^','A'});
        System.out.println("testArrowKeypad " + input);
        assertEquals(2, input.size());
    }

    @Test
    public void testChainedKeypads(){
        ArrayList<ArrayList<Character>> input = prepare2Keypads(new char[]{'1'});
        System.out.println("testChainedKeypads " + input);
        ArrayList<Character> expected = new ArrayList<>(Arrays.asList('<', 'A', 'v', '<', 'A', 'A', '>', '>', '^', 'A'));
        assertEquals(expected, input.getFirst());
        assertEquals(1, input.size());
    }

    @Test
    public void testSample1pad(){
        ArrayList<ArrayList<Character>> input = prepareKeypad(new char[]{'0','2','9','A'});
        System.out.println("testSample1pad " + input);
        ArrayList<String> actualStrings = Keypad.allSequenceToString(input);
        String expectedString = "<A^A>^^AvvvA";
        System.out.println("actual " + actualStrings);
        System.out.println("expected "+ expectedString);
        assertEquals(true, actualStrings.contains(expectedString));
    }

    @Test
    public void testSample2pad(){
        ArrayList<ArrayList<Character>> input = prepare2Keypads(new char[]{'0','2','9','A'});
        System.out.println("testSample2pad " + input);
        ArrayList<String> actualStrings = Keypad.allSequenceToString(input);
        String expectedString = "v<<A>>^A<A>AvA<^AA>A<vAAA>^A";
        System.out.println("actual " + actualStrings);
        System.out.println("expected "+ expectedString);
        assertEquals(true, actualStrings.contains(expectedString));
    }

    @Ignore
    @Test
    public void testSample3pad(){
        ArrayList<ArrayList<Character>> input = prepareAllKeypads(new char[]{'0','2','9','A'});
        System.out.println("testSample3pad " + input);
        ArrayList<String> actualStrings = Keypad.allSequenceToString(input);
        String expectedString = "<vA<AA>>^AvAA<^A>A<v<A>>^AvA^A<vA>^A<v<A>^A>AAvA^A<v<A>A>^AAAvA<^A>A";
        System.out.println("actual " + actualStrings);
        System.out.println("expected "+ expectedString);
        assertEquals(true, actualStrings.contains(expectedString));
    }

    @Test
    public void samplerestult1(){
        ArrayList<ArrayList<Character>> input = prepareAllKeypads(new char[]{'0','2','9','A'});
        int complexity = input.getFirst().size();
        assertEquals(68, complexity);
    }

    @Test
    public void samplerestult2(){
        ArrayList<ArrayList<Character>> input = prepareAllKeypads(new char[]{'9','8','0','A'});
        int complexity = input.getFirst().size();
        assertEquals(60, complexity);
    }

    @Test
    public void samplerestult3(){
        ArrayList<ArrayList<Character>> input = prepareAllKeypads(new char[]{'1','7','9','A'});
        int complexity = input.getFirst().size();
        assertEquals(68, complexity);
    }

    @Test
    public void samplerestult4(){
        ArrayList<ArrayList<Character>> input = prepareAllKeypads(new char[]{'4','5','6','A'});
        int complexity = input.getFirst().size();
        assertEquals(64, complexity);
    }

    @Test
    public void samplerestult5(){
        ArrayList<ArrayList<Character>> input = prepareAllKeypads(new char[]{'3','7','9','A'});
        ArrayList<String> actuaStrings = Keypad.allSequenceToString(input);
        System.out.println(actuaStrings.get(0));
        int complexity = input.getFirst().size();
        assertEquals(64, complexity);
    }

    @Ignore
    @Test
    public void testAllChainedKeypads(){
        ArrayList<ArrayList<Character>> input = prepareAllKeypads(new char[]{'0'});
        System.out.println("testAllChainedKeypads " + input);
        ArrayList<String> actuaStrings = Keypad.allSequenceToString(input);
        String expectedString = "<vA<AA>>^AvAA<^A>A<v<A>>^AvA^A<vA>^A<v<A>^A>AAvA^A<v<A>A>^AAAvA<^A>A";
        System.out.println("expected: " + expectedString);
        System.out.println("actual " + actuaStrings);

        assertEquals(true, actuaStrings.contains(expectedString));
        //assertEquals(expected, input.getFirst());
        //assertEquals(1, input.size());
    }

    @Ignore
    @Test
    public void sampletest1(){
        ArrayList<ArrayList<Character>> input = prepareAllKeypads(new char[]{'1'});
        System.out.println("testAllChainedKeypads " + input);
        ArrayList<String> actuaStrings = Keypad.allSequenceToString(input);
        String expectedString = "<vA<AA>>^AvAA<^A>A<v<A>>^AvA^A<vA>^A<v<A>^A>AAvA^A<v<A>A>^AAAvA<^A>A";
        System.out.println("expected: " + expectedString);
        System.out.println("actual " + actuaStrings);

        assertEquals(true, actuaStrings.contains(expectedString));
        //assertEquals(expected, input.getFirst());
        //assertEquals(1, input.size());
    }



    @Test
    public void testSmartInput1(){
        long amount = Main.smartInput("<A^A>^^AvvvA", 2);
        System.out.println("amount = " + amount);
        System.out.println("Sequence: " + Main.seqs[0]);
        assertEquals(68, amount);

    }

    @Test
    public void testSmartInput2(){
        long amount = Main.smartInput("^^^A<AvvvA>A", 2);
        System.out.println("amount = " + amount);
        System.out.println("Sequence: " + Main.seqs[0]);
        assertEquals(60, amount);
    }

    @Test
    public void testSmartInput3(){
        long amount = Main.smartInput("^<<A^^A>>AvvvA", 2);
        System.out.println("amount = " + amount);
        assertEquals(68, amount);
    }

    @Test
    public void testSmartInput4(){
        long amount = Main.smartInput("^^<<A>A>AvvA", 2);
        System.out.println("amount = " + amount);
        assertEquals(64, amount);
    }

    @Test
    public void testSmartInput5(){
        long amount = Main.smartInput("^A<<^^A>>AvvvA", 2);
        System.out.println("amount = " + amount);
        assertEquals(64, amount);
    }



    private static ArrayList<ArrayList<Character>> prepareKeypad(char[] keys) {
        Keypad kp = new Keypad();
        ArrayList<Character> list = new ArrayList<>();
        for(char key : keys){
            list.add(key);
        }
        return kp.getInput(list);
    }

    private static ArrayList<ArrayList<Character>> prepareArrowpad(char[] keys) {
        Keypad kp1 = new Keypad();
        Keypad kp2 = new Keypad(kp1);
        ArrayList<Character> list = new ArrayList<>();
        for(char key : keys){
            list.add(key);
        }
        return kp2.getInput(list);
    }

    private static ArrayList<ArrayList<Character>> prepare2Keypads(char[] keys) {
        Keypad kp = new Keypad();
        Keypad kp2 = new Keypad(kp);
        ArrayList<Character> list = new ArrayList<>();
        for(char key : keys){
            list.add(key);
        }
        return kp2.getChainedInput(list);
    }

    private static ArrayList<ArrayList<Character>> prepareAllKeypads(char[] keys) {
        Keypad kp = new Keypad();
        Keypad kp2 = new Keypad(kp);
        Keypad kp3 = new Keypad(kp2);
        ArrayList<Character> list = new ArrayList<>();
        for(char key : keys){
            list.add(key);
        }
        return kp3.getChainedInput(list);
    }

}
