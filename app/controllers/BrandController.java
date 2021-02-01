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
import views.xml.marca;
import views.xml.marcas;

import javax.inject.Inject;

/**
 * BrandController
 */
public class BrandController extends Controller {

    @Inject
    FormFactory formfactory;

    public Result getBrandItem(Http.Request request, String name) {
        // TO DO: return JSON!
        Marca brandTofind = Marca.findMarcaByNombre(name);
        if (brandTofind != null) {
            if (request.accepts("application/json")) {
                //JsonNode jsonFindMarca = play.libs.Json.toJson(brandTofind);
                return ok().as("application/json");
            } else if (request.accepts("application/xml")) {
                Content content = marca.render(brandTofind);
                return Results.ok(content).as("application/xml");
            }
        } else {
            return Results.notFound();
        }
        return status(406);
    }

    public Result putBrandItem(Http.Request request, String name) {
        // TO DO: return JSON!
        Form<Marca> m = formfactory.form(Marca.class).bindFromRequest(request);
        if(m.hasErrors()) {
            return Results.badRequest(m.errorsAsJson());
        } else {
            Marca brand = m.get();
            Marca brandTofind = Marca.findMarcaByNombre(name);
            Marcas brandInBrands = Marcas.findByNombre(name);
            if (brandTofind != null) {
                brandTofind.setNombre(brand.getNombre());
                brandTofind.setVegano(brand.getVegano());
                brandInBrands.setNombre(brand.getNombre());
                brandInBrands.update();
                brandTofind.update();
                if (request.accepts("application/json")) {
                    //JsonNode brandUpdated = play.libs.Json.toJson(brandTofind);
                    return ok().as("application/json");
                } else if (request.accepts("application/xml")) {
                    Content content = marca.render(brandTofind);
                    return Results.ok(content).as("application/xml");
                }
            } else {
                return Results.notFound();
            }
        }
        return status(406);
    }

    public Result deleteBrandItem(Http.Request request, String name) {
        // TO DO: return JSON!
        Marca brandTofind = Marca.findMarcaByNombre(name);
        Marcas brandInBrands = Marcas.findByNombre(name);
        if (brandTofind != null) {
            brandInBrands.deleteMarca(brandTofind);
            brandTofind.delete();
            if (request.accepts("application/json")) {
                //JsonNode jsonMarcas = play.libs.Json.toJson(Marcas.listaMarcas);
                return ok().as("application/json");
            } else if (request.accepts("application/xml")) {
                Content content = marcas.render(Marcas.listaMarcas);
                return Results.ok(content).as("application/xml");
            }
        } else {
            return Results.notFound();
        }
        return status(406);
    }

}
