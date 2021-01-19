package controllers;

import play.mvc.Controller;
import play.mvc.Result;

/**
 * Product Controller
 */
public class ProductController extends Controller {

    public Result index() {
        return ok(views.html.index.render());
    }

}
