/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class define the controller for the incidences management.
 * End-points:
 * GET AND POST FOR /incidence
 * GET DELETE for /incidence/:id
 */
package tfg.backend.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tfg.backend.DataAccessLopdModel.AccessDao;
import tfg.backend.DataAccessLopdModel.IncidenceDao;
import tfg.backend.DataAccessModel.UserDao;
import tfg.backend.LopdModel.Incidence;
import tfg.backend.Utils.Auth;

import java.util.ArrayList;
import java.util.List;

@RestController
public class IncidenceCtrl {

    @Autowired
    AccessDao accessDao;

    @Autowired
    UserDao userDao;

    @Autowired
    IncidenceDao incidenceDao;

    /**
     * @return all incidences in database. Only administrator can use this operation.
     */
    @RequestMapping(value = "/incidence", method = RequestMethod.GET)
    public ResponseEntity<List<Incidence>> searchAllIncidences(@RequestHeader HttpHeaders
                                                                   headers) {

        List<Incidence> list = new ArrayList<>();
        HttpStatus code = HttpStatus.INTERNAL_SERVER_ERROR;
        try {
            list = incidenceDao.findAllIncidences();
            code = HttpStatus.OK;
        } catch (Exception e) {
            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userDao, headers, "GET", code.value(),
                    "/incidence");
            return new ResponseEntity<List<Incidence>>(list, code);
        }

    }
    /**
     * save new incidence in database. Only administrator can use this operation.
     */
    @RequestMapping(value = "/incidence", method = RequestMethod.POST , consumes =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Incidence> saveIncidence(@RequestHeader HttpHeaders
                                                                     headers,
                                                   @RequestBody Incidence incidence) {

        Incidence result = new Incidence();
        HttpStatus code = HttpStatus.INTERNAL_SERVER_ERROR;
        try {
            incidence.setUser(Auth.getUserFromHeaders(headers,userDao));
            Long id = incidenceDao.saveIncidence(incidence);

            incidence.setId(id);
            result = incidence;
            code = HttpStatus.OK;
        } catch (Exception e) {
            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userDao, headers, "POST", code.value(),
                    "/incidence");
            return new ResponseEntity<Incidence>(result, code);
        }

    }
    /**
     * Delete the required incidence. Only administrator can use this operation.
     */
    @RequestMapping(value = "/incidence/{identifier}", method = RequestMethod.DELETE)
    public ResponseEntity<Long> deleteIncidence(@RequestHeader HttpHeaders headers,
                                              @PathVariable(value = "identifier")
                                                      long id) {

        Long result = new Long(-1);
        HttpStatus code = HttpStatus.INTERNAL_SERVER_ERROR;
        try {
             if(id > 0) {
                 incidenceDao.deleteIncidence(id);
                 code = HttpStatus.OK;
                 result = new Long(1);
             }else {

                 code = HttpStatus.BAD_REQUEST;
             }

        } catch (Exception e) {
            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userDao, headers, "DELETE", code.value(),
                    "/incidence/" + id);
            return new ResponseEntity<Long>(result, code);
        }

    }
    /**
     * Get the required incidence. Only administrator can use this operation.
     */
    @RequestMapping(value = "/incidence/{identifier}", method = RequestMethod.GET)
    public ResponseEntity<Incidence> findIncidence(@RequestHeader HttpHeaders headers,
                                              @PathVariable(value = "identifier")
                                                      long id) {

        Incidence result = new Incidence();
        HttpStatus code = HttpStatus.INTERNAL_SERVER_ERROR;
        try {

            result = incidenceDao.findIncidence(id);
            if (result != null) {
                code = HttpStatus.OK;
            }
            code = HttpStatus.BAD_REQUEST;

        } catch (Exception e) {
            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userDao, headers, "GET", code.value(),
                    "/incidence/" + id);
            return new ResponseEntity<Incidence>(result, code);
        }

    }

}
