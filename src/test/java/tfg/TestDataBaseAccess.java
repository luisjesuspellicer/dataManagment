package tfg;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import tfg.backend.BackendApplication;
import tfg.backend.DataAccessModel.*;
import tfg.backend.DataModel.*;
import tfg.backend.DataModel.Ids.DataID;


import javax.print.attribute.standard.MediaSize;
import javax.xml.crypto.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BackendApplication.class)
@WebAppConfiguration
public class TestDataBaseAccess {

    @Autowired
    private ToxicHabitsDao toxicHabitsDao;

    @Autowired
    private OtherHabitsDao otherHabitsDao;

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private BaseLineDao  baseLineDao;

    @Autowired
    private CognitiveTestDao cognitiveTestDao;

    @Autowired
    private CognitiveTestResolutionDao cognitiveTestResolutionDao;

    @Autowired
    private MilestoneDao milestoneDao;

    @Autowired
    private PhysiologicalDataDao physiologicalDataDao;

    @Autowired
    private PsychologicalDataDao psychologicalDataDao;

    @Autowired
    private StudyDao studyDao;

    @Autowired
    private SubjectDao subjectDao;

    @Autowired
    private SerieDao serieDao;


    /*
     * Test for simple operations in the interaction with database.
     * - Add new element.
     * - Delete one element.
     * - List all elements of 1 collection.
     * - Search one element into a collection.
     */

    /*
     * First it's obliged insert and search one study.
     */

    @Test
    public void insertAndSearchStudy(){

        Study study = new Study();
        study.setDescription("Test description");
        study.setStudyName("First study");
        studyDao.saveStudy(study);
        Study aux = studyDao.findStudy("First study");
        assertNotNull(aux);
        assertEquals(aux,study);
        studyDao.deleteStudy(study.getStudyName());
        aux = studyDao.findStudy("First study");
        assertNull(aux);
    }

    /*
     * After add one Study to database, it's obliged insert a subject.
     */
    @Test
    public void insertSubject(){
        Study study = new Study();
        study.setDescription("Test description");
        study.setStudyName("First study");
        studyDao.saveStudy(study);
         //First it's necesary search one study.
        Study aux = study ;

        // Secondly is necesary create subject.
        Subject subject = new Subject();
        subject.setAge(1);
        subject.setBalanced("machine");
        subject.setCheckIn(new Long(291232131));
        subject.setPersonalCode("abc");
        subject.setStudyName(aux);
        // Add Subject
       List<Subject>subjects = subjectDao.findAllSubject();

        Long a = subjectDao.saveSubject(subject);
        List<Subject> list = subjectDao.findAllSubject();
        assertEquals(new Double(subjects.size()+1),new Double(list.size()));
        subjectDao.deleteSubject(a);
        studyDao.deleteStudy(study.getStudyName());

    }

    /*
     * After add a new Study and a new Subject it's necessary to
     * test the personal data store in the database.
     */
     @Test
    public void insertToxicHabits() {
         Study study = new Study();
         study.setDescription("Test description");
         study.setStudyName("First study");
         studyDao.saveStudy(study);
         //First it's necesary search one study.
         Study aux = study ;

         // Secondly is necesary create subject.
         Subject subject = new Subject();
         subject.setAge(1);
         subject.setBalanced("machine");
         subject.setCheckIn(new Long(291232131));
         subject.setPersonalCode("abc");
         subject.setStudyName(aux);
         aux = studyDao.findStudy("First study");

        subject.setStudyName(aux);
        // Add Subject
        Long numSubject = subjectDao.saveSubject(subject);
        subject.setNumSubject(numSubject);

        // Create new element and insert into the database.
        ToxicHabits toxicHabits = new ToxicHabits();
        toxicHabits.setDayCigarettes(3);
        toxicHabits.setSmoker(true);
        toxicHabits.setOtherSubstances(true);
        toxicHabits.setAlcoholicDrinks(true);
        toxicHabits.setOtherSubstancesGr(100);

        // Create and set a new ID
        DataID id = new DataID();
       // id.setId(new Long(99));
        id.setNumSubject(subject);
        toxicHabits.setId(id);


        // Insert into database.
        toxicHabitsDao.saveToxicHabits(toxicHabits);

        /*
         * Check this conditions:
         * - Search not return null.
         * - The result of search have the same Subject
         * that the previous object.
         */

        ToxicHabits result = toxicHabitsDao.findToxicHabits(id);
        assertNotNull(result);
        assertEquals(result,toxicHabits);
         List<ToxicHabits> aux2 = toxicHabitsDao.findAllToxicHabits();
         for(ToxicHabits aux3 : aux2){
             if(aux3.getId().getNumSubject().getNumSubject() == subject.getNumSubject()){
                 toxicHabitsDao.deleteToxicHabits(aux3.getId());
             }
         }
         subjectDao.deleteSubject(subject.getNumSubject());
         studyDao.deleteStudy(study.getStudyName());
    }
   @Test
    public void insertOtherHabits() {
         Study study = new Study();
         study.setDescription("Test description");
         study.setStudyName("First study");
         studyDao.saveStudy(study);
         //First it's necesary search one study.
         Study aux = study ;

         // Secondly is necesary create subject.
         Subject subject = new Subject();
         subject.setAge(1);
         subject.setBalanced("machine");
         subject.setCheckIn(new Long(291232131));
         subject.setPersonalCode("abc");
         subject.setStudyName(aux);
         aux = studyDao.findStudy("First study");

        subject.setStudyName(aux);
        // Add Subject
        Long numSubject = subjectDao.saveSubject(subject);
        subject.setNumSubject(numSubject);

        // Create new element and insert into the database.
        OtherHabits otherHabits = new OtherHabits();
        otherHabits.setCaffeineConsumption(true);
        otherHabits.setKindOfSports("Submarinism");

        // Create and set a new ID
        DataID id = new DataID();
        id.setId(new Long(99));
        id.setNumSubject(subject);
        otherHabits.setId(id);


        // Insert into database.
        otherHabitsDao.saveOtherHabits(otherHabits);

        /*
         * Check this conditions:
         * - Search not return null.
         * - The result of search have the same Subject
         * that the previous object.
         */

        OtherHabits result = otherHabitsDao.findOtherHabits(id);
        assertNotNull(result);
        assertEquals(result,otherHabits);
        List<OtherHabits> aux2 = otherHabitsDao.findAllOtherHabits();
        for(OtherHabits aux3 : aux2){
            if(aux3.getId().getNumSubject().getNumSubject() == subject.getNumSubject()){
                otherHabitsDao.deleteOtherHabits(aux3.getId());
            }
        }
        subjectDao.deleteSubject(subject.getNumSubject());
        studyDao.deleteStudy(study.getStudyName());
    }

}
