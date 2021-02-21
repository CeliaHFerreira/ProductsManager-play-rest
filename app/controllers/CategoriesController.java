package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Categoria;
import models.Codigo;
import models.Marca;
import models.Producto;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.Messages;
import play.i18n.MessagesApi;
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

    private final play.i18n.MessagesApi messagesApi;

    @Inject
    public CategoriesController(MessagesApi messagesApi) {
        this.messagesApi = messagesApi;
    }


    public Result getCategory(Http.Request request) {
        Form<Categoria> c = formfactory.form(Categoria.class).bindFromRequest(request);
        if (c.hasErrors()) {
            return Results.badRequest(c.errorsAsJson());
        } else {
            Categoria category = c.get();
            Categoria categoryTofind = Categoria.findCategoriaByNombre(category.getNombre());
            if (categoryTofind != null) {
                if (request.accepts("application/json")) {
                    JsonNode jsonFindCategory = play.libs.Json.toJson(categoryTofind);
                    return ok(jsonFindCategory).as("application/json");
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

    public Result postProduct(Http.Request request, String name) {
        Categoria categoryTofind = Categoria.findCategoriaByNombre(name);
        Form<Producto> p = formfactory.form(Producto.class).bindFromRequest(request);
        if(p.hasErrors()) {
            return Results.badRequest(p.errorsAsJson());
        } else {
            Producto product = p.get();
            // Ver si esta repetido el producto
            Producto productRepeated = Producto.findProductoByNombreAndBrand(product.getNombre(), product.getNombreMarca());
            if (productRepeated != null && productRepeated.getNombreMarca() != product.getNombreMarca()) {
                Messages messages = this.messagesApi.preferred(request);
                String response = messages.at("product.repeated");
                return Results.badRequest(response);
            } else {
                Marca brand = Marca.findMarcaByNombre(product.getNombreMarca());
                if (categoryTofind == null) {
                    // No existe categoria
                    Messages messages = this.messagesApi.preferred(request);
                    String response = messages.at("category.not.found");
                    return Results.notFound(response);
                } else if (brand == null) {
                    // No existe marca
                    Messages messages = this.messagesApi.preferred(request);
                    String response = messages.at("brand.not.found");
                    return Results.notFound(response);
                }
                // Añadir producto
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
            Categoria categoryToDelete = Categoria.findCategoriaByNombre(category.getNombre());
            if (categoryToDelete != null) {
                // Buscar todos los productos de la categoria
                ArrayList<Producto> productoIterable = new ArrayList<Producto>();
                for (Producto product : categoryToDelete.getProductoID()) {
                    productoIterable.add(product);
                }
                for(Integer i = 0; i < productoIterable.size(); i ++) {
                    // Eliminar codigo de barras de cada producto
                    Codigo code = Codigo.findCodigoByProduct(productoIterable.get(i).getId());
                    if (code != null) {
                        code.deleteCodigoDeProducto(code.getIdProducto());
                        code.delete();
                        productoIterable.get(i).save();
                    }
                    // Eliminar el producto de la marca
                    Marca brandInProduct = Marca.findMarcaByNombre(productoIterable.get(i).getNombreMarca());
                    brandInProduct.deleteProducto(productoIterable.get(i));
                    brandInProduct.save();
                    // Eliminar el producto
                    categoryToDelete.removeProductoOfCategory(productoIterable.get(i));
                    categoryToDelete.save();
                    productoIterable.get(i).delete();
                }
                // Buscar las marcas de la categoria
                ArrayList<Marca> marcaIterable = new ArrayList<Marca>();
                for (Marca brand : categoryToDelete.getMarcaID()) {
                    marcaIterable.add(brand);
                }
                for(Integer i = 0; i < marcaIterable.size(); i++) {
                    // Eliminar las marcas
                    categoryToDelete.removeMarcaOfCategory(marcaIterable.get(i));
                    marcaIterable.get(i).save();
                }
                // Eliminar la categoria
                categoryToDelete.delete();
                if (request.accepts("application/json")) {
                    JsonNode jsonCategories = play.libs.Json.toJson(Categoria.getListaCategorias());
                    return ok(jsonCategories).as("application/json");
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
