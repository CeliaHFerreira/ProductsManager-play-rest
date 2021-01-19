package controllers;

import play.mvc.Controller;
import play.mvc.Result;

/**
 * BrandController
 */
public class BrandController extends Controller {

    public Result index() {
        return ok(views.html.index.render());
    }

}
