package pl.saz.model.komponent;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

/**
 * Created by maciej on 01.05.18.
 */
@Entity
@Table(name = "Components")
public class KomponentModel {

    @Id
    @Column(name = "Name",nullable = false)
    private String _name;
    @Column(name = "Description",length = 400)
    private String _description = "";
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

    public KomponentModel(){}
    //TODO dodać to typ sztuka brak możlwiości dodawanie dzieci
    public KomponentModel(String _name, String _description, Types _typ_1, Types _typ_2, Types _typ_3, Double _weight) {
        this._name = _name;
        this._description = _description;
        this._typ_1 = _typ_1;
        this._typ_2 = _typ_2;
        this._typ_3 = _typ_3;
        this._weight = _weight;
        this._childsElement = new ArrayList<>();
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

    public Double get_weight() {
        return _weight;
    }

    public void set_weight(Double _weight) {
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

    public String get_material() {
        return _material;
    }

    public void set_material(String _material) {
        this._material = _material;
    }

    public void add_Child(KomponentModel child, int ilosc){
        if(ilosc<0){ilosc = 0;};
        if(this.get_typ_1() != Types.PUSTY && this.get_typ_1() != Types.SZTUKA) {
            if (this._childsElement.size() > 0) {
                this._childsElement.removeIf(c -> c.get_name().equals(child.get_name()));
            }
            for (int i = 0; i < ilosc; i++) {
                this._childsElement.add(child);
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
                            sum.put(w.get_name(),sum.get(w.get_name())+1);
                        }else {
                            sum.put(w.get_name(),1);
                        }
                    }else if(w.get_typ_1() != Types.SZTUKA && w.get_typ_1() != Types.PUSTY)
                    {
                        recursion(w);
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


        public void recursion(KomponentModel c) {
            for (KomponentModel k:
                 c.get_childsElement()) {
                if(c.get_typ_1() == Types.SZTUKA) {
                    System.out.println("sztuka: "+k.get_name());
                    return;
                }else{
                    if(sum.containsKey(k.get_name())){
                        sum.put(k.get_name(),sum.get(k.get_name())+1);
                    }else {
                        sum.put(k.get_name(),1);
                    }
                    recursion(k);
                }
            }
        }

        public List<Calculation> get_resoult() {
            return _resoult;
        }

        public void set_resoult(List<Calculation> _resoult) {
            this._resoult = _resoult;
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
