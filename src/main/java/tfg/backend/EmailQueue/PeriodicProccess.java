package tfg.backend.EmailQueue;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import tfg.backend.DataAccessModel.UserDao;
import tfg.backend.DataModel.User;

import java.util.Date;
import java.util.List;


public class PeriodicProccess {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private UserDao userDao;

    // Check each day.
    @Scheduled(fixedDelay = 86400000L)
    //@Scheduled(fixedDelay = 10000L)
    public void send() {

        List<User> users = userDao.findAllUsers();
        for(User user: users){

            Date today = new Date();
            if(user.getExpiredPassword().before(today)){
                // Send email.
                this.rabbitTemplate.convertAndSend("emailQueue",user.getEmail());
            }
        }
    }
}
