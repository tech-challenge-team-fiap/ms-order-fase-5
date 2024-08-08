package br.com.fiap.techchallengeorder.domain.utils;

import java.util.Random;

public class NumberOrderGenerator {

    private static final String CARACTERES_PERMITIDOS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int TAMANHO_NUMERO_PEDIDO = 6;
    private static final Random RANDOM = new Random();

    public static String generateNumberOrder() {
        StringBuilder numberOrder = new StringBuilder();
        for (int i = 0; i < TAMANHO_NUMERO_PEDIDO; i++) {
            char letter = CARACTERES_PERMITIDOS.charAt(RANDOM.nextInt(CARACTERES_PERMITIDOS.length()));
            numberOrder.append(letter);
        }
        return numberOrder.toString();
    }
}
