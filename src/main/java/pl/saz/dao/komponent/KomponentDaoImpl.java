package pl.saz.dao.komponent;

import pl.saz.model.komponent.KomponentModel;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by maciej on 01.05.18.
 */
public class KomponentDaoImpl implements KomponentDao {
    @PersistenceContext
    private EntityManager manager;


    @Override
    public KomponentModel getKomponentByName(String name) {
        KomponentModel or = null;
        try {
            or = manager.createQuery("SELECT w FROM KomponentModel w where w._name = :name", KomponentModel.class)
                    .setParameter("name",name)
                    .getSingleResult();
        }catch (NoResultException e){
            System.out.println("Brak wynikow");
        }

        return or;
    }

    @Override
    public List<KomponentModel> getAllKomponents() {
        List<KomponentModel> or = null;
        try {
            or = manager.createNativeQuery("SELECT w FROM KomponentModel w", KomponentModel.class).getResultList();
        }catch (NoResultException e){
            System.out.println("Brak wynikow");
        }

        return or;
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
}
