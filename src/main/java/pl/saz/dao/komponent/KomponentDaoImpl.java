package pl.saz.dao.komponent;

import org.springframework.stereotype.Repository;
import pl.saz.model.komponent.KomponentModel;
import pl.saz.model.komponent.KomponentsQuantity;
import pl.saz.model.komponent.Types;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

        t.set_name(komponentModel.get_name());
        t.set_description(komponentModel.get_description());
        t.set_weight(komponentModel.get_weight());
        t.set_sortorder(komponentModel.get_sortorder());
        t.set_material(komponentModel.get_material());
        t.set_typ_1(komponentModel.get_typ_1());
        t.set_typ_2(Types.PUSTY);
        t.set_typ_3(Types.PUSTY);
        t.set_units(komponentModel.get_units());
        t.set_dimension_X(komponentModel.get_dimension_X());
        t.set_dimension_Y(komponentModel.get_dimension_Y());
        t.set_dimension_Z(komponentModel.get_dimension_Z());
        t.setChildSize(t.get_childsElement().size());

        return manager.merge(t);
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
               Set<KomponentsQuantity> qtmp = k.getKomponentQuntity();
               ktmp.removeIf(w -> w.get_name().equals(child.get_name()));
               qtmp.removeIf(s -> s.getKomponentName().equals(child.get_name()));
               k.set_childsElement(ktmp);
               k.setKomponentQuntity(qtmp);

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

    @Override
    public List<KomponentModel> getKomponentsWihoutStock() {
        List<KomponentModel> or = new ArrayList<KomponentModel>();
        try {
            or = manager.createNativeQuery("SELECT distinct c.* FROM STOCK s " +
                    "right join COMPONENTS  c on s.COMPONENTPK =c.name " +
                    "where s.COMPONENTPK is null", KomponentModel.class)
                    .getResultList();
        }catch (NoResultException e){
            System.out.println("Brak wynikow");
        }

        return or;
    }
}









