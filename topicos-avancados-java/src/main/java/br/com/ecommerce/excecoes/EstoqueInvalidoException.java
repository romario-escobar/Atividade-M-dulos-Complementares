package br.com.ecommerce.excecoes;

public class EstoqueInvalidoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EstoqueInvalidoException(String mensagem) {
        super(mensagem);
    }
}
