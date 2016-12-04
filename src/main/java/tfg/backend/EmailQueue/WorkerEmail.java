/**
 * Author: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: Send emails with information about the accounts.
 */
package tfg.backend.EmailQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by guytili on 01/11/2016.
 */
@Component
public class WorkerEmail implements Runnable {

    private String email;

    @Autowired
    Email emailTool;
    public void setEmail(String email) {

        this.email = email;

    }

    @Override public void run() {

         //emailTool.sendEmail();
    }

}
