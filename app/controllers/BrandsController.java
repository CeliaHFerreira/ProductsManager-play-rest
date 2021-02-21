package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Marca;
import models.Marcas;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.Messages;
import play.i18n.MessagesApi;
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

    private final play.i18n.MessagesApi messagesApi;

    @Inject
    public BrandsController(MessagesApi messagesApi) {
        this.messagesApi = messagesApi;
    }


    public Result getBrands(Http.Request request) {
        if (Marcas.getListaMarcas().size() == 0) {
            return Results.notFound();
        } else {
            if (request.accepts("application/json")) {
                JsonNode jsonBrands = play.libs.Json.toJson(Marcas.getListaMarcas());
                return ok(jsonBrands).as("application/json");
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
        Form<Marca> b = formfactory.form(Marca.class).bindFromRequest(request);
        Form<Marcas> bs = formfactory.form(Marcas.class).bindFromRequest(request);
        if (b.hasErrors() || bs.hasErrors()) {
            return Results.badRequest(b.errorsAsJson());
        } else {
            Marca brand = b.get();
            Marcas brands = bs.get();
            // Controlar que no esta ya introducida la marca
            Marca brandRepeated = Marca.findMarcaByNombre(brand.getNombre());
            if (brandRepeated != null) {
                Messages messages = this.messagesApi.preferred(request);
                String response = messages.at("brand.repeated");
                return Results.badRequest(response);
            } else {
                // Añadir marca
                brands.addMarca(brand);
                brands.save();
                if (request.accepts("application/json")) {
                    JsonNode jsonBrands = play.libs.Json.toJson(Marcas.getListaMarcas());
                    return ok(jsonBrands).as("application/json");
                } else if (request.accepts("application/xml")) {
                    Content content = marcas.render(Marcas.getListaMarcas());
                    return Results.ok(content).as("application/xml");
                }
            }
        }
        return Results.status(406);
    }

}
