package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Marca;
import models.Marcas;
import org.h2.util.json.JSONArray;
import org.h2.util.json.JSONObject;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
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
        if(request.accepts("application/json")) {
            JsonNode jsonMarcas = play.libs.Json.toJson(Marcas.listaMarcas);
            if (jsonMarcas.size() == 0) {
                return Results.notFound();
            } else {
                return ok(jsonMarcas).as("application/json");
            }
        } else if (request.accepts("application/xml")) {
            Content content = marcas.render(Marcas.getListaMarcas());
            if (Marcas.getListaMarcas().size() != 0) {
                return Results.ok(content).as("application/xml");
            } else {
                return Results.notFound();
            }
        }
        return Results.status(406);
    }

    public Result postBrands(Http.Request request) {
        // TO DO: return JSON!
        Form<Marca> b = formfactory.form(Marca.class).bindFromRequest(request);
        Marca brand = b.get();
        Form<Marcas> bs = formfactory.form(Marcas.class).bindFromRequest(request);
        Marcas brands = bs.get();
        Marca brandRepeated = Marca.findMarca(brand.getNombre());
        if (brandRepeated != null) {
            return Results.badRequest("La marca ya existe en el servidor");
        } else {
            brands.addMarca(brand);
            brands.save();
            if (request.accepts("application/json")) {
                //JsonNode jsonMarcas = play.libs.Json.toJson(Marcas.listaMarcas);
            /*for (Marcas item : Marcas.getListaMarcas()) {
                Marcas.listaMarcas.add(item);
            }*/
                System.out.println(Marcas.listaMarcas);

                JsonNode jsonMarcas = play.libs.Json.toJson(Marcas.getListaMarcas());
                //System.out.println(play.libs.Json.toJson(item));

                //System.out.println(jsonMarcas);
                return ok(jsonMarcas).as("application/json");
            } else if (request.accepts("application/xml")) {
                Content content = marcas.render(Marcas.getListaMarcas());
                return Results.ok(content).as("application/xml");
            }
        }
        return Results.status(406);
    }

}
