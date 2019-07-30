package calculator.validators;

import org.junit.Before;
import org.junit.Test;

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
    }

    @Test
    public void testValidateExpression(){
        assertTrue(checker.validateExpression("q23`o1ref wmlc knlsnlrne"));
        assertFalse(checker.validateExpression("(1+  1)/2"));
        assertTrue(checker.validateExpression("1  1+1/2+(1-20)"));
        assertTrue(checker.validateExpression("+ 11 11"));
        assertTrue(checker.validateExpression("2()5+22"));
        assertTrue(checker.validateExpression("1(+)5"));
        assertTrue(checker.validateExpression("()+4+5"));
        assertTrue(checker.validateExpression("1.1-1.2"));
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
