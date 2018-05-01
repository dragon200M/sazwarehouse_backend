package pl.saz.model.operationRecord;

import java.time.LocalDateTime;

/**
 * Created by maciej on 01.05.18.
 */
public class OperationRecords {
    private Long _id;
    private LocalDateTime _operationDate;
    private OperationTypes _type;
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
