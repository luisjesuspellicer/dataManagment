/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class define the controller for the subjects management.
 * End-points:
 * GET AND POST FOR /study
 * GET PUT DELETE for /study/:name
 */

package tfg.backend.Controllers;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import tfg.backend.DataAccessLopdModel.AccessDao;
import tfg.backend.DataAccessModel.*;
import tfg.backend.DataModel.*;
import tfg.backend.DataModel.Ids.DataID;
import tfg.backend.RequestsStructures.AllPersonData;

import tfg.backend.RequestsStructures.ResolutionTestSubject;
import tfg.backend.Utils.Auth;
import tfg.backend.Utils.Deletes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SubjectCtrl {

    @Autowired
    private UserDao userDao;

    @Autowired
    private SubjectDao subjectDao;

    @Autowired
    private ToxicHabitsDao toxicHabitsDao;

    @Autowired
    private OtherHabitsDao otherHabitsDao;

    @Autowired
    private PhysiologicalDataDao physiologicalDataDao;

    @Autowired
    private PsychologicalDataDao psychologicalDataDao;

    @Autowired
    CognitiveTestResolutionDao cognitiveTestResolutionDao;

    @Autowired
    SerieResolutionDao serieResolutionDao;

    @Autowired
    SerieDao serieDao;

    @Autowired
    CognitiveTestDao cognitiveTestDao;

    @Autowired
    BaseLineDao baseLineDao;

    @Autowired
    FileDao fileDao;

    @Autowired
    MilestoneDao milestoneDao;

    @Autowired
    AccessDao accessDao;

    /**
     * Create a new subject. Only authenticate users with permisions in the
     * required study can use this operation.
     *
     * @param data
     * @param headers
     * @return number of subject's if there is a new subject in db or -1 if
     * happend any error.
     */
    @RequestMapping(value = "/subject", method = RequestMethod.POST, consumes =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> updateWithMultipleObjects(
            @RequestBody AllPersonData data,
            @RequestHeader HttpHeaders headers) {

        Long res = new Long(-1);
        HttpStatus code = HttpStatus.BAD_REQUEST;
        try {
            if (Auth.isAuthenticated(data.getSubject().getStudyName().getStudyName
                    (), headers, userDao)) {
                Long id = subjectDao.saveSubject(data.getSubject());
                if (id >= 0) {
                    DataID toxicH = new DataID();
                    DataID other = new DataID();
                    DataID psy = new DataID();
                    DataID phy = new DataID();

                    Subject s = new Subject();
                    s.setNumSubject(new Long(id));
                    toxicH.setNumSubject(s);
                    other.setNumSubject(s);
                    psy.setNumSubject(s);
                    phy.setNumSubject(s);
                    ToxicHabits toxicHabits = data.getToxicHabits();
                    OtherHabits otherHabits = data.getOtherHabits();
                    PsychologicalData psychologicalData = data
                            .getPsychologicalData();
                    PhysiologicalData physiologicalData = data
                            .getPhysiologicalData();
                    toxicHabits.setId(toxicH);
                    otherHabits.setId(other);
                    physiologicalData.setId(phy);

                    toxicHabitsDao.saveToxicHabits(toxicHabits);

                    otherHabitsDao.saveOtherHabits(otherHabits);

                    physiologicalDataDao.savePhysiologicalData(physiologicalData);

                    psychologicalData.setId(psy);
                    psychologicalDataDao.savePsychologicalData(psychologicalData);

                    res = new Long(subjectDao.count());
                    code = HttpStatus.OK;
                } else {
                    code = HttpStatus.BAD_REQUEST;
                }

            } else {
                code = HttpStatus.UNAUTHORIZED;
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {

            Auth.saveTypeAccess(accessDao, userDao, headers, "POST", code.value(),
                    "/subject");
            return new ResponseEntity<Long>(res, code);
        }

    }

    /**
     * @return all subjects in database. Only administrator can use this operation.
     */
    @RequestMapping(value = "/subject", method = RequestMethod.GET)
    public ResponseEntity<List<Subject>> searchAllSubjects(
            @RequestHeader HttpHeaders headers) {

        List<Subject> list = new ArrayList<>();
        HttpStatus code = HttpStatus.INTERNAL_SERVER_ERROR;
        try {
            list = subjectDao.findAllSubject();
            code = HttpStatus.OK;
        } catch (Exception e) {
            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userDao, headers, "GET", code.value(),
                    "/subject");
            return new ResponseEntity<List<Subject>>(list, code);
        }

    }

    /**
     * Return the subject whose identifier is the same as
     * the request identifier .
     */

    @RequestMapping(value = "/subject/{identifier}", method = RequestMethod.GET)
    public ResponseEntity<Subject> searchOneSubject(
            @PathVariable(value = "identifier") Long id,
            @RequestHeader HttpHeaders headers) {

        Subject sub = new Subject();
        HttpStatus code = HttpStatus.INTERNAL_SERVER_ERROR;
        try {
            Subject study = subjectDao.findSubject(id);
            if (study == null) {
                return new ResponseEntity<Subject>(study, HttpStatus.NOT_FOUND);
            } else {
                if (Auth.isAuthenticated(study.getStudyName().getStudyName(),
                        headers, userDao)) {
                    sub = study;
                    code = HttpStatus.OK;
                } else {
                    code = HttpStatus.UNAUTHORIZED;
                }
            }
        } catch (Exception e) {
            code = HttpStatus.INTERNAL_SERVER_ERROR;

        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userDao, headers, "GET", code.value(),
                    "/subject/" + id);
            return new ResponseEntity<Subject>(sub, code);
        }
    }

    /**
     * Return the subject tests whose identifier is the same as
     * the request identifier .
     */

    @RequestMapping(value = "/subject/cognitiveTest/{identifier}", method =
            RequestMethod.GET)
    public ResponseEntity<ResolutionTestSubject> searchTestResponses(
            @PathVariable(value = "identifier") Long id,
            @RequestHeader HttpHeaders headers) {

        ResolutionTestSubject resolutionTestSubject = new ResolutionTestSubject();
        HttpStatus code = HttpStatus.BAD_REQUEST;
        try {
            Subject subject = subjectDao.findSubject(id);
            if (subject != null) {
                if (Auth.isAuthenticated(subject.getStudyName().getStudyName(),
                        headers, userDao)) {

                    // Firstly search all the resolution tests.
                    List<CognitiveTestResolution> resolutions =
                            cognitiveTestResolutionDao.searchBySubject(id);

                    // Secondly, search original tests.
                    List<CognitiveTest> tests = new ArrayList<>();
                    for (CognitiveTestResolution res : resolutions) {
                        CognitiveTest test = cognitiveTestDao.findCognitiveTest
                                (res.getTest_id().getId());
                        tests.add(test);

                    }

                    resolutionTestSubject.setSolutions(resolutions);
                    resolutionTestSubject.setTests(tests);

                    code = HttpStatus.OK;
                } else {
                    code = HttpStatus.NOT_FOUND;
                }

            } else {
                code = HttpStatus.NOT_FOUND;
            }
        } catch (Exception e) {
            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userDao, headers, "GET", code.value(),
                    "/subject/cognitiveTest/" + id);
            return new ResponseEntity<ResolutionTestSubject>
                    (resolutionTestSubject, code);
        }
    }

    /**
     * Only authenticated users with permisions in the required study can use this
     * operation.
     *
     * @param id
     * @param headers
     * @return the baseLine whose identifier is the same that the param id.
     */
    @RequestMapping(value = "/subject/baseLine/{identifier}", method =
            RequestMethod.GET)
    public ResponseEntity<List<BaseLine>> searchActivities(
            @PathVariable(value = "identifier") Long id,
            @RequestHeader HttpHeaders headers) {

        List<BaseLine> list = new ArrayList<>();
        HttpStatus code = HttpStatus.BAD_REQUEST;
        try {
            System.out.println("");
            Subject subject = subjectDao.findSubject(id);
            if (subject != null) {
                if (Auth.isAuthenticated(subject.getStudyName().getStudyName(),
                        headers, userDao)) {

                    // Firstly search all the resolution tests.
                    list = baseLineDao.searchBySubject(id);
                    code = HttpStatus.OK;
                } else {
                    code = HttpStatus.UNAUTHORIZED;
                }

            } else {
                // Bad Request
            }
        } catch (Exception e) {
            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userDao, headers, "GET", code.value(),
                    "/subject/baseLine/" + id);
            return new ResponseEntity<List<BaseLine>>(list, code);
        }
    }

    /**
     * Only authenticated users with permisions in the required study, can use
     * this operation.
     *
     * @param file
     * @param id
     * @param headers
     * @param basic
     * @return Save one file.
     */
    @RequestMapping(value = "/subject/fileInfo/{identifier}", method =
            RequestMethod.POST)
    public ModelAndView saveFile(@RequestParam("file") MultipartFile file,
                                 @PathVariable(value = "identifier") long id,
                                 @RequestHeader HttpHeaders headers,
                                 @CookieValue("Basic") String basic) {

        FileInfo fileInfo = new FileInfo();
        Subject subject = subjectDao.findSubject(id);
        if (subject != null) {
            if (Auth.isAuthenticated2(subject.getStudyName().getStudyName(),
                    basic, userDao)) {

                fileInfo.setNameFile(file.getOriginalFilename());

                fileInfo.setNumSubject(subject);

                fileDao.saveFileInfo(fileInfo, file);

                Auth.saveTypeAccess(accessDao, userDao, headers, "POST",
                        HttpStatus.OK.value(), "/subject/fileInfo/" + id);
                return new ModelAndView("redirect:/");
            } else {
                Auth.saveTypeAccess(accessDao, userDao, headers, "POST",
                        HttpStatus.UNAUTHORIZED.value(), "/subject/fileInfo/" + id);
                return new ModelAndView("redirect:/");
            }
        } else {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userDao, headers, "POST", HttpStatus
                    .NOT_FOUND.value(), "/subject/fileInfo/" + id);
            return new ModelAndView("redirect:/");
        }

    }

    /**
     * Only authenticated users with permisions in the required study, can use
     * this operation.
     *
     * @param id
     * @param headers
     * @param basic
     * @return the files's information of the required subject.
     */
    @RequestMapping(value = "/subject/fileInfo/{identifier}", method =
            RequestMethod.GET)
    public ResponseEntity<List<FileInfo>> searchFiles(
            @PathVariable(value = "identifier") long id,
            @RequestHeader HttpHeaders headers,
            @CookieValue("Basic") String basic) {

        HttpStatus code = HttpStatus.BAD_REQUEST;
        List<FileInfo> list = new ArrayList<>();
        try {

            Subject subject = subjectDao.findSubject(id);
            if (subject != null) {

                if (Auth.isAuthenticated2(subject.getStudyName().getStudyName(),
                        basic, userDao)) {

                    list = fileDao.findBySubject(id);
                    code = HttpStatus.OK;
                } else {
                    code = HttpStatus.UNAUTHORIZED;
                }
            } else {

                code = HttpStatus.NOT_FOUND;
            }
        } catch (Exception e) {
            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userDao, headers, "GET", code.value(),
                    "/subject/fileInfo/" + id);
            return new ResponseEntity<List<FileInfo>>(list, code);
        }

    }

    /**
     * Only authenticated users who have permisions in the required study.
     *
     * @param data
     * @param headers
     * @param basic
     * @param request
     * @param response
     * @return the request file.
     */
    @RequestMapping(value = "/fileInfo", method = RequestMethod.GET)
    public ModelAndView findFile(
            @RequestParam("file") String data,
            @RequestHeader HttpHeaders headers,
            @CookieValue("Basic") String basic,
            final HttpServletRequest request,
            final HttpServletResponse response) {


        try {
            FileInfo fileInfo = fileDao.findFileInfo(data);
            File file = fileDao.file(data);

            if (fileInfo != null) {
                if (fileInfo.getNumSubject() != null) {
                    if (Auth.isAuthenticated2(fileInfo.getNumSubject()
                            .getStudyName().getStudyName(), basic, userDao)) {

                        try (InputStream fileInputStream = new FileInputStream
                                (file); OutputStream output = response
                                .getOutputStream();) {

                            response.reset();
                            response.setContentType("application/octet-stream");
                            response.setContentLength((int) (file.length()));

                            response.setHeader("Content-Disposition", "attachment;" +
                                    "" + "" + "" + "" + "" + "" + "" + "" + "" +
                                    "" + "" + "" + "" + "" + "" + "" + "" + "" +
                                    "" + "" + "" + "" + "" + "" + "" + " " +
                                    "filename=\"" + file.getName() + "\"");

                            IOUtils.copyLarge(fileInputStream, output);
                            output.flush();

                            Auth.saveTypeAccess(accessDao, userDao, basic, "GET",
                                    HttpStatus.OK.value(), "/fileInfo/" + data);
                            return new ModelAndView("redirect:/");
                        } catch (IOException e) {
                            e.printStackTrace();

                            Auth.saveTypeAccess(accessDao, userDao, basic, "GET",
                                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                    "/fileInfo/" + data);
                            return new ModelAndView("redirect:/");
                        }

                    } else {

                        Auth.saveTypeAccess(accessDao, userDao, basic, "GET",
                                HttpStatus.UNAUTHORIZED.value(), "/fileInfo/" +
                                        data);
                        return new ModelAndView("redirect:/");
                    }
                } else {

                    Auth.saveTypeAccess(accessDao, userDao, basic, "GET",
                            HttpStatus.BAD_REQUEST.value(), "/fileInfo/" + data);
                    return new ModelAndView("redirect:/");
                }
            } else {

                Auth.saveTypeAccess(accessDao, userDao, basic, "GET", HttpStatus
                        .BAD_REQUEST.value(), "/fileInfo/" + data);
                return new ModelAndView("redirect:/");
            }
        } catch (Exception e) {

            Auth.saveTypeAccess(accessDao, userDao, basic, "GET", HttpStatus
                    .BAD_REQUEST.value(), "/fileInfo/" + data);
            e.printStackTrace();
            return new ModelAndView("redirect:/");

        }
    }

    /**
     * @param nameFile
     * @param headers
     * @return 1 if file was deleted or -1 if happend errors.
     */
    @RequestMapping(value = "/fileInfo", method = RequestMethod.DELETE)
    public ResponseEntity<Long> deleteFileS(@RequestParam("file") String nameFile,
                                            @RequestHeader HttpHeaders headers) {

        HttpStatus code = HttpStatus.BAD_REQUEST;
        Long res = new Long(-1);
        try {
            FileInfo fileInfo = fileDao.findFileInfo(nameFile);
            if (fileInfo != null) {
                if (fileInfo.getNumSubject() != null) {
                    if (Auth.isAuthenticated(fileInfo.getNumSubject().getStudyName
                            ().getStudyName(), headers, userDao)) {

                        fileDao.deleteFileInfo(nameFile);

                        res = new Long(1);
                        code = HttpStatus.OK;
                    } else {

                        code = HttpStatus.UNAUTHORIZED;
                    }
                } else {

                    code = HttpStatus.INTERNAL_SERVER_ERROR;
                }
            } else {
                code = HttpStatus.NOT_FOUND;
            }
        } catch (Exception e) {

            e.printStackTrace();
            code = HttpStatus.INTERNAL_SERVER_ERROR;

        } finally {

            Auth.saveTypeAccess(accessDao, userDao, headers, "DELETE", code.value
                    (), "/fileInfo/" + nameFile);
            return new ResponseEntity<Long>(res, code);
        }
    }

    /**
     * Only authenticated users with permisions in the request study can use this
     * operation.
     *
     * @param id
     * @param headers
     * @return 1 if delete the subject without errors or -1 if any errors happend
     */
    @RequestMapping(value = "/subject/{identifier}", method = RequestMethod.DELETE)
    public ResponseEntity<Long> deletesSubject(
            @PathVariable(value = "identifier")long id,
            @RequestHeader HttpHeaders headers) {

        HttpStatus code = HttpStatus.BAD_REQUEST;
        Long res = new Long(-1);
        try {
            Subject subject = subjectDao.findSubject(id);
            if (subject != null) {
                if (Auth.isAuthenticated(subject.getStudyName().getStudyName(),
                        headers, userDao)) {

                    Deletes d = new Deletes();
                    d.deleteSubject(id, toxicHabitsDao, otherHabitsDao,
                            subjectDao, serieResolutionDao,
                            cognitiveTestResolutionDao, psychologicalDataDao,
                            milestoneDao, physiologicalDataDao, baseLineDao,
                            fileDao);
                    res = new Long(1);
                    code = HttpStatus.OK;
                } else {

                    code = HttpStatus.UNAUTHORIZED;
                }
            } else {

                code = HttpStatus.NOT_FOUND;
            }

        } catch (Exception e) {

            e.printStackTrace();
            code = HttpStatus.INTERNAL_SERVER_ERROR;

        } finally {

            Auth.saveTypeAccess(accessDao, userDao, headers, "DELETE", code.value
                    (), "/subject/" + id);
            return new ResponseEntity<Long>(res,code);
        }
    }

}
