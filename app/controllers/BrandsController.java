package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Marca;
import models.Marcas;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import play.twirl.api.Content;
import views.xml.marcas;

import javax.inject.Inject;

/**
 * BrandsController
 */
public class BrandsController extends Controller {

    @Inject
    FormFactory formfactory;

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
        Form<Marca> b = formfactory.form(Marca.class).bindFromRequest(request);
        Marca brand = b.get();
        Form<Marcas> bs = formfactory.form(Marcas.class).bindFromRequest(request);
        Marcas brands = bs.get();
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
