package br.com.ecommerce;

import static br.com.ecommerce.logistica.StatusRastreio.EM_TRANSITO;
import static br.com.ecommerce.logistica.StatusRastreio.ENTREGUE;
import static br.com.ecommerce.logistica.StatusRastreio.EXTRAVIADO;
import static br.com.ecommerce.util.CalculadoraLogistica.calcularCustoFinal;
import static br.com.ecommerce.util.CalculadoraLogistica.formatarResumoOperacao;

import br.com.ecommerce.excecoes.LogisticaException;
import br.com.ecommerce.logistica.PacoteExpresso;
import br.com.ecommerce.util.FilaArmazenamento;

public class DemonstradorBloco2App {

    public static void main(String[] args) throws LogisticaException {
        System.out.println("=== BLOCO 2: ENUM, GENERICS E VARARGS ===");
        FilaArmazenamento<PacoteExpresso> fila = new FilaArmazenamento<>();
        fila.enfileirar(new PacoteExpresso("FILA01", 2.0));
        fila.enfileirar(new PacoteExpresso("FILA02", 4.0, 0.10, true));
        fila.enfileirar(new PacoteExpresso("FILA03", 1.0));
        System.out.println("Tamanho inicial: " + fila.getTamanho());

        while (!fila.estaVazia()) {
            PacoteExpresso pacote = fila.desenfileirar();
            double custoFinal = calcularCustoFinal(pacote.calcularFreteBase(), 2.50, 3.00, 1.50);
            pacote.setDespachado(true);
            System.out.println(formatarResumoOperacao(
                pacote.getCodigoEtiqueta(), EM_TRANSITO, custoFinal));
        }

        System.out.println("Fila vazia: " + fila.estaVazia());
        System.out.println("Tamanho final: " + fila.getTamanho());
        System.out.println("ENTREGUE finalizado: " + ENTREGUE.isFinalizado());
        System.out.println("EXTRAVIADO finalizado: " + EXTRAVIADO.isFinalizado());
        System.out.println("EM_TRANSITO finalizado: " + EM_TRANSITO.isFinalizado());
        System.out.println("ENTREGUE requer assinatura: " + ENTREGUE.isRequerAssinatura());
        System.out.println("Bloco 2 concluído.");
    }
}
