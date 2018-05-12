package pl.saz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.saz.model.stock.StockModel;
import pl.saz.model.stock.StockSummary;
import pl.saz.model.warehouse.WarehouseModel;
import pl.saz.service.stock.StockService;
import pl.saz.service.warehouse.WarehouseService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maciej on 04.05.18.
 */
@Controller
public class InfoController {

    @Autowired
    private StockService stockService;

    @Autowired
    private WarehouseService warehouseService;

    @RequestMapping(value = "/info")
    public String greeting( Model model){
        List<StockSummary> tmp =  stockService.getSummary();

        List<StockModel> w = stockService.getAll();
        List<StockModel.StockModelView> w2 = new ArrayList<>();

        for(StockModel s:w){
                StockModel.StockModelView b = s.new StockModelView();
                String s1 = b.getWareName();
                String s2 = b.getKomName();
                String s3 = b.getKomDesc();
                Double d =  b.getStock();
                System.out.println(s1+":"+s2+":"+s3+":"+Double.toString(d));
            w2.add(b);
        }


        model.addAttribute("testW", "wiadomosc testowa");
        model.addAttribute("stock",tmp);
        model.addAttribute("wyniki",w2);
        return "test";
    }


    @RequestMapping(value = "/magazyn")
    public String stockRedirect(Model model){
        List<WarehouseModel> w = warehouseService.getAvailable();
        model.addAttribute("wList",w);

        return "data-table";
    }

    @RequestMapping(value = "/magazyn/{id}")
    public String stock(@PathVariable String id, Model model){
        List<WarehouseModel> w = warehouseService.getAvailable();
        WarehouseModel warehouseModel = warehouseService.getByName(id);


        model.addAttribute("wList",w);
        model.addAttribute("stocks",stockService.getStockView(id));
        model.addAttribute("warhouse",warehouseModel);

        return "data-table";
    }

    @RequestMapping(value = "/magazyn/podsumowanie")
    public String stockSummary(Model model){
        List<WarehouseModel> w = warehouseService.getAvailable();
        List<StockSummary> view = stockService.getSummaryAvailable();

        model.addAttribute("wList",w);
        model.addAttribute("viewList",view);

        return "data-table";
    }

}
