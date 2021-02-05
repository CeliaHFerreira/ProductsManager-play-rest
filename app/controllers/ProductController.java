package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Categoria;
import models.Marca;
import models.Producto;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import play.twirl.api.Content;
import views.xml.producto;
import views.xml.productos;

import javax.inject.Inject;
import java.util.List;

/**
 * Product Controller
 */
public class ProductController extends Controller {
    @Inject
    FormFactory formfactory;

    public Result getProducts(Http.Request request) {
        if (Producto.getListaProductos().size() == 0) {
            return Results.notFound();
        } else {
            if (request.accepts("application/json")) {
                JsonNode jsonRest = play.libs.Json.toJson(Producto.getListaProductos());
                return ok(jsonRest).as("application/json");
            } else if (request.accepts("application/xml")) {
                Content content = productos.render(Producto.getListaProductos());
                if (Producto.getListaProductos().size() != 0) {
                    return Results.ok(content).as("application/xml");
                } else {
                    return Results.notFound();
                }
            }
        }
        return status(406);
    }

    public Result getProductItem(Http.Request request, String name) {
        Producto productTofind = Producto.findProductoByNombre(name);
        if (productTofind != null) {
            if (request.accepts("application/json")) {
                JsonNode jsonFindProducto = play.libs.Json.toJson(productTofind);
                return ok(jsonFindProducto).as("application/json");
            } else if (request.accepts("application/xml")) {
                Content content = producto.render(productTofind);
                return Results.ok(content).as("application/xml");
            }
        } else {
            return Results.notFound();
        }
        return status(406);
    }

    public Result putProductItem(Http.Request request, String name) {
        //To DO uodate categorie when many to many will be correct
        Form<Producto> p = formfactory.form(Producto.class).bindFromRequest(request);
        if (p.hasErrors()) {
            return Results.badRequest(p.errorsAsJson());
        }else {
            Producto product = p.get();
            Producto productTofind = Producto.findProductoByNombre(name);
            if (productTofind != null) {
                productTofind.setNombre(product.getNombre());
                productTofind.setVegano(product.getVegano());
                productTofind.setAptoCG(product.getAptoCG());
                productTofind.setPVP(product.getPVP());
                productTofind.setHNR(product.getHNR());
                productTofind.setNombreMarca(product.getNombreMarca());
                Marca brand = Marca.findMarcaByNombre(product.getNombreMarca());
                brand.update();
                productTofind.update();
                if (request.accepts("application/json")) {
                    JsonNode productUpdated = play.libs.Json.toJson(productTofind);
                    return ok(productUpdated).as("application/json");
                } else if (request.accepts("application/xml")) {
                    Content content = producto.render(productTofind);
                    return Results.ok(content).as("application/xml");
                }
            } else {
                return Results.notFound();
            }
        }
        return status(406);
    }

    public Result deleteProductItem(Http.Request request, String name) {
        //To DO when many to many will be correct
        Producto productTofind = Producto.findProductoByNombre(name);
        Marca productInBrand = Marca.findMarcaByNombre(productTofind.getNombreMarca());
        if (productTofind != null) {
            productInBrand.deleteProducto(productTofind);
            productTofind.delete();
            Producto.getListaProductos().remove(productTofind);
            if (request.accepts("application/json")) {
                JsonNode jsonProducto = play.libs.Json.toJson(Producto.getListaProductos());
                return ok(jsonProducto).as("application/json");
            } else if (request.accepts("application/xml")) {
                Content content = productos.render(Producto.getListaProductos());
                return Results.ok(content).as("application/xml");
            }
        } else {
            return Results.notFound();
        }
        return status(406);
    }


    public Result getProductListBrand(Http.Request request, String name) {
        Marca marcaTofind = Marca.findMarcaByNombre(name);
        if (marcaTofind != null) {
            if (request.accepts("application/json")) {
                JsonNode jsonListaProductos = play.libs.Json.toJson(Producto.getListaProductosMarca(marcaTofind.getNombre()));
                return ok(jsonListaProductos).as("application/json");
            } else if (request.accepts("application/xml")) {
                List<Producto> productList = Producto.getListaProductosMarca(marcaTofind.getNombre());
                if (productList != null) {
                    Content content = productos.render(productList);
                    return Results.ok(content).as("application/xml");
                } else {
                    return Results.notFound();
                }
            }
        } else {
            return Results.notFound();
        }
        return status(406);
    }

}
