package models;

import java.util.ArrayList;

public class Categoria {
    String nombre;
    String categoriaID;
    String productoID;
    String marcaID;

    public Categoria() {
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

    static public Categoria findCategoria(String nombre) {
        for (int c = 0; c < listaCategoria.size(); c++) {
            if (Categoria.listaCategoria.get(c).getNombre().equals(nombre)) {
                return Categoria.listaCategoria.get(c);
            }
        }
        return null;
    }
}
