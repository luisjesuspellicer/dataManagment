/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: Implementation of methods for interact with model PsychologicalData
 */
package tfg.backend.DataAccessImplementation;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.HibernateTemplate;
import tfg.backend.DataAccessModel.PsychologicalDataDao;
import tfg.backend.DataModel.Ids.DataID;
import tfg.backend.DataModel.PsychologicalData;
import tfg.backend.DataModel.Subject;

import java.util.List;


public class PsychologicalDataDaoImplementation implements PsychologicalDataDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;


    @Override
    public void savePsychologicalData(PsychologicalData psychologicalData) {


        try {
            psychologicalData.getId().setId(count());
            hibernateTemplate.save(psychologicalData);
            hibernateTemplate.flush();
        } catch (Exception e) {
            e.printStackTrace();
            savePsychologicalData( psychologicalData, count() +1);
        }


    }
    private void savePsychologicalData(PsychologicalData psychologicalData, Long id) {
        try {
            psychologicalData.getId().setId(id);
            hibernateTemplate.save(psychologicalData);
            hibernateTemplate.flush();
        } catch (Exception e) {
            e.printStackTrace();
            savePsychologicalData( psychologicalData, id +1);
        }
    }

    @Override
    public List<PsychologicalData> findAllPsychologicalData() {

        return hibernateTemplate.loadAll(PsychologicalData.class);
    }

    @Override
    public PsychologicalData findPsychologicalData(DataID id) {
        DetachedCriteria query = DetachedCriteria.forClass(PsychologicalData.class)
                .add(Property.forName("id.id").eq(id.getId()))
                .add(Property.forName("id.numSubject").eq(id.getNumSubject()));
        List<?>list =  hibernateTemplate.findByCriteria(query);
        if(list != null && list.size()>0){
            return (PsychologicalData) list.get(0);
        }else{ return null;}


    }

    @Override
    public void deletePsychologicalData(DataID id) {
        PsychologicalData psychologicalData = findPsychologicalData(id);
        if (psychologicalData != null) {
            hibernateTemplate.delete(findPsychologicalData(id));
            hibernateTemplate.flush();
        }
    }

    @Override
    public Long count() {
        DetachedCriteria query = DetachedCriteria.forClass(PsychologicalData.class).
                setProjection(Projections.count("id.id"));
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        Long a = (Long) hibernateTemplate.findByCriteria(query).get(0);
        if (a == null) {
            return new Long("0");
        } else {
            return a;
        }

    }

    @Override
    public List<PsychologicalData> searchBySubject(Long subject) {
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        DetachedCriteria query = DetachedCriteria.forClass(PsychologicalData.class)
                .add(Property.forName("id.numSubject.numSubject").eq(subject));
        List<?> list = hibernateTemplate.findByCriteria(query);
        if (list.size() == 0) {
            return null;
        } else {
            return (List<PsychologicalData>) list;
        }
    }
}