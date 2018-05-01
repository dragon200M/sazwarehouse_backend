package pl.saz.dao.operationRecord;


import pl.saz.model.operationRecord.OperationRecords;
import pl.saz.model.operationRecord.OperationSummary;
import pl.saz.model.operationRecord.OperationTypes;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by maciej on 01.05.18.
 */
public interface OperationRecordsDao {
    OperationRecords saveRecords(OperationRecords records);
    List<OperationRecords> getAll();
    String getByType(OperationTypes type);
    OperationRecords getByDate(LocalDateTime date);
    OperationSummary getOperationSummaryByType(OperationTypes type, LocalDateTime startDate, LocalDateTime endDate);
    OperationSummary getOperationSummaryBySubjectName(String subjectName, LocalDateTime startDate, LocalDateTime endDate);
    List<OperationSummary> getOperationSummary(LocalDateTime startDate,LocalDateTime endDate);
}
