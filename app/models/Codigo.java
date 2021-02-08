package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.ebean.Finder;
import io.ebean.Model;
import models.Validators.CodeValidation;
import models.Validators.HNRValidation;
import play.data.validation.Constraints;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
@JsonIgnoreProperties(value = {"_ebean_intercept"})
public class Codigo extends Model {

    @Constraints.Required(message = "El código de barras del producto es obligatorio")
    @Constraints.ValidateWith(CodeValidation.class)
    private String codigoBarras;

    @Id
    private Long id;

    @JsonIgnore
    @OneToOne(cascade= CascadeType.ALL)
    private Producto idProducto;

    public String getCodigoBarras() { return codigoBarras; }
    public Long getId() {  return id; }
    public Producto getIdProducto() { return idProducto; }


    public void setCodigoBarras(String codigoBarras) { this.codigoBarras = codigoBarras; }
    public void setId(Long id) { this.id = id; }
    public void setIdProducto(Producto idProducto) { this.idProducto = idProducto; }


    public static final Finder<Long,Codigo> find = new Finder<>(Codigo.class);

    static public Codigo findCodigoByCode(Long code) {
        return find.query().where().eq("CodigoBarras", code).findOne();
    }

    static public Codigo findCodigoById(Long id) {
        return find.query().where().eq("ID", id).findOne();
    }

    static public Codigo findCodigoByProduct(Long product) {
        return find.query().where().eq("ID_PRODUCTO_ID", product).findOne();
    }

    static public List<Codigo> getListaCodigos() {
        return find.query().findList();
    }

    public void addProductoToCodigo(Producto p) {
        this.idProducto = p;
        p.idCodigoBarras = this;
    }

    public void deleteCodigoDeProducto(Producto p) {
        this.idProducto = null;
        p.idCodigoBarras = null;
    }
}
