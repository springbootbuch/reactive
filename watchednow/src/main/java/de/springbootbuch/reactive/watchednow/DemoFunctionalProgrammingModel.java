package de.springbootbuch.reactive.watchednow;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;

import java.util.concurrent.CountDownLatch;

import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * Part of springbootbuch.de.
 *
 * @author Michael J. Simons
 * @author @rotnroll666
 */
public class DemoFunctionalProgrammingModel {
	public static void main(String...a) {

		HandlerFunction<ServerResponse> helloWorld =
			request -> ServerResponse.ok().body(
				Mono.just("Hello, World"), String.class
			);


		RouterFunction<ServerResponse> helloSpringRoute =
			request ->  {
				if(request.method() == GET &&
					request.path().equals("/hellospring")
				) {
					return Mono.just(r ->
						ok().body(
							Mono.just("Functional Spring"),
							String.class
						)
					);
				} else {
					return Mono.empty();
				}
			};

		RouterFunction<ServerResponse> helloWorldRoute =
			RouterFunctions.route(
				GET("/helloworld"), helloWorld);

		RouterFunction routes = helloSpringRoute
			.and(helloWorldRoute);

		HttpHandler httpHandler = RouterFunctions
			.toHttpHandler(routes);
		HttpServer.create().port(8080)
			.handle(new ReactorHttpHandlerAdapter(httpHandler))
			.bind()
			.block();

		CountDownLatch latch = new CountDownLatch(1);
		Thread awaitThread = new Thread(() -> {
			try {
				latch.await();
			} catch (InterruptedException ex) {
			}

		});
		awaitThread.setDaemon(false);
		awaitThread.start();
	}
}
