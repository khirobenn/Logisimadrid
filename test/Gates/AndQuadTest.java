package Gates;

import Circuit.QuadBool;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AndQuadTest {

    @Test
    public void testAnd_TrueTrue_ReturnsTrue() {
        assertEquals(QuadBool.TRUE, AndQuad.And(QuadBool.TRUE, QuadBool.TRUE));
    }

    @Test
    public void testAnd_TrueFalse_ReturnsFalse() {
        assertEquals(QuadBool.FALSE, AndQuad.And(QuadBool.TRUE, QuadBool.FALSE));
    }

    @Test
    public void testAnd_TrueNothing_ReturnsError() {
        assertEquals(QuadBool.ERROR, AndQuad.And(QuadBool.TRUE, QuadBool.NOTHING));
    }

    @Test
    public void testAnd_TrueError_ReturnsError() {
        assertEquals(QuadBool.ERROR, AndQuad.And(QuadBool.TRUE, QuadBool.ERROR));
    }

    @Test
    public void testAnd_FalseTrue_ReturnsFalse() {
        assertEquals(QuadBool.FALSE, AndQuad.And(QuadBool.FALSE, QuadBool.TRUE));
    }

    @Test
    public void testAnd_FalseFalse_ReturnsFalse() {
        assertEquals(QuadBool.FALSE, AndQuad.And(QuadBool.FALSE, QuadBool.FALSE));
    }

    @Test
    public void testAnd_FalseNothing_ReturnsFalse() {
        assertEquals(QuadBool.FALSE, AndQuad.And(QuadBool.FALSE, QuadBool.NOTHING));
    }

    @Test
    public void testAnd_FalseError_ReturnsError() {
        assertEquals(QuadBool.ERROR, AndQuad.And(QuadBool.FALSE, QuadBool.ERROR));
    }

    @Test
    public void testAnd_NothingTrue_ReturnsError() {
        assertEquals(QuadBool.ERROR, AndQuad.And(QuadBool.NOTHING, QuadBool.TRUE));
    }

    @Test
    public void testAnd_NothingFalse_ReturnsFalse() {
        assertEquals(QuadBool.FALSE, AndQuad.And(QuadBool.NOTHING, QuadBool.FALSE));
    }

    @Test
    public void testAnd_NothingNothing_ReturnsError() {
        assertEquals(QuadBool.ERROR, AndQuad.And(QuadBool.NOTHING, QuadBool.NOTHING));
    }

    @Test
    public void testAnd_NothingError_ReturnsError() {
        assertEquals(QuadBool.ERROR, AndQuad.And(QuadBool.NOTHING, QuadBool.ERROR));
    }

    @Test
    public void testAnd_ErrorTrue_ReturnsError() {
        assertEquals(QuadBool.ERROR, AndQuad.And(QuadBool.ERROR, QuadBool.TRUE));
    }

    @Test
    public void testAnd_ErrorFalse_ReturnsError() {
        assertEquals(QuadBool.ERROR, AndQuad.And(QuadBool.ERROR, QuadBool.FALSE));
    }

    @Test
    public void testAnd_ErrorNothing_ReturnsError() {
        assertEquals(QuadBool.ERROR, AndQuad.And(QuadBool.ERROR, QuadBool.NOTHING));
    }

    @Test
    public void testAnd_ErrorError_ReturnsError() {
        assertEquals(QuadBool.ERROR, AndQuad.And(QuadBool.ERROR, QuadBool.ERROR));
    }
}
