package com.example.sghapi.exception;

public class SenhaInvalidaException extends RuntimeException{
    public SenhaInvalidaException() {
        super("Senha inválida");
    }
}
