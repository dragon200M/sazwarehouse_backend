package pl.saz.dao.stock;

import org.springframework.stereotype.Repository;
import pl.saz.model.komponent.KomponentModel;
import pl.saz.model.stock.StockModel;
import pl.saz.model.stock.StockSummary;
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
public class StockDaoImpl implements StockDao {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<StockModel> getAll() {
        List<StockModel> or = null;

        try {
            or = manager.createQuery("SELECT w FROM StockModel w", StockModel.class).getResultList();
        }catch (NoResultException e){
            System.out.println("Brak wynikow");
        }

        return or;

    }

    @Override
    public StockModel getById(WarehouseModel w, KomponentModel k) {
        StockModel or = null;
        String war = w.get_name();
        String kom = k.get_name();
        try{
            or = manager.createQuery("select w from StockModel" +
                    " w where w._id._wName = :wName and w._id._kName = :kName",StockModel.class)
                    .setParameter("wName",war)
                    .setParameter("kName",kom)
                    .getSingleResult();

        }catch (NoResultException e){
            System.out.println("No resoult.");
        }


        return or;
    }

    @Override
    public StockModel getById(String w, String k) {
        StockModel or = null;
        String war = w;
        String kom = k;
        try{
            or = manager.createQuery("select w from StockModel" +
                    " w where w._id._wName = :wName and w._id._kName = :kName",StockModel.class)
                    .setParameter("wName",war)
                    .setParameter("kName",kom)
                    .getSingleResult();

        }catch (NoResultException e){
            System.out.println("No resoult.");
        }


        return or;
    }

    @Override
    public List<StockModel> getByWarehouse(WarehouseModel warehouse) {
        List<StockModel> or = null;
        String war = warehouse.get_name();

        try{
            or = manager.createQuery("select w from StockModel" +
                    " w where w._id._wName = :wName",StockModel.class)
                    .setParameter("wName",war)
                    .getResultList();

        }catch (NoResultException e){
            System.out.println("No resoult.");
        }


        return or;
    }

    @Override
    public List<StockModel> getByKomponent(KomponentModel komponent) {
        List<StockModel> or = null;
        String war = komponent.get_name();

        try{
            or = manager.createQuery("select w from StockModel" +
                    " w where w._id._kName = :wName",StockModel.class)
                    .setParameter("wName",war)
                    .getResultList();

        }catch (NoResultException e){
            System.out.println("No resoult.");
        }


        return or;
    }

    @Override
    public boolean saveStock(StockModel stock) {
      StockModel tmp = getById(stock.getWarehouse(),stock.getComponent());
      if(null == tmp ) {
          manager.persist(stock);
          return true;
      }

     return false;
    }

    @Override
    public StockModel updateStock(WarehouseModel warehouse, KomponentModel komponent, Double stock) {
        StockModel tmp = getById(warehouse,komponent);
        if(null != tmp) {
            tmp.set_stock(stock);
            return manager.merge(tmp);
        }

        return null;
    }

    @Override
    public StockModel updateStock(String warehouse, String komponent, Double stock) {
        StockModel tmp = getById(warehouse,komponent);
        if(null != tmp) {
            tmp.set_stock(stock);
            return manager.merge(tmp);
        }

        return null;
    }

    @Override
    public void deleteStock(StockModel stock) {
      manager.remove(manager.contains(stock) ? stock : manager.merge(stock));
    }

}
