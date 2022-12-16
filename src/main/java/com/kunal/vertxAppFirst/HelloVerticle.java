package com.kunal.vertxAppFirst;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class HelloVerticle extends AbstractVerticle {

  @Override
  public void stop(Promise<Void> stopPromise) throws Exception {

    vertx.eventBus().consumer("hello.vertx.addr", msg-> {
      msg.reply("Hello from Hello Verticle.. Lists");
    });
  }
}
