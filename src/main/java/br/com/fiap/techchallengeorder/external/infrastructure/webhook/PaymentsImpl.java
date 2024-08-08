package br.com.fiap.techchallengeorder.external.infrastructure.webhook;

import br.com.fiap.techchallengeorder.domain.exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;


@Component
public class PaymentsImpl implements Payments {

    private static final Logger logger = LoggerFactory.getLogger(PaymentsImpl.class);

    private static ClientAndServer mockServer;

    private String URL_PATH = "/check-payments/";

    public void startSetupMockServer(String numberOrder){
        logger.info("[PAYMENTS] Starting server with Mock.");
        mockServer = ClientAndServer.startClientAndServer(1080); // MockServer port

        new MockServerClient("localhost", 1080)
                .when(
                        request()
                                .withMethod(HttpMethod.GET.name())
                                .withPath(URL_PATH + numberOrder)
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody("true")
                );
    }

    public void stopMockServer(){
        logger.info("[PAYMENTS] Stopped server with mock." );
        mockServer.stop();
    }

    /**
     * Simulando uma chamada HTTP, o resultado não é usado por enquanto sempre retorna true
     * @param numberOrder
     * @return
     */
    @Override
    public boolean checkStatusPayments(String numberOrder) {
        try {
            startSetupMockServer(numberOrder);

            // Create a WebClient configured to point to the MockServer
            WebClient webClient = WebClient.builder().baseUrl("http://localhost:1080").build();

            // Simulate an HTTP call to the fake route using WebClient
            String responseBody = webClient.get()
                    .uri(URL_PATH + numberOrder)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            stopMockServer();

            logger.info("[PAYMENTS] Return API Mock Payments");
            return Boolean.parseBoolean(responseBody);

        } catch (Exception e) {
            throw new BaseException(e.getMessage());
        }
    }
}
