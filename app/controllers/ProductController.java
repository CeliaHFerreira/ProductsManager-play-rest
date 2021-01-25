package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Producto;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import play.twirl.api.Content;
import views.xml.productos;

/**
 * Product Controller
 */
public class ProductController extends Controller {

    public Result getProductItem(Http.Request request, String name) {
        Producto productTofind = Producto.findProducto(name);
        if (productTofind != null) {
            if (request.accepts("application/json")) {
                JsonNode jsonFindProducto = play.libs.Json.toJson(productTofind);
                return ok(jsonFindProducto).as("application/json");
            }  else if (request.accepts("application/xml")) {
                Content content = productos.render(productTofind);
                return Results.ok(content);
            }
        } else {
            return Results.notFound();
        }
        return status(406);
    }

    public Result putProductItem(Http.Request request, String name) {
        //to do content-negotiation update ids
        JsonNode json = request.body().asJson();
        Producto productTofind = Producto.findProducto(name);
        if (productTofind != null) {
            productTofind.setNombre(json.get("nombre").asText());
            productTofind.setVegano(json.get("vegano").asBoolean());
            productTofind.setAptoCG(json.get("aptcg").asBoolean());
            productTofind.setPVP(json.get("pvp").asDouble());
            productTofind.setHNR(json.get("hnr").asText());
            JsonNode productUpdated = play.libs.Json.toJson(productTofind);
            return ok(productUpdated).as("application/json");
        } else {
            return Results.notFound();
        }
    }

    public Result deleteProductItem(String name) {
        //to do content-negotiation
        Producto productTofind = Producto.findProducto(name);
        if (productTofind != null) {
            Producto.listaProducto.remove(productTofind);
            JsonNode jsonProducto = play.libs.Json.toJson(Producto.listaProducto);
            return ok(jsonProducto).as("application/json");
        } else {
            return Results.notFound();
        }
    }

}
