package tfg.backend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tfg.backend.DataAccessLopdModel.AccessDao;
import tfg.backend.DataAccessModel.UserDao;
import tfg.backend.DataModel.User;
import tfg.backend.RequestsStructures.CreateAdmin;
import tfg.backend.Utils.Auth;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserCtrl {

    @Autowired
    protected UserDao users;

    @Autowired
    public StandardPasswordEncoder standardPasswordEncoder;

    @Autowired
    public AccessDao accessDao;


    @RequestMapping(value = "/numUsers", method = RequestMethod.GET)
    public ResponseEntity<Integer> searchAllUsers() {

        try {
            List<User> userss = users.findAllUsers();
            if (userss.size() > 0) {
                return new ResponseEntity<>(new Integer(1), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new Integer(0), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new Integer(-1), HttpStatus
                    .INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * This end-point is public only one time when the app starts.
     *
     * @param admin
     * @return
     */
    @RequestMapping(value = "/admin", method = RequestMethod.POST, consumes =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateAdmin> createAdmin(
            @RequestBody User admin) {

        try {
            List<User> userss = users.findAllUsers();
            CreateAdmin c = new CreateAdmin();
            if (userss.size() > 0) {
                c.setResponse("There is already an administrator");
                return new ResponseEntity<CreateAdmin>(c, HttpStatus.UNAUTHORIZED);
            } else {
                if (admin.getPrivileges().compareTo("ROLE_ADMIN") != 0 || admin
                        .getPassword() == null || admin.getUserName() == null) {
                    c.setResponse("Bad Request, wrong role, " + "no password or "
                            + "no" + " username");
                    return new ResponseEntity<CreateAdmin>(c, HttpStatus
                            .BAD_REQUEST);
                } else {

                    users.saveUser(admin);
                    c.setResponse("New Admin add");
                    return new ResponseEntity<CreateAdmin>(c, HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            return new ResponseEntity<CreateAdmin>(new CreateAdmin(), HttpStatus
                    .INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Only a admin user can use this end-point
     *
     * @return
     */
    @RequestMapping(value = "/user/{identifier}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(
            @PathVariable(value = "identifier") String id,
            @RequestHeader HttpHeaders headers) {

        HttpStatus code = HttpStatus.BAD_REQUEST;
        User user = new User();

        try {

            Auth auth = new Auth();
            User aux = users.findUser(id);
            if (users.findUser(id) != null || Auth.getPrivilegesList(aux
                    .getPrivileges()).get(0).compareTo("ROLE_ADMIN") != 0) {
                users.deleteUser(aux.getUserName());
                user = aux;
                code = HttpStatus.OK;
            } else {

                // Bad Request
            }

        } catch (Exception e) {
            e.printStackTrace();

            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {

            Auth.saveTypeAccess(accessDao, users, headers, "DELETE", code.value(),
                    "/user/" + id);
            return new ResponseEntity(user, code);
        }
    }

    /**
     * Only a admin user can use this end-point
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST, consumes =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(
            @RequestBody User user,
            @RequestHeader HttpHeaders headers) {

        HttpStatus code = HttpStatus.BAD_REQUEST;
        User res = new User();
        try {

            if (user.getUserName() == null || user.getPassword() == null || user
                    .getPrivileges() == null) {
                // Bad request.
            } else {
                boolean admin = false;

                for (String priv : Auth.getPrivilegesList(user.getPrivileges())) {
                    if (priv.compareToIgnoreCase("ROLE_ADMIN") == 0) {
                        admin = true;
                    }
                }
                if (admin) {

                    // Bad request.
                } else {

                    users.saveUser(user);
                    res = user;
                    code = HttpStatus.OK;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {

            Auth.saveTypeAccess(accessDao, users, headers, "POST", code.value(),
                    "/user");
            return new ResponseEntity<User>(res, code);
        }
    }

    /**
     * Only a admin user or the same user can use this end-point
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/user/password", method = RequestMethod.PUT, consumes
            = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> modifyUserPassword(
            @RequestBody User user,
            @RequestHeader HttpHeaders headers) {

        HttpStatus code = HttpStatus.BAD_REQUEST;
        User res = new User();

        try {
            if (user.getUserName() == null || user.getPassword() == null) {

                // Bad Request.
            } else {
                Auth auth = new Auth();
                if (auth.isTheSameUser(user.getUserName(), headers, users,
                        standardPasswordEncoder)) {

                    users.changePassword(user.getUserName(), user.getPassword());
                    res = user;
                    code = HttpStatus.OK;
                } else {
                    code = HttpStatus.UNAUTHORIZED;
                }

            }
        } catch (Exception e) {

            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {

            Auth.saveTypeAccess(accessDao, users, headers, "PUT", code.value(),
                    "/user/password/" + user.getUserName());
            return new ResponseEntity<User>(res, code);
        }
    }

    /**
     * Only a admin user or the same user can use this end-point
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/user/email", method = RequestMethod.PUT, consumes =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> modifyUserEmail(
            @RequestBody User user,
            @RequestHeader HttpHeaders headers) {

        HttpStatus code = HttpStatus.BAD_REQUEST;
        User res = new User();

        try {
            if (user.getUserName() == null || user.getEmail() == null) {

                // Bad Request.
            } else {

                Auth auth = new Auth();

                if (auth.isTheSameUser(user.getUserName(), headers, users,
                        standardPasswordEncoder)) {

                    users.changeEmail(user.getUserName(), user.getEmail());
                    res = user;
                    code = HttpStatus.OK;
                } else {

                    code = HttpStatus.UNAUTHORIZED;
                }
            }
        } catch (Exception e) {
            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {

            Auth.saveTypeAccess(accessDao, users, headers, "PUT", code.value(),
                    "/user/email/" + user.getUserName());
            return new ResponseEntity<User>(res, code);
        }
    }

    /**
     * Only a admin user can use this end-point
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/user/privileges", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> modifyUserPrivileges(
            @RequestBody User user,
            @RequestHeader HttpHeaders headers) {

        HttpStatus code = HttpStatus.BAD_REQUEST;
        User res = new User();
        try {
            if (user.getUserName() == null || user.getPrivileges() == null) {
                // Bad Request.

            } else {
                users.changePrivileges(user.getUserName(), user.getPrivileges());
                User aux = users.findUser(user.getUserName());
                aux.setPassword(null);
                res = aux;
                code = HttpStatus.OK;
            }
        } catch (Exception e) {
            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {
            Auth.saveTypeAccess(accessDao, users, headers, "PUT", code.value(),
                    "/user/privileges/" + user.getUserName());
            return new ResponseEntity<User>(res, code);
        }
    }

    /**
     * Only a admin user or the same user can use this end-point
     *
     * @return
     */
    @RequestMapping(value = "/user/{identifier}", method = RequestMethod.GET)
    public ResponseEntity<User> searchOneUser(
            @PathVariable(value = "identifier") String id,
            @RequestHeader HttpHeaders headers) {

        HttpStatus code = HttpStatus.BAD_REQUEST;
        User res = new User();
        try {
            Auth auth = new Auth();
            if (auth.isTheSameUser(id, headers, users, standardPasswordEncoder)) {
                User aux = users.findUser(id);
                if (aux != null) {
                    aux.setPassword(null);
                    res = aux;
                    code = HttpStatus.OK;
                } else {
                    code = HttpStatus.NOT_FOUND;
                }

            } else {
                code = HttpStatus.UNAUTHORIZED;
            }
        } catch (Exception e) {

            code = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {

            Auth.saveTypeAccess(accessDao, users, headers, "PUT", code.value(),
                    "/user/" + id);
            return new ResponseEntity<User>(res, code);
        }
    }

    /**
     * Only a admin user can use this end-point
     *
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<List<User>> searchUsers(
            @RequestHeader HttpHeaders headers) {

        List<User> list = new ArrayList<>();
        HttpStatus code = HttpStatus.INTERNAL_SERVER_ERROR;
        try {
            List<User> userss = users.findAllUsers();
            for (User aux : userss) {
                aux.setPassword(null);
            }
            list = userss;
            code = HttpStatus.OK;
        } catch (Exception e) {
            // Internal error.

        } finally {
            Auth.saveTypeAccess(accessDao, users, headers, "GET", code.value(),
                    "/user");
            return new ResponseEntity<List<User>>(list, code);
        }
    }

    /**
     * Only a admin user can use this end-point
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/admin", method = RequestMethod.PUT, consumes =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> modifyAdminPassword(
            @RequestBody User user,
            @RequestHeader HttpHeaders headers) {

        HttpStatus code = HttpStatus.BAD_REQUEST;
        User res = new User();
        try {
            if (user.getUserName() == null || user.getPassword() == null || user
                    .getUserName().compareTo("") == 0 || user.getPassword()
                    .compareTo("") == 0) {

                // Bad request.

            } else {
                boolean admin = false;
                for (String priv : Auth.getPrivilegesList(user.getPrivileges())) {
                    if (priv.compareToIgnoreCase("ROLE_ADMIN") == 0) {
                        admin = true;
                    }
                }
                if (admin) {
                    users.changePassword(user.getUserName(), user.getPassword());

                    User aux = users.findUser(user.getUserName());
                    if (aux != null) {
                        aux.setPassword(null);
                        res = aux;
                        code = HttpStatus.OK;
                    } else {

                        // Bad request.
                    }
                } else {

                    // Bad request.
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            code = HttpStatus.INTERNAL_SERVER_ERROR;

        } finally {
            Auth.saveTypeAccess(accessDao, users, headers, "PUT", code.value(),
                    "/admin");
            return new ResponseEntity<User>(res, code);
        }
    }
}



