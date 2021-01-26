package models;

import java.util.ArrayList;

public class Marca {
    String nombre;
    String marcaID;
    String categoriaID;
    String productoID;
    Boolean vegano;

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
