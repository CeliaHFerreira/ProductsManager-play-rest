package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.ebean.Finder;
import io.ebean.Model;
import org.checkerframework.common.aliasing.qual.Unique;
import play.data.validation.Constraints;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@JsonIgnoreProperties(value = {"_ebean_intercept"})
public class Categoria extends Model {
    @Constraints.Required(message = "Nombre de la categoria es obligatorio")
    private String nombre;

    @Id private Long Id;

    @ManyToMany(cascade = CascadeType.ALL)
    public Set<Producto> productoID = new HashSet<Producto>();

    @JsonIgnore
    @ManyToMany(mappedBy = "categoriaID")
    public Set<Marca> marcaID = new HashSet<Marca>();


    public String getNombre() {
        return nombre;
    }
    public Set<Producto> getProductoID() {
        return productoID;
    }
    public Set<Marca> getMarcaID() {
        return marcaID;
    }
    public Long getId() { return Id; }

    public void setId(Long id) { Id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setProductoID(Set<Producto> productoID) { this.productoID = productoID; }
    public void setMarcaID(Set<Marca> marcaID) { this.marcaID = marcaID; }


    public static final Finder<Long,Categoria> find = new Finder<>(Categoria.class);

    static public Categoria findCategoriaByNombre(String nombre) {
        return find.query().where().eq("nombre", nombre).findOne();
    }

    static public Categoria findCategoriaById(Long id) {
        return find.query().where().eq("Id", id).findOne();
    }

    static public List<Categoria> getListaCategorias() {
        return find.query().findList();
    }

    public void addProductoToCategory(Producto p) {
        this.productoID.add(p);
        p.getCategoriaID().add(this);
    }
    public void removeProductoOfCategory(Producto p) {
        this.productoID.remove(p);
        p.getCategoriaID().remove(this);
    }

    public void addMarcaToCategory(Marca m) {
        this.marcaID.add(m);
        m.getCategoriaID().add(this);
    }
    public void removeMarcaOfCategory(Marca m) {
        this.marcaID.remove(m);
        m.getCategoriaID().remove(this);
    }
}
