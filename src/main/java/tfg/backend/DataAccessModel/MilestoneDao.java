/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class define the operations which interact with database for
 * milestones in base lines.
 */
package tfg.backend.DataAccessModel;

import tfg.backend.DataModel.Ids.MilestoneID;
import tfg.backend.DataModel.Milestone;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface MilestoneDao {

    public void saveMilestone(Milestone milestone);

    public List<Milestone> findAllMilestone();

    public List<Milestone> findMilestones(Long id);

    public void deleteMilestone(MilestoneID id);

    public Long count();

    public Milestone findMilestone(MilestoneID id );

    public List<Milestone> findByBaseLine(Long id);



}
