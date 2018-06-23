package pl.saz.service.komponent;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.saz.dao.komponent.KomponentDao;
import pl.saz.model.komponent.*;
import pl.saz.model.operationRecord.OperationRecords;
import pl.saz.model.operationRecord.OperationTypes;
import pl.saz.service.operationRecord.OperationRecordService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by maciej on 02.05.18.
 */
@Service
public class KomponentServiceImpl implements KomponentService {

    @Autowired
    private KomponentDao komponentDao;

    @Autowired
    private OperationRecordService recordService;



    private List<String> wynik = new ArrayList<String>();

    @Override
    public void findAllParents(String kompoenentName) {
        List<KomponentModel> k = getParentsOfChild(kompoenentName);
        k.forEach( w -> {
            wynik.add(w.get_name());
            findAllParents(w.get_name());
        });
    }
    public void findAllParentsStart(String kompoenentName) {
        wynik.clear();
        findAllParents(kompoenentName);
    }

    @Override
    public void printAllParents(String kompoenentName) {
        findAllParentsStart(kompoenentName);

    }

    @Override
    public List<String> getParents(String kompoenentName) {
        findAllParentsStart(kompoenentName);
        return wynik;
    }

    @Override
    public KomponentModel getKomponentByName(String name) {
        return komponentDao.getKomponentByName(name);
    }

    @Override
    public List<KomponentModel> getAllKomponents() {

        return komponentDao.getAllKomponents();
    }

    @Override
    public boolean saveKomponent(KomponentModel komponentModel) {

        boolean operation  = this.komponentDao.saveKomponent(komponentModel);
        if(operation) {
            Gson gson = new Gson();
            String json = gson.toJson(setKomponentRecords(komponentModel),KomponentRecords.class);
            OperationRecords op = new OperationRecords(OperationTypes.INSERT,json,KomponentRecords.class.getSimpleName());
            recordService.saveRecords(op);

        }

        return operation;
    }

    @Override
    public KomponentModel updateKomponent(KomponentModel komponentModel) {
        KomponentModel tmp =  komponentDao.updateKomponent(komponentModel);


        if(null != tmp) {
            Gson gson = new Gson();
            String json = gson.toJson(setKomponentRecords(tmp),KomponentRecords.class);
            OperationRecords op = new OperationRecords(OperationTypes.UPDATE,json,KomponentRecords.class.getSimpleName());
            recordService.saveRecords(op);
        }

        return tmp;
    }

    @Override
    public void deleteKomponent(KomponentModel kompoenentName) {
        if(null != kompoenentName) {
            komponentDao.deleteKomponent(kompoenentName);
            Gson gson = new Gson();
            String json = gson.toJson(setKomponentRecords(kompoenentName),KomponentRecords.class);
            OperationRecords op = new OperationRecords(OperationTypes.DELETE,json,KomponentRecords.class.getSimpleName());
            recordService.saveRecords(op);
        }
    }

    @Override
    public void deleteKomponentChild(KomponentModel kompoenentName,KomponentModel childName) {
        if(null != childName) {
            komponentDao.deleteKomponentChild(kompoenentName,childName);
            Gson gson = new Gson();
            String parent = gson.toJson(setKomponentRecords(kompoenentName),KomponentRecords.class);
            OperationRecords op = new OperationRecords(OperationTypes.UPDATE,parent,KomponentRecords.class.getSimpleName());
            recordService.saveRecords(op);
        }
    }

    @Override
    public boolean addChildToParent(String kompoenentName, String childName, int ilosc) {
       KomponentModel parent = getKomponentByName(kompoenentName);
       KomponentModel child  = getKomponentByName(childName);

       findAllParentsStart(kompoenentName);



       int check = 0;
       if(null != child && null != parent){
           check = child.get_childsElement().indexOf(parent);
       }

       if(null != parent
               && null != child
               && !wynik.contains(childName)
               && ilosc > 0
               && !kompoenentName.equals(childName)
               && check == -1){


           parent.add_Child(child,ilosc);
           komponentDao.updateKomponent(parent);

           Gson gson = new Gson();
           String json = gson.toJson(setKomponentRecords(parent),KomponentRecords.class);
           OperationRecords op = new OperationRecords(OperationTypes.UPDATE,json,KomponentRecords.class.getSimpleName());
           recordService.saveRecords(op);
           return true;
       }
        wynik.clear();

       return false;
    }



    @Override
    public List<KomponentModel> getParentsOfChild(String name) {


        return komponentDao.getParentsOfChild(name);
    }

    @Override
    public String getStockInfo(String name) {

        return komponentDao.getStockInfo(name);
    }

