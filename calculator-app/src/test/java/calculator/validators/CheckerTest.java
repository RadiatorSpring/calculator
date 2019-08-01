package calculator.validators;

import org.junit.Before;
import org.junit.Test;

import java.util.IllegalFormatException;

import static org.junit.Assert.*;

public class CheckerTest {

    private Checker checker;

    @Before
    public void setChecker() {
        checker = new Checker();
    }

    @Test
    public void testIsNumber() {
        assertTrue(checker.isNumber("1"));
        assertFalse(checker.isNumber("-"));
        assertFalse(checker.isNumber(" "));
        assertTrue(checker.isNumber("1.1"));
        assertFalse(checker.isNumber("1.2.3.4"));
        assertTrue(checker.isNumber(".1"));
    }



    @Test
    public void testIsDigit(){
        assertTrue(checker.isDigit('1'));
        assertFalse(checker.isDigit(' '));
        assertFalse(checker.isDigit('a'));
    }

    @Test
    public void isOperation(){
        assertTrue(checker.isOperation("-"));
        assertFalse(checker.isOperation(" "));
        assertFalse(checker.isOperation("("));
        assertFalse(checker.isOperation("1"));
    }
}
