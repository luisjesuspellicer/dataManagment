/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class define the operations for Users.
 */
package tfg.backend.DataAccessModel;


import org.springframework.stereotype.Component;
import tfg.backend.DataModel.User;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface UserDao {

     void saveUser(User user);

     List<User> findAllUsers();

     User findUser(String userName);

     void deleteUser(String userName);

     void changePassword(String userName, String password);

     void changePrivileges(String username, String privileges);

     void changeEmail(String username, String email);


}