    @Override
    public List<KomponentModel> getMainKomponents() {
        List<KomponentModel> tmp = komponentDao.getAllKomponents().stream().filter(k -> k.get_typ_1() == Types.GLOWNY).collect(Collectors.toList());

        return tmp;
    }

    @Override
    public List<KomponentModel> getKomponentsWihoutStock() {
        List<KomponentModel> tmp = komponentDao.getKomponentsWihoutStock();

        return tmp;
    }

    @Override
    public List<String> getKomponentsNameWihoutStock() {
        List<String> tmp = new ArrayList<String>();
        List<KomponentModel> km = komponentDao.getKomponentsWihoutStock();

        if(null != km) {
            if(km.size() > 0){
              tmp = new ArrayList<String>();
              for (KomponentModel k : km ) {
                  tmp.add(k.get_name());
              }
            }
        }

        return tmp;
    }

    @Override
    public List<KomponentsQuantity> komponentsQuantity(String parent) {
        KomponentModel km = getKomponentByName(parent);
        List<KomponentsQuantity> quantityList = new ArrayList<KomponentsQuantity>();
        if(null != km.getKomponentQuntity()){
            quantityList.addAll(km.getKomponentQuntity());
        }
        return quantityList;
    }

    @Override
    public void saveTest() {

        for(int i=0;i<100;i++){
            String name = "MM"+i;
            KomponentModel tmp =
                    new KomponentModel(name,name, Types.SZTUKA,Types.PUSTY,Types.PUSTY,0.1*i);
            tmp.set_units(Units.PIECE);
            komponentDao.saveKomponent(tmp);
        }

    }

    @Override
    public void saveTest2() {
        KomponentModel tmp =
                new KomponentModel("A","Kolor niebieski", Types.GLOWNY,Types.PUSTY,Types.PUSTY,0.01532);
        KomponentModel tmp2 =
                new KomponentModel("B","Kolor niebieski", Types.SZTUKA,Types.PUSTY,Types.PUSTY,0.01532);
        KomponentModel tmp3 =
                new KomponentModel("C","Kolor niebieski", Types.SZTUKA,Types.PUSTY,Types.PUSTY,0.01532);
        KomponentModel tmp4 =
                new KomponentModel("D","Kolor niebieski", Types.KOMPLET,Types.PUSTY,Types.PUSTY,0.01532);
        KomponentModel tmp5 =
                new KomponentModel("E","Kolor niebieski", Types.KOMPLET,Types.PUSTY,Types.PUSTY,0.01532);



        tmp.add_Child(tmp2,1);
        tmp.add_Child(tmp3,1);
        tmp4.add_Child(tmp2,1);
        tmp4.add_Child(tmp3,1);
        tmp5.add_Child(tmp2,1);
        tmp5.add_Child(tmp3,1);
        tmp.add_Child(tmp4,1);
        tmp.add_Child(tmp5,1);

        komponentDao.saveKomponent(tmp2);
        komponentDao.saveKomponent(tmp3);
        komponentDao.saveKomponent(tmp4);
        komponentDao.saveKomponent(tmp5);
        komponentDao.saveKomponent(tmp);
    }


    @Override
    public KomponentRecords setKomponentRecords(KomponentModel model) {
        KomponentRecords kr = new KomponentRecords();

        kr.updateChildsElement(model.get_childsElement());
        kr.set_name(model.get_name());
        kr.set_weight(model.get_weight());
        kr.set_description(model.get_description());
        kr.set_sortorder(model.get_sortorder());
        kr.set_material(model.get_material());
        kr.set_typ_1(model.get_typ_1());
        kr.set_typ_2(model.get_typ_3());
        kr.set_typ_3(model.get_typ_3());
        kr.set_units(model.get_units());
        kr.set_dimension_X(model.get_dimension_X());
        kr.set_dimension_Y(model.get_dimension_Y());
        kr.set_dimension_Z(model.get_dimension_Z());
        kr.setChildSize(kr.getChildSize());


        return kr;
    }

    @Override
    public KomponentRecords getKomponentRecords(String name) {
        KomponentModel km = getKomponentByName(name);
        KomponentRecords rs  = null;

        if(null != km) {
            rs = setKomponentRecords(km);
        }
        return rs;
    }

    @Override
    public List<KomponentRecords> getAllKomponentRecords() {
       List<KomponentModel> tmp = getAllKomponents();
       List<KomponentRecords> kr = new ArrayList<KomponentRecords>();

       tmp.forEach(el -> kr.add(setKomponentRecords(el)));


        return kr;
    }

    @Override
    public List<KomponentRecords> getMainKomponentRecords() {
        List<KomponentModel> tmp = getMainKomponents();
        List<KomponentRecords> kr = new ArrayList<KomponentRecords>();

        tmp.forEach(el -> kr.add(setKomponentRecords(el)));


        return kr;
    }
}
