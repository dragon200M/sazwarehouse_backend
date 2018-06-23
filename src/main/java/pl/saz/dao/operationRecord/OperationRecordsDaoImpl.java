package pl.saz.dao.operationRecord;

import org.springframework.stereotype.Repository;
import pl.saz.model.operationRecord.OperationRecords;
import pl.saz.model.operationRecord.OperationSummary;
import pl.saz.model.operationRecord.OperationTypes;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by maciej on 01.05.18.
 */
@Repository
@Transactional
public class OperationRecordsDaoImpl implements OperationRecordsDao {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void saveRecords(OperationRecords records) {

        manager.persist(records);
    }

    @Override
    public List<OperationRecords> getAll() {

        List<OperationRecords> or = null;
        try {
            or = manager.createNativeQuery("SELECT w FROM OperationRecords w", OperationRecords.class).getResultList();
        }catch (NoResultException e){
            System.out.println("Brak wynikow");
        }

        return or;
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
    public List<OperationRecords> getByDate(LocalDateTime startDate, LocalDateTime endDate) {

        List<OperationRecords> or = null;
        try {
            or = manager.createNativeQuery("SELECT w.* FROM RECORDS w WHERE " +
                    "OPERATION_DATE >= :start and OPERATION_DATE < :end", OperationRecords.class)
                    .setParameter("start", startDate)
                    .setParameter("end", endDate)
                    .getResultList();
        }catch (NoResultException e){
            System.out.println("Brak wynikow");
        }
        return or;
    }
}
