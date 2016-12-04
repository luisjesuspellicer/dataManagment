/**
 * Creator: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: Implementation of methods for interact with model Milestone
 */
package tfg.backend.DataAccessImplementation;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import tfg.backend.DataAccessModel.MilestoneDao;
import tfg.backend.DataModel.Ids.MilestoneID;
import tfg.backend.DataModel.Milestone;


import java.util.List;


public class MilestoneDaoImplementation implements MilestoneDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public void saveMilestone(Milestone milestone) {
        try {
            milestone.getId().setId_milestone(count());
            hibernateTemplate.save(milestone);
            hibernateTemplate.flush();
        }catch(Exception e){
            saveMilestone(milestone, count() +1);
        }
    }
    private void saveMilestone(Milestone milestone, Long id) {
        try{
            milestone.getId().setId_milestone(id);
            hibernateTemplate.save(milestone);
            hibernateTemplate.flush();
        }catch(Exception e){
            saveMilestone(milestone, id +1);
        }
    }
    @Override
    public List<Milestone> findAllMilestone() {
        return null;
    }

    @Override
    public List<Milestone> findMilestones(Long id) {
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        DetachedCriteria query = DetachedCriteria.forClass(Milestone.class)
                .add(Property.forName("id.id.id").eq(id));
        List<?> list = hibernateTemplate.findByCriteria(query);
        if(list.size() > 0 ){
            return (List<Milestone>)list;
        }
        return null;
    }


    @Override
    public void deleteMilestone(MilestoneID id ) {
        Milestone aux = findMilestone(id);
        if(aux != null){
            hibernateTemplate.delete(aux);
            hibernateTemplate.flush();
        }

    }

    @Override
    public Long count() {
        DetachedCriteria query =DetachedCriteria.forClass(Milestone.class).
                setProjection(Projections.count("id.id_milestone"));
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        Long a = (Long)hibernateTemplate.findByCriteria(query).get(0);
        if(a == null){
            return new Long("0");
        }else{
            return a;
        }

    }

    @Override
    public Milestone findMilestone(MilestoneID id) {
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        DetachedCriteria query = DetachedCriteria.forClass(Milestone.class)
                .add(Property.forName("id.id_milestone").eq(id.getId_milestone()))
                .add(Property.forName("id.id.id").eq(id.getId().getId()));
        List<?> list = hibernateTemplate.findByCriteria(query);
        if(list.size() >0 ){
            return (Milestone)list.get(0);
        }
        return null;
    }
    @Override
    public List<Milestone> findByBaseLine(Long id) {
        hibernateTemplate.flush();
        hibernateTemplate.clear();
        DetachedCriteria query = DetachedCriteria.forClass(Milestone.class)
                .add(Property.forName("id.id.id").eq(id));
        List<?> list = hibernateTemplate.findByCriteria(query);

            return (List<Milestone>)list;

    }


}


