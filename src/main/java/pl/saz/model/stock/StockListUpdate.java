package pl.saz.model.stock;

import pl.saz.dao.stock.StockOperation;

/**
 * Created by maciej on 12.05.18.
 */
public class StockListUpdate {
    private String warehouseName;
    private String komponentName;
    private Double oldStock;
    private Double newStock;
    private StockOperation type;


    public StockListUpdate(){}
    public StockListUpdate(String warehouseName, String komponentName, Double newStock, StockOperation type) {
        this.warehouseName = warehouseName;
        this.komponentName = komponentName;
        this.newStock = newStock;
        this.type = type;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getKomponentName() {
        return komponentName;
    }

    public void setKomponentName(String komponentName) {
        this.komponentName = komponentName;
    }

    public Double getNewStock() {
        return newStock;
    }

    public void setNewStock(Double newStock) {
        this.newStock = newStock;
    }

    public StockOperation getType() {
        return type;
    }

    public void setType(StockOperation type) {
        this.type = type;
    }

    public Double getOldStock() {
        return oldStock;
    }

    public void setOldStock(Double oldStock) {
        this.oldStock = oldStock;
    }

    @Override
    public String toString() {
        return "StockListUpdate{" +
                "warehouseName='" + warehouseName + '\'' +
                ", komponentName='" + komponentName + '\'' +
                ", newStock=" + newStock +
                ", type=" + type +
                '}';
    }
}
