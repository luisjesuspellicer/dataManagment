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
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import tfg.backend.DataAccessModel.SerieDao;
import tfg.backend.DataModel.CognitiveTest;
import tfg.backend.DataModel.Serie;

import java.util.List;

public class SerieDaoImplementation implements SerieDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public void saveSerie(Serie serie) {
        hibernateTemplate.save(serie);
    }

    @Override
    public List<Serie> findAllSerie() {
        return hibernateTemplate.loadAll(Serie.class);
    }

    @Override
    public Serie findSerie(Long id) {
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        DetachedCriteria query = DetachedCriteria.forClass(Serie.class)
                .add(Property.forName("id").eq(id));
        List<?> list = hibernateTemplate.findByCriteria(query);
        if(list.size() == 0){
            return null;
        }else{
            return (Serie) list.get(0);
        }
    }

    @Override
    public void deleteSerie(Long id) {

        Serie aux = findSerie(id);
        if(aux != null){
            hibernateTemplate.delete(findSerie(id));
        }

    }

    @Override
    public void deleteAllSeries() {
        List <Serie> aux = findAllSerie();
        if(aux != null && aux.size() > 0){
            hibernateTemplate.deleteAll(aux);
        }

    }

    public List<Serie> searchByTest(Long id){
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        DetachedCriteria query = DetachedCriteria.forClass(Serie.class)
                .add(Property.forName("test_id.test_id").eq(id));
        List<?> list = hibernateTemplate.findByCriteria(query);
        if(list.size() == 0){
            return null;
        }else{
            return (List<Serie>) list;
        }
    }
}
