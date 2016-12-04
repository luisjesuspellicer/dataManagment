/**
 * Author: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class contains the functionality receive one
 * email as String and pass this email to the worker.
 */
package tfg.backend.EmailQueue;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.messaging.handler.annotation.Payload;



@RabbitListener(queues = "emailQueue")
public class ListenerEmail {

    @Autowired
    WorkerEmail worker;

    @Autowired
    @Qualifier("taskExecutorEmail")
    TaskExecutor executor;

    @RabbitHandler public void process(@Payload String email) {

        worker.setEmail(email);
        executor.execute(worker);
    }
}
