package pl.saz.model.stock;

import pl.saz.model.komponent.KomponentModel;
import pl.saz.model.warehouse.WarehouseModel;

/**
 * Created by maciej on 01.05.18.
 */
public class StockSummary {
    //Podsumowanie ilosc komponentow w danym magazynie oraz ilosc wszystkich komponentows
    WarehouseModel _warehouse;
    KomponentModel _komponent;
    Double _stock;

    public StockSummary(WarehouseModel _warehouse, KomponentModel _komponent, Double _stock) {
        this._warehouse = _warehouse;
        this._komponent = _komponent;
        this._stock = _stock;
    }


    public StockSummary(KomponentModel _komponent, Double _stock) {
        this._komponent = _komponent;
        this._stock = _stock;
    }

    public WarehouseModel get_warehouse() {
        return _warehouse;
    }

    public void set_warehouse(WarehouseModel _warehouse) {
        this._warehouse = _warehouse;
    }

    public KomponentModel get_komponent() {
        return _komponent;
    }

    public void set_komponent(KomponentModel _komponent) {
        this._komponent = _komponent;
    }

    public Double get_stock() {
        return _stock;
    }

    public void set_stock(Double _stock) {
        this._stock = _stock;
    }
}
