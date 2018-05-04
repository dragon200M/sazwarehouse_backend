package pl.saz.service.stock;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.saz.dao.stock.StockDao;
import pl.saz.model.komponent.KomponentModel;
import pl.saz.model.operationRecord.OperationRecords;
import pl.saz.model.operationRecord.OperationTypes;
import pl.saz.model.stock.StockModel;
import pl.saz.model.stock.StockSummary;
import pl.saz.model.warehouse.WarehouseModel;
import pl.saz.service.komponent.KomponentService;
import pl.saz.service.operationRecord.OperationRecordService;
import pl.saz.service.warehouse.WarehouseService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by maciej on 02.05.18.
 */
@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockDao stockDao;

    @Autowired
    private OperationRecordService recordService;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private KomponentService komponentService;

    @Override
    public List<StockModel> getAll() {

        return stockDao.getAll();
    }

    @Override
    public StockModel getById(WarehouseModel w, KomponentModel k) {

        return stockDao.getById(w,k);
    }

    @Override
    public List<StockModel> getByWarehouse(WarehouseModel warehouse) {

        return stockDao.getByWarehouse(warehouse);
    }

    @Override
    public List<StockModel> getByKomponent(KomponentModel komponent) {
        return stockDao.getByKomponent(komponent);
    }

    @Override
    public boolean saveStock(StockModel stock) {

        boolean operation = this.stockDao.saveStock(stock);
        if(operation) {
            Gson gson = new Gson();
            String json = gson.toJson(stock,StockModel.class);
            OperationRecords op = new OperationRecords(OperationTypes.INSERT,json);
            recordService.saveRecords(op);
        }

        return operation;
    }

    @Override
    public boolean saveStock(String w, String k, Double q) {
        WarehouseModel wt = warehouseService.getByName(w);
        KomponentModel kt = komponentService.getKomponentByName(k);

        if(null != wt && null != kt && q >= 0){
            StockModel n = new StockModel(wt,kt,q);

            boolean operation = this.stockDao.saveStock(n);
            if(operation) {
                Gson gson = new Gson();
                String json = gson.toJson(n,StockModel.class);
                OperationRecords op = new OperationRecords(OperationTypes.INSERT,json);
                recordService.saveRecords(op);

            }

            return operation;
        }

        return false;
    }

    @Override
    public StockModel updateStock(WarehouseModel warehouse, KomponentModel komponent, Double stock) {

        return stockDao.updateStock(warehouse,komponent,stock);
    }

    @Override
    public StockModel updateStock(String warehouse, String komponent, Double stock) {
        return stockDao.updateStock(warehouse,komponent,stock);
    }

    @Override
    public List<StockSummary> getSummary() {
        List<StockModel> tmp = stockDao.getAll();
        List<StockSummary> r1 = new ArrayList<>();

        Map<String,Double> sum = tmp.stream().collect(
                Collectors.groupingBy(s -> s.getComponent().get_name(),Collectors.summingDouble(StockModel::get_stock)));

        for(Map.Entry<String,Double> e: sum.entrySet()){
            StockSummary s = new StockSummary("",e.getKey(),e.getValue());
            r1.add(s);
        }

        return r1;
    }

    @Override
    public List<StockSummary> getSummaryByWarehouses() {
        List<StockModel> tmp = stockDao.getAll();
        List<StockSummary> r1 = new ArrayList<>();

        Map<String,Map<String,Double>> w =
                tmp.stream()
                        .collect(
                                Collectors.groupingBy(s -> s.getWarehouse().get_name(),Collectors.groupingBy(s -> s.getComponent().get_name(),
                                        Collectors.summingDouble(StockModel::get_stock))));
        for(Map.Entry<String,Map<String,Double>> e:w.entrySet()){
            for(Map.Entry<String,Double> f:e.getValue().entrySet()){
                StockSummary s = new StockSummary(e.getKey(),f.getKey(),f.getValue());
                r1.add(s);
            }
        }

        return r1;
    }

    @Override
    public void deleteStock(StockModel stockModel) {
        stockDao.deleteStock(stockModel);
    }

    @Override
    public void deleteStock(String w, String k) {
        StockModel st = stockDao.getById(w, k);
        if (null != st) {
            stockDao.deleteStock(st);
        }
    }
    @Override
    public void saveTest() {
        WarehouseModel w1 = warehouseService.getByName("test4");
        WarehouseModel w2 = warehouseService.getByName("test45");
        KomponentModel k1 = komponentService.getKomponentByName("B");
        KomponentModel k2 = komponentService.getKomponentByName("C");

        StockModel s1 = new StockModel(w1,k1,100.0);
        StockModel s2 = new StockModel(w1,k2,53.0);
        StockModel s3 = new StockModel(w2,k1,10.0);
        StockModel s4 = new StockModel(w2,k2,5.0);

        stockDao.saveStock(s1);
        stockDao.saveStock(s2);
        stockDao.saveStock(s3);
        stockDao.saveStock(s4);
    }
}
