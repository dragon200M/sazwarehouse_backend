package pl.saz.model.komponent;

import javax.persistence.*;
import java.util.List;

/**
 * Created by maciej on 01.05.18.
 */
@Entity
@Table(name = "Components")
public class KomponentModel {

    @Id
    @Column(name = "Name",nullable = false)
    private String _name;
    @Column(name = "Description")
    private String _description;
    //Pattern: 65x1|65x0.8
    @Column(name = "Material")
    private String _material;
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
    private Double _dimension_X;
    @Column(name = "DimensionY")
    private Double _dimension_Y;
    @Column(name = "DimensionZ")
    private Double _dimension_Z;
    @Column(name = "Childs")
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<KomponentModel> _childsElement;

    public KomponentModel(){}

    public KomponentModel(String _name, String _description, Types _typ_1, Types _typ_2, Types _typ_3, Double _weight) {
        this._name = _name;
        this._description = _description;
        this._typ_1 = _typ_1;
        this._typ_2 = _typ_2;
        this._typ_3 = _typ_3;
        this._weight = _weight;
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
}
