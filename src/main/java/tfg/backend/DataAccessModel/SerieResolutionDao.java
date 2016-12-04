/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class define the operations which interact with database for
 * cognitive tests.
 */
package tfg.backend.DataAccessModel;


import tfg.backend.DataModel.SerieResolution;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by guytili on 05/09/2016.
 */
@Transactional
public interface SerieResolutionDao {

     void saveSerieResolution(SerieResolution serie);

     List<SerieResolution> findAllSerieResolution();

     SerieResolution findSerieResolution(Long id);

     void deleteSerieResolution(Long id);

     void deleteAllSeriesResolution();

     public SerieResolution searchByTestAndID(Long id, Long numTest);

     public List<SerieResolution> searchByTest(Long numTest);

}
