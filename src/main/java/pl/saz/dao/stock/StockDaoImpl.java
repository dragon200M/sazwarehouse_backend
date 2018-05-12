package pl.saz.dao.stock;

import org.springframework.stereotype.Repository;
import pl.saz.model.komponent.KomponentModel;
import pl.saz.model.stock.StockListUpdate;
import pl.saz.model.stock.StockModel;
import pl.saz.model.warehouse.WarehouseModel;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public List<StockModel> getByWarehouse(String warehouse) {
        List<StockModel> or = null;
        String war = warehouse;

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
    public List<StockModel> getByKomponent(String komponent) {
        List<StockModel> or = null;
        String war = komponent;

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
      List<StockModel> k =  getByKomponent(stock.getComponent().get_name());

      if(null == tmp && k.size() == 0) {
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
    public List<StockListUpdate> updateStock(List<StockListUpdate> updates) {
        List<StockModel> updatedStock = new ArrayList<StockModel>();
          List<StockListUpdate> el = new ArrayList<StockListUpdate>();
          List<StockListUpdate> newList = new ArrayList<StockListUpdate>();

          Map<String,List<StockListUpdate>> byWare =
                 updates.stream().collect(Collectors.groupingBy(StockListUpdate::getWarehouseName));

         byWare.forEach( (a,b) -> {

             b.stream().collect(Collectors.groupingBy( k -> k.getKomponentName(),
                     Collectors.summingDouble(k-> k.getType() == StockOperation.REMOVE ? -k.getNewStock(): k.getNewStock())))
                     .forEach( (id, s) -> {
                         StockListUpdate newListElement = new StockListUpdate(a,id,s,StockOperation.ADD);
                         newList.add(newListElement);
                     });
         });

          for(StockListUpdate listUpdate: newList){
              StockModel t = getById(listUpdate.getWarehouseName(),listUpdate.getKomponentName());
              if(null != t){
                  Double check = t.get_stock()+listUpdate.getNewStock();
                  if(check >= 0) {
                      updatedStock.add(t);
                  }else {
                      listUpdate.setType(StockOperation.ERROR);
                      listUpdate.setNewStock(t.get_stock()+listUpdate.getNewStock());
                      el.add(listUpdate);
                  }
              }else{
                  listUpdate.setType(StockOperation.ERROR);
                  el.add(listUpdate);
              }
          }

          if(el.size() == 0) {
              for(StockListUpdate l2: newList) {
                  for(StockModel st : updatedStock){
                      if(l2.getWarehouseName().equals(st.getWarehouse().get_name()) && l2.getKomponentName().equals(st.getComponent().get_name())) {
                          st.set_stock(st.get_stock() + l2.getNewStock());
                          manager.merge(st);
                      }
                  }
              }
          }



        return el;
    }



    @Override
    public void deleteStock(StockModel stock) {
      manager.remove(manager.contains(stock) ? stock : manager.merge(stock));
    }

}



















