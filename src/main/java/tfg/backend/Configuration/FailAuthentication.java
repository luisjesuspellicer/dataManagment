/**
 * Author: Eugen Paraschiv
 * Modified by Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class define the service for manage ip's with bad credentials.
 */

package tfg.backend.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import tfg.backend.DataAccessLopdModel.AccessDao;
import tfg.backend.DataAccessModel.UserDao;
import tfg.backend.DataModel.User;
import tfg.backend.LopdModel.Access;

@Component
public class FailAuthentication
        implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AccessDao accessDao;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        WebAuthenticationDetails details =
                (WebAuthenticationDetails) event.getAuthentication().getDetails();
        authenticationService.authenticationFailed(details.getRemoteAddress());
        try {
            System.out.println(event.getSource());

            System.out.println();
            String userName = event.getAuthentication().getPrincipal().toString();
            User user = userDao.findUser(userName);
            Access newAccess = new Access();

            newAccess.setUser(user);
            newAccess.setTypeAccess("Authentication");
            newAccess.setResult(401);
            newAccess.setAccess("Authentication");
            accessDao.saveAcess(newAccess);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
