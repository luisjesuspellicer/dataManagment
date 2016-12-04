package tfg.backend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tfg.backend.DataAccessLopdModel.AccessDao;
import tfg.backend.DataAccessModel.UserDao;
import tfg.backend.DataModel.User;
import tfg.backend.RequestsStructures.AllPersonData;
import tfg.backend.DataModel.ToxicHabits;
import tfg.backend.Utils.Auth;

import java.util.List;

/**
 * Created by guytili on 04/07/2016.
 */
@RestController
public class Login {


    @Autowired
    UserDao users;

    @Autowired
    public StandardPasswordEncoder standardPasswordEncoder;
    @Autowired
    AccessDao accessDao;

    @Autowired
    UserDao userDao;

    @RequestMapping(value = "/log", method = RequestMethod.POST, consumes =
            MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<User> login(@RequestHeader HttpHeaders headers, @RequestBody
            User user) {

        User us = new User();
        HttpStatus code = HttpStatus.BAD_REQUEST;
        try {

            if (user.getUserName() == null) {

                // Bad Request.
            } else {

                Auth auth = new Auth();
                if (auth.isSuccessLogin(headers, users, standardPasswordEncoder)) {
                    User user2 = users.findUser(user.getUserName());
                    if (user2 != null) {

                        user2.setPassword(null);
                        us = user2;
                        code = HttpStatus.OK;
                    } else {
                        // Not Found.
                        code = HttpStatus.NOT_FOUND;
                    }
                } else {
                    return new ResponseEntity<User>(new User(), HttpStatus
                            .UNAUTHORIZED);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();

            code = HttpStatus.INTERNAL_SERVER_ERROR;
            Auth.saveTypeAccess(accessDao, userDao, headers, "POST", code.value(), "/log");

        } finally {
            return new ResponseEntity<User>(us, code);
        }
    }

}

