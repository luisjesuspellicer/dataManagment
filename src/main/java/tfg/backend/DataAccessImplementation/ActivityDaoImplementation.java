/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: Implementation of methods for interact with model Activity.
 */
package tfg.backend.DataAccessImplementation;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import tfg.backend.DataAccessModel.ActivityDao;
import tfg.backend.DataModel.Activity;

import java.util.ArrayList;
import java.util.List;


public class ActivityDaoImplementation implements ActivityDao{

    @Autowired
    private HibernateTemplate hibernateTemplate;


    @Override
    public void saveActivity(Activity activity) {
        hibernateTemplate.clear();
        hibernateTemplate.flush();
        hibernateTemplate.save(activity); hibernateTemplate.flush();
    }

    @Override
    public List<Activity> findAllActivity() {
        hibernateTemplate.clear();
        hibernateTemplate.flush();
        List<?> list = hibernateTemplate.loadAll(Activity.class);
        if(list.size() >0){
            return (List<Activity>)list;
        }else{
            return new ArrayList<Activity>();
        }
    }

    @Override
    public Activity findActivity(Long numActivity) {
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        DetachedCriteria query = DetachedCriteria.forClass(Activity.class)
                .add(Property.forName("numActivity").eq(numActivity));
        List<?> list = hibernateTemplate.findByCriteria(query);
        if(list.size() >0 ){
            return (Activity)list.get(0);
        }
        return null;
    }


    @Override
    public void deleteActivity(Long numActivity) {
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        Activity aux = findActivity(numActivity);
        if(numActivity != null){
            hibernateTemplate.delete(findActivity(numActivity));
        }

        hibernateTemplate.flush();
        hibernateTemplate.clear();
    }

}
