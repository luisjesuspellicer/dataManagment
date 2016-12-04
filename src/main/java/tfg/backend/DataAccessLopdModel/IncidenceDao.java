/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: Methods for interact with model Incidence.
 */
package tfg.backend.DataAccessLopdModel;

import tfg.backend.DataModel.User;
import tfg.backend.LopdModel.Incidence;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional
public interface IncidenceDao {

    public Long saveIncidence(Incidence incidence);

    public List<Incidence> findAllIncidences();

    public Incidence findIncidence(Long numIncidence);

    public void deleteIncidence(Long numIncidence);

    public List<Incidence> searchByUser(User user);

    public List<Incidence> searchByTypeIncidence(String typeIncidence);

    public List<Incidence> searchByDate(Date date);
}
