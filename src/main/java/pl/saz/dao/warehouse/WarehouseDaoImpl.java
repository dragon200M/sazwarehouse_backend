package pl.saz.dao.warehouse;

import org.springframework.stereotype.Repository;
import pl.saz.model.warehouse.WarehouseModel;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by maciej on 01.05.18.
 */
@Repository
@Transactional
public class WarehouseDaoImpl implements WarehouseDao{

    @PersistenceContext
    private EntityManager manager;

    @Override
    public WarehouseModel getByName(String name) {
        WarehouseModel w = null;
        try{
            w =  manager.createQuery("SELECT w FROM WarehouseModel w where w._name = :name",WarehouseModel.class)
                    .setParameter("name",name)
                    .getSingleResult();
        }catch (NoResultException e) {
            System.out.println("no resoult");
        }

        return w;
    }



    @Override
    public boolean saveWarehouse(WarehouseModel warehouse) {
        WarehouseModel tmp = getByName(warehouse.get_name());
        if(null == tmp) {
            manager.persist(warehouse);
            return true;
        }
        return false;
    }

    @Override
    public WarehouseModel updateWarehouse(WarehouseModel warehouse) {


        return manager.merge(warehouse);
    }

    @Override
    public void deleteWarehouse(WarehouseModel warehouse) {
        manager.remove(warehouse);
    }

    @Override
    public List<WarehouseModel> getAll() {
        List<WarehouseModel> w = null;
        try {
            w =  manager.createQuery("SELECT w FROM WarehouseModel w",WarehouseModel.class).getResultList();
        }catch (NoResultException e) {
            System.out.println("no resoult");
        }

        return w;
    }
}
