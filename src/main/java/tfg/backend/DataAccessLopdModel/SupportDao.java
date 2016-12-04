/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: Methods for interact with model Support.
 */

package tfg.backend.DataAccessLopdModel;

import tfg.backend.LopdModel.Support;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface SupportDao {


    public Long saveSupport(Support support);

    public List<Support> findAllSupport();

    public Support findSupport(Long numSupport);

    public void deleteSupport(Long numSupport);

    public Support searchByName(String name);

    public List<Support> searchByTypeInformation(String typeInformation);

}
