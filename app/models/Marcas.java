package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Marcas extends Model {

    public String nombre;

    @Id public Long Id;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="marcasID")
    public List<Marca> marcaID;


    static public ArrayList<Marcas> listaMarcas = new ArrayList<Marcas>();


    public Long getId() {
        return Id;
    }
    public List getMarcaID() {
        return marcaID;
    }
    public String getNombre() {
        return nombre;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }
    public void setMarcaID(List marcaID) {
        this.marcaID = marcaID;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public static final Finder<Long,Marcas> find = new Finder<>(Marcas.class);

    static public Marcas findByNombre(String nombre) {
        return find.query().where().eq("nombre", nombre).findOne();
    }

    static public List<Marcas> getListaMarcas() {
        return find.query().findList();
    }

    public void addMarca(Marca m) {
        this.marcaID.add(m);
        m.marcasID = this;
    }
    public void updateMarca(Marca m) {
        this.marcaID.add(m);
    }
    public void deleteMarca(Marca m) {
        this.marcaID.remove(m);
    }
}
