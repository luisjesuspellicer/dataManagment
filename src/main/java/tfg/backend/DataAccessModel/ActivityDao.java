/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: Methods for interact with model Activity.
 */
package tfg.backend.DataAccessModel;


import tfg.backend.DataModel.Activity;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ActivityDao {

    public void saveActivity(Activity otherHabits);

    public List<Activity> findAllActivity();

    public Activity findActivity(Long numActivity);

    public void deleteActivity(Long numActivity);
}
