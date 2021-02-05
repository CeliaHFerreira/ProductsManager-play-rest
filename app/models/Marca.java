package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@JsonIgnoreProperties(value = {"_ebean_intercept"})
public class Marca extends Model {

    @Constraints.Required(message = "Nombre de la marca es obligatorio")
    @Constraints.MinLength(value = 2, message = "El nombre de la marca no puede ser tan corto")
    private String nombre;

    @Id private Long Id;

    @JsonIgnore
    @ManyToOne
    public Marcas marcasID;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Categoria> categoriaID = new HashSet<Categoria>();

    @JsonIgnore
    @OneToMany(cascade= CascadeType.ALL, mappedBy="marcaID")
    private List<Producto> productoID;

    private Boolean vegano;


    public String getNombre() { return nombre; }
    public Long getId() { return Id; }
    public Marcas getMarcaID() { return marcasID; }
    public Set getCategoriaID() {  return categoriaID; }
    public List getProductoID() {  return productoID;  }
    public Boolean getVegano() { return vegano; }

    public void setMarcaID(Marcas marcaID) { this.marcasID = marcaID; }
    public void setId(Long id) { Id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCategoriaID(Set categoriaID) { this.categoriaID = categoriaID; }
    public void setProductoID(List productoID) { this.productoID = productoID; }
    public void setVegano(Boolean vegano) { this.vegano = vegano; }


    public static final Finder<Long,Marca> find = new Finder<>(Marca.class);


    static public Marca findMarcaByNombre(String nombre) {
        return find.query().where().eq("nombre", nombre).findOne();
    }
    static public Marca findMarcaById(Long id) {
        return find.query().where().eq("Id", id).findOne();
    }


    public void addProducto(Producto p) {
        this.productoID.add(p);
        p.marcaID = this;
    }
    public void deleteProducto(Producto p) {
        this.productoID.remove(p);
    }

    public void addCategoryToBrand(Categoria c) {
        this.categoriaID.add(c);
        c.getMarcaID().add(this);
    }
    public void removeCategoryToBrand(Categoria c) {
        this.categoriaID.remove(c);
        c.getMarcaID().remove(this);
    }
}
