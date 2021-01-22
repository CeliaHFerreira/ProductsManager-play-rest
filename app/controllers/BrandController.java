package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Marca;
import models.Marcas;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

/**
 * BrandController
 */
public class BrandController extends Controller {

    public Result getBrandItem(String name) {
        //to do content-negotiation
        Marca brandTofind = Marca.findMarca(name);
        if (brandTofind != null) {
            JsonNode jsonFindMarca = play.libs.Json.toJson(brandTofind);
            return ok(jsonFindMarca).as("application/json");
        } else {
            return Results.notFound();
        }
    }

    public Result putBrandItem(Http.Request request, String name) {
        //to do content-negotiation update ids
        JsonNode json = request.body().asJson();
        Marca brandTofind = Marca.findMarca(name);
        Marcas brandInBrands = Marcas.findMarca(name);
        if (brandTofind != null) {
            brandTofind.setNombre(json.get("nombre").asText());
            brandTofind.setVegano(json.get("vegano").asBoolean());
            brandInBrands.setNombre(json.get("nombre").asText());
            JsonNode brandUpdated = play.libs.Json.toJson(brandTofind);
            return ok(brandUpdated).as("application/json");
        } else {
            return Results.notFound();
        }
    }

    public Result deleteBrandItem(String name) {
        //to do content-negotiation
        Marca brandTofind = Marca.findMarca(name);
        Marcas brandInBrands = Marcas.findMarca(name);
        if (brandTofind != null) {
            Marcas.listaMarcas.remove(brandInBrands);
            Marca.listaMarca.remove(brandTofind);
            JsonNode jsonMarcas = play.libs.Json.toJson(Marcas.listaMarcas);
            return ok(jsonMarcas).as("application/json");
        } else {
            return Results.notFound();
        }
    }

}
