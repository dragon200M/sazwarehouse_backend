package pl.saz.model.warehouse;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by maciej on 01.05.18.
 */
@Entity
@Table(name="Warehouses")
public class WarehouseModel implements Serializable{
    @Id
    @Column(name = "Name", nullable = false)
    private String _name;

    @Column(name = "Description")
    private String _description;

    @Column(name = "Available")
    private boolean _available;

    @Column(name = "VisibleName")
    private String _visibleName;

    public WarehouseModel(){}

    public WarehouseModel(String _name, String _description, boolean _available, String _visibleName) {
        this._name = _name;
        this._description = _description;
        this._available = _available;
        this._visibleName = _visibleName;
    }


    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {

        if(null == _name){
            _name ="";
        }
        this._name = _name;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {

        if(null == _description){
            _description ="";
        }
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
