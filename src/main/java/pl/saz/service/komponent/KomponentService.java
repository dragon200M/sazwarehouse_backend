package pl.saz.service.komponent;

import pl.saz.model.komponent.KomponentModel;

import java.util.List;

/**
 * Created by maciej on 01.05.18.
 */
public interface KomponentService {
    KomponentModel getKomponentByName(String name);
    List<KomponentModel> getAllKomponents();
    List<KomponentModel> getMainKomponents();
    List<KomponentModel> getParentsOfChild(String name);
    String getStockInfo(String name);
    boolean saveKomponent(KomponentModel komponentModel);
    KomponentModel updateKomponent(KomponentModel komponentModel);
    void deleteKomponent(KomponentModel kompoenentName);
    void deleteKomponentChild(KomponentModel kompoenentName,KomponentModel childName);
    boolean addChildToParent(String kompoenentName,String childName,int ilosc);
    void saveTest();
    void saveTest2();
    void findAllParents(String kompoenentName);
    void printAllParents(String kompoenentName);
    List<String> getParents(String kompoenentName);
}
