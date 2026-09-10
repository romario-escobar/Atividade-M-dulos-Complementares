package br.com.ecommerce.excecoes;

public class LogisticaException extends Exception {

    private static final long serialVersionUID = 1L;

    public LogisticaException(String mensagem) {
        super(mensagem);
    }
}
