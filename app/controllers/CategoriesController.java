package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Categoria;
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

/**
 * Categories controller
 */
public class CategoriesController extends Controller {

    @Inject
    FormFactory formfactory;

    public Result getCategorie(Http.Request request, String name) {
        Categoria categorieTofind = Categoria.findCategoria(name);
        if (categorieTofind != null) {
            if (request.accepts("application/json")) {
                JsonNode jsonFindCategoria = play.libs.Json.toJson(categorieTofind);
                return ok(jsonFindCategoria).as("application/json");
            } else if (request.accepts("application/xml")) {
                Content content = categoria.render(categorieTofind);
                return Results.ok(content).as("application/xml");
            }
        } else {
            return Results.notFound();
        }
        return status(406);
    }

    public Result postCategorie(Http.Request request, String name) {
        Categoria categorieTofind = Categoria.findCategoria(name);
        Form<Producto> p = formfactory.form(Producto.class).bindFromRequest(request);
        Producto product = p.get();
        //product.setMarcaID(categorieTofind.getCategoriaID());
        if (categorieTofind == null) {
            return Results.notFound();
        }
        product.save();
        //Producto.listaProducto.add(product);
        if (request.accepts("application/json")) {
            JsonNode jsonProductos = play.libs.Json.toJson(Producto.listaProducto);
            return ok(jsonProductos).as("application/json");
        } else if (request.accepts("application/xml")) {
            Content content = productos.render(Producto.getListaProductos());
            return Results.ok(content).as("application/xml");
        }
        return status(406);
    }

    public Result putCategorie(Http.Request request, String name) {
        //to do update ids
        Form<Categoria> c = formfactory.form(Categoria.class).bindFromRequest(request);
        Categoria categorie = c.get();
        Categoria categorieTofind = Categoria.findCategoria(name);
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
        return status(406);
    }

    public Result deleteCategorie(Http.Request request, String name) {
        Categoria categorieTofind = Categoria.findCategoria(name);
        if (categorieTofind != null) {
            Categoria.listaCategoria.remove(categorieTofind);
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
