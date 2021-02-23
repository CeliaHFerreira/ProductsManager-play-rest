package controllers;

import models.Categoria;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.GET;
import static play.test.Helpers.POST;
import static play.test.Helpers.route;

public class HomeControllerTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void testIndex() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/");

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }

    @Test
    public void getManagerFail() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/");
        Result result = new HomeController(null).getManager(request.build());
        assertEquals(NOT_FOUND, result.status());
    }

    @Test
    public void postManager() {
        Categoria category = new Categoria();
        category.setNombre("champu");
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .bodyJson(play.libs.Json.toJson(category))
                .uri("/");
        Result result = new HomeController(null).postManager(request.build());
        assertEquals(OK, result.status());
    }

    @Test
    public void getManagerSuccess() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/");
        Categoria category = new Categoria();
        category.setNombre("champu");
        category.save();
        Result result = new HomeController(null).getManager(request.build());
        assertEquals(OK, result.status());
    }

}
