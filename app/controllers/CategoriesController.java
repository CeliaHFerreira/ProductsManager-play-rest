package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Categoria;
import models.Codigo;
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
import java.util.*;

/**
 * Categories controller
 */
public class CategoriesController extends Controller {

    @Inject
    FormFactory formfactory;

    public Result getCategory(Http.Request request) {
        Form<Categoria> c = formfactory.form(Categoria.class).bindFromRequest(request);
        if (c.hasErrors()) {
            return Results.badRequest(c.errorsAsJson());
        } else {
            Categoria category = c.get();
            Categoria categoryTofind = Categoria.findCategoriaByNombre(category.getNombre());
            if (categoryTofind != null) {
                if (request.accepts("application/json")) {
                    JsonNode jsonFindCategoria = play.libs.Json.toJson(categoryTofind);
                    return ok(jsonFindCategoria).as("application/json");
                } else if (request.accepts("application/xml")) {
                    Content content = categoria.render(categoryTofind);
                    return Results.ok(content).as("application/xml");
                }
            } else {
                return Results.notFound();
            }
        }
        return status(406);
    }

    public Result postCategory(Http.Request request, String name) {
        Categoria categoryTofind = Categoria.findCategoriaByNombre(name);
        Form<Producto> p = formfactory.form(Producto.class).bindFromRequest(request);
        if(p.hasErrors()) {
            return Results.badRequest(p.errorsAsJson());
        } else {
            Producto product = p.get();
            // TO DOOOOOOOOO
            //Controlar que puede haber más de uno que se llame igual
            // Para que sea repetido tiene que llamarse igual, de la misma marca y en la misma categoria
            Producto productRepeated = Producto.findProductoByNombre(product.getNombre());
            if (productRepeated != null) {
                return Results.badRequest("El producto ya existe en el servidor");
            } else {
                Marca brand = Marca.findMarcaByNombre(product.getNombreMarca());
                if (categoryTofind == null) {
                    return Results.notFound("La categoria no existe");
                } else if (brand == null) {
                    return Results.notFound("La marca no existe");
                }
                categoryTofind.addProductoToCategory(product);
                brand.addCategoryToBrand(categoryTofind);
                brand.addProducto(product);
                brand.save();
                product.save();
                if (request.accepts("application/json")) {
                    JsonNode jsonProductos = play.libs.Json.toJson(Producto.getListaProductos());
                    return ok(jsonProductos).as("application/json");
                } else if (request.accepts("application/xml")) {
                    Content content = productos.render(Producto.getListaProductos());
                    return Results.ok(content).as("application/xml");
                }
            }
        }
        return status(406);
    }

    public Result putCategory(Http.Request request, String name) {
        Form<Categoria> c = formfactory.form(Categoria.class).bindFromRequest(request);
        if (c.hasErrors()) {
            return Results.badRequest(c.errorsAsJson());
        } else {
            Categoria category = c.get();
            Categoria categoryTofind = Categoria.findCategoriaByNombre(name);
            if (categoryTofind != null) {
                categoryTofind.setNombre(category.getNombre());
                categoryTofind.update();
                if (request.accepts("application/json")) {
                    JsonNode cataegoryUpdated = play.libs.Json.toJson(Categoria.findCategoriaByNombre(categoryTofind.getNombre()));
                    return ok(cataegoryUpdated).as("application/json");
                } else if (request.accepts("application/xml")) {
                    Content content = categoria.render(Categoria.findCategoriaByNombre(categoryTofind.getNombre()));
                    return Results.ok(content).as("application/xml");
                }
            } else {
                return Results.notFound();
            }
        }
        return status(406);
    }

    public Result deleteCategory(Http.Request request) {
        Form<Categoria> c = formfactory.form(Categoria.class).bindFromRequest(request);
        if (c.hasErrors()) {
            return Results.badRequest(c.errorsAsJson());
        } else {
            Categoria category = c.get();
            Categoria categorieToDelete = Categoria.findCategoriaByNombre(category.getNombre());
            if (categorieToDelete != null) {
                ArrayList<Producto> productoIterable = new ArrayList<Producto>();
                for (Producto product : categorieToDelete.getProductoID()) {
                    productoIterable.add(product);
                }
                for(Integer i = 0; i < productoIterable.size(); i ++) {
                    Codigo code = Codigo.findCodigoByProduct(productoIterable.get(i).getId());
                    if (code != null) {
                        code.deleteCodigoDeProducto(code.getIdProducto());
                        code.delete();
                        productoIterable.get(i).save();
                    }

                    Marca brandInProduct = Marca.findMarcaByNombre(productoIterable.get(i).getNombreMarca());
                    brandInProduct.deleteProducto(productoIterable.get(i));
                    brandInProduct.save();

                    categorieToDelete.removeProductoOfCategory(productoIterable.get(i));
                    categorieToDelete.save();
                    productoIterable.get(i).delete();
                }

                ArrayList<Marca> marcaIterable = new ArrayList<Marca>();
                for (Marca brand : categorieToDelete.getMarcaID()) {
                    marcaIterable.add(brand);
                }
                for(Integer i = 0; i < marcaIterable.size(); i++) {
                    categorieToDelete.removeMarcaOfCategory(marcaIterable.get(i));
                    marcaIterable.get(i).save();
                }

                categorieToDelete.delete();
                if (request.accepts("application/json")) {
                    JsonNode jsonCategorias = play.libs.Json.toJson(Categoria.getListaCategorias());
                    return ok(jsonCategorias).as("application/json");
                } else if (request.accepts("application/xml")) {
                    Content content = categorias.render(Categoria.getListaCategorias());
                    return Results.ok(content).as("application/xml");
                }
            } else {
                return Results.notFound();
            }
        }
        return status(406);
    }
}
