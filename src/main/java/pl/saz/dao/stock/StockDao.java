package pl.saz.dao.stock;

import pl.saz.model.komponent.KomponentModel;
import pl.saz.model.stock.StockModel;
import pl.saz.model.stock.StockSummary;
import pl.saz.model.warehouse.WarehouseModel;

import java.util.List;

/**
 * Created by maciej on 01.05.18.
 */
public interface StockDao {
    List<StockModel> getAll();
    StockModel getById(Long id);
    List<StockModel> getByWarehouse(WarehouseModel warehouse);
    List<StockModel> getByKomponent(KomponentModel komponent);
    StockModel saveStock(StockModel stock);
    StockModel updateStock(WarehouseModel warehouse,KomponentModel komponent, Double stock);
    StockSummary getSummary(WarehouseModel warehouse, KomponentModel komponent);
    StockModel changeWarehouse(KomponentModel komponent);
}
