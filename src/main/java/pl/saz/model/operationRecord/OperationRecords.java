package pl.saz.model.operationRecord;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by maciej on 01.05.18.
 */
@Entity
@Table(name = "Records")
public class OperationRecords {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long _id;

    @Column(name = "OperationDate")
    private LocalDateTime _operationDate;


    @Column(name = "Type",nullable = false )
    @Enumerated(EnumType.ORDINAL)
    private OperationTypes _type;

    @Column(name = "Description",length = 50000)
    private String _objectDescription;

    public OperationRecords() {}

    public OperationRecords(OperationTypes _type, String _objectDescription) {
        this._type = _type;
        this._objectDescription = _objectDescription;
        this._operationDate = LocalDateTime.now();
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public OperationTypes get_type() {
        return _type;
    }

    public void set_type(OperationTypes _type) {
        this._type = _type;
    }

    public String get_objectDescription() {
        return _objectDescription;
    }

    public void set_objectDescription(String _objectDescription) {
        this._objectDescription = _objectDescription;
    }
 //TODO nalezy dodać zapis do JSON-a
}
