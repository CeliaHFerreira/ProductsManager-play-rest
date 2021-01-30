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
    @Id
    Long id2;

    public Long getId2() {
        return id2;
    }

    public void setId2(Long id2) {
        this.id2 = id2;
    }


    @OneToMany(cascade=CascadeType.ALL, mappedBy="marcaID")
    public List<Marca> marcaID;


    static public ArrayList<Marcas> listaMarcas = new ArrayList<Marcas>();

    public List getMarcaID() {
        return marcaID;
    }
    public String getNombre() {
        return nombre;
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
        m.marcaID = this;
    }
}
