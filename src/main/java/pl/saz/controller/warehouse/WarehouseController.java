package pl.saz.controller.warehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.saz.model.warehouse.WarehouseModel;
import pl.saz.service.warehouse.WarehouseService;

import java.util.List;

/**
 * Created by maciej on 01.05.18.
 */
@RestController
@RequestMapping(value = "/warehouses")
public class WarehouseController {

  @Autowired
  private WarehouseService warehouseService;

  @RequestMapping(value = "/new",method = RequestMethod.POST)
  public ResponseEntity<String> saveWarehouse(@RequestBody WarehouseModel warehouse){
      HttpHeaders headers = new HttpHeaders();

      boolean check = warehouseService.saveWarehouse(warehouse);

      if(check){
          headers.set("WarehouseNew",warehouse.get_name());
          return new ResponseEntity<String>("Warehouse was added",headers,HttpStatus.CREATED);
      }
      headers.set("Failure",warehouse.get_name());

      return new ResponseEntity<String>("",headers,HttpStatus.NOT_MODIFIED);
  }

  @RequestMapping(value = "/getAll",method = RequestMethod.GET)
  public ResponseEntity<List<WarehouseModel>> getAllWarehouses(){
      List<WarehouseModel> w = warehouseService.getAll();

      return new ResponseEntity<List<WarehouseModel>>(w, HttpStatus.OK);
  }

    @RequestMapping(value = "/getVisible",method = RequestMethod.GET)
    public ResponseEntity<List<WarehouseModel>> getAllAvailable(){
        List<WarehouseModel> w = warehouseService.getAvailable();

        return new ResponseEntity<List<WarehouseModel>>(w, HttpStatus.OK);
    }

    @RequestMapping(value = "/update/{name}", method = RequestMethod.POST)
    public ResponseEntity<String> updateWarehouse(@RequestBody WarehouseModel warehouse,@PathVariable String name) {
        if(warehouse.get_name().equals(name)){
            warehouseService.updateWarehouse(warehouse);
            return new ResponseEntity<String>("Updated:"+warehouse.get_name(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("Doesn't exists:"+warehouse.get_name(), HttpStatus.NOT_MODIFIED);
    }

    @RequestMapping(value = "/setVisible/{name}", method = RequestMethod.POST)
    public ResponseEntity<String> setVisible(@PathVariable String name) {

        warehouseService.setVisible(name);

        return new ResponseEntity<String>("Visible:"+name, HttpStatus.OK);
    }

    @RequestMapping(value = "/setUnVisible/{name}", method = RequestMethod.POST)
    public ResponseEntity<String> setUnVisible(@PathVariable String name) {

        warehouseService.setUnVisible(name);

        return new ResponseEntity<String>("Unvisible:"+name, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{name}", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@PathVariable String name) {
        WarehouseModel tmp = warehouseService.getByName(name);


        if(null != tmp){
            warehouseService.deleteWarehouse(tmp);
            return new ResponseEntity<String>("deleted:"+name, HttpStatus.OK);
        }

        return new ResponseEntity<String>("failure", HttpStatus.NOT_MODIFIED);
    }

    @RequestMapping(value = "/exists/{name}", method = RequestMethod.GET)
    public ResponseEntity<String> exists(@PathVariable String name) {

        String w = "false";
        boolean _exists = warehouseService.checkIfExists(name);

        if(_exists){
            w = "true";
        }

        return new ResponseEntity<String>(w, HttpStatus.OK);
    }

    @RequestMapping(value = "/start", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void start() {
        warehouseService.saveTest();
    }

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public ResponseEntity<WarehouseModel> getTestWarehouse(){
        return new ResponseEntity<WarehouseModel>(warehouseService.getByName("Test1"),HttpStatus.OK);
    }



}
