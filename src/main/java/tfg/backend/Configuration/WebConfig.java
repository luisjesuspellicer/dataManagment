/**
 * Author: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class contains the main configuration
 * for Server's authorizations. Define what request are protected and
 * manages the users's sessions.
 */
package tfg.backend.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import tfg.backend.DataAccessModel.UserDao;

import javax.servlet.http.HttpServletRequest;

@EnableWebSecurity
public class WebConfig extends WebSecurityConfigurerAdapter {

    @Value("${tokenPass}")
    private String tokenPass;

    @Autowired
    public UserDao userDao;

    @Autowired
    public AuthenticationService authenticationService;

    @Autowired
    private HttpServletRequest context;

    @Autowired
    StandardPasswordEncoder standardPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and()

                // It's redundant but all end-points are here.
                .authorizeRequests()
                .antMatchers("/log").authenticated()
                // End-points for Resource User
                .antMatchers("/numUsers").permitAll()
                .antMatchers(HttpMethod.GET, "/user/{\\d+}").authenticated()
                .antMatchers(HttpMethod.PUT, "/user/password").authenticated()
                .antMatchers(HttpMethod.PUT, "/user/email").authenticated()

                .antMatchers(HttpMethod.POST, "/user").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.PUT, "/user/privileges").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/user/{\\d+}").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/user").hasRole("ADMIN")

                .antMatchers(HttpMethod.POST, "/admin").permitAll()
                .antMatchers(HttpMethod.PUT, "/admin").hasRole("ADMIN")

                //End-points for Resource Study
                .antMatchers(HttpMethod.GET, "/study/subjects/{\\d+}").authenticated()
                .antMatchers(HttpMethod.GET, "/study/{\\d+}").authenticated()
                .antMatchers(HttpMethod.GET, "/study").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/study").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/study").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/study").hasAnyAuthority("ROLE_ADMIN")

                // End-points for Resource Cognitive Test
                .antMatchers(HttpMethod.GET, "/cognitiveTest/{\\d+}").authenticated()
                .antMatchers(HttpMethod.GET, "/cognitiveTest").authenticated()
                .antMatchers(HttpMethod.POST, "/cognitiveTest")
                    .hasAnyAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.DELETE, "/cognitiveTest/{\\d+}")
                    .hasAnyAuthority("ROLE_ADMIN")

                // End-points for Resource Cognitive Test Resolutions
                .antMatchers(HttpMethod.GET,
                        "/cognitiveTestResolution/{\\d+}").authenticated()
                .antMatchers(HttpMethod.POST,
                        "/cognitiveTestResolution/{\\d+}").authenticated()
                .antMatchers(HttpMethod.GET,
                        "/cognitiveTestResolution").hasRole("ADMIN")


                // End-points for Resource Activity
                .antMatchers(HttpMethod.GET,
                        "/activity/{\\d+}").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.GET,
                        "/activity").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.POST,
                        "/activity").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.DELETE,
                        "/activity/{\\d+}").hasAnyAuthority("ROLE_ADMIN")


                // End-points for Resource Subject
                .antMatchers(HttpMethod.POST, "/subject").authenticated()                           //
                .antMatchers(HttpMethod.GET, "/subject/{\\d+}").authenticated()                    //
                .antMatchers(HttpMethod.GET,
                        "/subject").hasAnyAuthority("ROLE_ADMIN")            //
                .antMatchers(HttpMethod.GET,
                        "/subject/cognitiveTest/{\\d+}").authenticated()      //
                .antMatchers(HttpMethod.GET,
                        "/subject/baseLine/{\\d+}").authenticated()          //
                .antMatchers(HttpMethod.POST,
                        "/subject/fileInfo/{\\d+}").authenticated()        //
                .antMatchers(HttpMethod.GET,
                        "/subject/fileInfo/{\\d+}").authenticated()        //
                .antMatchers(HttpMethod.GET, "/fileInfo").authenticated()                      //
                .antMatchers(HttpMethod.DELETE, "/fileInfo").authenticated()


                // End-points for Resource FileInfo
                .antMatchers(HttpMethod.GET, "/fileInfo}").authenticated()

                // End-points for Resource BaseLine
                .antMatchers(HttpMethod.POST, "/baseLine").authenticated()
                .antMatchers(HttpMethod.GET, "/baseLine/{\\d+}").authenticated()

                // End-points for Resource Activity.
                .antMatchers("/activity").authenticated()

                //End-points for Resource Support.
                .antMatchers("/support").hasRole("ADMIN")
                .antMatchers("/support/typeInformation/{\\d+}").hasRole("ADMIN")
                .antMatchers("/support/name/{\\d+}").hasRole("ADMIN")
                .antMatchers("/support/{\\d+}").hasRole("ADMIN")

                //End-points for Resource SupportES
                .antMatchers("/supportES/receiver/{\\d+}").hasRole("ADMIN")
                .antMatchers("/supportES/emisor/{\\d+}").hasRole("ADMIN")
                .antMatchers("/supportES").hasRole("ADMIN")
                .antMatchers("/supportES/id/{\\d+}").hasRole("ADMIN")
                .antMatchers("/supportES/{\\d+}").hasRole("ADMIN")
                // End-points for Resource Incidence
                .antMatchers("/incidence").hasRole("ADMIN")
                .antMatchers("/incidence/{\\d+}").hasRole("ADMIN")

                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .rememberMe().tokenValiditySeconds(1800000).key(tokenPass)
                .and()
                .logout().logoutSuccessUrl("/")
                .deleteCookies().clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .and().csrf().disable();
        //.antMatchers("/index.html", "/home.html", "/").permitAll()

        //.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class).csrf().csrfTokenRepository(csrfTokenRepository());
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/app.js")
                .antMatchers("/AEPD/**")
                .antMatchers("/estudio/**")
                .antMatchers("/components/**")
                .antMatchers("/registro/**")
                .antMatchers("/login/**")
                .antMatchers("/auth/**")
                .antMatchers("/bower_components/**")
                .antMatchers("/styles/**")
                .antMatchers("/favicon.ico")
                .antMatchers("/favicon2.png")
                .antMatchers("/naviCtrl.js")
                .antMatchers("/cuentas/**")
                .antMatchers("/usuario/**")
                .antMatchers("/control/**")
                .antMatchers("/controlUsuario/**")
                .antMatchers("/controlEstudio/**")
                .antMatchers("/testCognitivo/**")
                .antMatchers("/lineaBase/**")
                .antMatchers("/actividad/**")
                .antMatchers("/testCognitivoResultado/**")
                .antMatchers("/controlSujeto/**")
                .antMatchers("/soportes/**")
                .antMatchers("/transporteSoportes/**")
                .antMatchers("/incidencias/**")
                .antMatchers("/images/**");


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserService(userDao, context,
                authenticationService)).passwordEncoder(standardPasswordEncoder);
    }
}
