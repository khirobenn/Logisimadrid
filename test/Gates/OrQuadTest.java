package Gates;

import Circuit.QuadBool;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrQuadTest {

    @Test
    public void testOr_TrueTrue_ReturnsTrue() {
        assertEquals(QuadBool.TRUE, OrQuad.Or(QuadBool.TRUE, QuadBool.TRUE));
    }

    @Test
    public void testOr_TrueFalse_ReturnsTrue() {
        assertEquals(QuadBool.TRUE, OrQuad.Or(QuadBool.TRUE, QuadBool.FALSE));
    }

    @Test
    public void testOr_FalseTrue_ReturnsTrue() {
        assertEquals(QuadBool.TRUE, OrQuad.Or(QuadBool.FALSE, QuadBool.TRUE));
    }

    @Test
    public void testOr_FalseFalse_ReturnsFalse() {
        assertEquals(QuadBool.FALSE, OrQuad.Or(QuadBool.FALSE, QuadBool.FALSE));
    }

    @Test
    public void testOr_FalseNothing_ReturnsError() {
        assertEquals(QuadBool.ERROR, OrQuad.Or(QuadBool.FALSE, QuadBool.NOTHING));
    }

    @Test
    public void testOr_TrueError_ReturnsTrue() {
        assertEquals(QuadBool.TRUE, OrQuad.Or(QuadBool.TRUE, QuadBool.ERROR));
    }

    @Test
    public void testOr_ErrorAny_ReturnsError() {
        assertEquals(QuadBool.ERROR, OrQuad.Or(QuadBool.ERROR, QuadBool.TRUE));
        assertEquals(QuadBool.ERROR, OrQuad.Or(QuadBool.ERROR, QuadBool.FALSE));
        assertEquals(QuadBool.ERROR, OrQuad.Or(QuadBool.ERROR, QuadBool.NOTHING));
    }

    @Test
    public void testOr_NothingTrue_ReturnsTrue() {
        assertEquals(QuadBool.TRUE, OrQuad.Or(QuadBool.NOTHING, QuadBool.TRUE));
    }

    @Test
    public void testOr_NothingFalse_ReturnsError() {
        assertEquals(QuadBool.ERROR, OrQuad.Or(QuadBool.NOTHING, QuadBool.FALSE));
    }

    @Test
    public void testOr_NothingNothing_ReturnsError() {
        assertEquals(QuadBool.ERROR, OrQuad.Or(QuadBool.NOTHING, QuadBool.NOTHING));
    }

    @Test
    public void testOr_NullInput_ReturnsNull() {
        assertNull(OrQuad.Or(null, QuadBool.TRUE));
        assertNull(OrQuad.Or(QuadBool.FALSE, null));
        assertNull(OrQuad.Or(null, null));
    }
}
