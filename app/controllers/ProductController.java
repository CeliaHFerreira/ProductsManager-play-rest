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
        // TO DO: return JSON!
        if(request.accepts("application/json")) {
            JsonNode jsonRest = play.libs.Json.toJson(Producto.getListaProductos());
            if (jsonRest.size() == 0) {
                return Results.notFound();
            } else {
                ObjectNode result = play.libs.Json.newObject();
                result.set("categories", jsonRest);
                return ok(result).as("application/json");
            }
        } else if (request.accepts("application/xml")) {
            Content content = productos.render(Producto.getListaProductos());
            if (Producto.getListaProductos().size() != 0) {
                return Results.ok(content).as("application/xml");
            } else {
                return Results.notFound();
            }
        }
        return status(406);
    }
    public Result getProductItem(Http.Request request, String name) {
        Producto productTofind = Producto.findProducto(name);
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
        Form<Producto> p = formfactory.form(Producto.class).bindFromRequest(request);
        Producto product = p.get();
        Producto productTofind = Producto.findProducto(name);
        if (productTofind != null) {
            productTofind.setNombre(product.getNombre());
            productTofind.setVegano(product.getVegano());
            productTofind.setAptoCG(product.getAptoCG());
            productTofind.setPVP(product.getPVP());
            productTofind.setHNR(product.getHNR());
            productTofind.setNombreMarca(product.getNombreMarca());
            Marca brand = Marca.findMarca(product.getNombreMarca());
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
        return status(406);
    }

    public Result deleteProductItem(Http.Request request, String name) {
        Producto productTofind = Producto.findProducto(name);
        Marca productInBrand = Marca.findMarca(productTofind.getNombreMarca());
        if (productTofind != null) {
            productInBrand.deleteProducto(productTofind);
            productTofind.delete();
            Producto.listaProducto.remove(productTofind);
            if (request.accepts("application/json")) {
                JsonNode jsonProducto = play.libs.Json.toJson(Producto.listaProducto);
                return ok(jsonProducto).as("application/json");
            } else if (request.accepts("application/xml")) {
                Content content = productos.render(Producto.listaProducto);
                return Results.ok(content).as("application/xml");
            }
        } else {
            return Results.notFound();
        }
        return status(406);
    }


    public Result getProductListBrand(Http.Request request, String name) {
        // TO DO: return JSON!
        Marca marcaTofind = Marca.findMarca(name);
        if (marcaTofind != null) {
            if (request.accepts("application/json")) {
                //TO DO
                return ok().as("application/json");
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
