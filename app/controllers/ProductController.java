package controllers;

import com.fasterxml.jackson.databind.JsonNode;
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

/**
 * Product Controller
 */
public class ProductController extends Controller {
    @Inject
    FormFactory formfactory;
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
        if (productTofind != null) {
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

}
