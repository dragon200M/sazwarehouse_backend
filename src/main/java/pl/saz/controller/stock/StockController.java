package pl.saz.controller.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.saz.model.komponent.KomponentModel;
import pl.saz.model.stock.StockModel;
import pl.saz.model.stock.StockSummary;
import pl.saz.model.warehouse.WarehouseModel;
import pl.saz.service.komponent.KomponentService;
import pl.saz.service.stock.StockService;
import pl.saz.service.warehouse.WarehouseService;

import java.util.List;

/**
 * Created by maciej on 01.05.18.
 */
@RestController
@RequestMapping(value = "/stock")
public class StockController {

    @Autowired
    private StockService stockService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private KomponentService komponentService;

    @RequestMapping(value = "/new",method = RequestMethod.POST)
    public ResponseEntity<String> saveStock(@RequestBody StockModel stockModel){
        HttpHeaders headers = new HttpHeaders();

        boolean check = stockService.saveStock(stockModel);

        if(check){
            headers.set("Stock",stockModel.get_id().get_wName()+":"
                    +stockModel.get_id().get_kName()+":"+Double.toString(stockModel.get_stock()));
            return new ResponseEntity<String>("Stock was added",headers,HttpStatus.CREATED);
        }
        headers.set("Failure",stockModel.get_id().get_wName()+":"
                +stockModel.get_id().get_kName()+":"+Double.toString(stockModel.get_stock()));

        return new ResponseEntity<String>("",headers,HttpStatus.NOT_MODIFIED);
    }


    @RequestMapping(value = "/new/{war}/{kom}/{quantity}",method = RequestMethod.POST)
    public ResponseEntity<String> saveStock2(@PathVariable String war,
                                             @PathVariable String kom,
                                             @PathVariable Double quantity){
        HttpHeaders headers = new HttpHeaders();

        boolean check = stockService.saveStock(war, kom, quantity);

        if(check){
            headers.set("Stock"," OK");
            return new ResponseEntity<String>("Stock was added",headers,HttpStatus.CREATED);
        }
        headers.set("Failure"," ERROR");

        return new ResponseEntity<String>("",headers,HttpStatus.NOT_MODIFIED);
    }

    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteStock(@RequestBody StockModel stockModel){
        HttpHeaders headers = new HttpHeaders();

        stockService.deleteStock(stockModel);

        headers.set("Delete"," ");

        return new ResponseEntity<String>("",headers,HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{war}/{kom}",method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteStock2(@PathVariable String war,
                                               @PathVariable String kom){
        HttpHeaders headers = new HttpHeaders();

        stockService.deleteStock(war, kom);

        headers.set("Delete"," ");

        return new ResponseEntity<String>("",headers,HttpStatus.OK);
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<String> updateStock(@RequestBody StockModel stockModel) {
            WarehouseModel w =  warehouseService.getByName(stockModel.getWarehouse().get_name());
            KomponentModel k =  komponentService.getKomponentByName(stockModel.getComponent().get_name());

            if(null != w && null != k){
                stockService.updateStock(w,k,stockModel.get_stock());
                return new ResponseEntity<String>("Updated", HttpStatus.OK);
            }

        return new ResponseEntity<String>("Doesn't exists", HttpStatus.NOT_MODIFIED);
    }

    @RequestMapping(value = "/update/{war}/{kom}/{quantity}", method = RequestMethod.POST)
    public ResponseEntity<String> updateStock2(@PathVariable String war,
                                               @PathVariable String kom,
                                               @PathVariable Double quantity) {

        if(null != war && null != kom && null != quantity){
            stockService.updateStock(war,kom,quantity);
            return new ResponseEntity<String>("Updated", HttpStatus.OK);
        }

        return new ResponseEntity<String>("Doesn't exists", HttpStatus.NOT_MODIFIED);
    }


    @RequestMapping(value = "/getAll",method = RequestMethod.GET)
    public ResponseEntity<List<StockModel>> getAllWarehouses(){
        List<StockModel> w = stockService.getAll();

        return new ResponseEntity<List<StockModel>>(w, HttpStatus.OK);
    }


    @RequestMapping(value = "/getSummary",method = RequestMethod.GET)
    public ResponseEntity<List<StockSummary>> getSummary(){
        List<StockSummary> w = stockService.getSummary();

        return new ResponseEntity<List<StockSummary>>(w, HttpStatus.OK);
    }

    @RequestMapping(value = "/getSummary2",method = RequestMethod.GET)
    public ResponseEntity<List<StockSummary>> getSummary2(){
        List<StockSummary> w = stockService.getSummaryByWarehouses();

        return new ResponseEntity<List<StockSummary>>(w, HttpStatus.OK);
    }



    @RequestMapping(value = "/start", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void start() {
        stockService.saveTest();
    }

}

