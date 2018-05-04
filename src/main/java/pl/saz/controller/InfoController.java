package pl.saz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.saz.model.stock.StockModel;
import pl.saz.model.stock.StockSummary;
import pl.saz.service.stock.StockService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maciej on 04.05.18.
 */
@Controller
public class InfoController {

    @Autowired
    private StockService stockService;

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
}
