package calculator.tokens;

import calculator.validators.Checker;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class TokenFactoryTest {

    @Mock
    public Checker checker;

    @InjectMocks
    public TokenFactory tokenFactory;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Test
    public void testTokens() {
        when(checker.isNumber('1')).thenReturn(true);
        when(checker.isOperationOrBracket('*')).thenReturn(true);

        assertTrue((tokenFactory.getTokenOfExpression('1') instanceof NumberToken));
        assertTrue(tokenFactory.getTokenOfExpression('*') instanceof OperationToken);

    }
}
