package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.*;
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
import java.util.ArrayList;

/**
 * BrandController
 */
public class BrandController extends Controller {

    @Inject
    FormFactory formfactory;

    public Result getBrandItem(Http.Request request, String name) {
        Marca brandTofind = Marca.findMarcaByNombre(name);
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

    public Result putBrandItem(Http.Request request) {
        Form<Marca> m = formfactory.form(Marca.class).bindFromRequest(request);
        if(m.hasErrors()) {
            return Results.badRequest(m.errorsAsJson());
        } else {
            Marca brand = m.get();
            Marca brandTofind = Marca.findMarcaByNombre(brand.getNombre());
            Marcas brandInBrands = Marcas.findById(brandTofind.getId());
            if (brandTofind != null) {
                brandTofind.setNombre(brand.getNombre());
                brandTofind.setVegano(brand.getVegano());
                brandInBrands.setNombre(brand.getNombre());
                brandInBrands.update();
                brandTofind.update();
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
        }
        return status(406);
    }

    public Result deleteBrandItem(Http.Request request) {
        Form<Marca> m = formfactory.form(Marca.class).bindFromRequest(request);
        if(m.hasErrors()) {
            return Results.badRequest(m.errorsAsJson());
        } else {
            Marca brand = m.get();
            Marca brandToDelete = Marca.findMarcaByNombre(brand.getNombre());
            Marcas brandToDeleteInBrands = Marcas.findByNombre(brand.getNombre());
            if (brandToDelete != null) {
                // Eliminar los productos que tiene
                ArrayList<Producto> productoIterable = new ArrayList<Producto>();
                for (Producto product : brandToDelete.getProductoID()) {
                    productoIterable.add(product);
                }
                for (Integer i = 0; i < productoIterable.size(); i++) {
                    brandToDelete.deleteProducto(productoIterable.get(i));
                    for (Categoria category: productoIterable.get(i).getCategoriaID()) {
                        category.removeProductoOfCategory(productoIterable.get(i));
                        category.save();
                    }
                    Codigo code = Codigo.findCodigoByProduct(productoIterable.get(i).getId());
                    if (code != null) {
                        code.deleteCodigoDeProducto(code.getIdProducto());
                        code.delete();
                    }
                    brandToDelete.save();
                    productoIterable.get(i).delete();
                }
                //Eliminar las relaciones que haya con categoria
                ArrayList<Categoria> categoryIterable = new ArrayList<Categoria>();
                for (Categoria category : brandToDelete.getCategoriaID()) {
                    categoryIterable.add(category);
                }
                for (Integer i = 0; i < categoryIterable.size(); i++) {
                    brandToDelete.removeCategoryOfBrand(categoryIterable.get(i));
                    categoryIterable.get(i).save();
                }
                brandToDeleteInBrands.deleteMarca(brandToDelete);
                brandToDeleteInBrands.save();
                brandToDeleteInBrands.delete();
                brandToDelete.delete();
                if (request.accepts("application/json")) {
                    JsonNode jsonMarcas = play.libs.Json.toJson(Marcas.getListaMarcas());
                    return ok(jsonMarcas).as("application/json");
                } else if (request.accepts("application/xml")) {
                    Content content = marcas.render(Marcas.getListaMarcas());
                    return Results.ok(content).as("application/xml");
                }
            } else {
                return Results.notFound();
            }
        }
        return status(406);
    }

}
