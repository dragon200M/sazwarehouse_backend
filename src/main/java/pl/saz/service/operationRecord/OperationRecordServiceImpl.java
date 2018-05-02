package pl.saz.service.operationRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.saz.dao.operationRecord.OperationRecordsDao;
import pl.saz.model.operationRecord.OperationRecords;
import pl.saz.model.operationRecord.OperationSummary;
import pl.saz.model.operationRecord.OperationTypes;

import java.time.LocalDateTime;
import java.util.List;

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
}
