package persistence;

import eu.drus.jpa.unit.api.InitialDataSets;
import org.junit.Before;
import org.junit.Test;
import persistence.dao.ExpressionResultDAO;
import persistence.dto.ExpressionResultDTO;

import java.util.List;

import static org.junit.Assert.assertEquals;

@InitialDataSets("../../resources/data.xml")
public class ExpressionResultDAOTest {

    private ExpressionResultDAO ExpressionResultDAO;

    @Before
    public void setup() {
        ExpressionResultDAO = new ExpressionResultDAO("test");
    }

    @Test
    public void saveAndRetrieveTest() {

        ExpressionResultDTO ExpressionResultDTO = new ExpressionResultDTO("1-1", 0);

        ExpressionResultDAO.save(ExpressionResultDTO);
        ExpressionResultDTO foundDTO = ExpressionResultDAO.getExpression(1L);

        assertEquals(foundDTO, ExpressionResultDTO);
    }

    @Test
    public void findAllTest() {
        ExpressionResultDTO ExpressionResultDTO = new ExpressionResultDTO("1-1", 0);
        ExpressionResultDTO e2 = new ExpressionResultDTO("2-2", 0);

        ExpressionResultDAO.save(ExpressionResultDTO);
        ExpressionResultDAO.save(e2);
        List<ExpressionResultDTO> list = ExpressionResultDAO.getAll();

        assertEquals(list.get(0),ExpressionResultDTO);

    }


}
