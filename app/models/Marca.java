package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Marca extends Model {
    public String nombre;

    @Id
    Long marcaID2;

    public Long getMarcaID2() {
        return marcaID2;
    }

    public void setMarcaID2(Long marcaID2) {
        this.marcaID2 = marcaID2;
    }

    @ManyToOne
    public Marcas marcaID;

    public String categoriaID;

    @OneToMany(cascade= CascadeType.ALL, mappedBy="productoID")
    public List<Producto> productoID;

    public Boolean vegano;


    static public ArrayList<Marca> listaMarca = new ArrayList<Marca>();

    public String getNombre() { return nombre; }

    public Marcas getMarcaID() { return marcaID; }

    public String getCategoriaID() {  return categoriaID; }

    public List getProductoID() {  return productoID;  }

    public Boolean getVegano() { return vegano; }

    public void setMarcaID(Marcas marcaID) { this.marcaID = marcaID; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public void setCategoriaID(String categoriaID) { this.categoriaID = categoriaID; }

    public void setProductoID(List productoID) { this.productoID = productoID; }

    public void setVegano(Boolean vegano) { this.vegano = vegano; }


    public static final Finder<Long,Marca> find = new Finder<>(Marca.class);

    static public Marca findMarca(String nombre) {
        /*
        for (int b = 0; b < listaMarca.size(); b++) {
            if (Marca.listaMarca.get(b).getNombre().equals(nombre)) {
                return Marca.listaMarca.get(b);
            }
        }
        return null;
         */
        return find.query().where().eq("nombre", nombre).findOne();
    }

}
