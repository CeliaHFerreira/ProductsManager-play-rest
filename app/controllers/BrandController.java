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
        Marca brandTofind = Marca.findMarca(name);
        if (brandTofind != null) {
            if (request.accepts("application/json")) {
                JsonNode jsonFindMarca = play.libs.Json.toJson(brandTofind);
                return ok(jsonFindMarca).as("application/json");
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
        //to do update ids
        Form<Marca> m = formfactory.form(Marca.class).bindFromRequest(request);
        Marca brand = m.get();
        Marca brandTofind = Marca.findMarca(name);
        Marcas brandInBrands = Marcas.findMarca(name);
        if (brandTofind != null) {
            brandTofind.setNombre(brand.getNombre());
            brandTofind.setVegano(brand.getVegano());
            brandInBrands.setNombre(brand.getNombre());
            if (request.accepts("application/json")) {
                JsonNode brandUpdated = play.libs.Json.toJson(brandTofind);
                return ok(brandUpdated).as("application/json");
            } else if (request.accepts("application/xml")) {
                Content content = marca.render(brandTofind);
                return Results.ok(content).as("application/xml");
            }
        } else {
            return Results.notFound();
        }
        return status(406);
    }

    public Result deleteBrandItem(Http.Request request, String name) {
        Marca brandTofind = Marca.findMarca(name);
        Marcas brandInBrands = Marcas.findMarca(name);
        if (brandTofind != null) {
            Marcas.listaMarcas.remove(brandInBrands);
            Marca.listaMarca.remove(brandTofind);
            if (request.accepts("application/json")) {
                JsonNode jsonMarcas = play.libs.Json.toJson(Marcas.listaMarcas);
                return ok(jsonMarcas).as("application/json");
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
