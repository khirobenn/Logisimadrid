package test.Gates;

import Circuit.QuadBool;
import Gates.NotQuad;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NotQuadTest {

    @Test
    public void testNotTrueReturnsFalse() {
        assertEquals(QuadBool.FALSE, NotQuad.Not(QuadBool.TRUE));
    }

    @Test
    public void testNotFalseReturnsTrue() {
        assertEquals(QuadBool.TRUE, NotQuad.Not(QuadBool.FALSE));
    }

    @Test
    public void testNotErrorReturnsError() {
        assertEquals(QuadBool.ERROR, NotQuad.Not(QuadBool.ERROR));
    }

    @Test
    public void testNotNothingReturnsError() {
        assertEquals(QuadBool.ERROR, NotQuad.Not(QuadBool.NOTHING));
    }

    
}
