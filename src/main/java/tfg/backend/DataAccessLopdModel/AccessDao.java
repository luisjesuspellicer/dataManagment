/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: Methods for interact with model Access.
 */
package tfg.backend.DataAccessLopdModel;


import tfg.backend.DataModel.Activity;
import tfg.backend.DataModel.User;
import tfg.backend.LopdModel.Access;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface AccessDao {

    public void saveAcess(Access access);

    public List<Access> findAllAccess();

    public Access findAccess(Long numAccess);

    public void deleteAccess(Long numAccess);

    public List<Access> searchByUser(User user);

    public List<Access> searchByAccess(String access);
}
