package pl.saz.model.komponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maciej on 15.06.18.
 */
public class KomponentRecords {

    private String _name;
    private String _description = "";
    private Integer _sortorder;
    private String _material = "";
    private Types _typ_1;
    private Types _typ_2;
    private Types _typ_3;
    private Units _units;
    private Double _weight;
    private Double _dimension_X = 0.0;
    private Double _dimension_Y = 0.0;
    private Double _dimension_Z = 0.0;
    private List<KomponentsQuantity> _childsElement;
    private Integer childSize;

    public KomponentRecords(){
        this._sortorder = 0;
        this._typ_1 = Types.SZTUKA;

    }

    public KomponentRecords(String _name, String _description, Types _typ_1, Types _typ_2, Types _typ_3, Double _weight) {
        this._name = _name;
        this._description = _description;
        this._typ_1 = _typ_1;
        this._typ_2 = _typ_2;
        this._typ_3 = _typ_3;
        this._weight = _weight;
        this._childsElement = new ArrayList<KomponentsQuantity>();
        this._sortorder = 0;
        this._units = Units.PIECE;
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

    public Integer get_sortorder() {
        return _sortorder;
    }

    public void set_sortorder(Integer _sortorder) {
        this._sortorder = _sortorder;
    }

    public String get_material() {
        return _material;
    }

    public void set_material(String _material) {
        this._material = _material;
    }

    public Types get_typ_1() {
        return _typ_1;
    }

    public void set_typ_1(Types _typ_1) {
        this._typ_1 = _typ_1;
    }

    public Types get_typ_2() {
        return _typ_2;
    }

    public void set_typ_2(Types _typ_2) {
        this._typ_2 = _typ_2;
    }

    public Types get_typ_3() {
        return _typ_3;
    }

    public void set_typ_3(Types _typ_3) {
        this._typ_3 = _typ_3;
    }

    public Units get_units() {
        return _units;
    }

    public void set_units(Units _units) {
        this._units = _units;
    }

    public Double get_weight() {
        return _weight;
    }

    public void set_weight(Double _weight) {
        this._weight = _weight;
    }

    public Double get_dimension_X() {
        return _dimension_X;
    }

    public void set_dimension_X(Double _dimension_X) {
        this._dimension_X = _dimension_X;
    }

    public Double get_dimension_Y() {
        return _dimension_Y;
    }

    public void set_dimension_Y(Double _dimension_Y) {
        this._dimension_Y = _dimension_Y;
    }

    public Double get_dimension_Z() {
        return _dimension_Z;
    }

    public void set_dimension_Z(Double _dimension_Z) {
        this._dimension_Z = _dimension_Z;
    }

    public List<KomponentsQuantity> get_childsElement() {
        return _childsElement;
    }

    public void set_childsElement(List<KomponentsQuantity> _childsElement) {
        this._childsElement = _childsElement;
    }

    public Integer getChildSize() {
        return childSize;
    }

    public void setChildSize(Integer childSize) {
        this.childSize = childSize;
    }

    public void updateChildsElement(List<KomponentModel> lista) {
        this._childsElement = new ArrayList<KomponentsQuantity>();
        Map<String,KomponentsQuantity> tmpMap = new HashMap<String,KomponentsQuantity>();
        if(lista.size() > 0){
            for (KomponentModel komponentModel : lista) {

              if(tmpMap.containsKey(komponentModel.get_name())){
                  KomponentsQuantity tmp = tmpMap.get(komponentModel.get_name());
                  tmp.set_quantity(tmp.get_quantity() + 1);
                  tmpMap.put(komponentModel.get_name(), tmp);
              }else {
                tmpMap.put(komponentModel.get_name(),new KomponentsQuantity(komponentModel.get_name(),1));
              }
            }
            tmpMap.forEach((k,v)-> this._childsElement.add(v));

        }


    }

    @Override
    public String toString() {
        return "KomponentRecords{" +
                "_name='" + _name + '\'' +
                ", _description='" + _description + '\'' +
                ", _sortorder=" + _sortorder +
                ", _material='" + _material + '\'' +
                ", _typ_1=" + _typ_1 +
                ", _typ_2=" + _typ_2 +
                ", _typ_3=" + _typ_3 +
                ", _units=" + _units +
                ", _weight=" + _weight +
                ", _dimension_X=" + _dimension_X +
                ", _dimension_Y=" + _dimension_Y +
                ", _dimension_Z=" + _dimension_Z +
                ", childSize=" + childSize +
                '}';
    }

    public class KomponentsQuantity {
        private String _name;
        private Integer _quantity;

        public KomponentsQuantity() {};

        public KomponentsQuantity(String _name, Integer _quantity) {
            this._name = _name;
            this._quantity = _quantity;
        }

        public String get_name() {
            return _name;
        }

        public void set_name(String _name) {
            this._name = _name;
        }

        public Integer get_quantity() {
            return _quantity;
        }

        public void set_quantity(Integer _quantity) {
            this._quantity = _quantity;
        }
    }
}

