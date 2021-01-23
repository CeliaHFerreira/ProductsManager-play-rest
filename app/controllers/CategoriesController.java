package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Categoria;
import models.Producto;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

/**
 * Categories controller
 */
public class CategoriesController extends Controller {

    public Result getCategorie(String name) {
        //to do content-negotiation
        Categoria categorieTofind = Categoria.findCategoria(name);
        if (categorieTofind != null) {
            JsonNode jsonFindCategoria = play.libs.Json.toJson(categorieTofind);
            return ok(jsonFindCategoria).as("application/json");
        } else {
            return Results.notFound();
        }
    }

    public Result postCategorie(Http.Request request, String name) {
        // TO DO: content-negotiation update ids
        JsonNode json = request.body().asJson();
        Categoria categorieTofind = Categoria.findCategoria(name);
        if (categorieTofind == null) {
            return Results.notFound();
        }
        Producto product = new Producto(json.get("nombre").asText(), json.get("productoid").asText(),
                null, categorieTofind.getCategoriaID(), json.get("vegano").asBoolean(), json.get("aptocg").asBoolean(), json.get("PVP").asDouble(), json.get("HNR").asText());
        Producto.listaProducto.add(product);
        JsonNode jsonProductos = play.libs.Json.toJson(Producto.listaProducto);
        return ok(jsonProductos).as("application/json");
    }

    public Result putCategorie(Http.Request request, String name) {
        //to do content-negotiation update ids
        JsonNode json = request.body().asJson();
        Categoria categorieTofind = Categoria.findCategoria(name);
        if (categorieTofind != null) {
            categorieTofind.setNombre(json.get("nombre").asText());
            JsonNode cataegorieUpdated = play.libs.Json.toJson(categorieTofind);
            return ok(cataegorieUpdated).as("application/json");
        } else {
            return Results.notFound();
        }
    }

    public Result deleteCategorie(String name) {
        //to do content-negotiation
        Categoria categorieTofind = Categoria.findCategoria(name);
        if (categorieTofind != null) {
            Categoria.listaCategoria.remove(categorieTofind);
            JsonNode jsonCategorias = play.libs.Json.toJson(Categoria.listaCategoria);
            return ok(jsonCategorias).as("application/json");
        } else {
            return Results.notFound();
        }
    }

}
