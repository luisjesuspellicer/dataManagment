/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: Implementation of methods for interact with model SupportES.
 */
package tfg.backend.DataAccessLopdDaoImplementation;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.HibernateTemplate;
import tfg.backend.DataAccessLopdModel.SupportESDao;
import tfg.backend.LopdModel.SupportES;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SupportESDaoImplementation implements SupportESDao {


    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override public Long saveSupportES(SupportES supportES) {

        try {
            hibernateTemplate.flush();
            Date date = new Date();

            supportES.setDate(date);
            Long id = (Long)hibernateTemplate.save(supportES);
            hibernateTemplate.flush();
            return id;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new Long(-1);
        }
    }

    @Override public List<SupportES> findAllSupportES() {

        List<SupportES> aux = hibernateTemplate.loadAll(SupportES.class);
        if (aux.equals(null)) {
            return new ArrayList<SupportES>();
        } else {
            return aux;
        }
    }

    @Override public SupportES findSupportES(Long numSupportES) {

        if (numSupportES != null) {
            DetachedCriteria query = DetachedCriteria.forClass(SupportES.class)
                    .add(Property.forName("id").eq(numSupportES));
            List<?> u = hibernateTemplate.findByCriteria(query);
            if (u.size() > 0) {

                return (SupportES) u.get(0);
            } else {

                return null;
            }
        } else {

            return null;
        }
    }

    @Override public void deleteSupportES(Long numSupportES) {

        if (numSupportES != null) {
            SupportES access = findSupportES(numSupportES);
            if (access != null) {
                hibernateTemplate.delete(access);
            }
            hibernateTemplate.flush();
        }
    }

    @Override public List<SupportES> searchByEmisor(String emisor) {

        if (emisor != null) {
            DetachedCriteria query = DetachedCriteria.forClass(SupportES.class)
                    .add(Property.forName("emisor").eq(emisor));
            List<?> u = hibernateTemplate.findByCriteria(query);
            if (u.size() > 0) {

                return (List<SupportES>) u;
            } else {

                return null;
            }
        } else {

            return null;
        }
    }

    @Override public List<SupportES> searchByReceiver(String receiver) {

        if (receiver != null) {
            DetachedCriteria query = DetachedCriteria.forClass(SupportES.class)
                    .add(Property.forName("receiver").eq(receiver));
            List<?> u = hibernateTemplate.findByCriteria(query);
            if (u.size() > 0) {

                return (List<SupportES>) u;
            } else {

                return null;
            }
        } else {

            return null;
        }
    }

    @Override public List<SupportES> searchByDate(Date date) {

        if (date != null) {
            DetachedCriteria query = DetachedCriteria.forClass(SupportES.class)
                    .add(Property.forName("date").eq(date));
            List<?> u = hibernateTemplate.findByCriteria(query);
            if (u.size() > 0) {

                return (List<SupportES>) u;
            } else {

                return null;
            }
        } else {

            return null;
        }
    }
}