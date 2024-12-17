package Day17;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class MainTest {
    @Test
    public void example1(){
        Main.solveProgram("example1");
        assertEquals(1, Main.RegisterB);
    }

    @Test
    public void example2(){
        Main.solveProgram("example2");
        assertEquals(Integer.valueOf(0), Main.output.get(0));
        assertEquals(Integer.valueOf(1), Main.output.get(1));
        assertEquals(Integer.valueOf(2), Main.output.get(2));
    }

    @Test
    public void example3(){
        Main.solveProgram("example3");
        int[] results = new int[]{4,2,5,6,7,7,7,7,3,1,0};
        assertOutput(results);
        assertEquals(0, Main.RegisterA);
    }

    @Test
    public void example4(){
        Main.solveProgram("example4");
        assertEquals(26, Main.RegisterB);
    }

    @Test
    public void example5(){
        Main.solveProgram("example5");
        assertEquals(44354, Main.RegisterB);
    }

    @Test
    public void sampleinput(){
        Main.solveProgram("sampleinput");
        int[] results = new int[]{4,6,3,5,6,3,5,2,1,0};
        assertOutput(results);
    }

    @Test
    public void examplep2() {
        Main.solveProgram("examplep2");
        int[] results = new int[]{0,3,5,4,3,0};
        assertOutput(results);
    }


    public void assertOutput(int[] expected){
        for (int i = 0; i < expected.length; i++) {
            assertEquals(Integer.valueOf(expected[i]), Main.output.get(i));
        }
    }
}
