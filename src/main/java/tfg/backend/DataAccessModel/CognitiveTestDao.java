/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: Methods for interact with model CognitiveTest.
 */
package tfg.backend.DataAccessModel;



import tfg.backend.DataModel.CognitiveTest;
import tfg.backend.DataModel.CognitiveTestResolution;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Transactional
public interface CognitiveTestDao {

    public Serializable saveCognitiveTest(CognitiveTest cognitiveTest);

    public List<CognitiveTest> findAllCognitiveTest();

    public CognitiveTest findCognitiveTest(long id);

    public void deleteCognitiveTest(long id);

    public void deleteAllCognitiveTest();

    public Long count();
}
