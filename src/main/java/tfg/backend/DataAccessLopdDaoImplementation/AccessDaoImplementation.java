/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: Implementation of methods for interact with model Access
 */
package tfg.backend.DataAccessLopdDaoImplementation;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

import tfg.backend.DataAccessLopdModel.AccessDao;


import tfg.backend.DataModel.User;
import tfg.backend.LopdModel.Access;

import java.util.ArrayList;
import java.util.List;


public class AccessDaoImplementation implements AccessDao {


    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public void saveAcess(Access access) {
        hibernateTemplate.save(access);
        hibernateTemplate.flush();
    }

    @Override
    public List<Access> findAllAccess() {
        List<Access> aux = hibernateTemplate.loadAll(Access.class);
        if (aux.equals(null)) {
            return new ArrayList<Access>();
        } else {
            return aux;
        }
    }

    @Override
    public Access findAccess(Long numAccess) {
        if (numAccess != null) {
            DetachedCriteria query = DetachedCriteria.forClass(Access.class)
                    .add(Property.forName("id").eq(numAccess));
            List<?> u = hibernateTemplate.findByCriteria(query);
            if (u.size() > 0) {

                return (Access) u.get(0);
            } else {

                return null;
            }
        } else {

            return null;
        }
    }

    @Override
    public void deleteAccess(Long numAccess) {
        if (numAccess != null) {
            Access access = findAccess(numAccess);
            if (access != null) {
                hibernateTemplate.delete(access);
            }
            hibernateTemplate.flush();
        }
    }

    @Override
    public List<Access> searchByUser(User user) {
        if (user != null) {
            DetachedCriteria query = DetachedCriteria.forClass(Access.class)
                    .add(Property.forName("id").eq(user.getUserName()));
            List<?> u = hibernateTemplate.findByCriteria(query);
            if (u.size() > 0) {

                return (List<Access>) u;
            } else {

                return null;
            }
        } else {

            return null;
        }
    }

    @Override
    public List<Access> searchByAccess(String access) {
        if (access != null && access.compareTo("") != 0) {
            DetachedCriteria query = DetachedCriteria.forClass(Access.class)
                    .add(Property.forName("id").eq(access));
            List<?> u = hibernateTemplate.findByCriteria(query);
            if (u.size() > 0) {

                return (List<Access>) u;
            } else {

                return null;
            }
        } else {

            return null;
        }
    }
}