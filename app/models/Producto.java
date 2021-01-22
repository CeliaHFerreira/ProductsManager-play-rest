package models;

public class Producto {
    String nombre;
    String productoID;
    String marcaID;
    String categoriaID;
    Boolean vegano;
    Boolean aptoCG;
    float PVP;
    String HNR;

    public Producto(String nombre, String productoID, String marcaID, String categoriaID, Boolean vegano, Boolean aptoCG, float PVP, String HNR) {
        this.nombre = nombre;
        this.productoID = productoID;
        this.marcaID = marcaID;
        this.categoriaID = categoriaID;
        this.vegano = vegano;
        this.aptoCG = aptoCG;
        this.PVP = PVP;
        this.HNR = HNR;
    }

    public String getNombre() {
        return nombre;
    }

    public String getProductoID() {
        return productoID;
    }

    public String getMarcaID() {
        return marcaID;
    }

    public String getCategoriaID() {
        return categoriaID;
    }

    public Boolean getVegano() {
        return vegano;
    }

    public Boolean getAptoCG() {
        return aptoCG;
    }

    public float getPVP() {
        return PVP;
    }

    public String getHNR() {
        return HNR;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setProductoID(String productoID) {
        this.productoID = productoID;
    }

    public void setMarcaID(String marcaID) {
        this.marcaID = marcaID;
    }

    public void setCategoriaID(String categoriaID) {
        this.categoriaID = categoriaID;
    }

    public void setVegano(Boolean vegano) {
        this.vegano = vegano;
    }

    public void setAptoCG(Boolean aptoCG) {
        this.aptoCG = aptoCG;
    }

    public void setPVP(float PVP) {
        this.PVP = PVP;
    }

    public void setHNR(String HNR) {
        this.HNR = HNR;
    }
}
