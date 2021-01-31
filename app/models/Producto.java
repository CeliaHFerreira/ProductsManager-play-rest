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
    public String nombre;

    @Id public Long id;

    @ManyToOne
    public Marca marcaID;

    @ManyToMany(mappedBy = "productoID")
    public Set<Categoria> categoriaID;

    public Boolean vegano;
    public Boolean aptoCG;
    public Double PVP;
    public String HNR;
    public String nombreMarca;


    static public ArrayList<Producto> listaProducto = new ArrayList<Producto>();


    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public Marca getMarcaID() { return marcaID; }
    public Set<Categoria> getCategoriaID() { return categoriaID; }
    public Boolean getVegano() { return vegano; }
    public Boolean getAptoCG() { return aptoCG; }
    public Double getPVP() { return PVP; }
    public String getHNR() { return HNR; }
    public String getNombreMarca() { return nombreMarca; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setId(Long id) { this.id = id; }
    public void setMarcaID(Marca marcaID) { this.marcaID = marcaID; }
    public void setCategoriaID(Set<Categoria> categoriaID) { this.categoriaID = categoriaID; }
    public void setVegano(Boolean vegano) { this.vegano = vegano; }
    public void setAptoCG(Boolean aptoCG) { this.aptoCG = aptoCG; }
    public void setPVP(Double PVP) { this.PVP = PVP; }
    public void setHNR(String HNR) { this.HNR = HNR; }
    public void setNombreMarca(String nombreMarca) { this.nombreMarca = nombreMarca; }


    public static final Finder<Long,Producto> find = new Finder<>(Producto.class);

    static public Producto findProducto(String nombre) {
        return find.query().where().eq("nombre", nombre).findOne();
    }

    static public List<Producto> getListaProductos() {
        return find.query().findList();
    }

    static public List<Producto> getListaProductosCategoria(Long categoriaID) {
        return find.query().where().eq("categoriaID", categoriaID).findList();
    }

    static public List<Producto> getListaProductosMarca(String nombreMarca) {
        return find.query().where().eq("nombreMarca", nombreMarca).findList();
    }

    /*static public Set setCategoriaSeleccionada(Categoria c) {

    }

     */

}
