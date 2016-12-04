/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: Implementation of methods for interact with model CognitiveTestResolution
 */
package tfg.backend.DataAccessImplementation;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import tfg.backend.DataAccessModel.CognitiveTestResolutionDao;
import tfg.backend.DataModel.CognitiveTest;
import tfg.backend.DataModel.CognitiveTestResolution;
import tfg.backend.DataModel.Ids.CognitiveTestResolutionID;
import tfg.backend.DataModel.Subject;

import java.util.List;

public class CognitiveTestResolutionDaoImplementation implements CognitiveTestResolutionDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public Long saveCognitiveTestResolution(CognitiveTestResolution cognitiveTestResolution) {

        try{
            cognitiveTestResolution.getTest_id_resolution().setId(count());
            hibernateTemplate.save(cognitiveTestResolution);
            return cognitiveTestResolution.getTest_id_resolution().getId();
        }catch(Exception e){
            return saveCognitiveTestResolution(cognitiveTestResolution,count()+1);
        }
    }
    private Long saveCognitiveTestResolution(CognitiveTestResolution cognitiveTestResolution, Long id){
        try{
            cognitiveTestResolution.getTest_id_resolution().setId(id);
            hibernateTemplate.save(cognitiveTestResolution);
            return cognitiveTestResolution.getTest_id_resolution().getId();
        }catch(Exception e){
            return saveCognitiveTestResolution(cognitiveTestResolution,id +1);
        }
    }
    @Override
    public List<CognitiveTestResolution> findAllCognitiveTestResolution() {

        hibernateTemplate.flush();
        hibernateTemplate.clear();
        return hibernateTemplate.loadAll(CognitiveTestResolution.class);
    }

    @Override
    public CognitiveTestResolution findCognitiveTestResolution(long id) {

        hibernateTemplate.flush();
        hibernateTemplate.clear();
        DetachedCriteria query = DetachedCriteria.forClass(CognitiveTestResolution.class)
                .add(Property.forName("test_id_resolution.id").eq(id));
        List<?> list = hibernateTemplate.findByCriteria(query);
        if(list.size() == 0){
            return null;
        }else{
            return (CognitiveTestResolution) hibernateTemplate.findByCriteria(query).get(0);
        }
    }

    @Override
    public void deleteCognitiveTestResolution(long id) {
        CognitiveTestResolution aux = findCognitiveTestResolution(id);
        if(aux != null){
            hibernateTemplate.delete(aux);
        }


    }

    @Override
    public Long count() {
        DetachedCriteria query =DetachedCriteria.forClass(CognitiveTestResolution.class).
                setProjection(Projections.count("test_id_resolution.id"));
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        Long a = (Long)hibernateTemplate.findByCriteria(query).get(0);
        if(a == null){
            return new Long("0");
        }else{
            return a;
        }

    }

    @Override
    public List<CognitiveTestResolution> searchBySubject(Long subject) {
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        DetachedCriteria query = DetachedCriteria.forClass(CognitiveTestResolution.class)
                .add(Property.forName("test_id_resolution.numSubject.numSubject").eq(subject));
        List<?> list = hibernateTemplate.findByCriteria(query);
        if (list.size() == 0) {
            return null;
        } else {
            return (List<CognitiveTestResolution>) list;
        }
    }
}
