package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Codigo;
import models.Producto;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import play.twirl.api.Content;
import views.xml.codigos;

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
            Content content = codigos.render(Codigo.getListaCodigos());
            if (Codigo.getListaCodigos().size() != 0) {
                return Results.ok(content).as("application/xml");
            } else {
                return Results.notFound();
            }
        }
        return Results.status(406);
    }

    public Result postCode(Http.Request request, String name) {
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
                Content content = codigos.render(Codigo.getListaCodigos());
                return Results.ok(content).as("application/xml");
            }
        }
        return Results.status(406);
    }

    public Result putCode(Http.Request request) {
        JsonNode json = request.body().asJson();
        String nuevoCodigo = json.get("newCode").asText();
        Long viejoCodigo = json.get("oldCode").asLong();
        Codigo codeToUpdate = Codigo.findCodigoById(viejoCodigo);
        if (codeToUpdate != null) {
            codeToUpdate.setCodigoBarras(nuevoCodigo);
            codeToUpdate.update();
            if (request.accepts("application/json")) {
                JsonNode jsonCodigos = play.libs.Json.toJson(Codigo.getListaCodigos());
                return Results.ok(jsonCodigos).as("application/json");
            } else if (request.accepts("application/xml")) {
                Content content = codigos.render(Codigo.getListaCodigos());
                return Results.ok(content).as("application/xml");
            }
        } else {
            return Results.notFound();
        }
        return Results.status(406);
    }

    public Result deleteCode(Http.Request request) {
        JsonNode json = request.body().asJson();
        Long id = json.get("id").asLong();
        Codigo codeToDelete = Codigo.findCodigoById(id);
        if (codeToDelete != null) {
            codeToDelete.deleteCodigoDeProducto(codeToDelete.getIdProducto());
            codeToDelete.delete();
            if (request.accepts("application/json")) {
                JsonNode jsonCodigos = play.libs.Json.toJson(Codigo.getListaCodigos());
                return Results.ok(jsonCodigos).as("application/json");
            } else if (request.accepts("application/xml")) {
                Content content = codigos.render(Codigo.getListaCodigos());
                return Results.ok(content).as("application/xml");
            }
        } else {
            return Results.notFound();
        }
        return Results.status(406);
    }
}
