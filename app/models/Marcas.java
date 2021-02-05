package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@JsonIgnoreProperties(value = {"_ebean_intercept"})
public class Marcas extends Model {

    @Constraints.Required(message = "Nombre de la marca es obligatorio")
    @Constraints.MinLength(value = 2, message = "El nombre de la marca no puede ser tan corto")
    private String nombre;

    @Id private Long Id;

    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, mappedBy="marcasID")
    private List<Marca> marcaID;


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

    static public Marcas findById(Long id) {
        return find.query().where().eq("Id", id).findOne();
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
