/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: Methods for interact with model SupportES.
 */

package tfg.backend.DataAccessLopdModel;

import tfg.backend.LopdModel.SupportES;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional
public interface SupportESDao {

    public Long saveSupportES(SupportES supportES);

    public List<SupportES> findAllSupportES();

    public SupportES findSupportES(Long numSupport);

    public void deleteSupportES(Long numSupportES);

    public List<SupportES> searchByEmisor(String emisor);

    public List<SupportES> searchByReceiver(String receiver);

    public List<SupportES> searchByDate(Date date);

}
