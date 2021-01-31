package models;

import io.ebean.Finder;
import io.ebean.Model;
import org.checkerframework.common.aliasing.qual.Unique;
import play.data.validation.Constraints;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Categoria extends Model {
    @Constraints.Required(message = "Nombre de la categoria es obligatorio")
    public String nombre;

    @Unique
    @Id public Long Id;

    public Long categoriaID;

    @ManyToMany(cascade = CascadeType.ALL)
    public List<Producto> productoID = new ArrayList<Producto>();

    //Falta esta many to many
    public String marcaID;


    static public ArrayList<Categoria> listaCategoria = new ArrayList<Categoria>();


    public String getNombre() {
        return nombre;
    }
    public Long getCategoriaID() { return categoriaID; }
    public List getProductoID() {
        return productoID;
    }
    public String getMarcaID() {
        return marcaID;
    }
    public Long getId() { return Id; }

    public void setId(Long id) { Id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCategoriaID(Long categoriaID) { this.categoriaID = categoriaID;}
    public void setProductoID(List productoID) { this.productoID = productoID; }
    public void setMarcaID(String marcaID) { this.marcaID = marcaID; }


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

    /*public void addProducto(Producto p, Categoria c) {
        this.productoID.add(p);
        p.categoriaID = p.setCategoriaSeleccionada(c);
    }

     */
}
