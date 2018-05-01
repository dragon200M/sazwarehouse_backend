package pl.saz.model.komponent;

/**
 * Created by maciej on 01.05.18.
 */
public class KomponentModel {
    private Long _id;
    private String _name;
    private String _description;
    private Types _typ_1;
    private Types _typ_2;
    private Types _typ_3;
    private Double _weight;
    private KomponentModel _childsElement;

    public KomponentModel(){}

    public KomponentModel(String _name, String _description, Types _typ_1, Types _typ_2, Types _typ_3, Double _weight, KomponentModel _childsElement) {
        this._name = _name;
        this._description = _description;
        this._typ_1 = _typ_1;
        this._typ_2 = _typ_2;
        this._typ_3 = _typ_3;
        this._weight = _weight;
        this._childsElement = _childsElement;
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

    public KomponentModel get_childsElement() {
        return _childsElement;
    }

    public void set_childsElement(KomponentModel _childsElement) {
        this._childsElement = _childsElement;
    }
}
