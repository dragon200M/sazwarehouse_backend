package pl.saz.service.stock;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import pl.saz.model.komponent.KomponentModel;
import pl.saz.model.stock.MaxToDo;
import pl.saz.model.stock.StockListUpdate;
import pl.saz.model.stock.StockModel;
import pl.saz.model.stock.StockSummary;
import pl.saz.model.warehouse.WarehouseModel;

import java.util.List;

/**
 * Created by maciej on 01.05.18.
 */
public interface StockService {
    List<StockModel> getAll();
    StockModel getById(WarehouseModel w,KomponentModel k);
    StockModel getById(String w,String k);
    List<StockModel> getByWarehouse(WarehouseModel warehouse);
    List<StockModel> getByKomponent(KomponentModel komponent);
    List<StockModel> getByWarehouse(String warehouse);
    List<StockModel> getByKomponent(String komponent);
    List<StockModel.StockModelView> getStockView(String warehouseName);
    List<StockModel.StockModelView> getStockViewAll();
    List<StockModel.StockModelView> getRolleStockView();
    boolean saveStock(StockModel stock);
    boolean saveStock(String w,String k,Double q);
    void deleteStock(StockModel stockModel);
    boolean deleteStock(String w,String k);
    StockModel updateStock(WarehouseModel warehouse,KomponentModel komponent, Double stock);
    StockModel updateStock(String warehouse,String komponent, Double stock);
    List<StockListUpdate> updateStocks(List<StockListUpdate> newStock);
    List<StockListUpdate> getPieceQuantity(List<StockListUpdate> newStock);
    List<StockListUpdate> updateStockByFile(MultipartFile[] uploadfile);
    List<StockSummary> getSummary();
    List<StockSummary> getSummaryAvailable();
    List<StockSummary> getSummaryByWarehouses();
    List<MaxToDo> getPosibleToDo();
    void saveTest();
    XSSFWorkbook getAllStock();

}
