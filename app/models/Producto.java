package models;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Producto extends Model {
    String nombre;

    @Id
    Long id2;

    public Long getId2() {
        return id2;
    }

    public void setId2(Long id2) {
        this.id2 = id2;
    }

    @ManyToOne
    public Marca productoID;

    String marcaID;

    @ManyToMany(mappedBy = "productoID")
    public Set<Categoria> categoriaID;

    Boolean vegano;
    Boolean aptoCG;
    Double PVP;
    String HNR;


    /*static public Set setCategoriaSeleccionada(Categoria c) {

    }

     */

    static public ArrayList<Producto> listaProducto = new ArrayList<Producto>();

    public String getNombre() {
        return nombre;
    }

    public Marca getProductoID() {
        return productoID;
    }

    public String getMarcaID() {
        return marcaID;
    }

    public Set<Categoria> getCategoriaID() { return categoriaID; }

    public Boolean getVegano() {
        return vegano;
    }

    public Boolean getAptoCG() {
        return aptoCG;
    }

    public Double getPVP() {
        return PVP;
    }

    public String getHNR() { return HNR; }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setProductoID(Marca productoID) {
        this.productoID = productoID;
    }

    public void setMarcaID(String marcaID) {
        this.marcaID = marcaID;
    }

    public void setCategoriaID(Set<Categoria> categoriaID) { this.categoriaID = categoriaID; }

    public void setVegano(Boolean vegano) {
        this.vegano = vegano;
    }

    public void setAptoCG(Boolean aptoCG) {
        this.aptoCG = aptoCG;
    }

    public void setPVP(Double PVP) {
        this.PVP = PVP;
    }

    public void setHNR(String HNR) { this.HNR = HNR; }

    static public Producto findProducto(String nombre) {
        return find.query().where().eq("nombre", nombre).findOne();
    }

    public static final Finder<Long,Producto> find = new Finder<>(Producto.class);

    static public List<Producto> getListaProductos() {
        return find.query().findList();
    }
}
