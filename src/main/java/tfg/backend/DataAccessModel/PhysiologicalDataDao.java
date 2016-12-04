/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class define the operations which interact with database for
 * physiological data.
 */
package tfg.backend.DataAccessModel;

import tfg.backend.DataModel.Ids.DataID;
import tfg.backend.DataModel.PhysiologicalData;
import tfg.backend.DataModel.Subject;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface PhysiologicalDataDao {

    public void savePhysiologicalData(PhysiologicalData physiologicalData);

    public List<PhysiologicalData> findAllPhysiologicalData();

    public PhysiologicalData findPhysiologicalData(DataID id);

    public void deletePhysiologicalData(DataID id);

    public Long count();

    public List<PhysiologicalData> searchBySubject(Long subject);
}
