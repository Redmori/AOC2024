package Day7;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class TestMain {
    @Test
    public void validateValidate(){
        assertEquals(true, new Equation(4,new ArrayList<>(Arrays.asList(2,2))).validate(1));
        assertEquals(true, new Equation(8,new ArrayList<>(Arrays.asList(2,2,2))).validate(1));
        assertEquals(true, new Equation(6,new ArrayList<>(Arrays.asList(2,2,2))).validate(1));
        assertEquals(true, new Equation(120,new ArrayList<>(Arrays.asList(5,4,6))).validate(1));
        assertEquals(true, new Equation(26,new ArrayList<>(Arrays.asList(5,4,6))).validate(1));
        assertEquals(true, new Equation(54,new ArrayList<>(Arrays.asList(5,4,6))).validate(1));
        assertEquals(true, new Equation(15,new ArrayList<>(Arrays.asList(5,4,6))).validate(1));
        assertEquals(false, new Equation(14,new ArrayList<>(Arrays.asList(5,4,6))).validate(1));
        assertEquals(false, new Equation(53,new ArrayList<>(Arrays.asList(5,4,6))).validate(1));
        assertEquals(false, new Equation(121,new ArrayList<>(Arrays.asList(5,4,6))).validate(1));
        assertEquals(false, new Equation(10,new ArrayList<>(Arrays.asList(2,2,2))).validate(1));
    }

    @Test
    public void validatePart21(){
        assertEquals(true, new Equation(156,new ArrayList<>(Arrays.asList(15,6))).validate(2));

    }

    @Test
    public void validatePart22(){
        assertEquals(true, new Equation(7290,new ArrayList<>(Arrays.asList(6,8,6,15))).validate(2));

    }

    @Test
    public void validatePart23(){
        assertEquals(true, new Equation(192,new ArrayList<>(Arrays.asList(17,8,14))).validate(2));

    }


}
