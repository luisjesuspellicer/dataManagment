/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: Implementation of methods for interact with model ToxicHabits
 */
package tfg.backend.DataAccessImplementation;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.HibernateTemplate;
import tfg.backend.DataAccessModel.ToxicHabitsDao;
import tfg.backend.DataModel.Ids.DataID;
import tfg.backend.DataModel.ToxicHabits;

import java.util.List;

public class ToxicHabitsDaoImplementation implements ToxicHabitsDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public void saveToxicHabits(ToxicHabits toxicHabits) {
        try {
            hibernateTemplate.flush();
            hibernateTemplate.clear();
            toxicHabits.getId().setId(count());
            hibernateTemplate.save(toxicHabits);
            hibernateTemplate.flush();
        } catch (Exception e) {
            e.printStackTrace();
            saveToxicHabits(toxicHabits,count()+1);
        }
    }
    private void saveToxicHabits(ToxicHabits toxicHabits,Long id) {
        try {
            hibernateTemplate.flush();
            hibernateTemplate.clear();
            toxicHabits.getId().setId(id);
            hibernateTemplate.save(toxicHabits);
            hibernateTemplate.flush();
        } catch (Exception e) {
            e.printStackTrace();
            saveToxicHabits(toxicHabits,id +1);
        }
    }
    @Override
    public List<ToxicHabits> findAllToxicHabits() {
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        return hibernateTemplate.loadAll(ToxicHabits.class);
    }

    @Override
    public ToxicHabits findToxicHabits(DataID id) {
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        DetachedCriteria query = DetachedCriteria.forClass(ToxicHabits.class)
                .add(Property.forName("id.id").eq(id.getId()))
                .add(Property.forName("id.numSubject").eq(id.getNumSubject()));
        List<?> list = hibernateTemplate.findByCriteria(query);
        if (list != null && list.size() > 0) {
            return (ToxicHabits) list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void deleteToxicHabits(DataID id) {
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        ToxicHabits toxicHabits = findToxicHabits(id);
        if (toxicHabits != null) {
            hibernateTemplate.delete(findToxicHabits(id));
        }
        hibernateTemplate.flush();
        hibernateTemplate.clear();
    }

    @Override
    public Long count() {
        DetachedCriteria query = DetachedCriteria.forClass(ToxicHabits.class).
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
    public List<ToxicHabits> searchBySubject(Long subject) {
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        DetachedCriteria query = DetachedCriteria.forClass(ToxicHabits.class)
                .add(Property.forName("id.numSubject.numSubject").eq(subject));
        List<?> list = hibernateTemplate.findByCriteria(query);
        if (list.size() == 0) {
            return null;
        } else {
            return (List<ToxicHabits>) list;
        }
    }
}