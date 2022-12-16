package com.kunal.vertxAppFirst;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class MainVerticle extends AbstractVerticle {

  Router router = Router.router(vertx);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    attachRoutes();
    vertx.createHttpServer().requestHandler(router)
        .listen(8888, httpServerAsyncResult -> {
          if(httpServerAsyncResult.succeeded()) {
            startPromise.complete();
            System.out.println("HTTP SERVER started on port 8888");
          } else {
            startPromise.fail(httpServerAsyncResult.cause());
          }
        });

  }

  public void attachRoutes() {
    vertx.deployVerticle(new HelloVerticle());
    router.get("/v1/list").handler(this::list);
  }

  public void list(RoutingContext routingContext) {
    vertx.eventBus().request("hello.vertx.addr", "", reply -> {
      routingContext.request().response().end((String)reply.result().body());
    });

  }
}
