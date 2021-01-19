package controllers;

import play.mvc.Controller;
import play.mvc.Result;

/**
 * Categories controller
 */
public class CategoriesController extends Controller {

    public Result index() {
        return ok(views.html.index.render());
    }

}
