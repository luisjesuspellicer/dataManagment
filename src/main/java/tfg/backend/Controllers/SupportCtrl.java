/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class define the controller for the supports management.
 * End-points:
 * GET AND POST FOR /support
 * GET FOR /support/typeInformation/:type
 * GET FOR /support/name/:name
 * GET AND POST FOR /supportES
 * GET DELETE for /support/:id
 * GET  for /supportES/receiver/:name
 * GET  for /supportES/emisor/:name
 */
package tfg.backend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tfg.backend.DataAccessLopdModel.AccessDao;
import tfg.backend.DataAccessLopdModel.SupportDao;
import tfg.backend.DataAccessLopdModel.SupportESDao;
import tfg.backend.DataAccessModel.UserDao;
import tfg.backend.LopdModel.Support;
import tfg.backend.LopdModel.SupportES;
import tfg.backend.Utils.Auth;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SupportCtrl {

    @Autowired
    AccessDao accessDao;

    @Autowired
    SupportDao supportDao;

    @Autowired
    UserDao userDao;

    @Autowired
    SupportESDao supportESDao;

    /**
     * @return all supports in database. Only administrator can use this operation.
     */
    @RequestMapping(value = "/support", method = RequestMethod.GET)
    public ResponseEntity<List<Support>> searchAllSupports(@RequestHeader
                                                                       HttpHeaders
                                                                       headers) {

        List<Support> list = new ArrayList<>();
        HttpStatus code = HttpStatus.INTERNAL_SERVER_ERROR;
        try {
            list = supportDao.findAllSupport();
            code = HttpStatus.OK;
        } catch (Exception e) {
            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userDao, headers, "GET", code.value(),
                    "/support");
            return new ResponseEntity<List<Support>>(list, code);
        }

    }

    /**
     * @return all supports in database. Only administrator can use this operation.
     */
    @RequestMapping(value = "/support/{identifier}", method = RequestMethod.GET)
    public ResponseEntity<Support> searchSupport(@RequestHeader HttpHeaders
                                                             headers,
                                                 @PathVariable(value =
                                                         "identifier") long id) {

        Support result = new Support();
        HttpStatus code = HttpStatus.INTERNAL_SERVER_ERROR;
        try {

            result = supportDao.findSupport(id);
            code = HttpStatus.OK;


        } catch (Exception e) {
            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userDao, headers, "GET", code.value(),
                    "/support/" + id);
            return new ResponseEntity<Support>(result, code);
        }
    }

    /**
     * Delete the required support. Only administrator can use this operation.
     */
    @RequestMapping(value = "/support/id/{identifier}", method = RequestMethod
            .DELETE)
    public ResponseEntity<Long> deleteSupport(@RequestHeader HttpHeaders headers,
                                              @PathVariable(value = "identifier")
                                                      long id) {

        Long result = new Long(-1);
        HttpStatus code = HttpStatus.INTERNAL_SERVER_ERROR;
        try {

            supportDao.deleteSupport(id);

            code = HttpStatus.OK;
            result = new Long(1);


        } catch (Exception e) {
            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userDao, headers, "DELETE", code.value
                    (), "/support/" + id);
            return new ResponseEntity<Long>(result, code);
        }

    }

    /**
     * Delete the required supportes. Only administrator can use this operation.
     */
    @RequestMapping(value = "/supportES/id/{identifier}", method = RequestMethod
            .DELETE)
    public ResponseEntity<Long> deleteSupportES(@RequestHeader HttpHeaders
                                                            headers, @PathVariable
            (value = "identifier") long id) {

        Long result = new Long(-1);
        HttpStatus code = HttpStatus.INTERNAL_SERVER_ERROR;
        try {

            if (id > 0) {
                supportESDao.deleteSupportES(id);
                code = HttpStatus.OK;
                result = new Long(1);
            } else {
                code = HttpStatus.BAD_REQUEST;
            }

        } catch (Exception e) {
            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userDao, headers, "DELETE", code.value
                    (), "/supportES/" + id);
            return new ResponseEntity<Long>(result, code);
        }

    }


    /**
     * @return supports with the name required in database. Only administrator can
     * use this operation.
     */
    @RequestMapping(value = "/support/name/{name}", method = RequestMethod.GET)
    public ResponseEntity<Support> searchSupportByName(
            @RequestHeader HttpHeaders headers,
            @PathVariable(value = "name") String name) {

        Support result = new Support();
        HttpStatus code = HttpStatus.INTERNAL_SERVER_ERROR;
        try {

            result = supportDao.searchByName(name);
            code = HttpStatus.OK;


        } catch (Exception e) {
            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userDao, headers, "GET", code.value(),
                    "/support/name/" + name);
            return new ResponseEntity<Support>(result, code);
        }

    }

    /**
     * @return supports of the emisor required in database. Only administrator can
     * use this operation.
     */
    @RequestMapping(value = "/supportES/emisor/{name}", method = RequestMethod.GET)
    public ResponseEntity<List<SupportES>> searchSupportESByEmisor(
            @RequestHeader HttpHeaders headers,
            @PathVariable(value = "name") String name) {

        List<SupportES> result = new ArrayList<>();
        HttpStatus code = HttpStatus.INTERNAL_SERVER_ERROR;
        try {
            if (name != null && name.compareTo("") != 0) {
                result = supportESDao.searchByEmisor(name);
                code = HttpStatus.OK;
            } else {
                code = HttpStatus.BAD_REQUEST;
            }

        } catch (Exception e) {
            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userDao, headers, "GET", code.value(),
                    "/supportES/emisor/" + name);
            return new ResponseEntity<List<SupportES>>(result, code);
        }

    }

    /**
     * @return information of {id} supportES.
     */
    @RequestMapping(value = "/supportES/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<SupportES> searchSupportESById(
            @RequestHeader HttpHeaders headers,
            @PathVariable(value = "id") Long id) {

        SupportES result = new SupportES();
        HttpStatus code = HttpStatus.INTERNAL_SERVER_ERROR;
        try {

            //result = supportESDao.searchByEmisor(name);
            result = supportESDao.findSupportES(id);
            if (result != null) {

                code = HttpStatus.OK;
            } else {

                code = HttpStatus.BAD_REQUEST;
            }

        } catch (Exception e) {
            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userDao, headers, "GET", code.value(),
                    "/supportES/id/" + id);
            return new ResponseEntity<SupportES>(result, code);
        }

    }

    /**
     * @return supports of the emisor required in database. Only administrator can
     * use this operation.
     */
    @RequestMapping(value = "/supportES/receiver/{name}",
            method = RequestMethod.GET)
    public ResponseEntity<List<SupportES>> searchSupportESByReceiver
    (@RequestHeader HttpHeaders headers, @PathVariable(value = "name") String name) {

        List<SupportES> result = new ArrayList<>();
        HttpStatus code = HttpStatus.INTERNAL_SERVER_ERROR;
        try {
            if (name != null && name.compareTo("") != 0) {
                result = supportESDao.searchByReceiver(name);
                code = HttpStatus.OK;
            } else {
                code = HttpStatus.BAD_REQUEST;
            }

        } catch (Exception e) {
            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userDao, headers, "GET", code.value(),
                    "/supportES/receiver/" + name);
            return new ResponseEntity<List<SupportES>>(result, code);
        }

    }

    /**
     * @return supports with the name required in database. Only administrator can
     * use this operation.
     */
    @RequestMapping(value = "/support/typeInformation/{type}", method =
            RequestMethod.GET)
    public ResponseEntity<List<Support>> searchSupportsByTypeInformation
    (@RequestHeader HttpHeaders headers, @PathVariable(value = "type") String type) {

        List<Support> result = new ArrayList<>();
        HttpStatus code = HttpStatus.INTERNAL_SERVER_ERROR;
        try {

            result = supportDao.searchByTypeInformation(type);
            code = HttpStatus.OK;


        } catch (Exception e) {
            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userDao, headers, "GET", code.value(),
                    "/support/typeInformation/" + type);
            return new ResponseEntity<List<Support>>(result, code);
        }

    }

    /**
     * Only administrator can use this operation.  Returo the required support if
     * it was saved, else return empty support.
     */
    @RequestMapping(value = "/support", method = RequestMethod.POST, consumes =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Support> saveSupport(@RequestHeader HttpHeaders headers,
                                               @RequestBody Support support) {

        Support result = new Support();
        HttpStatus code = HttpStatus.INTERNAL_SERVER_ERROR;
        try {
            Support aux = supportDao.searchByName(support.getName());
            if (aux == null) {
                supportDao.saveSupport(support);
                result = support;
                code = HttpStatus.OK;
            } else {

                code = HttpStatus.BAD_REQUEST;
            }

        } catch (Exception e) {
            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userDao, headers, "POST", code.value(),
                    "/support");
            return new ResponseEntity<Support>(result, code);
        }
    }

    /**
     * @return all supports in database. Only administrator can use this operation.
     */
    @RequestMapping(value = "/supportES", method = RequestMethod.GET)
    public ResponseEntity<List<SupportES>> searchAllSupportsES(@RequestHeader
                                                                           HttpHeaders headers) {

        List<SupportES> list = new ArrayList<>();
        HttpStatus code = HttpStatus.INTERNAL_SERVER_ERROR;
        try {
            list = supportESDao.findAllSupportES();
            code = HttpStatus.OK;
        } catch (Exception e) {
            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userDao, headers, "GET", code.value(),
                    "/supportES");
            return new ResponseEntity<List<SupportES>>(list, code);
        }

    }

    /**
     * Only administrator can use this operation.  Returo the required support if
     * it was saved, else return empty support.
     */
    @RequestMapping(value = "/supportES", method = RequestMethod.POST, consumes =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SupportES> saveSupportES(@RequestHeader HttpHeaders
                                                               headers,
                                                   @RequestBody SupportES
                                                           supportES) {

        SupportES result = new SupportES();
        HttpStatus code = HttpStatus.INTERNAL_SERVER_ERROR;

        Support aux = supportDao.searchByName(supportES.getSupport().getName());
        if(aux != null){
            supportES.setSupport(aux);
        }
        try {
            supportESDao.saveSupportES(supportES);
            result = supportES;
            code = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {

            //Save the access and its results
            Auth.saveTypeAccess(accessDao, userDao, headers, "POST", code.value(),
                    "/support");
            return new ResponseEntity<SupportES>(result, code);
        }

    }


}
