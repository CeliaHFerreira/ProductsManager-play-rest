package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@JsonIgnoreProperties(value = {"_ebean_intercept"})
public class Producto extends Model {

    @Constraints.Required(message = "Nombre del producto es obligatorio")
    @Constraints.MinLength(value = 2, message = "El nombre del producto no puede ser tan corto")
    public String nombre;

    @Id public Long id;

    @JsonIgnore
    @ManyToOne
    public Marca marcaID;

    @JsonIgnore
    @ManyToMany(mappedBy = "productoID")
    public Set<Categoria> categoriaID = new HashSet<Categoria>();

    public Boolean vegano;
    public Boolean aptoCG;

    @Constraints.Min(value = 1, message = "El valor mínimo de un producto es 1€")
    public Double PVP;

    @Constraints.ValidateWith(HNRValidation.class)
    public String HNR;

    @Constraints.Required(message = "Nombre de la marca del producto es obligatorio")
    public String nombreMarca;


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

    static public Producto findProductoByNombre(String nombre) {
        return find.query().where().eq("nombre", nombre).findOne();
    }

    static public Producto findProductoById(Long id) {
        return find.query().where().eq("Id", id).findOne();
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
