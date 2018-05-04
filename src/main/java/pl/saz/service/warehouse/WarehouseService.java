package pl.saz.service.warehouse;

import pl.saz.model.warehouse.WarehouseModel;

import java.util.List;

/**
 * Created by maciej on 01.05.18.
 */
public interface WarehouseService {
    WarehouseModel getByName(String name);
    WarehouseModel getById(Long id);
    boolean saveWarehouse(WarehouseModel warehouse);
    WarehouseModel updateWarehouse(WarehouseModel warehouse);
    void deleteWarehouse(WarehouseModel warehouse);
    boolean checkIfExists(String warehouseName);
    boolean setVisible(String warehouseName);
    boolean setUnVisible(String warehouseName);
    void saveTest();
    List<WarehouseModel> getAll();
    List<WarehouseModel> getAvailable();
    String getStockInfo(String name);
}
