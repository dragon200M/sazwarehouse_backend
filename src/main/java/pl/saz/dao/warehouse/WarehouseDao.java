package pl.saz.dao.warehouse;

import pl.saz.model.warehouse.WarehouseModel;

/**
 * Created by maciej on 01.05.18.
 */
public interface WarehouseDao {
    WarehouseModel getByName(String name);
    WarehouseModel getById(Long id);
    WarehouseModel saveWarehouse(WarehouseModel warehouse);
    WarehouseModel updateWarehouse(WarehouseModel warehouse);
    WarehouseModel deleteWarehouse(WarehouseModel warehouse);
}
