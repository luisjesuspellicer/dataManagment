/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class have usefuls functions to avoid repeating code.
 */
package tfg.backend.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Component;
import tfg.backend.DataAccessLopdModel.AccessDao;
import tfg.backend.DataAccessModel.UserDao;
import tfg.backend.DataModel.User;
import tfg.backend.LopdModel.Access;

import javax.persistence.Entity;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Auth {

    /**
     * Get user's information from headers and search in DB.
     * @param headers
     * @param userDao
     */
    public static User getUserFromHeaders(HttpHeaders headers, UserDao userDao){
        List<String> aux = headers.get("Authorization");
        String[] aux2 = aux.get(0).split(" ");
        String aux3 = new String(Base64.decode(aux2[1].getBytes()));
        String[] aux4 = aux3.split(":");

        User user = userDao.findUser(aux4[0]);
        return user;
    }
    /**
     * Get user's information from headers and search in DB.
     * @param headers
     * @param userDao
     */
    public static User getUserFromHeaders(String headers, UserDao userDao){

        String[] aux2 = headers.split(" ");
        String aux3 = new String(Base64.decode(aux2[1].getBytes()));
        String[] aux4 = aux3.split(":");

        User user = userDao.findUser(aux4[0]);
        return user;
    }

    /**
     * Checks if the user has access for get the specific study.
     *
     * @param id
     * @param headers
     * @param userDao
     * @return
     */
    public static boolean isAuthenticated(String id, HttpHeaders headers, UserDao userDao) {

        try {
            User user = getUserFromHeaders(headers, userDao);
            if (user != null) {
                boolean auth = false;

                for (String privilege : Auth.getPrivilegesList(user.getPrivileges())) {

                    if (privilege.compareToIgnoreCase("ROLE_" + id) == 0
                            || privilege.compareToIgnoreCase("ROLE_ADMIN") == 0) {

                        auth = true;
                    }
                }
                return auth;
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if the user has permisions for get the specific study.
     *
     * @param id
     * @param headers
     * @param userDao
     * @return
     */
    public static boolean isAuthenticated2(String id, String headers, UserDao userDao) {

        try {
            String aux3 = new String(Base64.decode(headers.getBytes()));
            String[] aux4 = aux3.split(":");

            User user = userDao.findUser(aux4[0]);
            if (user != null) {
                boolean auth = false;

                for (String privilege : Auth.getPrivilegesList(user.getPrivileges())) {

                    if (privilege.compareToIgnoreCase("ROLE_" + id) == 0
                            || privilege.compareToIgnoreCase("ROLE_ADMIN") == 0) {

                        auth = true;
                    }
                }
                return auth;
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if the user has access for get the specific user.
     *
     * @param headers
     * @param userDao
     * @return
     */
    public boolean isTheSameUser(String username, HttpHeaders headers, UserDao userDao, StandardPasswordEncoder standardPasswordEncoder) {
        try {
            User user = getUserFromHeaders(headers, userDao);

            //encode password.
            User user2 = userDao.findUser(username);

            if (user != null) {
                if (user.equals(user2)) {
                    return true;
                } else {

                    boolean auth = false;
                    for (String privilege : Auth.getPrivilegesList(user.getPrivileges())) {

                        if (privilege.compareToIgnoreCase("ROLE_ADMIN") == 0) {
                            // Checks role admin password.


                            return true;
                        }

                    }
                    return auth;
                }
            } else {
                return false;

            }
        } catch (Exception e) {
            return false;
        }
    }

    public static List<String> getPrivilegesList(String privileges) {
        return Arrays.asList(privileges.split(";"));
    }

    public boolean isSuccessLogin(HttpHeaders headers, UserDao userDao, StandardPasswordEncoder standardPasswordEncoder) {

        try {
            List<String> aux = headers.get("Authorization");
            String[] aux2 = aux.get(0).split(" ");
            String aux3 = new String(Base64.decode(aux2[1].getBytes()));
            String[] aux4 = aux3.split(":");

            User user = userDao.findUser(aux4[0]);

            String password = aux4[1];


            if (user != null) {
                if (standardPasswordEncoder.matches(password, user.getPassword())) {
                    return true;
                } else {
                    return false;
                }
            } else {


                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Save in Database one access. Access's information includes
     * type of access, the user, and the result of this access.
     * @param accessDao
     * @param userDao
     * @param headers
     * @param typeAccess
     * @return
     */
    public static boolean saveTypeAccess(AccessDao accessDao, UserDao userDao,
                                  String headers, String typeAccess,
                                  int result, String access) {
        try {
            String aux3 = new String(Base64.decode(headers.getBytes()));
            String[] aux4 = aux3.split(":");

            User user = userDao.findUser(aux4[0]);
            Access newAccess = new Access();
            newAccess.setUser(user);
            newAccess.setTypeAccess(typeAccess);
            newAccess.setResult(result);
            newAccess.setAccess(access);
            accessDao.saveAcess(newAccess);
            return true;
        }catch(Exception e){
            return false;
        }

    }

    /**
     * Save in Database one access. Access's information includes
     * type of access, the user, and the result of this access.
     * @param accessDao
     * @param userDao
     * @param headers
     * @param typeAccess
     * @return
     */
    public static boolean saveTypeAccess(AccessDao accessDao, UserDao userDao,
                                         HttpHeaders headers, String typeAccess,
                                         int result, String access) {
        try {
            Date date = new Date();
            User user = getUserFromHeaders(headers, userDao);
            Access newAccess = new Access();
            newAccess.setDate(date);
            newAccess.setUser(user);
            newAccess.setTypeAccess(typeAccess);
            newAccess.setResult(result);
            newAccess.setAccess(access);
            accessDao.saveAcess(newAccess);
            return true;
        }catch(Exception e){
            return false;
        }

    }
}
