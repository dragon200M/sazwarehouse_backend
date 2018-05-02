package pl.saz.service.stock;

import pl.saz.model.komponent.KomponentModel;
import pl.saz.model.stock.StockModel;
import pl.saz.model.stock.StockSummary;
import pl.saz.model.warehouse.WarehouseModel;

import java.util.List;

/**
 * Created by maciej on 02.05.18.
 */
public class StockServiceImpl implements StockService {
    @Override
    public List<StockModel> getAll() {
        return null;
    }

    @Override
    public StockModel getById(Long id) {
        return null;
    }

    @Override
    public List<StockModel> getByWarehouse(WarehouseModel warehouse) {
        return null;
    }

    @Override
    public List<StockModel> getByKomponent(KomponentModel komponent) {
        return null;
    }

    @Override
    public StockModel saveStock(StockModel stock) {
        return null;
    }

    @Override
    public StockModel updateStock(WarehouseModel warehouse, KomponentModel komponent, Double stock) {
        return null;
    }

    @Override
    public StockSummary getSummary(WarehouseModel warehouse, KomponentModel komponent) {
        return null;
    }

    @Override
    public StockModel changeWarehouse(KomponentModel komponent) {
        return null;
    }
}
