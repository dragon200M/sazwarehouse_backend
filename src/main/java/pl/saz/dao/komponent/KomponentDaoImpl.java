package pl.saz.dao.komponent;

import org.springframework.stereotype.Repository;
import pl.saz.model.komponent.KomponentModel;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maciej on 01.05.18.
 */
@Repository
@Transactional
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
            or = manager.createNativeQuery("SELECT w.* FROM COMPONENTS w", KomponentModel.class).getResultList();
        }catch (NoResultException e){
            System.out.println("Brak wynikow");
        }

        return or;
    }



    @Override
    public boolean saveKomponent(KomponentModel komponentModel) {
       KomponentModel tmp = getKomponentByName(komponentModel.get_name());
       if(null == tmp){
           manager.persist(komponentModel);
           return true;
       }
       return false;
    }

    @Override
    public KomponentModel updateKomponent(KomponentModel komponentModel) {
        KomponentModel t = getKomponentByName(komponentModel.get_name());

        List<KomponentModel> tmpChild = t.get_childsElement();

        komponentModel.set_childsElement(tmpChild);


        return manager.merge(komponentModel);
    }

    @Override
    public void deleteKomponent(KomponentModel kompoenentName) {
       manager.remove(kompoenentName);
    }

    @Override
    public void deleteKomponentChild(KomponentModel parent, KomponentModel child) {
       KomponentModel k = getKomponentByName(parent.get_name());
       if(null != k && !parent.equals(child)){
           if(k.get_childsElement().size() > 0) {
               List<KomponentModel> ktmp = k.get_childsElement();
               ktmp.removeIf(w -> w.get_name().equals(child.get_name()));
               k.set_childsElement(ktmp);
               updateKomponent(k);
           }
       }
    }

    @Override
    public List<KomponentModel> getParentsOfChild(String name) {
        List<KomponentModel> or = null;
        try {
            or = manager.createNativeQuery("SELECT distinct c.* FROM COMPONENTS__CHILDS_ELEMENT  h\n" +
                    "        inner join Components c on h.Komponent_Model_Name = c.Name\n" +
                    "        where _Childs_Element_name = :name", KomponentModel.class)
                    .setParameter("name",name)
                    .getResultList();
        }catch (NoResultException e){
            System.out.println("Brak wynikow");
        }

        return or;

    }

    @Override
    public String getStockInfo(String name) {
        String info = null;

        try {
            info = (String) manager.createNativeQuery("SELECT s.COMPONENTPK FROM STOCK s WHERE COMPONENTPK = :komp")
                    .setParameter("komp",name)
                    .getSingleResult();
        }catch (NoResultException e){
            info = null;
            System.out.println("Brak wynikow");
        }


        return info;
    }
}









