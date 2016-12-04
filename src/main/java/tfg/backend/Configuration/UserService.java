/**
 * Author: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class contains the main configuration
 * for Server's authorizations. Define what request are protected and
 * manages the users's sessions.
 */
package tfg.backend.Configuration;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import tfg.backend.DataAccessModel.UserDao;
import tfg.backend.DataModel.User;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class UserService implements UserDetailsService {

    private final UserDao userDao;
    private final HttpServletRequest context;
    private final AuthenticationService authenticationService;

    public UserService(UserDao userDao, HttpServletRequest cont,
                       AuthenticationService auth) {

        this.userDao = userDao;
        this.context = cont;
        authenticationService = auth;
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails
    loadUserByUsername(String username) throws UsernameNotFoundException {

        String address = getClientAddress();

        if (authenticationService.isBlocked(address)) {

            throw new RuntimeException("blocked");
        }
        //Check if password expired 1 month ago

        User user = userDao.findUser(username);

        if (user != null) {

            if (user.getExpiredPassword().after(new Date(new Date().getTime() -
                    new Long("2629746000")))) {
                List<GrantedAuthority> authorities = new
                        ArrayList<GrantedAuthority>();
                authorities.add(new SimpleGrantedAuthority(user.getPrivileges()));
                return new org.springframework.security.core.userdetails.User(user
                        .getUserName(), user.getPassword(), authorities);

            } else {
                throw new RuntimeException("blocked");
            }

        } else {

            throw new UsernameNotFoundException("User '" + username + "' not found" +
                    ".");
        }
    }

    private String getClientAddress() {

        String header = context.getHeader("X-Forwarded-For");
        if (header == null) {
            System.out.println(context.getRemoteAddr());
            return context.getRemoteAddr();
        } else {
            System.out.println(header.split(",")[0]);
            return header.split(",")[0];

        }
    }

}
