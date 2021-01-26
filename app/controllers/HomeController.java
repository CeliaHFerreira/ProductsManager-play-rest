package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Categoria;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;
import play.twirl.api.Content;
import views.xml.categorias;

import javax.inject.Inject;

/**
 * HomeController
 */
public class HomeController extends Controller {

    @Inject
    FormFactory formfactory;

    public Result index() {
        return ok(views.html.index.render());
    }

    public Result getManager(Http.Request request) {
        if(request.accepts("application/json")) {
            JsonNode jsonRest = play.libs.Json.toJson(Categoria.listaCategoria);
            if (jsonRest.size() == 0) {
                return Results.notFound();
            } else {
                ObjectNode result = play.libs.Json.newObject();
                result.put("brands", "/marcas");
                result.set("categories", jsonRest);
                return ok(result).as("application/json");
            }
        } else if (request.accepts("application/xml")) {
            Content content = categorias.render(Categoria.listaCategoria);
            if (Categoria.listaCategoria.size() != 0) {
                return Results.ok(content).as("application/xml");
            } else {
                return Results.notFound();
            }
        }
        return status(406);
    }

    public Result postManager(Http.Request request) {
        Form<Categoria> c = formfactory.form(Categoria.class).bindFromRequest(request);
        Categoria category = c.get();
        Categoria.listaCategoria.add(category);
        if(request.accepts("application/json")) {
            JsonNode jsonCategorias = play.libs.Json.toJson(Categoria.listaCategoria);
            return ok(jsonCategorias).as("application/json");
        } else if (request.accepts("application/xml")) {
            Content content = categorias.render(Categoria.listaCategoria);
            return Results.ok(content).as("application/xml");
        }
        return status(406);
    }

}
