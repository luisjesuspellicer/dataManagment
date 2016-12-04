/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class define the controller for the cognitiveTestResolution
 * management.
 * End-points:
 * - POST /cognitiveTestResolution
 * - GET /cognitiveTestResolution
 */
package tfg.backend.Controllers;

import org.apache.tools.ant.taskdefs.condition.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tfg.backend.DataAccessLopdModel.AccessDao;
import tfg.backend.DataAccessModel.*;
import tfg.backend.DataModel.CognitiveTest;
import tfg.backend.DataModel.CognitiveTestResolution;
import tfg.backend.DataModel.Ids.CognitiveTestResolutionID;
import tfg.backend.DataModel.SerieResolution;
import tfg.backend.DataModel.Subject;
import tfg.backend.RequestsStructures.AllCognitiveTestData;
import tfg.backend.RequestsStructures.AllCognitiveTestResolutionData;
import tfg.backend.Utils.Auth;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CognitiveTestResolutionCtrl {

    @Autowired
    CognitiveTestResolutionDao cognitiveTestResolutionDao;

    @Autowired
    SerieResolutionDao serieResolutionDao;

    @Autowired
    UserDao userDao;

    @Autowired
    SubjectDao subjectDao;

    @Autowired
    CognitiveTestDao cognitiveTestDao;

    @Autowired
    AccessDao accessDao;

    /**
     * This method save the new cognitiveTestResolution if the authentication is
     * correct.
     *
     * @param data
     * @param headers
     */
    @RequestMapping(value = "/cognitiveTestResolution", method = RequestMethod.POST)
    public ResponseEntity<AllCognitiveTestResolutionData>
    saveCognitiveTestResolution(@RequestBody AllCognitiveTestResolutionData data,
                                @RequestHeader HttpHeaders headers) {

        AllCognitiveTestResolutionData dat = new AllCognitiveTestResolutionData();
        HttpStatus code = HttpStatus.BAD_REQUEST;
        try {
            if (data == null) {

                // Bad request due to the param data is empty.  Bad Request.
            }

            // Check if NumSubject and numTest are valid params.
            else if (data.getSubject() == null || data.getSubject().getNumSubject
                    () == null || data.getCognitiveTest() == null || data
                    .getCognitiveTest().getTest_id() == null) {

                // Bad request due to the param data is empty. Bad Request.

            } else {

                // First check if it's an authorized request.
                Subject subject = subjectDao.findSubject(data.getSubject()
                        .getNumSubject());

                if (subject == null) {

                    // The subject not exists. Bad Request.

                } else {

                    if (Auth.isAuthenticated(subject.getStudyName().getStudyName()
                            , headers, userDao)) {

                        // Check Cognitive test exists.
                        CognitiveTest cognitiveTest = cognitiveTestDao
                                .findCognitiveTest(data.getCognitiveTest().getId());

                        if (cognitiveTest == null) {

                            // The cognitiveTest not exists. Bad Request.
                        } else {


                            // New object
                            CognitiveTestResolution cognitiveTestResolution = new
                                    CognitiveTestResolution();


                            // New ID
                            CognitiveTestResolutionID cognitiveTestResolutionID =
                                    new CognitiveTestResolutionID();
                            cognitiveTestResolutionID.setNumSubject(subject);

                            // Add the id
                            cognitiveTestResolution.setTest_id_resolution
                                    (cognitiveTestResolutionID);

                            cognitiveTestResolution.setTest_id(cognitiveTest);

                            //Save cognitive Test.
                            Long newId = cognitiveTestResolutionDao.
                                    saveCognitiveTestResolution
                                            (cognitiveTestResolution);

                            cognitiveTestResolution.getTest_id_resolution().setId
                                    (newId);
                            int i = 6;
                            for (SerieResolution aux : data.getSeries()) {
                                aux.setTest_id(cognitiveTestResolution);
                                i++;

                                serieResolutionDao.saveSerieResolution(aux);
                            }
                            //Save all serieResolution.

                            code = HttpStatus.OK;
                            dat = data;

                        }

                    } else {
                        code = HttpStatus.UNAUTHORIZED;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = HttpStatus.INTERNAL_SERVER_ERROR;

        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userDao, headers, "POST", code.value(),
                    "/cognitiveTestResolution");
            return new ResponseEntity<AllCognitiveTestResolutionData>(dat, code);
        }
    }

    @RequestMapping(value = "/cognitiveTestResolution", method = RequestMethod.GET)
    public ResponseEntity<List<CognitiveTestResolution>>
    searchAllCognitiveTestResolution(@RequestHeader HttpHeaders headers) {

        List<CognitiveTestResolution> list = new ArrayList<>();
        HttpStatus code = HttpStatus.BAD_REQUEST;
        try {

            list = cognitiveTestResolutionDao.findAllCognitiveTestResolution();
            code = HttpStatus.OK;
        } catch (Exception e) {

            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userDao, headers, "GET", code.value(),
                    "/cognitiveTestResolution");
            return new ResponseEntity<List<CognitiveTestResolution>>(list, code);
        }

    }

    /**
     * Return series of the cognitiveTestResolution whose id it's equal to
     * identifier.
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/cognitiveTestResolution/{identifier}", method =
            RequestMethod.GET)
    public ResponseEntity<List<SerieResolution>>
    findSeriesOfCognitiveTestResolution(@PathVariable(value = "identifier") long
                                                id, @RequestHeader HttpHeaders
            headers) {

        List<SerieResolution> list = new ArrayList<>();
        HttpStatus code = HttpStatus.NOT_FOUND;

        try {

            CognitiveTestResolution cognitiveTestResolution =
                    cognitiveTestResolutionDao.findCognitiveTestResolution(id);
            if (cognitiveTestResolution != null) {
                list = serieResolutionDao.searchByTest(id);
                code = HttpStatus.OK;
            } else {

                // Not found.
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userDao, headers, "GET", code.value(), "/cognitiveTestResolution/" + id);
            return new ResponseEntity<List<SerieResolution>>(list, code);
        }
    }
}