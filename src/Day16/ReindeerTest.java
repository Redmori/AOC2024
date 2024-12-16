package Day16;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ReindeerTest {

    @Test
    public void rotate(){
        Reindeer r = new Reindeer(0,0);
        r.direction = 0;
        r.rotate(true);
        assertEquals(1, r.direction );

        r.direction = 1;
        r.rotate(true);
        assertEquals(2, r.direction );

        r.direction = 1;
        r.rotate(false);
        assertEquals(0, r.direction );

        r.direction = 0;
        r.rotate(false);
        assertEquals(3, r.direction );

    }
}
