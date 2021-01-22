package models;

import java.util.ArrayList;

public class Categoria {
    String nombre;
    String categoriaID;
    String productoID;
    String marcaID;

    public Categoria(String nombre, String categoriaID, String productoID, String marcaID) {
        this.nombre = nombre;
        this.categoriaID = categoriaID;
        this.productoID = productoID;
        this.marcaID = marcaID;
    }

    static public ArrayList<Categoria> listaCategoria = new ArrayList<Categoria>();

    public String getNombre() {
        return nombre;
    }

    public String getCategoriaID() { return categoriaID; }

    public String getProductoID() {
        return productoID;
    }

    public String getMarcaID() {
        return marcaID;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCategoriaID(String categoriaID) {
        this.categoriaID = categoriaID;
    }

    public void setProductoID(String productoID) {
        this.productoID = productoID;
    }

    public void setMarcaID(String marcaID) {
        this.marcaID = marcaID;
    }
}
