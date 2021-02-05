package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Categoria;
import models.Codigo;
import models.Producto;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import play.twirl.api.Content;
import views.xml.categorias;

import javax.inject.Inject;

/**
 * Codigo Controller
 */
public class CodigoController {
    @Inject
    FormFactory formfactory;

    public Result getCode(Http.Request request) {
        if(request.accepts("application/json")) {
            JsonNode jsonCodigos = play.libs.Json.toJson(Codigo.getListaCodigos());
            if (jsonCodigos.size() == 0) {
                return Results.notFound();
            } else {
                return Results.ok(jsonCodigos).as("application/json");
            }
        } else if (request.accepts("application/xml")) {
            // Content content = categorias.render(Categoria.getListaCategorias());
            if (Codigo.getListaCodigos().size() != 0) {
                return Results.ok().as("application/xml");
            } else {
                return Results.notFound();
            }
        }
        return Results.status(406);
    }

    public Result postCodigo(Http.Request request, String name) {
        Form<Codigo> c = formfactory.form(Codigo.class).bindFromRequest(request);
        if (c.hasErrors()) {
            return Results.badRequest(c.errorsAsJson());
        } else {
            Codigo code = c.get();
            Producto product = Producto.findProductoByNombre(name);
            code.addProductoToCodigo(product);
            product.save();
            code.save();
            if (request.accepts("application/json")) {
                JsonNode jsonCodigos = play.libs.Json.toJson(Codigo.getListaCodigos());
                return Results.ok(jsonCodigos).as("application/json");
            } else if (request.accepts("application/xml")) {
                //Content content = categorias.render(Categoria.getListaCategorias());
                return Results.ok().as("application/xml");
            }
        }
        return Results.status(406);
    }
}
