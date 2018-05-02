package pl.saz.service.komponent;

import pl.saz.model.komponent.KomponentModel;

import java.util.List;

/**
 * Created by maciej on 02.05.18.
 */
public class KomponentServiceImpl implements KomponentService {
    @Override
    public KomponentModel getKomponentByName(String name) {
        return null;
    }

    @Override
    public List<KomponentModel> getAllKomponents() {
        return null;
    }

    @Override
    public KomponentModel saveKomponent(KomponentModel komponentModel) {
        return null;
    }

    @Override
    public KomponentModel updateKomponent(KomponentModel komponentModel) {
        return null;
    }

    @Override
    public boolean deleteKomponent(String kompoenentName) {
        return false;
    }

    @Override
    public boolean deleteKomponentChild(String childName) {
        return false;
    }

    @Override
    public void saveTest() {

    }
}
