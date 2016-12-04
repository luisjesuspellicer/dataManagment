/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class define the operations which interact with database for
 * participants's information in the studies.
 */
package tfg.backend.DataAccessModel;

import tfg.backend.DataModel.Subject;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface SubjectDao {

    public Long saveSubject(Subject subject);

    public List<Subject> findAllSubject();

    public Subject findSubject(Long subjectNumber);

    public void deleteSubject(Long subjectNumber);

    public Long count();

    public List<Subject> searchByStudy(String studyName);

    public boolean searchByPersonalCodeAndStudyName(String studyName, String personalCode);
}
