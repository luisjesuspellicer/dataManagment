/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class define the operations which interact with database for
 * toxic habits of the experimental subjects.
 */
package tfg.backend.DataAccessModel;

import tfg.backend.DataModel.Ids.DataID;
import tfg.backend.DataModel.Subject;
import tfg.backend.DataModel.ToxicHabits;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ToxicHabitsDao {

    public void saveToxicHabits(ToxicHabits habits);

    public List<ToxicHabits> findAllToxicHabits();

    public ToxicHabits findToxicHabits(DataID id);

    public void deleteToxicHabits(DataID id);

    public Long count();

    public List<ToxicHabits> searchBySubject(Long subject);
}
