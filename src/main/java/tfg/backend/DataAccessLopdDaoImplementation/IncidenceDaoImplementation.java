/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: Implementation of methods for interact with model Incidence
 */
package tfg.backend.DataAccessLopdDaoImplementation;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import tfg.backend.DataAccessLopdModel.IncidenceDao;
import tfg.backend.DataModel.User;
import tfg.backend.LopdModel.Incidence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IncidenceDaoImplementation implements IncidenceDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;


    @Override public Long saveIncidence(Incidence incidence) {
        Date date = new Date();
        incidence.setDate(date);
        Long id = (Long)hibernateTemplate.save(incidence);
        hibernateTemplate.flush();
        return id;
    }

    @Override public List<Incidence> findAllIncidences() {
        List<Incidence> aux = hibernateTemplate.loadAll(Incidence.class);
        if (aux.equals(null)) {
            return new ArrayList<Incidence>();
        } else {
            return aux;
        }
    }

    @Override public Incidence findIncidence(Long numAccess) {

        if (numAccess != null) {
            DetachedCriteria query = DetachedCriteria.forClass(Incidence.class)
                    .add(Property.forName("id").eq(numAccess));
            List<?> u = hibernateTemplate.findByCriteria(query);
            if (u.size() > 0) {

                return (Incidence) u.get(0);
            } else {

                return null;
            }
        } else {

            return null;
        }
    }

    @Override
    public void deleteIncidence(Long numAccess) {
        if (numAccess != null) {
            Incidence access = findIncidence(numAccess);
            if (access != null) {
                hibernateTemplate.delete(access);
            }
            hibernateTemplate.flush();
        }
    }

    @Override
    public List<Incidence> searchByUser(User user) {
        if (user != null) {
            DetachedCriteria query = DetachedCriteria.forClass(Incidence.class)
                    .add(Property.forName("user.username").eq(user.getUserName()));
            List<?> u = hibernateTemplate.findByCriteria(query);
            if (u.size() > 0) {

                return (List<Incidence>) u;
            } else {

                return null;
            }
        } else {

            return null;
        }
    }

    @Override
    public List<Incidence> searchByTypeIncidence(String typeIncidence) {

        if (typeIncidence != null) {
            DetachedCriteria query = DetachedCriteria.forClass(Incidence.class)
                    .add(Property.forName("typeIncidence").eq(typeIncidence));
            List<?> u = hibernateTemplate.findByCriteria(query);
            if (u.size() > 0) {

                return (List<Incidence>) u;
            } else {

                return null;
            }
        } else {

            return null;
        }
    }

    @Override
    public List<Incidence> searchByDate(Date date) {

        if (date != null) {
            DetachedCriteria query = DetachedCriteria.forClass(Incidence.class)
                    .add(Property.forName("date").eq(date));
            List<?> u = hibernateTemplate.findByCriteria(query);
            if (u.size() > 0) {

                return (List<Incidence>) u;
            } else {

                return null;
            }
        } else {

            return null;
        }
    }
}