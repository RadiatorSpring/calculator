package unit.persistence;

import eu.drus.jpa.unit.api.JpaUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import unit.persistence.dto.ExpressionDTO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RunWith(JpaUnitRunner.class)
public class ExpressionDTOTest {
    @PersistenceContext(unitName = "test")
    private EntityManager manager;

    @Test
    public void someTest(){
        ExpressionDTO expressionDTO= new ExpressionDTO("1-1",0);

        manager.persist(expressionDTO);
    }


}
