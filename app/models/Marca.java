package models;

import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;

@Entity
public class Marca extends Model {
    String nombre;
    @Id
    String marcaID;
    String categoriaID;
    String productoID;
    Boolean vegano;

    public Marca() {
        this.nombre = nombre;
        this.marcaID = marcaID;
        this.categoriaID = categoriaID;
        this.productoID = productoID;
        this.vegano = vegano;
    }

    static public ArrayList<Marca> listaMarca = new ArrayList<Marca>();

    public String getNombre() { return nombre; }

    public String getMarcaID() { return marcaID; }

    public String getCategoriaID() {
        return categoriaID;
    }

    public String getProductoID() {
        return productoID;
    }

    public Boolean getVegano() {
        return vegano;
    }

    public void setMarcaID(String marcaID) { this.marcaID = marcaID; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public void setCategoriaID(String categoriaID) { this.categoriaID = categoriaID; }

    public void setProductoID(String productoID) { this.productoID = productoID; }

    public void setVegano(Boolean vegano) { this.vegano = vegano; }

    static public Marca findMarca(String nombre) {
        for (int b = 0; b < listaMarca.size(); b++) {
            if (Marca.listaMarca.get(b).getNombre().equals(nombre)) {
                return Marca.listaMarca.get(b);
            }
        }
        return null;
    }
}
