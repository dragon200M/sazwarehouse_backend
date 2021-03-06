package pl.saz.model.komponent;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Created by maciej on 01.05.18.
 */
@Entity
@Table(name = "Components")
public class KomponentModel implements Serializable {

    @Id
    @Column(name = "Name",nullable = false)
    private String _name;
    @Column(name = "Description",length = 400)
    private String _description = "";
    @Column(name = "SortOrder")
    private Integer _sortorder;
    //Pattern: 65x1|65x0.8
    @Column(name = "Material",length = 400)
    private String _material = "";
    @Column(name = "Typ1")
    @Enumerated(EnumType.ORDINAL)
    private Types _typ_1;
    @Column(name = "Typ2")
    @Enumerated(EnumType.ORDINAL)
    private Types _typ_2;
    @Column(name = "Typ3")
    @Enumerated(EnumType.ORDINAL)
    private Types _typ_3;
    @Column(name = "Units")
    @Enumerated(EnumType.ORDINAL)
    private Units _units;
    @Column(name = "Weight")
    private Double _weight;
    @Column(name = "DimensionX")
    private Double _dimension_X = 0.0;
    @Column(name = "DimensionY")
    private Double _dimension_Y = 0.0;
    @Column(name = "DimensionZ")
    private Double _dimension_Z = 0.0;

    @Column(name = "Childs")
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<KomponentModel> _childsElement;
    @JsonIgnore
    private Integer childSize;

    @JsonIgnore
    @ElementCollection
    @CollectionTable(name = "ComponentQuantity")
    private Set<KomponentsQuantity> komponentQuntity = new HashSet<KomponentsQuantity>();



    public KomponentModel(){
        this._sortorder = 0;
        this._typ_1 = Types.SZTUKA;
        set_units(Units.PIECE);
        if(null == get_childsElement()) {
            this.childSize = 0;
        } else {
            this.childSize = this._childsElement.size();
        }

    }
    //TODO dodać to typ sztuka brak możlwiości dodawanie dzieci
    public KomponentModel(String _name, String _description, Types _typ_1, Types _typ_2, Types _typ_3, Double _weight) {
        this._name = _name;
        this._description = _description;
        this._typ_1 = _typ_1;
        this._typ_2 = _typ_2;
        this._typ_3 = _typ_3;
        this._weight = _weight;
        this._childsElement = new ArrayList<>();
        this._sortorder = 0;
        this._units = Units.PIECE;
        this.childSize  = this._childsElement.size();
    }


