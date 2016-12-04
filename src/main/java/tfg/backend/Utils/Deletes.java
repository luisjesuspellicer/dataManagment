/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class have usefuls functions to delete resources.
 */
package tfg.backend.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import tfg.backend.DataAccessModel.*;
import tfg.backend.DataModel.*;

import java.util.List;


public class Deletes {

    /**
     * The content in comments is the efective drop of the subject.
     * The actual requisites obey keep the information in database.
     * @param id
     * @param toxicHabitsDao
     * @param otherHabitsDao
     * @param subjectDao
     * @param serieResolutionDao
     * @param cognitiveTestResolutionDao
     * @param psychologicalDataDao
     * @param milestoneDao
     * @param physiologicalDataDao
     * @param baseLineDao
     * @param fileDao
     * @return
     */
    public boolean deleteSubject(Long id, ToxicHabitsDao toxicHabitsDao,
                                 OtherHabitsDao otherHabitsDao, SubjectDao subjectDao,
                                 SerieResolutionDao serieResolutionDao,
                                 CognitiveTestResolutionDao cognitiveTestResolutionDao,
                                 PsychologicalDataDao psychologicalDataDao,
                                 MilestoneDao milestoneDao,
                                 PhysiologicalDataDao physiologicalDataDao,
                                 BaseLineDao baseLineDao, FileDao fileDao){

        try {
            Subject subject = subjectDao.findSubject(id);
            if(subject != null){
                /*List<PhysiologicalData> physiologicalDatas
                        = physiologicalDataDao.
                        searchBySubject(subject.getNumSubject());

                List<PsychologicalData> psychologicalDatas
                        = psychologicalDataDao.
                        searchBySubject(subject.getNumSubject());

                List<OtherHabits> otherHabitses
                        = otherHabitsDao.searchBySubject(subject.getNumSubject());

                List<ToxicHabits> toxicHabitses
                        = toxicHabitsDao.searchBySubject(subject.getNumSubject());
                if (toxicHabitses != null) {
                    for (int i = 0; i < toxicHabitses.size(); i++) {
                        if (physiologicalDatas != null && physiologicalDatas.size() > i) {
                            physiologicalDataDao.deletePhysiologicalData
                                    (physiologicalDatas.get(i).getId());
                        }
                        if (psychologicalDatas != null && psychologicalDatas.size() > i) {
                            psychologicalDataDao.deletePsychologicalData
                                    (psychologicalDatas.get(i).getId());
                        }
                        if (otherHabitses != null && otherHabitses.size() > i) {
                            otherHabitsDao.deleteOtherHabits
                                    (otherHabitses.get(i).getId());
                        }
                        if (toxicHabitses.size() > i) {
                            toxicHabitsDao.deleteToxicHabits
                                    (toxicHabitses.get(i).getId());
                        }

                    }
                }
                //Thirstly Find Test Cognitive Resolution
                List<CognitiveTestResolution> cognitiveTestResolutions
                        = cognitiveTestResolutionDao.
                        searchBySubject(subject.getNumSubject());
                if (cognitiveTestResolutions != null) {
                    // Find all series of tests Resolutions
                    for (CognitiveTestResolution cognitiveTestResolution :
                            cognitiveTestResolutions) {

                        List<SerieResolution> serieResolutions
                                = serieResolutionDao.
                                searchByTest(
                                        cognitiveTestResolution.getTest_id_resolution().getId());

                        //Delete series Resolutions.
                        if(serieResolutions != null) {
                            for (SerieResolution serieResolution : serieResolutions) {
                                serieResolutionDao.deleteSerieResolution(serieResolution.getNumSerieResolution());
                            }
                        }
                        //Delete cognitive TestResolution
                        cognitiveTestResolutionDao.deleteCognitiveTestResolution(cognitiveTestResolution.getTest_id_resolution().getId());
                    }
                }

                // To finish, find LineBase and Milestones

                List<BaseLine> baseLines = baseLineDao.searchBySubject(subject.getNumSubject());
                if (baseLines != null) {
                    // Find Milestones
                    for (BaseLine baseLine : baseLines) {
                        List<Milestone> milestones = milestoneDao
                                .findMilestones(baseLine.getId());
                        if(milestones!= null) {
                            for (Milestone milestone : milestones) {
                                milestoneDao.deleteMilestone(milestone.getId());
                            }
                        }
                        baseLineDao.deleteBaseLine(baseLine.getId());
                    }
                }

                // Delete Files
                List<FileInfo> fileInfos = fileDao.findBySubject(subject.getNumSubject());
                if(fileInfos != null){
                    for(FileInfo file: fileInfos){
                        fileDao.deleteFileInfo(file.getNameFile());
                    }
                }  */
                subjectDao.deleteSubject(id);
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }
    public boolean deleteStudy(String studyName, ToxicHabitsDao toxicHabitsDao,
                               OtherHabitsDao otherHabitsDao, SubjectDao subjectDao,
                               SerieResolutionDao serieResolutionDao,
                               CognitiveTestResolutionDao cognitiveTestResolutionDao,
                               StudyDao studyDao,
                               PsychologicalDataDao psychologicalDataDao,
                               MilestoneDao milestoneDao,
                               PhysiologicalDataDao physiologicalDataDao,
                               BaseLineDao baseLineDao, FileDao fileDao) {
        // Firstly search subjects
        try {
            List<Subject> subjects = subjectDao.searchByStudy(studyName);
            if (subjects != null && subjects.size() > 0) {
                /*
                 *Secondly search otherHabits, toxicHabits, psychological data and
                 * physiological data.
                 */
                for (Subject subject : subjects){
                    this.deleteSubject(subject.getNumSubject(),toxicHabitsDao,
                            otherHabitsDao,  subjectDao,
                            serieResolutionDao,
                            cognitiveTestResolutionDao,
                            psychologicalDataDao,
                            milestoneDao,
                            physiologicalDataDao,
                            baseLineDao,
                            fileDao);
                }
                studyDao.deleteStudy(studyName);
                return true;
            }else{
                studyDao.deleteStudy(studyName);
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

}
