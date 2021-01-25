package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Categoria;
import models.Marca;
import models.Marcas;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import play.twirl.api.Content;
import views.xml.marcas;

/**
 * BrandsController
 */
public class BrandsController extends Controller {

    public Result getBrands(Http.Request request) {
        if(request.accepts("application/json")) {
            JsonNode jsonMarcas = play.libs.Json.toJson(Marcas.listaMarcas);
            if (jsonMarcas.size() == 0) {
                return Results.notFound();
            } else {
                return ok(jsonMarcas).as("application/json");
            }
        } else if (request.accepts("application/xml")) {
            Content content = marcas.render(Marcas.listaMarcas);
            if (Marcas.listaMarcas.size() != 0) {
                return Results.ok(content).as("application/xml");
            } else {
                return Results.notFound();
            }
        }
        return Results.status(406);
    }

    public Result postBrands(Http.Request request) {
        // TO DO: not repeat brand and update ids
        JsonNode json = request.body().asJson();
        Marcas brands = new Marcas(json.get("marcaid").asText(), json.get("nombre").asText());
        Marca brand = new Marca(json.get("marcaid").asText(), json.get("nombre").asText(), null, null, null);
        Marcas.listaMarcas.add(brands);
        Marca.listaMarca.add(brand);
        if(request.accepts("application/json")) {
            JsonNode jsonMarcas = play.libs.Json.toJson(Marcas.listaMarcas);
            return ok(jsonMarcas).as("application/json");
        } else if (request.accepts("application/xml")) {
            Content content = marcas.render(Marcas.listaMarcas);
            return Results.ok(content).as("application/xml");
        }
        return Results.status(406);
    }

}
