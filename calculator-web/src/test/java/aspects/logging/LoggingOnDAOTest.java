package aspects.logging;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import persistence.dao.CalculationsDAO;
import persistence.dto.CalculationsDTO;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoggingOnDAOTest {
    private CalculationsDAO calculationsDAO;
    private PrintStream actualStream;

    @Mock
    private PrintStream mockStream;

    @Mock
    private EntityManager entityManager;

    @Mock
    private EntityTransaction entityTransaction;

    @Before
    public void setup() {
        calculationsDAO = new CalculationsDAO(entityManager);
        actualStream = System.out;
        System.setOut(mockStream);
    }

    @After
    public void clearUp() {
        System.setOut(actualStream);
    }

    @Test
    public void testMultipleCallsForDaoLogger() throws IOException {
        CalculationsDTO emptyCalculation = new CalculationsDTO();
        CalculationsDTO calculationWithParameters = new CalculationsDTO("1-1", 0.0, "");
        List<String> patterns = new ArrayList<>(Arrays.asList(asRegexPattern(emptyCalculation), asRegexPattern(calculationWithParameters)));

        mockEntityManagerMethods();
        saveAll(emptyCalculation, calculationWithParameters);
        List<byte[]> arguments = getAllArguments();

        assertMultipleArguments(patterns, arguments);
    }

    private void assertMultipleArguments(List<String> patterns, List<byte[]> arguments) {
        for (int i = 0; i < patterns.size(); i++) {
            byte[] argument = arguments.get(i);
            String pattern = patterns.get(i);
            String actual = new String(argument);

            assertTrue(actual.matches(pattern));
        }
    }

    private String asRegexPattern(CalculationsDTO calculationsDTO) {
        return ".*The dto is : " +
                "CalculationsDTO\\{" +
                "expression='" + calculationsDTO.getExpression() + '\'' +
                ", evaluation=" + calculationsDTO.getEvaluation() +
                ", error='" + calculationsDTO.getError() + '\'' + '}' +
                ".*\\r*\\n*";
    }

    private void mockEntityManagerMethods() {
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        doNothing().when(entityTransaction).begin();
        doNothing().when(entityTransaction).commit();
    }

    private void saveAll(CalculationsDTO... calculationsDTO) {
        for (CalculationsDTO dto : calculationsDTO) {
            calculationsDAO.save(dto);
        }
    }

    private List<byte[]> getAllArguments() throws IOException {
        ArgumentCaptor<byte[]> captor = ArgumentCaptor.forClass(byte[].class);
        verify(System.out,times(2)).write(captor.capture());
        return captor.getAllValues();
    }


}
