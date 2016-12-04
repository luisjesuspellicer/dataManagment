/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: Implementation of methods for interact with model BaseLine
 */
package tfg.backend.DataAccessImplementation;

import com.fasterxml.jackson.databind.deser.Deserializers;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import tfg.backend.DataAccessModel.BaseLineDao;
import tfg.backend.DataModel.BaseLine;
import tfg.backend.DataModel.Subject;

import java.util.List;

public class BaseLineDaoImplementation implements BaseLineDao{

    @Autowired
    private HibernateTemplate hibernateTemplate;


    @Override
    public Long saveBaseLine(BaseLine baseLine) {

        return (Long)hibernateTemplate.save(baseLine);
    }

    @Override
    public List<BaseLine> findAllBaseLine() {
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        return hibernateTemplate.loadAll(BaseLine.class);
    }

    @Override
    public BaseLine findBaseLine(Long id) {
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        DetachedCriteria query = DetachedCriteria.forClass(BaseLine.class)
                .add(Property.forName("id").eq(id));
        List<?> list = hibernateTemplate.findByCriteria(query);
        if(list.size() >0 ){
            return (BaseLine)list.get(0);
        }
        return null;
    }

    @Override
    public void deleteBaseLine(Long id) {
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        BaseLine aux = findBaseLine(id);
        if(aux != null){
            hibernateTemplate.delete(aux);
            //hibernateTemplate.flush();
            //hibernateTemplate.clear();
        }
    }

    @Override
    public List<BaseLine> searchBySubject(Long subject) {
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        DetachedCriteria query = DetachedCriteria.forClass(BaseLine.class)
                .add(Property.forName("numSubjectOne.numSubject").eq(subject));
        DetachedCriteria query2 = DetachedCriteria.forClass(BaseLine.class)
                .add(Property.forName("numSubjectTwo.numSubject").eq(subject));

        List<?> list = hibernateTemplate.findByCriteria(query);
        List<?> list2 = hibernateTemplate.findByCriteria(query2);

        if (list.size() == 0 && list2.size() == 0) {
            return null;
        } else {

            List<BaseLine> list3 = (List<BaseLine>)list;
            List<BaseLine> list4 = (List<BaseLine>)list2;
            list3.addAll(list4);
            return list3 ;
        }

    }
}
