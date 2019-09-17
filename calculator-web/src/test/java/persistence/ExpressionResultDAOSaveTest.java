//package persistence;
//
//import eu.drus.jpa.unit.api.JpaUnitRunner;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import persistence.dao.ExpressionResultDAO;
//import persistence.dto.ExpressionResultDTO;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//@RunWith(JpaUnitRunner.class)
//public class ExpressionResultDAOSaveTest {
//
//    @PersistenceContext(unitName = "test")
//    private EntityManager entityManager;
//    private ExpressionResultDAO expressionResultDAO;
//
//    @Before
//    public void setup(){
//        expressionResultDAO = new ExpressionResultDAO("test");
//    }
//
//
//    @Test
//    public void testSave(){
//        expressionResultDAO.save(new ExpressionResultDTO("1-1",0));
//        expressionResultDAO.getExpression(1L);
//    }
//
//}
