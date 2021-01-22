package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Marca;
import models.Marcas;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

/**
 * BrandsController
 */
public class BrandsController extends Controller {

    public Result getBrands() {
        // TO DO: content-negotiation
        JsonNode jsonMarcas = play.libs.Json.toJson(Marcas.listaMarcas);
        if (jsonMarcas.size() == 0) {
            return Results.notFound();
        } else {
            return ok(jsonMarcas).as("application/json");
        }
    }

    public Result postBrands(Http.Request request) {
        // TO DO: content-negotiation and not repeat brand update ids
        JsonNode json = request.body().asJson();
        Marcas brands = new Marcas(json.get("marcaid").asText(), json.get("nombre").asText());
        Marca brand = new Marca(json.get("marcaid").asText(), json.get("nombre").asText(), null, null, null);
        Marcas.listaMarcas.add(brands);
        Marca.listaMarca.add(brand);
        JsonNode jsonMarcas = play.libs.Json.toJson(Marcas.listaMarcas);
        return ok(jsonMarcas).as("application/json");
    }

}
