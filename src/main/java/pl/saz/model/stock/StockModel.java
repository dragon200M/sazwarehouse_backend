package pl.saz.model.stock;

import pl.saz.model.komponent.KomponentModel;
import pl.saz.model.warehouse.WarehouseModel;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by maciej on 01.05.18.
 */
@Entity
@Table(name = "STOCK")
public class StockModel implements Serializable{

    @EmbeddedId
    private StockModelPK _id;

    @OneToOne
    private WarehouseModel warehouse;

    @OneToOne
    private KomponentModel component;

    @Column(name = "Stock")
    private Double _stock;


    public StockModel(){}

    public StockModel(Double _stock) {

        this._stock = _stock;
    }

    public StockModel(WarehouseModel warehouse, KomponentModel component, Double _stock) {
        this.warehouse = warehouse;
        this.component = component;
        this._stock = _stock;
        this._id = new StockModelPK(warehouse,component);
    }

    public Double get_stock() {
        return _stock;
    }

    public void set_stock(Double _stock) {
        this._stock = _stock;
    }


    public StockModelPK get_id() {
        return _id;
    }

    public void set_id(StockModelPK _id) {
        this._id = _id;
    }

    public WarehouseModel getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseModel warehouse) {
        this.warehouse = warehouse;
    }

    public KomponentModel getComponent() {
        return component;
    }

    public void setComponent(KomponentModel component) {
        this.component = component;
    }

    @Embeddable
    public static class StockModelPK implements Serializable{
        @Column(name = "WarehousePK")
        private String _wName;
        @Column(name = "ComponentPK")
        private String _kName;

        public StockModelPK(String _wName, String _kName) {
            this._wName = _wName;
            this._kName = _kName;
        }

        public StockModelPK(WarehouseModel _w, KomponentModel _k){
            this._wName = _w.get_name();
            this._kName = _k.get_name();
        }

        public StockModelPK(){}

        public String get_wName() {
            return _wName;
        }

        public void set_wName(String _wName) {
            this._wName = _wName;
        }

        public String get_kName() {
            return _kName;
        }

        public void set_kName(String _kName) {
            this._kName = _kName;
        }


    }

    public class StockModelView{
        private String wareName;
        private String komName;
        private String komDesc;
        private Double stock;

        public StockModelView(){
            this.wareName = StockModel.this.getWarehouse().get_name();
            this.komName = StockModel.this.getComponent().get_name();
            this.komDesc = StockModel.this.getComponent().get_description();
            this.stock = StockModel.this.get_stock();
        }

        public StockModelView(String wareName, String komName, String komDesc, Double stock) {
            this.wareName = wareName;
            this.komName = komName;
            this.komDesc = komDesc;
            this.stock = stock;
        }

        public String getWareName() {
            return wareName;
        }

        public void setWareName(String wareName) {
            this.wareName = wareName;
        }

        public String getKomName() {
            return komName;
        }

        public void setKomName(String komName) {
            this.komName = komName;
        }

        public String getKomDesc() {
            return komDesc;
        }

        public void setKomDesc(String komDesc) {
            this.komDesc = komDesc;
        }

        public Double getStock() {
            return stock;
        }

        public void setStock(Double stock) {
            this.stock = stock;
        }
    }
}
