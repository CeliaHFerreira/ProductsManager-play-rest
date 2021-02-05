package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@JsonIgnoreProperties(value = {"_ebean_intercept"})
public class Codigo extends Model {

    @Constraints.Required(message = "El código de barras del producto es obligatorio")
    public Long codigoBarras;

    @Id
    public Long id;

    @JsonIgnore
    @OneToOne(mappedBy="idCodigoBarras")
    public Producto idProducto;

    public Long getCodigoBarras() { return codigoBarras; }
    public Long getId() {  return id; }
    public Producto getIdProducto() { return idProducto; }


    public void setCodigoBarras(Long codigoBarras) { this.codigoBarras = codigoBarras; }
    public void setId(Long id) { this.id = id; }
    public void setIdProducto(Producto idProducto) { this.idProducto = idProducto; }

}
