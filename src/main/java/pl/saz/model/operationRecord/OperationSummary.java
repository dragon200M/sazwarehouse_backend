package pl.saz.model.operationRecord;

import java.time.LocalDateTime;

/**
 * Created by maciej on 01.05.18.
 */
public class OperationSummary {

    private LocalDateTime _startDate;
    private LocalDateTime _endDate;
    private OperationTypes _type;
    private String _subject;
    private Double _quantity;

    public OperationSummary(LocalDateTime _startDate, LocalDateTime _endDate, OperationTypes _type, String _subject, Double _quantity) {
        this._startDate = _startDate;
        this._endDate = _endDate;
        this._type = _type;
        this._subject = _subject;
        this._quantity = _quantity;
    }

    public LocalDateTime get_startDate() {
        return _startDate;
    }

    public void set_startDate(LocalDateTime _startDate) {
        this._startDate = _startDate;
    }

    public LocalDateTime get_endDate() {
        return _endDate;
    }

    public void set_endDate(LocalDateTime _endDate) {
        this._endDate = _endDate;
    }

    public OperationTypes get_type() {
        return _type;
    }

    public void set_type(OperationTypes _type) {
        this._type = _type;
    }

    public String get_subject() {
        return _subject;
    }

    public void set_subject(String _subject) {
        this._subject = _subject;
    }

    public Double get_quantity() {
        return _quantity;
    }

    public void set_quantity(Double _quantity) {
        this._quantity = _quantity;
    }
}
