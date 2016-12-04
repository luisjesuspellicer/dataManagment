/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description:  This class define the operations which interact with
 * database for the responses of cognitive tests in the studies.
 */
package tfg.backend.DataAccessModel;


import tfg.backend.DataModel.CognitiveTestResolution;
import tfg.backend.DataModel.Subject;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface CognitiveTestResolutionDao {

    public Long saveCognitiveTestResolution(CognitiveTestResolution cognitiveTestResolution);

    public List<CognitiveTestResolution> findAllCognitiveTestResolution();

    public CognitiveTestResolution findCognitiveTestResolution(long id);

    public void deleteCognitiveTestResolution(long id);

    public Long count();

    public List<CognitiveTestResolution> searchBySubject(Long subject);
}
