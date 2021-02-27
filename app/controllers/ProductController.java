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
import views.xml.producto;
import views.xml.productos;

import javax.inject.Inject;
import java.util.List;

/**
 * Product Controller
 */
public class ProductController extends Controller {
    @Inject
    FormFactory formfactory;

    public Result getProducts(Http.Request request) {
        if (Producto.getListaProductos().size() == 0) {
            return Results.notFound();
        } else {
            if (request.accepts("application/json")) {
                JsonNode jsonProducts = play.libs.Json.toJson(Producto.getListaProductos());
                return ok(jsonProducts).as("application/json");
            } else if (request.accepts("application/xml")) {
                Content content = productos.render(Producto.getListaProductos());
                if (Producto.getListaProductos().size() != 0) {
                    return Results.ok(content).as("application/xml");
                } else {
                    return Results.notFound();
                }
            }
        }
        return status(406);
    }

    public Result getProductItem(Http.Request request, Long id) {
        Producto productTofind = Producto.findProductoById(id);
        if (productTofind != null) {
            if (request.accepts("application/json")) {
                JsonNode jsonFindProduct = play.libs.Json.toJson(productTofind);
                return ok(jsonFindProduct).as("application/json");
            } else if (request.accepts("application/xml")) {
                Content content = producto.render(productTofind);
                return Results.ok(content).as("application/xml");
            }
        } else {
            return Results.notFound();
        }
        return status(406);
    }

    public Result putProductItem(Http.Request request, Long id) {
        Form<Producto> p = formfactory.form(Producto.class).bindFromRequest(request);
        if (p.hasErrors()) {
            return Results.badRequest(p.errorsAsJson());
        }else {
            Producto product = p.get();
            Producto productTofind = Producto.findProductoById(id);
            if (productTofind != null) {
                productTofind.setNombre(product.getNombre());
                productTofind.setVegano(product.getVegano());
                productTofind.setAptoCG(product.getAptoCG());
                productTofind.setPVP(product.getPVP());
                productTofind.setHNR(product.getHNR());
                productTofind.setNombreMarca(product.getNombreMarca());
                Marca brand = Marca.findMarcaByNombre(product.getNombreMarca());
                brand.update();
                productTofind.update();
                if (request.accepts("application/json")) {
                    JsonNode productUpdated = play.libs.Json.toJson(productTofind);
                    return ok(productUpdated).as("application/json");
                } else if (request.accepts("application/xml")) {
                    Content content = producto.render(productTofind);
                    return Results.ok(content).as("application/xml");
                }
            } else {
                return Results.notFound();
            }
        }
        return status(406);
    }

    public Result deleteProductItem(Http.Request request, Long id) {
        Producto productTofind = Producto.findProductoById(id);
        Marca brandOfProduct = Marca.findMarcaByNombre(productTofind.getNombreMarca());
        if (productTofind != null) {
            // Eliminar el producto de la marca
            brandOfProduct.deleteProducto(productTofind);
            // Eliminar el producto de la categoria
            for (Categoria category: productTofind.getCategoriaID()) {
                category.removeProductoOfCategory(productTofind);
                category.save();
            }
            // Eliminar el codigo del producto
            Codigo code = Codigo.findCodigoByProduct(productTofind.getId());
            if (code != null) {
                code.deleteCodigoDeProducto(code.getIdProducto());
                code.delete();
            }
            brandOfProduct.save();
            productTofind.delete();
            if (request.accepts("application/json")) {
                JsonNode jsonProduct = play.libs.Json.toJson(Producto.getListaProductos());
                return ok(jsonProduct).as("application/json");
            } else if (request.accepts("application/xml")) {
                Content content = productos.render(Producto.getListaProductos());
                return Results.ok(content).as("application/xml");
            }
        } else {
            return Results.notFound();
        }
        return status(406);
    }


    public Result getProductListBrand(Http.Request request, String name) {
        Marca marcaTofind = Marca.findMarcaByNombre(name);
        if (marcaTofind != null) {
            if (request.accepts("application/json")) {
                JsonNode jsonListProducts = play.libs.Json.toJson(Producto.getListaProductosMarca(marcaTofind.getNombre()));
                return ok(jsonListProducts).as("application/json");
            } else if (request.accepts("application/xml")) {
                List<Producto> productList = Producto.getListaProductosMarca(marcaTofind.getNombre());
                if (productList != null) {
                    Content content = productos.render(productList);
                    return Results.ok(content).as("application/xml");
                } else {
                    return Results.notFound();
                }
            }
        } else {
            return Results.notFound();
        }
        return status(406);
    }

    public Result getProductListHNR(Http.Request request) {
        JsonNode json = request.body().asJson();
        String hnr = json.get("hnr").asText();
        List<Producto> productList = Producto.findProductoByHNR(hnr);
        if (productList != null) {
            if (request.accepts("application/json")) {
                JsonNode jsonListProducts = play.libs.Json.toJson(productList);
                return ok(jsonListProducts).as("application/json");
            } else if (request.accepts("application/xml")) {
                Content content = productos.render(productList);
                return Results.ok(content).as("application/xml");
            }
        } else {
            return Results.notFound();
        }
        return status(406);
    }

    public Result getProductListVegano(Http.Request request) {
        JsonNode json = request.body().asJson();
        String vegano = json.get("vegano").asText();
        List<Producto> productList = Producto.findProductoByVegano(vegano);
        if (productList != null) {
            if (request.accepts("application/json")) {
                JsonNode jsonListProducts = play.libs.Json.toJson(productList);
                return ok(jsonListProducts).as("application/json");
            } else if (request.accepts("application/xml")) {
                Content content = productos.render(productList);
                return Results.ok(content).as("application/xml");
            }
        } else {
            return Results.notFound();
        }
        return status(406);
    }

}
