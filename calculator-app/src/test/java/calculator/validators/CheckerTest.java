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
    }

    @Test
    public void testValidateExpression(){
        assertTrue(checker.validateExpression("q23`o1ref wmlc knlsnlrne"));
        assertFalse(checker.validateExpression("(1+  1)/2"));
        assertTrue(checker.validateExpression("1  1+1/2+(1-20)"));
    }

    @Test
    public void testOperationOrBracket(){
        assertTrue(checker.isOperationOrBracket("("));
        assertFalse(checker.isOperationOrBracket("1"));
        assertFalse(checker.isOperationOrBracket(" "));
    }
}
