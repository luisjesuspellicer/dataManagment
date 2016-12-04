/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: Implementation of methods for interact with model PhysiologicalData
 */
package tfg.backend.DataAccessImplementation;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.HibernateTemplate;
import tfg.backend.DataAccessModel.PhysiologicalDataDao;
import tfg.backend.DataModel.Ids.DataID;
import tfg.backend.DataModel.PhysiologicalData;
import tfg.backend.DataModel.Subject;

import java.util.*;


public class PhysiologicalDataDaoImplementation implements PhysiologicalDataDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;


    @Override
    public void savePhysiologicalData(PhysiologicalData physiologicalData) {

        try {
            physiologicalData.getId().setId(count());
            hibernateTemplate.save(physiologicalData);
            hibernateTemplate.flush();
        } catch (Exception e) {
            e.printStackTrace();
            savePhysiologicalData(physiologicalData,count()+1);
        }
    }
    private void savePhysiologicalData(PhysiologicalData physiologicalData, Long id) {
        try {
            physiologicalData.getId().setId(id);
            hibernateTemplate.save(physiologicalData);
            hibernateTemplate.flush();
        } catch (Exception e) {
            e.printStackTrace();
            savePhysiologicalData(physiologicalData,id + 1);
        }
    }
    @Override
    public List<PhysiologicalData> findAllPhysiologicalData() {

        return hibernateTemplate.loadAll(PhysiologicalData.class);
    }

    @Override
    public PhysiologicalData findPhysiologicalData(DataID id) {

        DetachedCriteria query = DetachedCriteria.forClass(PhysiologicalData.class)
                .add(Property.forName("id.id").eq(id.getId()))
                .add(Property.forName("id.numSubject").eq(id.getNumSubject()))
                ;
        List<?> list = hibernateTemplate.findByCriteria(query);
        if(list == null || list.size() == 0){
            return null;
        }else {
            return (PhysiologicalData) hibernateTemplate.findByCriteria(query).get(0);
        }
    }

    @Override
    public void deletePhysiologicalData(DataID id) {
        PhysiologicalData aux = findPhysiologicalData(id);
        if(aux != null) {
            hibernateTemplate.delete(aux);
            hibernateTemplate.flush();
        }
    }
    @Override
    public Long count() {
        DetachedCriteria query =DetachedCriteria.forClass(PhysiologicalData.class).
                setProjection(Projections.count("id.id"));
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
    public List<PhysiologicalData> searchBySubject(Long subject) {
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        DetachedCriteria query = DetachedCriteria.forClass(PhysiologicalData.class)
                .add(Property.forName("id.numSubject.numSubject").eq(subject));
        List<?> list = hibernateTemplate.findByCriteria(query);
        if (list.size() == 0) {
            return null;
        } else {
            return (List<PhysiologicalData>) list;
        }
    }

}