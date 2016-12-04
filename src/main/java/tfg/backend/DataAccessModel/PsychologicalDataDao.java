/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class define the operations which interact with database for
 * psichological data.
 */
package tfg.backend.DataAccessModel;

import tfg.backend.DataModel.Ids.DataID;
import tfg.backend.DataModel.PsychologicalData;
import tfg.backend.DataModel.Subject;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface PsychologicalDataDao {

     void savePsychologicalData(PsychologicalData psychologicalData);

     List<PsychologicalData> findAllPsychologicalData();

     PsychologicalData findPsychologicalData(DataID id);

     void deletePsychologicalData(DataID id);

     Long count();

     public List<PsychologicalData> searchBySubject(Long subject);
}
