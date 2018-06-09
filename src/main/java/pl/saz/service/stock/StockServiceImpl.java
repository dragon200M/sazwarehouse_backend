package pl.saz.service.stock;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.saz.dao.stock.StockDao;
import pl.saz.dao.stock.StockOperation;
import pl.saz.model.komponent.KomponentModel;
import pl.saz.model.komponent.Types;
import pl.saz.model.operationRecord.OperationRecords;
import pl.saz.model.operationRecord.OperationTypes;
import pl.saz.model.stock.MaxToDo;
import pl.saz.model.stock.StockListUpdate;
import pl.saz.model.stock.StockModel;
import pl.saz.model.stock.StockSummary;
import pl.saz.model.warehouse.WarehouseModel;
import pl.saz.service.komponent.KomponentService;
import pl.saz.service.operationRecord.OperationRecordService;
import pl.saz.service.warehouse.WarehouseService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.*;
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
    public StockModel getById(String w, String k) {
        WarehouseModel wtmp = warehouseService.getByName(w);
        KomponentModel ktmp = komponentService.getKomponentByName(k);

        return stockDao.getById(wtmp,ktmp);
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
    public List<StockModel> getByWarehouse(String warehouse) {
        return stockDao.getByWarehouse(warehouse);
    }



    @Override
    public List<StockModel> getByKomponent(String komponent) {
        return stockDao.getByKomponent(komponent);
    }

    @Override
    public boolean saveStock(StockModel stock) {

        boolean operation = this.stockDao.saveStock(stock);
        if(operation) {
            Gson gson = new Gson();
            String json = gson.toJson(stock,StockModel.class);
            OperationRecords op = new OperationRecords(OperationTypes.INSERT,json,StockModel.class.getSimpleName());
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
                OperationRecords op = new OperationRecords(OperationTypes.INSERT,json,StockModel.class.getSimpleName());
                recordService.saveRecords(op);

            }

            return operation;
        }

        return false;
    }


    @Override
    public StockModel updateStock(WarehouseModel warehouse, KomponentModel komponent, Double stock) {
        StockModel st = stockDao.updateStock(warehouse,komponent,stock);

        if(null != st){
            Gson gson = new Gson();
            String json = gson.toJson(st,StockModel.class);
            OperationRecords p = new OperationRecords(OperationTypes.INSERT,json,StockModel.class.getSimpleName());
            recordService.saveRecords(p);
        };

        return st;
    }

    @Override
    public StockModel updateStock(String warehouse, String komponent, Double stock) {
        StockModel st = stockDao.updateStock(warehouse,komponent,stock);

        if(null != st){
            Gson gson = new Gson();
            String json = gson.toJson(st,StockModel.class);
            OperationRecords p = new OperationRecords(OperationTypes.INSERT,json,StockModel.class.getSimpleName());
            recordService.saveRecords(p);
        };

        return st;
    }


    @Override
    public List<StockListUpdate> updateStocks(List<StockListUpdate> newStock) {
         List<StockListUpdate> st = stockDao.updateStock(newStock);

          final boolean[] err = {true};
          st.forEach(e -> {
             if(e.getType() == StockOperation.ERROR){
                 err[0] = false;
             }
          });

         if(err[0]){
            Type listTYPE = new TypeToken<List<StockListUpdate>>(){}.getType();
            Gson gson = new Gson();
            String json = gson.toJson(newStock,listTYPE);
            OperationRecords p = new OperationRecords(OperationTypes.INSERT,json,StockListUpdate.class.getSimpleName());
            recordService.saveRecords(p);
         };

        return st;
    }

    @Override
    public List<StockListUpdate> updateStockByFile(MultipartFile[] uploadfile) {
        Map<String,Double> ms = new HashMap<String,Double>();

        List<StockListUpdate> slu = new ArrayList<StockListUpdate>();




        for(MultipartFile m: uploadfile){
            try {
                HSSFWorkbook workbook = new HSSFWorkbook(m.getInputStream());
                HSSFSheet sheet =workbook.getSheetAt(0);
                Iterator<Row> r= sheet.iterator();
                HSSFRow row;
                while(r.hasNext()) {
                    row = (HSSFRow) r.next();
                    if(row.getRowNum() > 0){

                        String st = new String(row.getCell(0).toString().trim());
                        Double d = Double.parseDouble(row.getCell(1).toString());
                        if(st.length() > 0) {
                            if (ms.containsKey(st)) {
                                ms.put(st, ms.get(st) + d);
                            } else {
                                ms.put(st, d);
                            }
                        }
                    }
                }

            }catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch(Exception e){
                System.out.println(e);
            }

        }
        ms.forEach((k,v) -> {

            List<StockModel> tmp = getByKomponent(k);
            if (tmp.size() > 0) {
                StockListUpdate st2 = new StockListUpdate(tmp.get(0).getWarehouse().get_name(), k,
                        v, StockOperation.ADD);
                slu.add(st2);
            } else {
                StockListUpdate st2 = new StockListUpdate("", k,
                        v, StockOperation.ERROR);
                slu.add(st2);
            }

        });

        return slu;
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
    public List<StockSummary> getSummaryAvailable() {
        List<StockModel> tmp = stockDao.getAll().stream().filter(p -> p.getWarehouse().is_available()  == true).collect(Collectors.toList());
        List<StockSummary> r1 = new ArrayList<>();

        Map<KomponentModel,Double> sum = tmp.stream().collect(
                Collectors.groupingBy(s -> s.getComponent(),Collectors.summingDouble(StockModel::get_stock)));

        for(Map.Entry<KomponentModel,Double> e: sum.entrySet()){
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
    public List<StockListUpdate> getPieceQuantity(List<StockListUpdate> newStock) {
        List<StockListUpdate> tmp = new ArrayList<StockListUpdate>();
        List<KomponentModel.Calculation> w = new ArrayList<KomponentModel.Calculation>();

        for(StockListUpdate el: newStock) {
            KomponentModel a = komponentService.getKomponentByName(el.getKomponentName());
            if(null != a) {
                if(a.get_typ_1() != Types.TASMA) {
                    List<KomponentModel.Calculation> tc = a.calc();
                    for (KomponentModel.Calculation c : tc) {
                        c.set_quantity(c.get_quantity() * el.getNewStock().intValue());
                        w.add(c);
                    }
                }
            }
        }

        w.stream().collect(Collectors.groupingBy(s -> s.get_childComponentName(),Collectors.summingInt(s -> s.get_quantity())))
                .forEach( (s,i) -> {

                 StockModel kw = getByKomponent(s).size() == 0 ? null : getByKomponent(s).get(0) ;
                 if(null != kw) {
                     System.out.println("dodawanie magazynu do komponentu");
                     StockListUpdate stu = new StockListUpdate(kw.getWarehouse().get_name(),s, i.doubleValue(),StockOperation.REMOVE);
                     stu.setOldStock(kw.get_stock());
                     tmp.add(stu);
                 }else{
                     System.out.println("dodawanie magazynu do komponentu 2");
                     StockListUpdate stu = new StockListUpdate("",s, i.doubleValue(),StockOperation.REMOVE);
                     stu.setOldStock(0.0);
                     tmp.add(stu);
                 }
              });

        return tmp;
    }

    @Override
    public List<MaxToDo> getPosibleToDo() {
        List<KomponentModel> ktmp = komponentService.getAllKomponents();
        List<KomponentModel> komponents ;



        List<StockModel> tmp = stockDao.getAll().stream().filter(st -> st.getComponent().get_typ_1() == Types.SZTUKA).collect(Collectors.toList());


        Map<String,Double> wynik = tmp.stream().collect(Collectors.groupingBy(k->k.getComponent()
                .get_name(),Collectors.summingDouble(k-> k.get_stock())));


        komponents = ktmp.stream().filter(k-> k.get_typ_1() == Types.GLOWNY)
                .collect(Collectors.toList());


        List<MaxToDo> m = new ArrayList<MaxToDo>();
          komponents.forEach(el -> {
              el.calc().forEach(b -> {
                  if(null != wynik.get(b.get_childComponentName()) ){
                      m.add(new MaxToDo(el.get_name(), new Double(wynik.get(b.get_childComponentName()) / b.get_quantity()).intValue()));
                  }
              });
          });

          List<MaxToDo> resoult = new ArrayList<MaxToDo>();

          m.stream().collect(Collectors.groupingBy(MaxToDo::getKomponentModel,Collectors.minBy(Comparator.comparingInt(MaxToDo::getResoult))))
                  .forEach((v,k)-> resoult.add(k.get()));

        return resoult;
    }

    @Override
    public void deleteStock(StockModel stockModel) {
        Gson gson = new Gson();
        String json = gson.toJson(stockModel,StockModel.class);
        OperationRecords op = new OperationRecords(OperationTypes.DELETE,json);

        stockDao.deleteStock(stockModel);


    }

    @Override
    public boolean deleteStock(String w, String k) {
        StockModel st = stockDao.getById(w, k);
        if (null != st) {
            if(st.get_stock() == 0){
                Gson gson = new Gson();
                String json = gson.toJson(st,StockModel.class);
                OperationRecords op = new OperationRecords(OperationTypes.DELETE,json);
                stockDao.deleteStock(st);
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public List<StockModel.StockModelView> getStockView(String warehouseName) {
        List<StockModel> st = getByWarehouse(warehouseName);

        List<StockModel.StockModelView> w2 = new ArrayList<>();

        for(StockModel s:st){
            StockModel.StockModelView b = s.new StockModelView();
            w2.add(b);
        }

        return w2;
    }

    @Override
    public List<StockModel.StockModelView> getStockViewAll() {
        List<StockModel> st = getAll();

        List<StockModel.StockModelView> w2 = new ArrayList<>();

        for(StockModel s:st){
            StockModel.StockModelView b = s.new StockModelView();
            w2.add(b);
        }

        return w2;
    }

    @Override
    public List<StockModel.StockModelView> getRolleStockView() {
        List<StockModel> st = getAll().stream().filter(roller -> roller.getComponent().get_typ_1() == Types.TASMA).collect(Collectors.toList());

        List<StockModel.StockModelView> w2 = new ArrayList<>();

        for(StockModel s:st){
            StockModel.StockModelView b = s.new StockModelView();
            w2.add(b);
        }

        return w2;
    }

    @Override
    public XSSFWorkbook getAllStock() {
        String[] columnName = {"Magazyn","Opis","Komponent","Opis","Stan","J.M"};
        int nextRow = 1;

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Magazyn");
        Row row1 = sheet.createRow(0);
        Cell date = row1.createCell(0);
        date.setCellValue(LocalDateTime.now().toString());
        Row row = sheet.createRow(nextRow++);


        for(int i = 0;i < columnName.length;i++){
            Cell c1 = row.createCell(i);
            c1.setCellValue(columnName[i]);
        }


        List<StockModel> st = getAll();

        if(st.size() > 0){
            for (StockModel stockModel : st) {
                Row nRow = sheet.createRow(nextRow++);
                Cell c2 = nRow.createCell(0);
                c2.setCellValue(stockModel.getWarehouse().get_name());
                Cell c3 = nRow.createCell(1);
                c3.setCellValue(stockModel.getWarehouse().get_description());
                Cell c4 = nRow.createCell(2);
                c4.setCellValue(stockModel.getComponent().get_name());
                Cell c5 = nRow.createCell(3);
                c5.setCellValue(stockModel.getComponent().get_description());
                Cell c6 = nRow.createCell(4);
                c6.setCellValue(stockModel.get_stock());
                Cell c7 = nRow.createCell(5);

                String unit = "";
                if(null != stockModel.getComponent().get_units()){
                 unit =  stockModel.getComponent().get_units().toString();
                }
                c7.setCellValue(unit);
            }
        }




        return workbook;
    }

    @Override
    public void saveTest() {
//        WarehouseModel w1 = warehouseService.getByName("test4");
//        WarehouseModel w2 = warehouseService.getByName("test45");
//        KomponentModel k1 = komponentService.getKomponentByName("B");
//        KomponentModel k2 = komponentService.getKomponentByName("C");
//
//        StockModel s1 = new StockModel(w1,k1,100.0);
//        StockModel s2 = new StockModel(w1,k2,53.0);
//        StockModel s3 = new StockModel(w2,k1,10.0);
//        StockModel s4 = new StockModel(w2,k2,5.0);
//
//        stockDao.saveStock(s1);
//        stockDao.saveStock(s2);
//        stockDao.saveStock(s3);
//        stockDao.saveStock(s4);

        WarehouseModel w1 = warehouseService.getByName("Panele");
        List<KomponentModel> k = komponentService.getAllKomponents().stream().filter(x -> x.get_name().startsWith("MM")).collect(Collectors.toList());
        k.forEach( a -> {
            int randomStock = 1 +(int)(Math.random() * ((40000 -1)+1));
            System.out.println(a.toString());
            StockModel tmp = new StockModel(w1,a, Double.parseDouble(Integer.toString(randomStock)));
            stockDao.saveStock(tmp);

        });



    }
}
