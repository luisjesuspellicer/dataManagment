/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: Implementation of methods for interact with model Study
 */
package tfg.backend.DataAccessImplementation;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.HibernateTemplate;
import tfg.backend.DataAccessModel.StudyDao;
import tfg.backend.DataModel.Study;
import tfg.backend.DataModel.Subject;


import java.util.List;


public class StudyDaoImplementation implements StudyDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override public void saveStudy(Study study) {

        try {
            hibernateTemplate.flush();
            study.setShowW(true);
            hibernateTemplate.clear();
            hibernateTemplate.save(study);
            hibernateTemplate.flush();
            hibernateTemplate.clear();
        } catch (Exception e) {

        }
    }

    @Override public List<Study> findAllStudy() {

        hibernateTemplate.flush();
        hibernateTemplate.clear();
        DetachedCriteria query = DetachedCriteria.forClass(Study.class).add
                (Property.forName("showW").eq(true));
        List<?> results = hibernateTemplate.findByCriteria(query);
        if (results.size() > 0) {

            return (List<Study>) results;
        } else {
            return null;
        }

    }

    @Override public Study findStudy(String name) {

        try {
            hibernateTemplate.flush();
            hibernateTemplate.clear();
            DetachedCriteria query = DetachedCriteria.forClass(Study.class).add
                    (Property.forName("studyName").eq(name)).add(Property.forName
                    ("showW").eq(true));
            ;
            ;
            List<?> list = hibernateTemplate.findByCriteria(query);
            if (list.size() == 0) {
                return null;
            } else {
                return (Study) hibernateTemplate.findByCriteria(query).get(0);
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Must delete all subjects.
     *
     * @param name
     */
    @Override public void deleteStudy(String name) {

        try {
            // Delete users
            DetachedCriteria query = DetachedCriteria.forClass(Study.class).add
                    (Property.forName("studyName").eq(name)).add(Property.forName
                    ("showW").eq(true));
            List<?> list = hibernateTemplate.findByCriteria(query);
            //Delete all information
            Study aux = (Study) list.get(0);
            if (aux != null) {
                // hibernateTemplate.delete(list.get(0));
                aux.setShowW(false);
                hibernateTemplate.update(aux);
            }

            hibernateTemplate.flush();
            hibernateTemplate.clear();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Override public void updateStudy(String studyName, String description) {

        DetachedCriteria query = DetachedCriteria.forClass(Study.class).add
                (Property.forName("studyName").eq(studyName));
        // .add(Property.forName("showW").eq(true));

        List<?> list = hibernateTemplate.findByCriteria(query);
        if (list.size() > 0) {
            Study aux = (Study) hibernateTemplate.findByCriteria(query).get(0);
            aux.setDescription(description);
            hibernateTemplate.save(aux);
            hibernateTemplate.flush();
            hibernateTemplate.clear();
        }
    }
}
