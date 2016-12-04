/**
 * Author: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class contains the main configuration
 * for Spring. It defines the general beans and the database's options.
 */
package tfg.backend.Configuration;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;


import org.springframework.amqp.core.Queue;;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import tfg.backend.DataAccessImplementation.*;
import tfg.backend.DataAccessLopdDaoImplementation.AccessDaoImplementation;
import tfg.backend.DataAccessLopdDaoImplementation.IncidenceDaoImplementation;
import tfg.backend.DataAccessLopdDaoImplementation.SupportDaoImplementation;
import tfg.backend.DataAccessLopdDaoImplementation.SupportESDaoImplementation;
import tfg.backend.DataAccessLopdModel.AccessDao;
import tfg.backend.DataAccessLopdModel.IncidenceDao;
import tfg.backend.DataAccessLopdModel.SupportDao;
import tfg.backend.DataAccessLopdModel.SupportESDao;
import tfg.backend.DataAccessModel.*;
import tfg.backend.DataModel.*;
import tfg.backend.DataModel.Ids.CognitiveTestResolutionID;
import tfg.backend.DataModel.Ids.DataID;
import tfg.backend.DataModel.Ids.MilestoneID;
import tfg.backend.EmailQueue.Email;
import tfg.backend.EmailQueue.ListenerEmail;
import tfg.backend.EmailQueue.PeriodicProccess;
import tfg.backend.LopdModel.Access;
import tfg.backend.LopdModel.Incidence;
import tfg.backend.LopdModel.Support;
import tfg.backend.LopdModel.SupportES;

import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;


@Configuration
@EnableScheduling
@EnableTransactionManagement
public class AppConfig {

    @Value("${databaseServer}")
    private String databaseServer;

    @Value("${databaseName}")
    private String dataBaseName;

    @Value("${passDB}")
    private String passDB;

    @Value("${userNameDB}")
    private String usernameDB;

    @Bean public PhysiologicalDataDao physiologicalDataDao() {

        return new PhysiologicalDataDaoImplementation();
    }

    @Bean public PsychologicalDataDao psichologicalDataDao() {

        return new PsychologicalDataDaoImplementation();
    }

    @Bean public OtherHabitsDao otherHabitsDao() {

        return new OtherHabitsDaoImplementation();
    }

    @Bean public ToxicHabitsDao toxicHabitsDao() {

        return new ToxicHabitsDaoImplementation();
    }

    @Bean public ActivityDao activityDao() {

        return new ActivityDaoImplementation();
    }

    @Bean public BaseLineDao baseLineDao() {

        return new BaseLineDaoImplementation();
    }


    @Bean public CognitiveTestResolutionDao cognitiveTestResolutionDao() {

        return new CognitiveTestResolutionDaoImplementation();
    }

    @Bean public CognitiveTestDao cognitiveTestDao() {

        return new CognitiveTestDaoImplementation();
    }

    @Bean public MilestoneDao milestoneDao() {

        return new MilestoneDaoImplementation();
    }

    @Bean public StudyDao studyDao() {

        return new StudyDaoImplementation();
    }

    @Bean public SubjectDao subjectDao() {

        return new SubjectDaoImplementation();
    }


    @Bean public UserDao userDao() {

        return new UserDaoImplementation();
    }

    @Bean SerieDao serieDao() {

        return new SerieDaoImplementation();
    }

    @Bean SerieResolutionDao serieResolutionDao() {

        return new SerieResolutionDaoImplementation();
    }

    @Bean FileDao fileInfoDao() {

        return new FileDaoImplementation();
    }

    @Bean AccessDao accessDao() {

        return new AccessDaoImplementation();
    }

    @Bean IncidenceDao incidenceDao() {

        return new IncidenceDaoImplementation();
    }

    @Bean SupportDao supportDao() {

        return new SupportDaoImplementation();
    }

    @Bean SupportESDao supportESDao() {

        return new SupportESDaoImplementation();
    }


    @Bean public HibernateTemplate hibernateTemplate() {

        return new HibernateTemplate(sessionFactory());
    }
    @Bean public Email email(){
        return new Email();
    }


    @Bean public SessionFactory sessionFactory() {

        return new LocalSessionFactoryBuilder(getDataSource()).addAnnotatedClasses
                (PhysiologicalData.class, PsychologicalData.class, ToxicHabits
                        .class, OtherHabits.class, Subject.class, Study.class,
                        DataID.class, CognitiveTest.class,
                        CognitiveTestResolutionID.class, BaseLine.class,
                        CognitiveTestResolution.class, Activity.class, Milestone
                                .class, MilestoneID.class, User.class, Serie
                                .class, SerieResolution.class, FileInfo.class,
                        Access.class, Incidence.class, Support.class, SupportES
                                .class).addProperties(hibernateProperties())
                .buildSessionFactory();
    }

    @Bean public DataSource getDataSource() {

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://" + databaseServer + "/" + dataBaseName);
        dataSource.setUsername(usernameDB);
        dataSource.setPassword(passDB);


        return dataSource;
    }

    @Bean public HttpSessionEventPublisher httpSessionEventPublisher() {

        return new HttpSessionEventPublisher();
    }

    Properties hibernateProperties() {

        return new Properties() {

            {
                setProperty("hibernate.hbm2ddl.auto", "update");
                setProperty("hibernate.connection.autocommit", "true");
                setProperty("hibernate.c3p0.max_statements", "50");
                setProperty("hibernate.c3p0.timeout", "1500");
                setProperty("hibernate.c3p0.max_size", "20");
                setProperty("hibernate.c3p0.min_size", "5");
            }
        };
    }


    @Bean public HibernateTransactionManager hibTransMan() {

        return new HibernateTransactionManager(sessionFactory());
    }

    @Bean public SimpleUrlHandlerMapping faviconHandlerMapping() {

        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setOrder(Integer.MIN_VALUE);
        mapping.setUrlMap(Collections.singletonMap("resources/favicon.ico",
                faviconRequestHandler()));
        return mapping;
    }

    @Bean protected ResourceHttpRequestHandler faviconRequestHandler() {

        ResourceHttpRequestHandler requestHandler = new ResourceHttpRequestHandler();
        requestHandler.setLocations(Arrays.<Resource>asList(new ClassPathResource
                ("/")));
        return requestHandler;
    }

    @Bean public StandardPasswordEncoder standardPasswordEncoder() {

        return new StandardPasswordEncoder();
    }

    @Bean public FailAuthentication failAuthentication() {

        return new FailAuthentication();
    }

    @Bean public SuccessAuthentication successAuthentication() {

        return new SuccessAuthentication();
    }

    @Bean public AuthenticationService authenticationService() {

        return new AuthenticationService();
    }

    @Bean
    public Queue emailQueue (){
        return new Queue("emailQueue");
    }

    @Bean ListenerEmail listenerEmail(){
        return new ListenerEmail();
    }

    @Bean
    PeriodicProccess periodicProccess(){
        return new PeriodicProccess();
    }
    @Bean
    public TaskExecutor taskExecutorEmail() {

        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

        taskExecutor.setMaxPoolSize(1);
        taskExecutor.setCorePoolSize(1);
        taskExecutor.afterPropertiesSet();

        return taskExecutor;

    }
}