/**
 * Author: Eugen Paraschiv
 * Modified by Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class define the service for manage ip's with correct credentials.
 */
package tfg.backend.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public class SuccessAuthentication
        implements ApplicationListener <AuthenticationSuccessEvent>{

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        WebAuthenticationDetails details =
                (WebAuthenticationDetails)event.getAuthentication().getDetails();

        authenticationService.authenticationSuccessful(details.getRemoteAddress());
    }
}
