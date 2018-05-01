package pl.saz.model.operationRecord;

/**
 * Created by maciej on 01.05.18.
 */
public class OperationRecords {
    private Long _id;
    private OperationTypes _type;
    private String _description;

    public OperationRecords() {}

    public OperationRecords(OperationTypes _type, String _description) {
        this._type = _type;
        this._description = _description;
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

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }
}
