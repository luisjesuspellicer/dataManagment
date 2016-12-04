/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: Implementation of methods for interact with model Subject
 */
package tfg.backend.DataAccessImplementation;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import tfg.backend.DataAccessModel.SubjectDao;
import tfg.backend.DataModel.Subject;


import java.util.ArrayList;
import java.util.List;

public class SubjectDaoImplementation implements SubjectDao {


    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public Long saveSubject(Subject subject) {
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        subject.setShowW(true);
        if(!searchByPersonalCodeAndStudyName(subject.getStudyName().getStudyName(),subject.getPersonalCode())){


            return (Long) hibernateTemplate.save(subject);
        }else{
            return new Long(-1);
        }
    }

    @Override
    public List<Subject> findAllSubject() {
        hibernateTemplate.clear();
        hibernateTemplate.flush();
        DetachedCriteria query = DetachedCriteria.forClass(Subject.class)
                .add(Property.forName("showW").eq(true));;
        //
        List<Subject> list = (List<Subject>) hibernateTemplate.findByCriteria(query);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            list = new ArrayList<>();
            return list;
        }

    }

    @Override
    public Long count() {
        DetachedCriteria query = DetachedCriteria.forClass(Subject.class).
                setProjection(Projections.max("numSubject"));
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        Long a = (Long) hibernateTemplate.findByCriteria(query).get(0);
        return a;
    }

    @Override
    public Subject findSubject(Long subjectNumber) {
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        DetachedCriteria query = DetachedCriteria.forClass(Subject.class)
                .add(Property.forName("numSubject").eq(subjectNumber))
                .add(Property.forName("showW").eq(true));
        List<?> results =hibernateTemplate.findByCriteria(query);
        if(results.size() > 0){
            Subject subject = (Subject)results.get(0);
            if(subject.showW){
                return subject;
            }
            else{
                return null;
            }
        }     else{
            return null;
        }
    }

    @Override
    public void deleteSubject(Long subjectNumber) {
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        Subject subject = findSubject(subjectNumber);
        if(subject != null){
           // hibernateTemplate.delete(subject);
            subject.setShowW(false);
            hibernateTemplate.update(subject);
        }

    }

    @Override
    public List<Subject> searchByStudy(String studyName) {
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        DetachedCriteria query = DetachedCriteria.forClass(Subject.class)
                .add(Property.forName("studyName.studyName").eq(studyName))
                .add(Property.forName("showW").eq(true));
        List<?> list = hibernateTemplate.findByCriteria(query);
        if (list.size() == 0) {
            return null;
        } else {
            return (List<Subject>) list;
        }

    }
    @Override
    public boolean searchByPersonalCodeAndStudyName(String studyName, String personalCode) {
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        DetachedCriteria query = DetachedCriteria.forClass(Subject.class)
                .add(Property.forName("studyName.studyName").eq(studyName))
                .add(Property.forName("personalCode").eq(personalCode))
                .add(Property.forName("showW").eq(true));
        List<?> list = hibernateTemplate.findByCriteria(query);
        if (list.size() == 0) {
            return false;
        } else {
            return true;
        }
    }
}