    public KomponentModel(String _name, String _description, Types _typ_1, Types _typ_2, Types _typ_3, Units _units, Double _weight) {
        this._name = _name;
        this._description = _description;
        this._typ_1 = _typ_1;
        this._typ_2 = _typ_2;
        this._typ_3 = _typ_3;
        this._units = _units;
        this._weight = _weight;
        this._sortorder = 0;
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

    public Integer get_sortorder() {
        if(null == this._sortorder){this._sortorder = 0;};
        return _sortorder;
    }

    public void set_sortorder(Integer _sortorder) {
        if(null == this._sortorder){this._sortorder = 0;}
        this._sortorder = _sortorder;
    }
    public Integer getChildSize() {
        return childSize;
    }

    public void setChildSize(Integer childSize) {
        if(null == childSize){
            childSize = 0;
        }

        this.childSize = childSize;
    }


    public Types get_typ_1() {
        return _typ_1;
    }

    public void set_typ_1(Types _typ_1) {
        Types tmp = _typ_1;
        if(null == _typ_1){
            _typ_1 = Types.SZTUKA;
        } else {
            if(this.childSize > 0 && tmp == Types.SZTUKA) {
                _typ_1 = this._typ_1;
            }
        }
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

    public Double get_weight() {
        return _weight;
    }

    public void set_weight(Double _weight) {
        if(null == _weight){
            _weight = 0.0;
        }

        this._weight = _weight;
    }

    public List<KomponentModel> get_childsElement() {
        return _childsElement;
    }


    public void set_childsElement(List<KomponentModel> _childsElement) {


        this._childsElement = _childsElement;
    }

    public Double get_dimension_X() {
        return _dimension_X;
    }

    public void set_dimension_X(Double _dimension_X) {
        if(null == _dimension_X){
            _dimension_X = 0.0;
        }

        this._dimension_X = _dimension_X;
    }

    public Double get_dimension_Y() {

        return _dimension_Y;
    }

    public void set_dimension_Y(Double _dimension_Y) {
        if(null == _dimension_Y){
            _dimension_Y = 0.0;
        }
        this._dimension_Y = _dimension_Y;
    }

    public Double get_dimension_Z() {
        return _dimension_Z;
    }

    public void set_dimension_Z(Double _dimension_Z) {
        if(null == _dimension_Z){
            _dimension_Z = 0.0;
        }

        this._dimension_Z = _dimension_Z;
    }

    public String get_material() {
        return _material;
    }

    public void set_material(String _material) {

        if(null == _material){
            _material = "";
        }

        this._material = _material;
    }



    public Units get_units() {

        return _units;
    }
    public void set_units(Units _units) {

        if(null == this._units){
            this._units = Units.PIECE;
        }else {
            this._units = _units;
        }
    }

    public Set<KomponentsQuantity> getKomponentQuntity() {
        return komponentQuntity;
    }

    public void setKomponentQuntity(Set<KomponentsQuantity> komponentQuntity) {
        this.komponentQuntity = komponentQuntity;
    }

    @Override
    public String toString() {
        return "KomponentModel{" +
                "_name='" + _name + '\'' +
                ", _description='" + _description + '\'' +
                ", _material='" + _material + '\'' +
                ", _typ_1=" + _typ_1 +
                ", _units=" + _units +
                ", _weight=" + _weight +
                '}';
    }


    public void add_Child(KomponentModel child, int ilosc){
        if(ilosc<0){ilosc = 0;}

        if(this.get_typ_1() == Types.TASMA){
            if(ilosc > 1){ilosc = 0;}
            if(child.get_typ_1() != Types.SZTUKA){ilosc = 0;}
        };

        if(this.get_typ_1() != Types.PUSTY && this.get_typ_1() != Types.SZTUKA) {

            if (this._childsElement.size() > 0) {
                this._childsElement.removeIf(c -> c.get_name().equals(child.get_name()));
            }

            this._childsElement.add(child);

            komponentQuntity.removeIf(c -> c.getKomponentName().equals(child.get_name()));
            komponentQuntity.add(new KomponentsQuantity(ilosc, child.get_name()));



            if (this._childsElement.size() > 0) {
                setChildSize(this._childsElement.size());
            }
        }

    }



    public List<Calculation> calc(){
        Calculation c = new Calculation();
        c.calculate();
        return  c.get_resoult();
    }

    public class Calculation {

        private String _childComponentName;
        private Integer _quantity;
        @JsonIgnore
        private List<Calculation> _resoult;
        @JsonIgnore
        private Map<String,Integer> sum = new HashMap<String,Integer>();


        public Calculation(String _childComponentName, Integer _quantity) {
            this._childComponentName = _childComponentName;
            this._quantity = _quantity;
            this._resoult = new ArrayList<>();
        }
        public  Calculation() {
            this._childComponentName ="";
            this._quantity = 0;
            this._resoult = new ArrayList<>();
        }

        public String get_childComponentName() {
            return _childComponentName;
        }

        public void set_childComponentName(String _childComponentName) {
            this._childComponentName = _childComponentName;
        }

        public Integer get_quantity() {
            return _quantity;
        }

        public void set_quantity(Integer _quantity) {
            this._quantity = _quantity;
        }

        public void calculate(){

            if (KomponentModel.this.get_childsElement().size() > 0) {

                for (KomponentModel w:
                        KomponentModel.this.get_childsElement()) {

                    if(w.get_typ_1() == Types.SZTUKA){
                        if(sum.containsKey(w.get_name())){
                            KomponentModel.this.getKomponentQuntity().forEach(el -> {
                                if(el.getKomponentName().equals(w.get_name())){
                                    sum.put(w.get_name(),sum.get(w.get_name())+el.getQuantity());
                                }
                            });

                        }else {
                            KomponentModel.this.getKomponentQuntity().forEach(el -> {
                                if(el.getKomponentName().equals(w.get_name())){
                                    sum.put(w.get_name(),el.getQuantity());
                                }
                            });
                        }

                    }else if(w.get_typ_1() != Types.SZTUKA) {

                        KomponentModel.this.getKomponentQuntity().forEach(el -> {
                            if(el.getKomponentName().equals(w.get_name())){
                                recursion(w, el.getQuantity());
                            }
                        });
                    }
                }
                for(Map.Entry<String,Integer>  e:sum.entrySet()){
                    Calculation n = new Calculation();
                    n.set_childComponentName(e.getKey());
                    n.set_quantity(e.getValue());
                    this._resoult.add(n);
                }
            }
        }


        public void recursion(KomponentModel c, int ilosc) {

            for (KomponentModel k:
                    c.get_childsElement()) {
                if(k.get_typ_1() != Types.SZTUKA){
                    c.getKomponentQuntity().forEach(el ->{
                        if(el.getKomponentName().equals(k.get_name())){
                            recursion(k,el.getQuantity()*ilosc);
                        }
                    });
                } else {
                    if(sum.containsKey(k.get_name())){
                        c.getKomponentQuntity().forEach(el -> {
                            if(el.getKomponentName().equals(k.get_name())){
                                sum.put(k.get_name(),sum.get(k.get_name())+el.getQuantity()*ilosc);
                            }
                        });
                    }else {
                        c.getKomponentQuntity().forEach(el -> {
                            if(el.getKomponentName().equals(k.get_name())){
                                sum.put(k.get_name(),el.getQuantity()*ilosc);
                            }
                        });
                    }

                }
            }
        }

        public List<Calculation> get_resoult() {
            return _resoult;
        }

        public void set_resoult(List<Calculation> _resoult) {
            this._resoult = _resoult;
        }

        @Override
        public String toString() {
            return "Calculation{" +
                    "_childComponentName='" + _childComponentName + '\'' +
                    ", _quantity=" + _quantity +
                    '}';
        }
    }

    public class InfoStore{
        private Integer _quantity;
        private Integer _position;

        public InfoStore(Integer _quantity, Integer _position) {
            this._quantity = _quantity;
            this._position = _position;
        }

        public Integer get_quantity() {
            return _quantity;
        }

        public void set_quantity(Integer _quantity) {
            this._quantity = _quantity;
        }

        public Integer get_position() {
            return _position;
        }

        public void set_position(Integer _position) {
            this._position = _position;
        }

    }



}
