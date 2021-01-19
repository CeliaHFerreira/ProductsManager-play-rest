package controllers;

import play.mvc.Controller;
import play.mvc.Result;

/**
 * BrandsController
 */
public class BrandsController extends Controller {

    public Result index() {
        return ok(views.html.index.render());
    }

}
