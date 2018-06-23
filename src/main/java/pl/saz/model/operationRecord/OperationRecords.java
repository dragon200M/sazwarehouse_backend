package pl.saz.model.operationRecord;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.saz.utils.CompressionUtils;

import javax.persistence.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.zip.DataFormatException;

/**
 * Created by maciej on 01.05.18.
 */
@Entity
@Table(name = "Records")
public class OperationRecords {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long _id;

    @Column(name = "OperationDate")
    private LocalDateTime _operationDate;


    @Column(name = "Type",nullable = false )
    @Enumerated(EnumType.ORDINAL)
    private OperationTypes _type;

    @Column(name = "Description")
    private String _objectDescription;

    @Column(name = "ClassName")
    private String _className = "";

    @JsonIgnore
    @Column(length = 2000000)
    private byte[] inBinary;





    public OperationRecords() {}

    public OperationRecords(OperationTypes _type, String _objectDescription){
        this._type = _type;
        this._objectDescription = "";
        this._operationDate = LocalDateTime.now();
        this.setInBinary(_objectDescription.getBytes(StandardCharsets.UTF_8));
    }

    public OperationRecords(OperationTypes _type, String _objectDescription, String _className) {
        this._operationDate = LocalDateTime.now();
        this._type = _type;
        this._objectDescription = "";
        this._className = _className;
        this.setInBinary(_objectDescription.getBytes(StandardCharsets.UTF_8));
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public OperationTypes get_type() {
        return _type;
    }

    public void set_type(OperationTypes _type) {
        this._type = _type;
    }

    public String get_objectDescription() {
        if(inBinary.length > 0){
            return new String(inBinary);

        }

        return _objectDescription;
    }

    public void set_objectDescription(String _objectDescription) {
        this._objectDescription = _objectDescription;
    }

    public LocalDateTime get_operationDate() {
        return _operationDate;
    }

    public void set_operationDate(LocalDateTime _operationDate) {
        this._operationDate = _operationDate;
    }

    public String get_className() {
        return _className;
    }

    public void set_className(String _className) {
        this._className = _className;
    }



    public byte[] getInBinary() {
        return inBinary;
    }

    public void setInBinary(byte[] inBinary) {
        this.inBinary = inBinary;
    }

    private byte[] compress(String _objectDescription){
        byte[] w = new byte[_objectDescription.length() / 2];
        try {
            w = CompressionUtils.compress(_objectDescription.getBytes(StandardCharsets.UTF_8));
            String str = new String(CompressionUtils.decompress(w),"UTF-8");


        } catch (IOException e){
            System.out.println(e);
        } catch (DataFormatException e) {
            e.printStackTrace();
        }

        return w;
    }

}
