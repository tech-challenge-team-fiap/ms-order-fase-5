package br.com.fiap.techchallengeorder.external.infrastructure.webhook;

public interface Payments {

    boolean checkStatusPayments(String id);

    public void startSetupMockServer(String numberOrder);

    public void stopMockServer();
}
