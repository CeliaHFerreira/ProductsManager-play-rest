package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Categoria extends Model {
    public String nombre;

    @Id public Long Id;

    public Long categoriaID;

    @ManyToMany(cascade = CascadeType.ALL)
    public List<Producto> productoID = new ArrayList<Producto>();

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

    static public Categoria findCategoria(String nombre) {
        return find.query().where().eq("nombre", nombre).findOne();
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
