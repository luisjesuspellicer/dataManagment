/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class define the controller for the cognitiveTest management.
 * End-points:
 * - POST /cognitiveTest
 * - GET /cognitiveTest
 * - GET /cognitiveTest/{{id}}
 * - DELETE /cognitiveTest/{{id}}
 */
package tfg.backend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tfg.backend.DataAccessLopdModel.AccessDao;
import tfg.backend.DataAccessModel.CognitiveTestDao;
import tfg.backend.DataAccessModel.SerieDao;
import tfg.backend.DataAccessModel.UserDao;
import tfg.backend.DataModel.CognitiveTest;
import tfg.backend.DataModel.Serie;
import tfg.backend.RequestsStructures.AllCognitiveTestData;
import tfg.backend.Utils.Auth;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CognitiveTestCtrl {

    @Autowired
    CognitiveTestDao cognitiveTestDao;

    @Autowired
    SerieDao serieDao;

    @Autowired
    UserDao userDao;

    @Autowired
    AccessDao accessDao;

    /**
     * This method save a new cognitiveTest and save the
     * result of this operation.
     *
     * @param data
     * @param headers
     */
    @RequestMapping(value = "/cognitiveTest", method = RequestMethod.POST)
    public ResponseEntity<AllCognitiveTestData> saveCognitiveTest(
            @RequestBody AllCognitiveTestData data,
            @RequestHeader HttpHeaders headers) {

        AllCognitiveTestData allCognitiveTestData = new AllCognitiveTestData();
        HttpStatus code = HttpStatus.BAD_REQUEST;

        try {

            Long id = (Long) cognitiveTestDao.saveCognitiveTest(data
                    .getCognitiveTest());
            CognitiveTest aux = new CognitiveTest();
            aux.setId(id);
            for (Serie serie : data.getSeries()) {
                serie.setTest_id(aux);
                serieDao.saveSerie(serie);
            }
            allCognitiveTestData = data;
            code = HttpStatus.OK;

        } catch (Exception e) {

            code = HttpStatus.INTERNAL_SERVER_ERROR;

        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userDao, headers, "POST", code.value(),
                    "/cognitiveTest");
            return new ResponseEntity<AllCognitiveTestData>(allCognitiveTestData,
                    code);
        }

    }

    /**
     * This method return all cognitive tests. Only Administrator can make
     * this request.
     */
    @RequestMapping(value = "/cognitiveTest", method = RequestMethod.GET)
    public ResponseEntity<List<CognitiveTest>> searchAllCognitiveTest
    (@RequestHeader HttpHeaders headers) {

        List<CognitiveTest> list = new ArrayList<>();
        HttpStatus code = HttpStatus.BAD_REQUEST;

        try {
            list = cognitiveTestDao.findAllCognitiveTest();
            code = HttpStatus.OK;

        } catch (Exception e) {
            code = HttpStatus.INTERNAL_SERVER_ERROR;

        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userDao, headers, "GET", code.value(),
                    "/cognitiveTest");
            return new ResponseEntity<>(list, code);
        }

    }

    /**
     * This method return the cognitive test whose identifier is the same
     * that the param id. All authenticated users can use this operation.
     *
     * @param id
     */
    @RequestMapping(value = "/cognitiveTest/{identifier}", method = RequestMethod
            .GET)
    public ResponseEntity<List<Serie>> findSeriesOfCognitiveTest(
            @PathVariable(value = "identifier") long id,
            @RequestHeader HttpHeaders headers) {

        HttpStatus code = HttpStatus.BAD_REQUEST;
        List<Serie> list = new ArrayList<>();
        try {

            CognitiveTest cognitiveTest = cognitiveTestDao.findCognitiveTest(id);
            if (cognitiveTest != null) {

                list = serieDao.searchByTest(id);
                code = HttpStatus.OK;
            } else {

                code = HttpStatus.NOT_FOUND;
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userDao, headers, "GET", code.value(),
                    "/cognitiveTest/" + id);
            return new ResponseEntity<List<Serie>>(list, code);
        }
    }

    /**
     * Delete all series of CognitiveTest whose identifier is the same that the
     * param id. Delete all asociated information too.
     *
     * @param id
     */
    @RequestMapping(value = "/cognitiveTest/{identifier}", method = RequestMethod
            .DELETE)
    public ResponseEntity<List<Serie>> deleteCognitiveTest(
            @PathVariable(value = "identifier") long id,
            @RequestHeader HttpHeaders headers) {

        List<Serie> list = new ArrayList<>();
        HttpStatus code = HttpStatus.BAD_REQUEST;
        try {

            CognitiveTest cognitiveTest = cognitiveTestDao.findCognitiveTest(id);
            if (cognitiveTest != null) {

                List<Serie> series = serieDao.searchByTest(id);
                if (series != null) {

                    for (Serie a : series) {
                        serieDao.deleteSerie(a.getNumSerie());
                    }
                    cognitiveTestDao.deleteCognitiveTest(id);
                }
                list = serieDao.searchByTest(id);
                code = HttpStatus.OK;

            } else {

                code = HttpStatus.NOT_FOUND;

            }
        } catch (Exception e) {

            e.printStackTrace();
            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userDao, headers, "DELETE", code.value
                    (), "/cognitiveTest/" + id);
            return new ResponseEntity<List<Serie>>(list,code);
        }
    }


}
