/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class implements the operations which interact with database for
 * series of cognitive tests.
 */
package tfg.backend.DataAccessImplementation;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import tfg.backend.DataAccessModel.SerieResolutionDao;
import tfg.backend.DataModel.Serie;
import tfg.backend.DataModel.SerieResolution;

import java.util.List;

public class SerieResolutionDaoImplementation implements SerieResolutionDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public void saveSerieResolution(SerieResolution serie) {

        hibernateTemplate.save(serie);
    }

    @Override
    public List<SerieResolution> findAllSerieResolution() {
        return hibernateTemplate.loadAll(SerieResolution.class);
    }

    @Override
    public SerieResolution findSerieResolution(Long id) {
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        DetachedCriteria query = DetachedCriteria.forClass(SerieResolution.class)
                .add(Property.forName("id").eq(id));
        List<?> list = hibernateTemplate.findByCriteria(query);
        if (list.size() == 0) {
            return null;
        } else {
            return (SerieResolution) list.get(0);
        }
    }

    @Override
    public void deleteSerieResolution(Long id) {
        SerieResolution serieResolution = findSerieResolution(id);
        if(serieResolution != null){
            hibernateTemplate.delete(findSerieResolution(id));
        }

    }

    @Override
    public void deleteAllSeriesResolution() {
        List<SerieResolution> aux = findAllSerieResolution();
        if (aux != null && aux.size() > 0) {
            hibernateTemplate.deleteAll(aux);
        }

    }
    @Override
    public SerieResolution searchByTestAndID(Long id, Long NumTest) {
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        DetachedCriteria query = DetachedCriteria.forClass(SerieResolution.class)
                .add(Property.forName("test_id_resolution.test_id_resolution.id").eq(NumTest))
                .add(Property.forName("test_id_resolution.id").eq(id));
        List<?> list = hibernateTemplate.findByCriteria(query);
        if (list.size() == 0) {
            return null;
        } else {
            return (SerieResolution) list.get(0);
        }
    }

    @Override
    public List<SerieResolution> searchByTest(Long numTest) {
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        DetachedCriteria query = DetachedCriteria.forClass(SerieResolution.class)
                .add(Property.forName("test_id_resolution.test_id_resolution.id").eq(numTest));
        List<?> list = hibernateTemplate.findByCriteria(query);
        if (list.size() == 0) {
            return null;
        } else {
            return (List<SerieResolution>) list;
        }
    }


}
