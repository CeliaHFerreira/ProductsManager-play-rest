package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Categoria;
import models.Marca;
import models.Producto;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import play.twirl.api.Content;
import views.xml.categoria;
import views.xml.categorias;
import views.xml.productos;

import javax.inject.Inject;
import java.util.List;

/**
 * Categories controller
 */
public class CategoriesController extends Controller {

    @Inject
    FormFactory formfactory;

    public Result getCategorie(Http.Request request, String name) {
        // TO DO: return JSON! and correct response, now return all the products
        Categoria categorieTofind = Categoria.findCategoriaByNombre(name);
        if (categorieTofind != null) {
            if (request.accepts("application/json")) {
                JsonNode jsonFindCategoria = play.libs.Json.toJson(categorieTofind);
                return ok(jsonFindCategoria).as("application/json");
            } else if (request.accepts("application/xml")) {
                List<Producto> productList = Producto.getListaProductosCategoria(categorieTofind.getCategoriaID());
                Content content = productos.render(productList);
                return Results.ok(content).as("application/xml");
            }
        } else {
            return Results.notFound();
        }
        return status(406);
    }

    public Result postCategorie(Http.Request request, String name) {
        // TO DO: return JSON!
        Categoria categorieTofind = Categoria.findCategoriaByNombre(name);
        Form<Producto> p = formfactory.form(Producto.class).bindFromRequest(request);
        if(p.hasErrors()) {
            return Results.badRequest(p.errorsAsJson());
        } else {
            Producto product = p.get();
            Producto productRepeated = Producto.findProductoByNombre(product.getNombre());
            if (productRepeated != null) {
                return Results.badRequest("El producto ya existe en el servidor");
            } else {
                Marca brand = Marca.findMarcaByNombre(product.getNombreMarca());
                if (categorieTofind == null) {
                    return Results.notFound("La categoria no existe");
                } else if (brand == null) {
                    return Results.notFound("La marca no existe");
                }
                brand.addProducto(product);
                product.save();
                if (request.accepts("application/json")) {
                    JsonNode jsonProductos = play.libs.Json.toJson(Producto.listaProducto);
                    return ok(jsonProductos).as("application/json");
                } else if (request.accepts("application/xml")) {
                    Content content = productos.render(Producto.getListaProductos());
                    return Results.ok(content).as("application/xml");
                }
            }
        }
        return status(406);
    }

    public Result putCategorie(Http.Request request, String name) {
        // TO DO: return JSON!
        Form<Categoria> c = formfactory.form(Categoria.class).bindFromRequest(request);
        if (c.hasErrors()) {
            return Results.badRequest(c.errorsAsJson());
        } else {
            Categoria categorie = c.get();
            Categoria categorieTofind = Categoria.findCategoriaByNombre(name);
            if (categorieTofind != null) {
                categorieTofind.setNombre(categorie.getNombre());
                if (request.accepts("application/json")) {
                    JsonNode cataegorieUpdated = play.libs.Json.toJson(categorieTofind);
                    return ok(cataegorieUpdated).as("application/json");
                } else if (request.accepts("application/xml")) {
                    Content content = categoria.render(categorieTofind);
                    return Results.ok(content).as("application/xml");
                }
            } else {
                return Results.notFound();
            }
        }
        return status(406);
    }

    public Result deleteCategorie(Http.Request request, String name) {
        // TO DO: return JSON!
        Categoria categorieTofind = Categoria.findCategoriaByNombre(name);
        if (categorieTofind != null) {
            //Categoria.listaCategoria.remove(categorieTofind);
            categorieTofind.delete();
            if (request.accepts("application/json")) {
                JsonNode jsonCategorias = play.libs.Json.toJson(Categoria.listaCategoria);
                return ok(jsonCategorias).as("application/json");
            } else if (request.accepts("application/xml")) {
                Content content = categorias.render(Categoria.listaCategoria);
                return Results.ok(content).as("application/xml");
            }
        } else {
            return Results.notFound();
        }
        return status(406);
    }
}
