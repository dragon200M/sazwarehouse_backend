package pl.saz.controller.komponent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.saz.model.komponent.KomponentModel;
import pl.saz.service.komponent.KomponentService;

import java.util.List;

/**
 * Created by maciej on 01.05.18.
 */
@RestController
@RequestMapping(value = "/components")
public class KomponentController {

    @Autowired
    private KomponentService komponentService;


    @RequestMapping(value = "/new",method = RequestMethod.POST)
    public ResponseEntity<String> saveKomoponent(@RequestBody KomponentModel komponent){
        HttpHeaders headers = new HttpHeaders();

        boolean check = komponentService.saveKomponent(komponent);

        if(check){
            headers.set("KomponentNew",komponent.get_name());
            return new ResponseEntity<String>("Komponent was added",headers,HttpStatus.CREATED);
        }
        headers.set("Failure",komponent.get_name());

        return new ResponseEntity<String>("",headers,HttpStatus.NOT_MODIFIED);
    }

    @RequestMapping(value = "/update/{name}", method = RequestMethod.POST)
    public ResponseEntity<String> updateKomponent(@RequestBody KomponentModel komponentModel, @PathVariable String name) {
        if(komponentModel.get_name().equals(name)){
            komponentService.updateKomponent(komponentModel);
            return new ResponseEntity<String>("Updated:"+komponentModel.get_name(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("Doesn't exists:"+komponentModel.get_name(), HttpStatus.NOT_MODIFIED);
    }



    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity<List<KomponentModel>> getAllKomponents(){
        List<KomponentModel> k = komponentService.getAllKomponents();

        return new ResponseEntity<List<KomponentModel>>(k,HttpStatus.OK);
    }

    @RequestMapping(value = "/getMain", method = RequestMethod.GET)
    public ResponseEntity<List<KomponentModel>> getMainKomponents(){
        List<KomponentModel> k = komponentService.getMainKomponents();

        return new ResponseEntity<List<KomponentModel>>(k,HttpStatus.OK);
    }

    @RequestMapping(value = "/getByName/{name}", method = RequestMethod.GET)
    public ResponseEntity<KomponentModel> getByName(@PathVariable String name){
        KomponentModel k = komponentService.getKomponentByName(name);


        return new ResponseEntity<KomponentModel>(k,HttpStatus.OK);
    }


    @RequestMapping(value = "/getParent/{childName}", method = RequestMethod.GET)
    public ResponseEntity<List<KomponentModel>> getParents(@PathVariable String childName){
        List<KomponentModel> k = komponentService.getParentsOfChild(childName);

        return new ResponseEntity<List<KomponentModel>>(k,HttpStatus.OK);
    }

    @RequestMapping(value = "/addChild/{parent}/{child}/{ilosc}", method = RequestMethod.POST)
    public ResponseEntity<String> addChildToParent(
            @PathVariable String parent,@PathVariable String child, @PathVariable Integer ilosc)
    {
        if(!parent.equals(child) && ilosc > 0){
            komponentService.addChildToParent(parent,child,ilosc);
        }
        return new ResponseEntity<String>("Failure",HttpStatus.NOT_MODIFIED);
    }

    @RequestMapping(value = "/delete/{name}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable String name) {
        KomponentModel tmp = komponentService.getKomponentByName(name);
        String stock = komponentService.getStockInfo(name);

        HttpHeaders headers = new HttpHeaders();
        if(null != tmp && null == stock) {
            if (komponentService.getParentsOfChild(name).size() > 0) {
                headers.set("Attention","parents");
                return new ResponseEntity<String>("failure",headers, HttpStatus.CHECKPOINT);
            } else {
                komponentService.deleteKomponent(tmp);
                return new ResponseEntity<String>("deleted:" + name, HttpStatus.OK);
            }
        }

        return new ResponseEntity<String>("failure", HttpStatus.NOT_MODIFIED);
    }

    @RequestMapping(value = "/delete/{parent}/{child}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteChild(@PathVariable String parent,@PathVariable String child) {
        KomponentModel p = komponentService.getKomponentByName(parent);
        KomponentModel c = komponentService.getKomponentByName(child);
        HttpHeaders headers = new HttpHeaders();

        if(null != p && null != c) {
            komponentService.deleteKomponentChild(p,c);
            return new ResponseEntity<String>("deleted:", HttpStatus.OK);
        }

        return new ResponseEntity<String>("failure", HttpStatus.NOT_MODIFIED);
    }



    @RequestMapping(value = "/start", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void start() {
        komponentService.saveTest();
    }
    @RequestMapping(value = "/start2", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void start2() {
        komponentService.saveTest2();
    }


    @RequestMapping(value = "/getSummary/{name}", method = RequestMethod.GET)
    public ResponseEntity<List<KomponentModel.Calculation>> getTestSummary(@PathVariable String name){
        KomponentModel k = komponentService.getKomponentByName(name);
        List<KomponentModel.Calculation> w = k.calc();

        return new ResponseEntity<List<KomponentModel.Calculation>>(w,HttpStatus.OK);
    }

}
