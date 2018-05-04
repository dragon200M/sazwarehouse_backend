package pl.saz.service.warehouse;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.saz.dao.warehouse.WarehouseDao;
import pl.saz.model.operationRecord.OperationRecords;
import pl.saz.model.operationRecord.OperationTypes;
import pl.saz.model.warehouse.WarehouseModel;
import pl.saz.service.operationRecord.OperationRecordService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by maciej on 02.05.18.
 */
@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    private WarehouseDao warehouseDao;

    @Autowired
    private OperationRecordService recordService;


    @Override
    public WarehouseModel getByName(String name) {
        return this.warehouseDao.getByName(name);
    }

    @Override
    public WarehouseModel getById(Long id) {
        return null;
    }

    @Override
    public boolean saveWarehouse(WarehouseModel warehouse) {

        boolean operation  = this.warehouseDao.saveWarehouse(warehouse);
        if(operation) {
            Gson gson = new Gson();
            String json = gson.toJson(warehouse,WarehouseModel.class);
            OperationRecords op = new OperationRecords(OperationTypes.INSERT,json);
            recordService.saveRecords(op);
        }

        return operation;
    }

    @Override
    public WarehouseModel updateWarehouse(WarehouseModel warehouse) {
        WarehouseModel tmp =  warehouseDao.updateWarehouse(warehouse);
        if(null != tmp) {
            Gson gson = new Gson();
            String json = gson.toJson(tmp,WarehouseModel.class);
            OperationRecords op = new OperationRecords(OperationTypes.UPDATE,json);
            recordService.saveRecords(op);
        }

        return tmp;
    }

    @Override
    public void deleteWarehouse(WarehouseModel warehouse) {
        if(null != warehouse) {
            warehouseDao.deleteWarehouse(warehouse);
            Gson gson = new Gson();
            String json = gson.toJson(warehouse,WarehouseModel.class);
            OperationRecords op = new OperationRecords(OperationTypes.DELETE,json);
            recordService.saveRecords(op);
        }
    }

    @Override
    public boolean checkIfExists(String warehouseName) {
        if(null != warehouseDao.getByName(warehouseName)){
            return true;
        }
        return false;
    }

    @Override
    public boolean setVisible(String warehouseName) {
        WarehouseModel tmp = warehouseDao.getByName(warehouseName);
        if(null != tmp) {
            tmp.set_available(true);
            warehouseDao.updateWarehouse(tmp);

            Gson gson = new Gson();
            String json = gson.toJson(tmp,WarehouseModel.class);
            OperationRecords op = new OperationRecords(OperationTypes.UPDATE,json);
            recordService.saveRecords(op);

            return tmp.is_available();
        }

        return false;
    }

    @Override
    public boolean setUnVisible(String warehouseName) {
        WarehouseModel tmp = warehouseDao.getByName(warehouseName);
        if(null != tmp) {
            tmp.set_available(false);
            warehouseDao.updateWarehouse(tmp);

            Gson gson = new Gson();
            String json = gson.toJson(tmp,WarehouseModel.class);
            OperationRecords op = new OperationRecords(OperationTypes.UPDATE,json);
            recordService.saveRecords(op);

            return tmp.is_available();
        }

        return true;
    }

    @Override
    public void saveTest() {
        WarehouseModel wtest = new WarehouseModel("Test1","Maciek testowy",true,"Ladny");

        this.warehouseDao.saveWarehouse(wtest);
    }

    @Override
    public List<WarehouseModel> getAll() {

        return  warehouseDao.getAll();
    }

    @Override
    public List<WarehouseModel> getAvailable() {
        List<WarehouseModel> availables = warehouseDao.getAll().stream()
                .filter(p -> p.is_available() == true).collect(Collectors.toList());

        return availables;
    }

    @Override
    public String getStockInfo(String name) {

        return warehouseDao.getStockInfo(name);
    }
}
