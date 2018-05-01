package pl.saz.dao.operationRecord;

import pl.saz.model.operationRecord.OperationRecords;
import pl.saz.model.operationRecord.OperationSummary;
import pl.saz.model.operationRecord.OperationTypes;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by maciej on 01.05.18.
 */
public class OperationRecordsDaoImpl implements OperationRecordsDao {
    @Override
    public OperationRecords saveRecords(OperationRecords records) {
        return null;
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
}
