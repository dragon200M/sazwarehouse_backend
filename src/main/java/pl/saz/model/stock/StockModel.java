package pl.saz.model.stock;

import pl.saz.model.komponent.KomponentModel;
import pl.saz.model.komponent.Types;
import pl.saz.model.komponent.Units;
import pl.saz.model.warehouse.WarehouseModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public String toString() {
        return "StockModel{" +
                "_id=" + _id.get_kName()+"|"+ _id.get_wName() +
                ", warehouse=" + warehouse.get_name() +
                ", component=" + component.get_name() +
                ", _stock=" + _stock +
                '}';
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


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof StockModelPK)) return false;

            StockModelPK that = (StockModelPK) o;

            if (_wName != null ? !_wName.equals(that._wName) : that._wName != null) return false;
            return _kName != null ? _kName.equals(that._kName) : that._kName == null;
        }

        @Override
        public int hashCode() {
            int result = _wName != null ? _wName.hashCode() : 0;
            result = 31 * result + (_kName != null ? _kName.hashCode() : 0);
            return result;
        }
    }

    public class StockModelView{
        private String wareName;
        private String komName;
        private String komDesc;
        private Types types;
        private Units units;
        private Double stock;
        private List<PosibleToDo> posible = new ArrayList<PosibleToDo>();

        public StockModelView(){
            this.wareName = StockModel.this.getWarehouse().get_name();
            this.komName = StockModel.this.getComponent().get_name();
            this.komDesc = StockModel.this.getComponent().get_description();
            this.stock = StockModel.this.get_stock();
            this.types = StockModel.this.getComponent().get_typ_1();
            this.units = StockModel.this.getComponent().get_units();

            if(StockModel.this.getComponent().get_typ_1() == Types.TASMA) {
                KomponentModel tmp = StockModel.this.getComponent();
                tmp.get_childsElement().forEach( w -> {
                    if(w.get_weight()>0) {

                        posible.add(new PosibleToDo(w.get_name(), new Double(StockModel.this.get_stock() / w.get_weight()).intValue()));
                    }
                });
            }
        }

        public StockModelView(String wareName, String komName, String komDesc, Double stock) {
            this.wareName = wareName;
            this.komName = komName;
            this.komDesc = komDesc;
            this.stock = stock;
            if(StockModel.this.getComponent().get_typ_1() == Types.TASMA) {
                KomponentModel tmp = StockModel.this.getComponent();
                tmp.get_childsElement().forEach( w -> {
                    if(w.get_weight()>0) {
                        posible.add(new PosibleToDo(w.get_name(), new Double(StockModel.this.get_stock() / w.get_weight()).intValue()));
                    }
                });
            }
            this.types = StockModel.this.getComponent().get_typ_1();
            this.units = StockModel.this.getComponent().get_units();
        }

        public Types getTypes() {
            return types;
        }

        public void setTypes(Types types) {
            this.types = types;
        }

        public Units getUnits() {
            return units;
        }

        public void setUnits(Units units) {
            this.units = units;
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

        public List<PosibleToDo> getPosible() {

            return posible;
        }

        public void setPosible(List<PosibleToDo> posible) {
            this.posible = posible;
        }

    }

    public class PosibleToDo{
        private String komponentName;
        private Integer posible;

        public PosibleToDo(){};

        public PosibleToDo(String komponentName, Integer posible) {
            this.komponentName = komponentName;
            this.posible = posible;
        }

        public String getKomponentName() {
            return komponentName;
        }

        public void setKomponentName(String komponentName) {
            this.komponentName = komponentName;
        }

        public Integer getPosible() {
            return posible;
        }

        public void setPosible(Integer posible) {
            this.posible = posible;
        }

        @Override
        public String toString() {
            return "PosibleToDo{" +
                    "komponentName='" + komponentName + '\'' +
                    ", posible=" + posible +
                    '}';
        }
    }

}
