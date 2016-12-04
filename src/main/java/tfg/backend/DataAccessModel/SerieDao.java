/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class define the operations which interact with database for
 * cognitive tests.
 */
package tfg.backend.DataAccessModel;


import tfg.backend.DataModel.Serie;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by guytili on 05/09/2016.
 */
@Transactional
public interface SerieDao {

     void saveSerie(Serie serie);

     List<Serie> findAllSerie();

     Serie findSerie(Long id);

     void deleteSerie(Long id);

     void deleteAllSeries();

      List<Serie> searchByTest(Long id);
}
