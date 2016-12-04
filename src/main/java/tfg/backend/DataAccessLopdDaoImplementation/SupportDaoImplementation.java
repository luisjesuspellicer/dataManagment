/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: Implementation of methods for interact with model Support.
 */
package tfg.backend.DataAccessLopdDaoImplementation;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import tfg.backend.DataAccessLopdModel.SupportDao;
import tfg.backend.LopdModel.Support;

import java.util.ArrayList;
import java.util.List;

public class SupportDaoImplementation implements SupportDao {


    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override public Long saveSupport(Support support) {
        Long id = (Long)hibernateTemplate.save(support);
        hibernateTemplate.flush();
        return id;

    }

    @Override public List<Support> findAllSupport() {
        List<Support> aux = hibernateTemplate.loadAll(Support.class);
        if (aux.equals(null)) {
            return new ArrayList<Support>();
        } else {
            return aux;
        }
    }

    @Override public Support findSupport(Long numSupport) {

        if (numSupport != null) {
            DetachedCriteria query = DetachedCriteria.forClass(Support.class)
                    .add(Property.forName("id").eq(numSupport));
            List<?> u = hibernateTemplate.findByCriteria(query);
            if (u.size() > 0) {

                return (Support) u.get(0);
            } else {

                return null;
            }
        } else {

            return null;
        }
    }

    @Override public void deleteSupport(Long numSupport) {

        if (numSupport != null) {
            Support access = findSupport(numSupport);
            if (access != null) {
                hibernateTemplate.delete(access);
            }
            hibernateTemplate.flush();
        }
    }

    @Override public Support searchByName(String name) {
        if (name != null) {
            DetachedCriteria query = DetachedCriteria.forClass(Support.class)
                    .add(Property.forName("name").eq(name));
            List<?> u = hibernateTemplate.findByCriteria(query);
            if (u.size() > 0) {

                return (Support) u.get(0);
            } else {

                return null;
            }
        } else {

            return null;
        }
    }

    @Override public List<Support> searchByTypeInformation(String typeInformation) {
        if (typeInformation != null) {
            DetachedCriteria query = DetachedCriteria.forClass(Support.class)
                    .add(Property.forName("typeInformation").eq(typeInformation));
            List<?> u = hibernateTemplate.findByCriteria(query);
            if (u.size() > 0) {

                return (List<Support>) u;
            } else {

                return null;
            }
        } else {

            return null;
        }
    }
}
