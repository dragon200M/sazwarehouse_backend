package pl.saz.model.warehouse;

/**
 * Created by maciej on 01.05.18.
 */
public class WarehouseModel {
    private Long _id;
    private String _name;
    private String _description;
    private boolean _available;
    private String _visibleName;

    public WarehouseModel(){}

    public WarehouseModel(String _name, String _description, boolean _available, String _visibleName) {
        this._name = _name;
        this._description = _description;
        this._available = _available;
        this._visibleName = _visibleName;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public boolean is_available() {
        return _available;
    }

    public void set_available(boolean _available) {
        this._available = _available;
    }

    public String get_visibleName() {
        return _visibleName;
    }

    public void set_visibleName(String _visibleName) {
        this._visibleName = _visibleName;
    }
}
