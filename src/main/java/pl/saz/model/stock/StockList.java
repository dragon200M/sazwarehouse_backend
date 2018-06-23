package pl.saz.model.stock;

import pl.saz.dao.stock.StockOperation;

import java.time.LocalDateTime;

/**
 * Created by maciej on 11.06.18.
 */
public class StockList extends StockListUpdate {

    private LocalDateTime time;

    public StockList() {};

    public StockList(LocalDateTime operationDate,String warehouseName, String komponentName, Double newStock, StockOperation type) {
        super(warehouseName,komponentName,newStock,type);
        this.time = operationDate;
    };

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }


}
