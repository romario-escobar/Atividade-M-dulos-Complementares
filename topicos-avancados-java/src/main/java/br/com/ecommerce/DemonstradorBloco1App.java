package br.com.ecommerce;

import br.com.ecommerce.excecoes.EstoqueInvalidoException;
import br.com.ecommerce.excecoes.LogisticaException;
import br.com.ecommerce.logistica.ItemLogistico;
import br.com.ecommerce.logistica.PacoteExpresso;

public class DemonstradorBloco1App {

    public static void main(String[] args) {
        System.out.println("=== BLOCO 1: CLASSES E EXCEÇÕES ===");
        tentarCriarPacote("EXP001", 2.0);
        tentarCriarPacote(null, 2.0);
        tentarCriarPacote("AB", 2.0);
        tentarCriarPacote("EXP002", 0.0);
        tentarCriarPacote("EXP003", -1.0);

        System.out.println("Itens válidos gerados: " + ItemLogistico.getTotalItensGerados());
        System.out.println("Valor recebido pelo chamador: " + demonstrarReturnEFinally());
        demonstrarTryFinally();
        System.out.println("Bloco 1 concluído.");
    }

    private static void tentarCriarPacote(String etiqueta, double peso) {
        try {
            PacoteExpresso pacote = new PacoteExpresso(etiqueta, peso);
            pacote.setDespachado(true);
            System.out.println("Pacote criado: " + pacote);
            System.out.println("Despachado: " + pacote.isDespachado());
        } catch (LogisticaException | EstoqueInvalidoException e) {
            System.out.println("Falha tratada [" + e.getClass().getSimpleName() + "]: "
                + e.getMessage());
        }
    }

    private static String demonstrarReturnEFinally() {
        try {
            System.out.println("TRY: preparando o retorno.");
            return "OPERAÇÃO FINALIZADA";
        } finally {
            System.out.println("FINALLY: executado antes de o chamador receber o valor.");
        }
    }

    private static void demonstrarTryFinally() {
        StringBuilder bufferLocal = new StringBuilder();
        try {
            bufferLocal.append("Manifesto temporário do armazém");
            System.out.println("TRY sem catch: " + bufferLocal);
        } finally {
            bufferLocal.setLength(0);
            System.out.println("FINALLY sem catch: buffer local limpo; tamanho="
                + bufferLocal.length());
        }
    }
}
