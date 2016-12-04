/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class define the controller for the activity management.
 * End-points:
 * GET AND POST FOR /activity
 * GET for /activity/:name
 */
package tfg.backend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tfg.backend.DataAccessLopdModel.AccessDao;
import tfg.backend.DataAccessModel.ActivityDao;
import tfg.backend.DataAccessModel.UserDao;
import tfg.backend.DataModel.Activity;
import tfg.backend.Utils.Auth;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ActivityCtrl {

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private AccessDao accessDao;

    @Autowired
    private UserDao userDao;

    private final String path = "/activity";

    /**
     * Only admin user has permisions for this end-point.
     * Save a new activity in database.
     *
     * @return
     */
    @RequestMapping(value = path, method = RequestMethod.POST, consumes =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Activity> saveOneActivity(@RequestBody Activity data,
                                                    @RequestHeader HttpHeaders
                                                            headers) {

        HttpStatus code = HttpStatus.BAD_REQUEST;
        Activity activity = new Activity();
        try {
            if (data.getTypeActivity() == null || data.getDescription().compareTo
                    ("") == 0 || data.getTypeActivity().compareTo("") == 0 || data
                    .getDescription() == null) {
                code = HttpStatus.BAD_REQUEST;

                 /*
                  * In this case  activity = empty due to the fact
                  * that the request has bad parametres.
                  */

            } else {

                /*
                 * In this case return the same object that
                 * receive in the request.
                 */
                activityDao.saveActivity(data);
                code = HttpStatus.OK;
                activity = data;

            }
        } catch (Exception e) {
            e.printStackTrace();

             /*
              * In this case  activity = empty due to the fact
              * that the request has bad parametres.
              */
            code = HttpStatus.INTERNAL_SERVER_ERROR;

        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userDao, headers, "POST", code.value(),
                    path);
            return new ResponseEntity<Activity>(activity, code);
        }
    }

    /**
     * Only admin user has permisions for this end-point.
     *
     * @return all activities.
     */
    @RequestMapping(value = path, method = RequestMethod.GET)
    public ResponseEntity<List<Activity>> getallActivity(@RequestHeader
                                                                     HttpHeaders
                                                                     headers) {

        HttpStatus code = HttpStatus.BAD_REQUEST;
        List<Activity> list = new ArrayList<Activity>();
        try {

            list = activityDao.findAllActivity();
            code = HttpStatus.OK;

        } catch (Exception e) {
            e.printStackTrace();
            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userDao, headers, "GET", code.value(),
                    path);
            return new ResponseEntity<List<Activity>>(list, code);
        }
    }

    /**
     * Only admin user has permisions for this end-point.
     *
     * @return activity whose identifier is the same that
     * the identifier in the path of the request.
     */
    @RequestMapping(value = "/activity/{identifier}", method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Activity> getOneActivity(@PathVariable(value =
            "identifier") Long id, @RequestHeader HttpHeaders headers) {

        Activity activity = new Activity();
        HttpStatus code = HttpStatus.BAD_REQUEST;
        try {
            code = HttpStatus.OK;
            activity = activityDao.findActivity(id);

        } catch (Exception e) {
            code = HttpStatus.INTERNAL_SERVER_ERROR;
            e.printStackTrace();

        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userDao, headers, "GET", code.value(),
                    "/activity/" + id);
            return new ResponseEntity<Activity>(activity, code);
        }
    }

    /**
     * Only admin user has permisions for this end-point.
     *
     * @return 1 if activity was delete or -1 if happend one or more errors.
     */
    @RequestMapping(value = "/activity/{identifier}", method = RequestMethod.DELETE)
    public ResponseEntity<Long> deleteOneActivity(@PathVariable(value =
            "identifier") Long id, @RequestHeader HttpHeaders headers) {

        Long result = new Long(-1);
        HttpStatus code = HttpStatus.BAD_REQUEST;
        try {

            activityDao.deleteActivity(id);
            result = new Long(1);
            code = HttpStatus.OK;

        } catch (Exception e) {
            code = HttpStatus.INTERNAL_SERVER_ERROR;
            e.printStackTrace();
            result = new Long(-1);
        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userDao, headers, "DELETE", code.value(),
                    "/activity/" + id);
            return new ResponseEntity<Long>(result, code);
        }
    }
}
