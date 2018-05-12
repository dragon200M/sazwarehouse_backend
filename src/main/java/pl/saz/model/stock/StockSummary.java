package pl.saz.model.stock;

import pl.saz.model.komponent.KomponentModel;

/**
 * Created by maciej on 01.05.18.
 */
public class StockSummary {
    //Podsumowanie ilosc komponentow w danym magazynie oraz ilosc wszystkich komponentows
    private String _warehouse;
    private String _komponent;
    private KomponentModel _komponentModel;
    private Double _stock;

    public StockSummary() {}

    public StockSummary(String _warehouse, String _komponent, Double _stock) {
        this._warehouse = _warehouse;
        this._komponent = _komponent;
        this._stock = _stock;
    }
    public StockSummary(String _warehouse, KomponentModel _komponentModel, Double _stock) {
        this._warehouse = _warehouse;
        this._komponentModel= _komponentModel;
        this._komponent = _komponentModel.get_name();
        this._stock = _stock;
    }


    public String get_warehouse() {
        return _warehouse;
    }

    public void set_warehouse(String _warehouse) {
        this._warehouse = _warehouse;
    }

    public String get_komponent() {
        return _komponent;
    }

    public void set_komponent(String _komponent) {
        this._komponent = _komponent;
    }

    public Double get_stock() {
        return _stock;
    }

    public void set_stock(Double _stock) {
        this._stock = _stock;
    }

    public KomponentModel get_komponentModel() {
        return _komponentModel;
    }

    public void set_komponentModel(KomponentModel _komponentModel) {
        this._komponentModel = _komponentModel;
    }
}
