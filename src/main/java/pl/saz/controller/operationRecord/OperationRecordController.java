package pl.saz.controller.operationRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.saz.model.stock.StockList;
import pl.saz.service.operationRecord.OperationRecordService;

import java.util.List;

/**
 * Created by maciej on 01.05.18.
 */
@RestController
@RequestMapping(value = "/records")
public class OperationRecordController {

    @Autowired
    private OperationRecordService recordService;

    @RequestMapping(value = "/getByInterval/{start}/{end}", method = RequestMethod.GET)
    public ResponseEntity<List<StockList>> getAllKomponents(
            @PathVariable String start,@PathVariable String end)
    {
         List<StockList> rekords = recordService.getByDate(start, end);

        return  new ResponseEntity<List<StockList>>(rekords, HttpStatus.OK);
    }

}
