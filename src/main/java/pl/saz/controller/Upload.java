package pl.saz.controller;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by maciej on 06.05.18.
 */
@Controller
public class Upload {


    //http://www.mkyong.com/spring-boot/spring-boot-file-upload-example-ajax-and-rest/
    @PostMapping(value = "/api/upload")
    @ResponseBody
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile uploadfile) throws IOException {

        if(uploadfile.isEmpty()){
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }

        try {
            OPCPackage pkg = OPCPackage.open(uploadfile.getInputStream());
            XSSFWorkbook workbook = new XSSFWorkbook(pkg);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();

            System.out.println(datatypeSheet.getSheetName());




        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }


        return new ResponseEntity("Successfully uploaded - " +
                uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
    }
}






























