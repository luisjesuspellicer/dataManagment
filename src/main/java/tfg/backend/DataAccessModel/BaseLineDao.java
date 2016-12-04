/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: Methods for interact with model BaseLine
 */
package tfg.backend.DataAccessModel;

import tfg.backend.DataModel.BaseLine;
import tfg.backend.DataModel.Subject;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by guytili on 06/07/2016.
 */
@Transactional
public interface BaseLineDao {

    public Long saveBaseLine(BaseLine baseLine);

    public List<BaseLine> findAllBaseLine();

    public BaseLine findBaseLine(Long id);

    public void deleteBaseLine(Long id);

    public List<BaseLine> searchBySubject(Long subject);
}
