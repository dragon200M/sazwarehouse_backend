package pl.saz.model.stock;

/**
 * Created by maciej on 13.05.18.
 */
public class MaxToDo {
    private String komponentModel;
    private Integer resoult;

    public MaxToDo(){}
    public MaxToDo(String komponentModel, Integer resoult) {
        this.komponentModel = komponentModel;
        this.resoult = resoult;
    }

    public String getKomponentModel() {
        return komponentModel;
    }

    public void setKomponentModel(String komponentModel) {
        this.komponentModel = komponentModel;
    }

    public Integer getResoult() {
        return resoult;
    }

    public void setResoult(Integer resoult) {
        this.resoult = resoult;
    }

    @Override
    public String toString() {
        return "MaxToDo{" +
                "komponentModel='" + komponentModel + '\'' +
                ", resoult=" + resoult +
                '}';
    }
}
