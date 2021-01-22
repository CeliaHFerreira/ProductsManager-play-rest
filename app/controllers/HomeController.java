package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Categoria;
import models.Marcas;
import play.mvc.*;

/**
 * HomeController
 */
public class HomeController extends Controller {

    public Result index() {
        return ok(views.html.index.render());
    }

    public Result getManager() {
        // TO DO: content-negotiation
        JsonNode jsonRest = play.libs.Json.toJson(Categoria.listaCategoria);
        if (jsonRest.size() == 0) {
            return Results.notFound();
        } else {
            ObjectNode result = play.libs.Json.newObject();
            result.put("brands", "/marcas");
            result.set("categories", jsonRest);
            return ok(result).as("application/json");
        }
    }

    public Result postManager(Http.Request request) {
        // TO DO: content-negotiation update ids
        JsonNode json = request.body().asJson();
        Categoria category = new Categoria(json.get("nombre").asText(), json.get("categoriaid").asText(), null, null);
        Categoria.listaCategoria.add(category);
        JsonNode jsonCategorias = play.libs.Json.toJson(Categoria.listaCategoria);
        return ok(jsonCategorias).as("application/json");
    }

}
