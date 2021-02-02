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
        // TO DO: return JSON!
        if (Marcas.getListaMarcas().size() == 0) {
            return Results.notFound();
        } else {
            if (request.accepts("application/json")) {
                JsonNode jsonMarcas = play.libs.Json.toJson(Marcas.getListaMarcas());
                return ok(jsonMarcas).as("application/json");
            } else if (request.accepts("application/xml")) {
                Content content = marcas.render(Marcas.getListaMarcas());
                if (Marcas.getListaMarcas().size() != 0) {
                    return Results.ok(content).as("application/xml");
                } else {
                    return Results.notFound();
                }
            }
        }
        return Results.status(406);
    }

    public Result postBrands(Http.Request request) {
        // TO DO: return JSON!
        Form<Marca> b = formfactory.form(Marca.class).bindFromRequest(request);
        Form<Marcas> bs = formfactory.form(Marcas.class).bindFromRequest(request);
        if (b.hasErrors() || bs.hasErrors()) {
            return Results.badRequest(b.errorsAsJson());
        } else {
            Marca brand = b.get();
            Marcas brands = bs.get();
            Marca brandRepeated = Marca.findMarcaByNombre(brand.getNombre());
            if (brandRepeated != null) {
                return Results.badRequest("La marca ya existe en el servidor");
            } else {
                brands.addMarca(brand);
                brands.save();
                if (request.accepts("application/json")) {
                    JsonNode jsonMarcas = play.libs.Json.toJson(Marcas.getListaMarcas());
                    return ok(jsonMarcas).as("application/json");
                } else if (request.accepts("application/xml")) {
                    Content content = marcas.render(Marcas.getListaMarcas());
                    return Results.ok(content).as("application/xml");
                }
            }
        }
        return Results.status(406);
    }

}
