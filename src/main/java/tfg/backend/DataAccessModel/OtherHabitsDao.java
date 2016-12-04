/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class define the operations which interact with database for
 * other habits of the experimental subjects.
 */
package tfg.backend.DataAccessModel;

import tfg.backend.DataModel.Ids.DataID;
import tfg.backend.DataModel.OtherHabits;
import tfg.backend.DataModel.Subject;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface OtherHabitsDao {

    public void saveOtherHabits(OtherHabits otherHabits);

    public List<OtherHabits> findAllOtherHabits();

    public OtherHabits findOtherHabits(DataID id);

    public void deleteOtherHabits(DataID id);

    public Long count();

    public List<OtherHabits> searchBySubject(Long subject);

}
