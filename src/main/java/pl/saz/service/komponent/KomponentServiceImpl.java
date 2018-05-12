package pl.saz.service.komponent;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.saz.dao.komponent.KomponentDao;
import pl.saz.model.komponent.KomponentModel;
import pl.saz.model.komponent.Types;
import pl.saz.model.operationRecord.OperationRecords;
import pl.saz.model.operationRecord.OperationTypes;
import pl.saz.service.operationRecord.OperationRecordService;

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
            String json = gson.toJson(komponentModel,KomponentModel.class);
            OperationRecords op = new OperationRecords(OperationTypes.INSERT,json);
            recordService.saveRecords(op);
        }

        return operation;
    }

    @Override
    public KomponentModel updateKomponent(KomponentModel komponentModel) {
        KomponentModel tmp =  komponentDao.updateKomponent(komponentModel);
        if(null != tmp) {
            Gson gson = new Gson();
            String json = gson.toJson(tmp,KomponentModel.class);
            OperationRecords op = new OperationRecords(OperationTypes.UPDATE,json);
            recordService.saveRecords(op);
        }

        return tmp;
    }

    @Override
    public void deleteKomponent(KomponentModel kompoenentName) {
        if(null != kompoenentName) {
            komponentDao.deleteKomponent(kompoenentName);
            Gson gson = new Gson();
            String json = gson.toJson(kompoenentName,KomponentModel.class);
            OperationRecords op = new OperationRecords(OperationTypes.DELETE,json);
            recordService.saveRecords(op);
        }
    }

    @Override
    public void deleteKomponentChild(KomponentModel kompoenentName,KomponentModel childName) {
        if(null != childName) {
            komponentDao.deleteKomponentChild(kompoenentName,childName);
            Gson gson = new Gson();
            String parent = gson.toJson(kompoenentName,KomponentModel.class);
            OperationRecords op = new OperationRecords(OperationTypes.UPDATE,parent);
            recordService.saveRecords(op);
        }
    }

    @Override
    public void addChildToParent(String kompoenentName, String childName, int ilosc) {
       KomponentModel parent = getKomponentByName(kompoenentName);
       KomponentModel child  = getKomponentByName(childName);

       int check = 0;
       if(null != child && null != parent){
           check = child.get_childsElement().indexOf(parent);
       }

       if(null != parent
               && null != child
               && ilosc > 0
               && !kompoenentName.equals(childName)
               && check == -1){
           parent.add_Child(child,ilosc);
           komponentDao.updateKomponent(parent);

           Gson gson = new Gson();
           String json = gson.toJson(parent,KomponentModel.class);
           OperationRecords op = new OperationRecords(OperationTypes.UPDATE,json);
           recordService.saveRecords(op);
       }
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
    public void saveTest() {
        KomponentModel tmp =
                new KomponentModel("Dlugopis","Kolor niebieski", Types.SZTUKA,Types.PUSTY,Types.PUSTY,0.01532);
        komponentDao.saveKomponent(tmp);

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

}
