package pl.saz.service.operationRecord;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.saz.dao.operationRecord.OperationRecordsDao;
import pl.saz.dao.stock.StockOperation;
import pl.saz.model.operationRecord.OperationRecords;
import pl.saz.model.operationRecord.OperationSummary;
import pl.saz.model.operationRecord.OperationTypes;
import pl.saz.model.stock.StockList;
import pl.saz.model.stock.StockListUpdate;
import pl.saz.model.stock.StockModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by maciej on 02.05.18.
 */
@Service
public class OperationRecordServiceImpl implements OperationRecordService{


    @Autowired
    private OperationRecordsDao recordsDao;

    @Override
    public void saveRecords(OperationRecords records) {

        recordsDao.saveRecords(records);
    }

    @Override
    public List<OperationRecords> getAll() {

        return null;
    }

    @Override
    public String getByType(OperationTypes type) {
        return null;
    }

    @Override
    public OperationRecords getByDate(LocalDateTime date) {
        return null;
    }

    @Override
    public OperationSummary getOperationSummaryByType(OperationTypes type, LocalDateTime startDate, LocalDateTime endDate) {
        return null;
    }

    @Override
    public OperationSummary getOperationSummaryBySubjectName(String subjectName, LocalDateTime startDate, LocalDateTime endDate) {
        return null;
    }

    @Override
    public List<OperationSummary> getOperationSummary(LocalDateTime startDate, LocalDateTime endDate) {
        return null;
    }

    @Override
    public OperationRecords getByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return null;
    }

    @Override
    public List<StockList> getByDate(String startDate, String endDate) {
        String time = "0000";
        String s = startDate+time;
        String e = endDate+time;

        String pattern = "yyyyMMddHHmm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime start = LocalDateTime.parse(s, formatter);
        LocalDateTime end = LocalDateTime.parse(e, formatter);

         List<StockList> sl = new ArrayList<>();
         List<OperationRecords> result = recordsDao.getByDate(start, end);
        if(null != result) {
            Gson gson = new Gson();
            List<OperationRecords> tmp1 = result.stream().filter(s1 -> s1.get_className().equals("StockListUpdate")).collect(Collectors.toList());
            List<OperationRecords> tmp2 = result.stream().filter(s1 -> s1.get_className().equals("StockModel")).collect(Collectors.toList());

            if(tmp1.size() > 0) {
                for (OperationRecords or : tmp1) {
                    LocalDateTime operationDate = or.get_operationDate();
                    StockListUpdate[] slu = gson.fromJson(new String(or.getInBinary()),
                            StockListUpdate[].class);
                    for (StockListUpdate sl1 : slu) {

                        StockList slTmp = new StockList(operationDate,
                                sl1.getWarehouseName(),
                                sl1.getKomponentName(),
                                sl1.getNewStock(),
                                sl1.getType());
                        slTmp.setOldStock(0.0);

                        sl.add(slTmp);
                    }
                }
            }
            if(tmp2.size() > 0){
                for (OperationRecords or : tmp2) {
                    LocalDateTime operationDate = or.get_operationDate();
                    StockModel slu = gson.fromJson(new String(or.getInBinary()), StockModel.class);
                    StockListUpdate stTmp = new StockListUpdate(slu.getWarehouse().get_name(), slu.getComponent().get_name()
                            , slu.get_stock(), StockOperation.INIT);
                    stTmp.setOldStock(0.0);

                    StockList slTmp = new StockList(operationDate,
                            stTmp.getWarehouseName(),
                            stTmp.getKomponentName(),
                            stTmp.getNewStock(),
                            stTmp.getType());
                    slTmp.setOldStock(0.0);

                    sl.add(slTmp);

                }
            }

        }

        return sl;
    }
}
