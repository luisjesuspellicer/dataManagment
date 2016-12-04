/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: Implementation of methods for interact with model User
 */
package tfg.backend.DataAccessImplementation;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import tfg.backend.DataAccessModel.UserDao;
import tfg.backend.DataModel.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class UserDaoImplementation implements UserDao {


    @Autowired
    private HibernateTemplate hibernateTemplate;
    @Autowired
    StandardPasswordEncoder standardPasswordEncoder;

    @Override
    public void saveUser(User user) {
        user.setPassword(standardPasswordEncoder.encode(user.getPassword()));
        Date dat = new Date();
        long mill = dat.getTime() + new Long("15552000000");
        user.setExpiredPassword(new Date(mill));
        hibernateTemplate.save(user);
        hibernateTemplate.flush();
    }

    @Override
    public List<User> findAllUsers() {

        List<User> aux = hibernateTemplate.loadAll(User.class);
        if (aux.equals(null)) {
            return new ArrayList<User>();
        } else {
            return aux;
        }
    }

    @Override
    public User findUser(String userName) {
        if (userName != null && userName.compareTo("") != 0) {
            DetachedCriteria query = DetachedCriteria.forClass(User.class)
                    .add(Property.forName("userName").eq(userName));
            List<?> u = hibernateTemplate.findByCriteria(query);
            if (u.size() > 0) {

                return (User) u.get(0);
            } else {

                return null;
            }
        } else {

            throw new UsernameNotFoundException("User  not found.");
        }

    }

    @Override
    public void deleteUser(String userName) {
        if (userName != null && userName.compareTo("") != 0) {
            User user = findUser(userName);
            if(user != null){
                hibernateTemplate.delete(user);
            }
            hibernateTemplate.flush();
        }
    }

    @Override
    public void changePassword(String userName, String password) {
        if (userName != null && userName.compareTo("") != 0) {
            DetachedCriteria query = DetachedCriteria.forClass(User.class)
                    .add(Property.forName("userName").eq(userName));
            User aux = (User) hibernateTemplate.findByCriteria(query).get(0);
            aux.setPassword(standardPasswordEncoder.encode(password));
            Date dat = new Date();

            // The new Password expired in 6 Months
            long mill = dat.getTime() + new Long("15552000000");
            aux.setExpiredPassword(new Date(mill));

            hibernateTemplate.update(aux);
            hibernateTemplate.flush();
        }

    }

    @Override
    public void changePrivileges(String userName, String privileges) {
        if (userName != null && userName.compareTo("") != 0) {
            DetachedCriteria query = DetachedCriteria.forClass(User.class)
                    .add(Property.forName("userName").eq(userName));
            User aux = (User) hibernateTemplate.findByCriteria(query).get(0);
            aux.setPrivileges(privileges);

            hibernateTemplate.save(aux);
            hibernateTemplate.flush();
        }


    }

    @Override
    public void changeEmail(String userName, String email) {
        if (userName != null && userName.compareTo("") != 0) {
            DetachedCriteria query = DetachedCriteria.forClass(User.class)
                    .add(Property.forName("userName").eq(userName));
            User aux = (User) hibernateTemplate.findByCriteria(query).get(0);
            aux.setEmail(email);

            // The new Password expired in 6 Months

            hibernateTemplate.save(aux);
            hibernateTemplate.flush();
        }

    }
}