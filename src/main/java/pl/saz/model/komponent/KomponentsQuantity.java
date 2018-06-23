package pl.saz.model.komponent;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created by maciej on 19.06.18.
 */

@Embeddable
public class KomponentsQuantity {


    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private String komponentName;



    public KomponentsQuantity(Integer quantity, String komponentName) {
        this.quantity = quantity;
        this.komponentName = komponentName;
    }

    private KomponentsQuantity(){}

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getKomponentName() {
        return komponentName;
    }

    public void setKomponentName(String komponentName) {
        this.komponentName = komponentName;
    }

    @Override
    public String toString() {
        return "KomponentsQuantity{" +
                "quantity=" + quantity +
                ", komponentName='" + komponentName + '\'' +
                '}';
    }
}
