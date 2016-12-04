/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class define the operations which interact with database for
 * studies's information.
 */
package tfg.backend.DataAccessModel;

import tfg.backend.DataModel.Study;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface StudyDao {

    public void saveStudy(Study study);

    public List<Study> findAllStudy();

    public Study findStudy(String studyName);

    public void deleteStudy(String studyName);

    public void updateStudy(String studyName, String description);

}
