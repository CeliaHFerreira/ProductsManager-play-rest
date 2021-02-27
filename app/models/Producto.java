package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.ebean.Finder;
import io.ebean.Model;
import models.Validators.HNRValidation;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@JsonIgnoreProperties(value = {"_ebean_intercept"})
public class Producto extends Model {

    @Constraints.Required(message = "product-name-required")
    @Constraints.MinLength(value = 2, message = "product-name-length")
    private String nombre;

    @Id private Long id;

    @JsonIgnore
    @ManyToOne
    public Marca marcaID;

    @JsonIgnore
    @ManyToMany(mappedBy = "productoID")
    private Set<Categoria> categoriaID = new HashSet<Categoria>();

    private Boolean vegano;
    private Boolean aptoCG;

    @Constraints.Min(value = 1, message = "product-pvp")
    private Double PVP;

    @Constraints.ValidateWith(HNRValidation.class)
    private String HNR;

    @Constraints.Required(message = "product-name-brand-required")
    @Constraints.MinLength(value = 2, message = "brand-name-lenght")
    private String nombreMarca;

    @JsonIgnore
    @OneToOne(mappedBy="idProducto")
    public Codigo idCodigoBarras;

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public Marca getMarcaID() { return marcaID; }
    public Set<Categoria> getCategoriaID() { return categoriaID; }
    public Boolean getVegano() { return vegano; }
    public Boolean getAptoCG() { return aptoCG; }
    public Double getPVP() { return PVP; }
    public String getHNR() { return HNR; }
    public String getNombreMarca() { return nombreMarca; }
    public Codigo getIdCodigoBarras() { return idCodigoBarras; }


    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setId(Long id) { this.id = id; }
    public void setMarcaID(Marca marcaID) { this.marcaID = marcaID; }
    public void setCategoriaID(Set<Categoria> categoriaID) { this.categoriaID = categoriaID; }
    public void setVegano(Boolean vegano) { this.vegano = vegano; }
    public void setAptoCG(Boolean aptoCG) { this.aptoCG = aptoCG; }
    public void setPVP(Double PVP) { this.PVP = PVP; }
    public void setHNR(String HNR) { this.HNR = HNR; }
    public void setNombreMarca(String nombreMarca) { this.nombreMarca = nombreMarca; }
    public void setIdCodigoBarras(Codigo idCodigoBarras) { this.idCodigoBarras = idCodigoBarras; }

    public static final Finder<Long,Producto> find = new Finder<>(Producto.class);

    static public Producto findProductoByNombreAndBrand(String nombre, String brand) {
        return find.query().where().eq("nombre", nombre).eq("nombreMarca", brand).findOne();
    }

    static public Producto findProductoById(Long id) {
        return find.query().where().eq("id", id).findOne();
    }

    static public List<Producto> findProductoByHNR(String hnr) {
        return find.query().where().eq("hnr", hnr).findList();
    }

    static public List<Producto> findProductoByVegano(String vegano) {
        return find.query().where().eq("vegano", vegano).findList();
    }

    static public List<Producto> getListaProductos() {
        return find.query().findList();
    }

    static public List<Producto> getListaProductosMarca(String nombreMarca) {
        return find.query().where().eq("nombreMarca", nombreMarca).findList();
    }

}
