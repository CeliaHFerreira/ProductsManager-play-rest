package models;

import java.util.ArrayList;

public class Marcas {
    String nombre;
    String marcaID;

    public Marcas() {
        this.nombre = nombre;
        this.marcaID = marcaID;
    }

    static public ArrayList<Marcas> listaMarcas = new ArrayList<Marcas>();

    public String getMarcaID() {
        return marcaID;
    }
    public String getNombre() {
        return nombre;
    }

    public void setMarcaID(String marcaID) {
        this.marcaID = marcaID;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    static public Marcas findMarca(String nombre) {
        for (int b = 0; b < listaMarcas.size(); b++) {
            if (Marcas.listaMarcas.get(b).getNombre().equals(nombre)) {
                return Marcas.listaMarcas.get(b);
            }
        }
        return null;
    }
}
