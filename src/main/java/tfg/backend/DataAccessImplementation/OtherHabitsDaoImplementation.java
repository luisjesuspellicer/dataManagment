/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: Implementation of methods for interact with model OtherHabits
 */
package tfg.backend.DataAccessImplementation;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.HibernateTemplate;
import tfg.backend.DataAccessModel.OtherHabitsDao;
import tfg.backend.DataModel.Ids.DataID;
import tfg.backend.DataModel.OtherHabits;
import tfg.backend.DataModel.PhysiologicalData;
import tfg.backend.DataModel.PsychologicalData;
import tfg.backend.DataModel.Subject;

import javax.print.attribute.standard.MediaSize;
import java.util.List;


public class OtherHabitsDaoImplementation implements OtherHabitsDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;


    @Override
    public void saveOtherHabits(OtherHabits otherHabits) {

        try {
            otherHabits.getId().setId(count());
            hibernateTemplate.save(otherHabits);
            hibernateTemplate.flush();
        } catch (Exception e) {
            saveOtherHabits(otherHabits,count()+1);
        }
    }
    private void saveOtherHabits(OtherHabits otherHabits, Long id) {
        try{
            otherHabits.getId().setId(id);
            hibernateTemplate.save(otherHabits);
            hibernateTemplate.flush();
        }catch(Exception e){
            saveOtherHabits(otherHabits,id+1);
        }
    }
    @Override
    public List<OtherHabits> findAllOtherHabits() {

        return hibernateTemplate.loadAll(OtherHabits.class);
    }

    @Override
    public OtherHabits findOtherHabits(DataID id) {

        DetachedCriteria query = DetachedCriteria.forClass(OtherHabits.class)
                .add(Property.forName("id.id").eq(id.getId()))
                .add(Property.forName("id.numSubject").eq(id.getNumSubject()));
        List <?> list = hibernateTemplate.findByCriteria(query);
        if(list != null && list.size() >0){
            return (OtherHabits) list.get(0);
        }
        else{return null;}
    }

    @Override
    public void deleteOtherHabits(DataID id) {
        OtherHabits aux = findOtherHabits(id);
        if (aux != null) {
            hibernateTemplate.delete(aux);
            hibernateTemplate.flush();
        }
    }

    @Override
    public Long count() {
        DetachedCriteria query = DetachedCriteria.forClass(OtherHabits.class).
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
    public List<OtherHabits> searchBySubject(Long subject) {
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        DetachedCriteria query = DetachedCriteria.forClass(OtherHabits.class)
                .add(Property.forName("id.numSubject.numSubject").eq(subject));
        List<?> list = hibernateTemplate.findByCriteria(query);
        if (list.size() == 0) {
            return null;
        } else {
            return (List<OtherHabits>) list;
        }
    }
}