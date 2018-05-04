package pl.saz.dao.stock;

import pl.saz.model.komponent.KomponentModel;
import pl.saz.model.stock.StockModel;
import pl.saz.model.warehouse.WarehouseModel;

import java.util.List;

/**
 * Created by maciej on 01.05.18.
 */
public interface StockDao {
    List<StockModel> getAll();
    StockModel getById(WarehouseModel w,KomponentModel k);
    StockModel getById(String w,String k);
    List<StockModel> getByWarehouse(WarehouseModel warehouse);
    List<StockModel> getByKomponent(KomponentModel komponent);
    List<StockModel> getByWarehouse(String warehouse);
    List<StockModel> getByKomponent(String komponent);
    boolean saveStock(StockModel stock);
    StockModel updateStock(WarehouseModel warehouse,KomponentModel komponent, Double stock);
    StockModel updateStock(String warehouse,String komponent, Double stock);
    void deleteStock(StockModel stock);
}
