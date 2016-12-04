/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class define the controller for the studies management.
 * End-points:
 * GET AND POST FOR /study
 * GET PUT DELETE for /study/:name
 * GET /study/subjects/:id
 */

package tfg.backend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import tfg.backend.DataAccessLopdModel.AccessDao;
import tfg.backend.DataAccessModel.*;
import tfg.backend.DataModel.Study;
import tfg.backend.DataModel.Subject;
import tfg.backend.Utils.Auth;
import tfg.backend.Utils.Deletes;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudyCtrl {

    @Autowired
    private StudyDao studyDao;

    @Autowired
    private UserDao userdDao;

    @Autowired
    ToxicHabitsDao toxicHabitsDao;

    @Autowired
    OtherHabitsDao otherHabitsDao;

    @Autowired
    SubjectDao subjectDao;

    @Autowired
    SerieResolutionDao serieResolutionDao;

    @Autowired
    CognitiveTestResolutionDao cognitiveTestResolutionDao;

    @Autowired
    PsychologicalDataDao psychologicalDataDao;

    @Autowired
    MilestoneDao milestoneDao;

    @Autowired
    PhysiologicalDataDao physiologicalDataDao;

    @Autowired
    BaseLineDao baseLineDao;

    @Autowired
    UserDao userDao;

    @Autowired
    FileDao fileDao;

    @Autowired
    AccessDao accessDao;

    /**
     * Only admin user has permisions for this end-point.
     *
     * @return all studies in database.
     */
    @RequestMapping(value = "/study", method = RequestMethod.GET)
    public ResponseEntity<List<Study>> searchAllStudy(@RequestHeader HttpHeaders
                                                                  headers) {

        HttpStatus code = HttpStatus.NOT_FOUND;
        List<Study> list = new ArrayList<>();
        try {

            list = studyDao.findAllStudy();
            code = HttpStatus.OK;
            return new ResponseEntity<>(studyDao.findAllStudy(), HttpStatus.OK);
        } catch (Exception e) {

            // Not Found.
        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userdDao, headers, "GET", code.value(),
                    "/study");
            return new ResponseEntity<>(list, code);
        }
    }

    /**
     * Only admin user has permisions for this end-point.
     * Save a new study in database.
     *
     * @return
     */
    @RequestMapping(value = "/study", method = RequestMethod.POST, consumes =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Study> saveOneStudy(@RequestBody Study data,
                                              @RequestHeader HttpHeaders headers) {

        HttpStatus code = HttpStatus.BAD_REQUEST;
        try {

            if (data.getStudyName() == null || data.getStudyName().compareTo("")
                    == 0) {

                // Bad Request. Params are empties
            } else {
                Study aux = studyDao.findStudy(data.getStudyName());
                if (aux == null) {
                    studyDao.saveStudy(data);
                    code = HttpStatus.OK;
                } else {
                    code = HttpStatus.CONFLICT;
                }

            }
        } catch (Exception e) {
            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userdDao, headers, "POST", code.value()
                    , "/study");
            return new ResponseEntity<Study>(data, code);
        }

    }

    /**
     * Checks if the user has permisions for the study.
     * Modify the study.
     */
    @RequestMapping(value = "/study/{identifier}", method = RequestMethod.PUT)
    public ResponseEntity<Study> modifyStudy(@PathVariable(value = "identifier")
                                                         String id, @RequestHeader
            HttpHeaders headers, @RequestBody Study study2) {

        Study study = new Study();
        HttpStatus code = HttpStatus.UNAUTHORIZED;
        try {
            if (Auth.isAuthenticated(id, headers, userdDao) &&
                    study2.getDescription() != null && study2.getDescription()
                    .compareTo("") != 0) {

                studyDao.updateStudy(id, study2.getDescription());
                study = study2;
                code = HttpStatus.OK;

            } else {

                // Unauthorized.
            }
        } catch (Exception e) {
            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userdDao, headers, "PUT", code.value(),
                    "/study/" + id);
            return new ResponseEntity<Study>(study, code);
        }
    }

    /**
     * Checks if the user has permisions for the study.
     * Return the study whose identifier is the same as
     * the request identifier .
     */
    @RequestMapping(value = "/study/{identifier}", method = RequestMethod.GET)
    public ResponseEntity<Study> searchOneStudy(@PathVariable(value =
            "identifier") String id, @RequestHeader HttpHeaders headers) {


        Study st = new Study();
        HttpStatus code = HttpStatus.UNAUTHORIZED;
        try {
            if (Auth.isAuthenticated(id, headers, userdDao)) {

                Study study = studyDao.findStudy(id);
                if (study == null || study.getStudyName() == null || study
                        .getStudyName().compareTo("") == 0) {

                    code = HttpStatus.BAD_REQUEST;
                } else {
                    st = study;
                    code = HttpStatus.OK;
                }
            } else {

                // Unauthorized.

            }
        } catch (Exception e) {
            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userdDao, headers, "GET", code.value(),
                    "/study/" + id);
            return new ResponseEntity<Study>(st, code);
        }
    }

    /**
     * Only administrator can use this operation.
     * Delete the study whose identifier is the same as
     * the request identifier and all delete all subjects of this study.
     */
    @RequestMapping(value = "/study/{identifier}", method = RequestMethod.DELETE)
    public ResponseEntity<Study> deleteStudy(@PathVariable(value = "identifier")
                                                         String id, @RequestHeader
            HttpHeaders headers) {

        Study st = new Study();
        HttpStatus code = HttpStatus.UNAUTHORIZED;
        try {

            Study study = studyDao.findStudy(id);
            if (study == null || study.getStudyName() == null || study
                    .getStudyName().compareTo("") == 0) {

                code = HttpStatus.BAD_REQUEST;
            } else {

                Deletes util = new Deletes();
                util.deleteStudy(id, toxicHabitsDao, otherHabitsDao, subjectDao,
                        serieResolutionDao, cognitiveTestResolutionDao, studyDao,
                        psychologicalDataDao, milestoneDao, physiologicalDataDao,
                        baseLineDao, fileDao);

                code = HttpStatus.OK;
            }

        } catch (Exception e) {
            e.printStackTrace();
            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userdDao, headers, "DELETE", code.value
                    (), "/study/" + id);
            return new ResponseEntity<Study>(st, code);
        }
    }

    /**
     * Checks if the user has permisions for the study.
     * Delete the study whose identifier is the same as
     * the request identifier and all delete all subjects of this study.
     */
    @RequestMapping(value = "/study/subjects/{identifier}", method = RequestMethod
            .GET)
    public ResponseEntity<List<Subject>> searchSubjectsOfStudy(@PathVariable(value
            = "identifier") String id, @RequestHeader HttpHeaders headers) {

        HttpStatus code = HttpStatus.NOT_FOUND;
        List<Subject> list = new ArrayList<>();
        try {
            if (Auth.isAuthenticated(id, headers, userDao)) {

                Study study = studyDao.findStudy(id);
                if (study == null || study.getStudyName() == null || study
                        .getStudyName().compareTo("") == 0) {
                    code = HttpStatus.BAD_REQUEST;
                } else {

                    list = subjectDao.searchByStudy(id);
                    if (list != null) {
                        code = HttpStatus.OK;
                    } else {
                        // Not Found
                    }
                }
            } else {
                code = HttpStatus.UNAUTHORIZED;
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userdDao, headers, "GET", code.value(),
                    "/study/subjects/" + id);
            return new ResponseEntity<List<Subject>>(list, code);
        }
    }
}
