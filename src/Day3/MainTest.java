package Day3;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class MainTest {

    @Test
    public void canRecognizeMul(){
        assertEquals(Boolean.TRUE, Main.containsMul("mul("));
        assertEquals(Boolean.TRUE, Main.containsMul("mul(*"));
        assertEquals(Boolean.TRUE, Main.containsMul("smul(3"));
    }

    @Test
    public void canRecognizeWrongMul(){
        assertEquals(Boolean.FALSE, Main.containsMul("nul("));
        assertEquals(Boolean.FALSE, Main.containsMul("mul*("));
        assertEquals(Boolean.FALSE, Main.containsMul("mul)"));
    }

    @Test
    public void canRecognizeFullMulWith1Digit(){
        assertEquals(Boolean.TRUE, Main.containsFullMul("mul(1,1)"));
        assertEquals(Boolean.TRUE, Main.containsFullMul("mul(9,9)"));
        assertEquals(Boolean.TRUE, Main.containsFullMul("mul(0,0)"));
    }

    @Test
    public void canRecognizeFullMulWith3Digits(){
        assertEquals(Boolean.TRUE, Main.containsFullMul("mul(1,1)"));
        assertEquals(Boolean.TRUE, Main.containsFullMul("mul(12,12)"));
        assertEquals(Boolean.TRUE, Main.containsFullMul("mul(12,12)"));
        assertEquals(Boolean.TRUE, Main.containsFullMul("mul(123,123)"));
        assertEquals(Boolean.TRUE, Main.containsFullMul("mul(999,999)"));
        assertEquals(Boolean.TRUE, Main.containsFullMul("mul(0,0)"));
    }

    @Test
    public void canRecognizeWrongFullMul(){
        assertEquals(Boolean.FALSE, Main.containsFullMul("mul(1000,1)"));
        assertEquals(Boolean.FALSE, Main.containsFullMul("mul(1,1000)"));
        assertEquals(Boolean.FALSE, Main.containsFullMul("mul(1,A)"));
        assertEquals(Boolean.FALSE, Main.containsFullMul("mul(B,1)"));
        assertEquals(Boolean.FALSE, Main.containsFullMul("mul(e,1)"));
    }

    @Test
    public void canRecognizeMulWithExtra(){
        assertEquals(Boolean.TRUE, Main.containsFullMul("amul(1,1)"));
        assertEquals(Boolean.TRUE, Main.containsFullMul("mul(1,1)b"));
        assertEquals(Boolean.TRUE, Main.containsFullMul("amul(1,1)b"));
        assertEquals(Boolean.TRUE, Main.containsFullMul("@#$%@#%mul(1,1)!@#$!#^$"));
    }

    @Test
    public void canMatchNumbers(){
        assertArrayEquals(new int[]{1,1}, Main.getMulNumbers("mul(1,1)").getFirst());
        assertArrayEquals(new int[]{0,0}, Main.getMulNumbers("mul(0,0)").getFirst());
        assertArrayEquals(new int[]{123,123}, Main.getMulNumbers("mul(123,123)").getFirst());
    }

    @Test
    public void canMatchMultipleNumbers(){
        assertArrayEquals(new int[]{1,1}, Main.getMulNumbers("mul(1,1)mul(1,1)").get(0));
        assertArrayEquals(new int[]{2,2}, Main.getMulNumbers("mul(1,1)mul(2,2)").get(1));
        assertArrayEquals(new int[]{6,6}, Main.getMulNumbers("mul(1,1)mul(2,2)mul(2,2)mul(2,2)mul(2,2)mul(6,6)").get(5));
        assertArrayEquals(new int[]{6,6}, Main.getMulNumbers("@$%^&#mul(1,1)@#$%$#mul(2,2)@#$%@#$mul(2,2)$%^&@mul(2,2)!$#%^&@mul(2,2)@#$%&^&@mul(6,6)").get(5));
    }

    @Test
    public void  canSplitDo(){
        assertEquals(new String[]{"test1","test2"},Main.grabDoStrings("test1do()test2"));
        assertEquals(new String[]{"","test1","test2"},Main.grabDoStrings("do()test1do()test2"));
        assertEquals(new String[]{"test1","test2"},Main.grabDoStrings("test1do()test2do()"));
    }

    @Test
    public void canStripDont(){
        assertEquals("test1",Main.stripDont("test1don't()test2"));
        assertEquals("",Main.stripDont("don't()test1"));
        assertEquals("",Main.stripDont("don't()"));
    }

    @Test
    public void canStripDonts(){
        assertEquals(new String[]{"test0","test1",""},Main.stripDonts(new String[]{"test0","test1don't()test2", "don't()test3"}));
    }

}
