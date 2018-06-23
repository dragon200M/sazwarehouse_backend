package pl.saz.controller;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import pl.saz.model.stock.StockListUpdate;
import pl.saz.service.stock.StockService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by maciej on 06.05.18.
 */
@Controller
public class Upload {

    @Autowired
    private StockService stockService;


    @PutMapping(value = "/api/upload/file")
    @ResponseBody
    public String uploadFile(
            @RequestParam("file") MultipartFile uploadfile) throws IOException {

        String filesName = uploadfile.getOriginalFilename();




        return "{\"name\":\""+filesName+"\"}";
    }



    @PutMapping(value = "/api/upload/files")
    @ResponseBody
    public ResponseEntity<List<StockListUpdate>> uploadFiles(
            @RequestParam("files") MultipartFile[] uploadfile) throws IOException {

        String filesName = Arrays.stream(uploadfile).map(x -> x.getOriginalFilename())
                .filter(x -> !org.springframework.util.StringUtils.isEmpty(x)).collect(Collectors.joining(":"));

        if(StringUtils.isEmpty(filesName)){
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }

        List<StockListUpdate> slu = stockService.updateStockByFile(uploadfile);

        if(slu.size() > 0){
            return new ResponseEntity<List<StockListUpdate>>(slu, new HttpHeaders(), HttpStatus.OK);
        }


        return new ResponseEntity<List<StockListUpdate>>(slu, new HttpHeaders(), HttpStatus.OK);
    }


    @GetMapping(value = "/api/download/stock")
    public void downloadStock(HttpServletResponse response) throws IOException {

        //https://cursache.wordpress.com/2016/02/02/integrating-spring-mvc-and-apache-poi/
        LocalDate today = LocalDate.now();

        String magazyn = "Magazyn_"+today.toString()+".xlsx";

        XSSFWorkbook wb = null;
        try{
          wb = stockService.getAllStock();
          response.setContentType("application/vnd.ms-excel");
          response.setHeader("Content-disposition", "attachment; filename="+magazyn);
          wb.write(response.getOutputStream());
        }catch (IOException ioe) {
            throw new RuntimeException("Bład zapisu magazynu do pliku excel");
        } finally {
            System.out.println("Powodzenie zapisu magazynu do pliku");
        }

    }




}























