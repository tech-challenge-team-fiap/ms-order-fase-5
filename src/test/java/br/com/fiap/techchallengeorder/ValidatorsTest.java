package br.com.fiap.techchallengeorder;

import br.com.fiap.techchallengeorder.domain.validation.CpfValidator;
import br.com.fiap.techchallengeorder.domain.validation.EmailValidator;
import br.com.fiap.techchallengeorder.domain.validation.PhoneNumberValidator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidatorsTest {

    @Test
    void shouldValidateCpf() {
        assertTrue(CpfValidator.isValidCpf("550.705.870-99"));
        assertFalse(CpfValidator.isValidCpf("530.705.870-99"));
    }


    @Test
    void shouldValidateEmail() {
        assertTrue(EmailValidator.isValidEmail("teste@hotmail.com"));
        assertFalse(EmailValidator.isValidEmail("testehotmail.com"));
    }


    @Test
    void shouldValidatePhoneNumber() {
        assertTrue(PhoneNumberValidator.isValidPhoneNumber("+5511987654321"));
        assertFalse(PhoneNumberValidator.isValidPhoneNumber("11987654321"));
    }
}