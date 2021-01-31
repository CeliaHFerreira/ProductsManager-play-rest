package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Marca extends Model {

    @Constraints.Required(message = "Nombre de la marca es obligatorio")
    @Constraints.MinLength(value = 2, message = "El nombre de la marca no puede ser tan corto")
    public String nombre;

    @Id public Long Id;

    @ManyToOne
    public Marcas marcasID;

    //Falta many to many
    public String categoriaID;

    @OneToMany(cascade= CascadeType.ALL, mappedBy="marcaID")
    public List<Producto> productoID;

    public Boolean vegano;


    static public ArrayList<Marca> listaMarca = new ArrayList<Marca>();


    public String getNombre() { return nombre; }
    public Long getId() { return Id; }
    public Marcas getMarcaID() { return marcasID; }
    public String getCategoriaID() {  return categoriaID; }
    public List getProductoID() {  return productoID;  }
    public Boolean getVegano() { return vegano; }

    public void setMarcaID(Marcas marcaID) { this.marcasID = marcaID; }
    public void setId(Long id) { Id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCategoriaID(String categoriaID) { this.categoriaID = categoriaID; }
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
}
