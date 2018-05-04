package pl.saz.dao.warehouse;

import pl.saz.model.warehouse.WarehouseModel;

import java.util.List;

/**
 * Created by maciej on 01.05.18.
 */
public interface WarehouseDao {
    WarehouseModel getByName(String name);
    List<WarehouseModel> getAll();
    boolean saveWarehouse(WarehouseModel warehouse);
    WarehouseModel updateWarehouse(WarehouseModel warehouse);
    void deleteWarehouse(WarehouseModel warehouse);
    String getStockInfo(String name);
}
