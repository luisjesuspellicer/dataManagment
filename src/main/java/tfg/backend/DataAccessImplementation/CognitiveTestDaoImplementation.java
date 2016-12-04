/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: Implementation of methods for interact with model CognitiveTest
 */
package tfg.backend.DataAccessImplementation;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import tfg.backend.DataAccessModel.CognitiveTestDao;
import tfg.backend.DataModel.CognitiveTest;


import java.io.Serializable;
import java.util.List;

public class CognitiveTestDaoImplementation implements CognitiveTestDao{

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public Serializable saveCognitiveTest(CognitiveTest cognitiveTest)
    {
        return hibernateTemplate.save(cognitiveTest);
    }

    @Override
    public List<CognitiveTest> findAllCognitiveTest() {
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        return hibernateTemplate.loadAll(CognitiveTest.class);
    }

    @Override
    public CognitiveTest findCognitiveTest(long id) {
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        DetachedCriteria query = DetachedCriteria.forClass(CognitiveTest.class)
                .add(Property.forName("test_id").eq(id));
        List<?> list = hibernateTemplate.findByCriteria(query);
        if(list.size() == 0){
            return null;
        }else{
            return (CognitiveTest) hibernateTemplate.findByCriteria(query).get(0);
        }
    }

    @Override
    public void deleteCognitiveTest(long id) {
        CognitiveTest aux = findCognitiveTest(id);
        if(aux != null){
            hibernateTemplate.delete(aux);
        }
    }

    @Override
    public void deleteAllCognitiveTest() {
        List <CognitiveTest> aux = findAllCognitiveTest();
        if(aux != null && aux.size() > 0){
            hibernateTemplate.deleteAll(aux);
        }

    }

    @Override
    public Long count() {
        DetachedCriteria query =DetachedCriteria.forClass(CognitiveTest.class).
                setProjection(Projections.count("test_id"));
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        Long a = (Long)hibernateTemplate.findByCriteria(query).get(0);
        if(a == null){
            return new Long("0");
        }else{
            return a;
        }

    }
}
