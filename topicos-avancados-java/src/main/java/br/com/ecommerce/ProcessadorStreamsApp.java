package br.com.ecommerce;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import br.com.ecommerce.excecoes.LogisticaException;
import br.com.ecommerce.logistica.PacoteExpresso;

public class ProcessadorStreamsApp {

    public static void main(String[] args) throws LogisticaException {
        System.out.println("=== BLOCO 3: CLASSE ANÔNIMA, LAMBDA E STREAMS ===");
        List<PacoteExpresso> lote = new ArrayList<>();
        lote.add(new PacoteExpresso("LOTE01", 2.0, 0.05, false));
        lote.add(new PacoteExpresso("LOTE02", 5.0, 0.10, true));
        lote.add(new PacoteExpresso("LOTE03", 1.0, 0.02, false));
        lote.add(new PacoteExpresso("LOTE04", 3.0, 0.08, true));
        lote.add(new PacoteExpresso("LOTE05", 10.0, 0.20, false));

        List<PacoteExpresso> porClasseAnonima = new ArrayList<>(lote);
        Collections.sort(porClasseAnonima, new Comparator<PacoteExpresso>() {
            @Override
            public int compare(PacoteExpresso p1, PacoteExpresso p2) {
                return Double.compare(p1.calcularFreteBase(), p2.calcularFreteBase());
            }
        });
        exibirLote("Ordenação com classe anônima:", porClasseAnonima);

        List<PacoteExpresso> porLambda = new ArrayList<>(lote);
        Collections.sort(porLambda, (p1, p2) ->
            Double.compare(p1.calcularFreteBase(), p2.calcularFreteBase()));
        exibirLote("Mesma ordenação com lambda, sem declarar classe e método compare:", porLambda);
        System.out.println("Ordenações equivalentes: " + porClasseAnonima.equals(porLambda));

        List<PacoteExpresso> mesmoDia = lote.stream()
            .filter(PacoteExpresso::isEntregaNoMesmoDia)
            .collect(Collectors.toList());
        exibirLote("Pacotes com entrega no mesmo dia:", mesmoDia);

        OptionalDouble media = lote.stream()
            .mapToDouble(PacoteExpresso::calcularFreteBase)
            .average();
        if (media.isPresent()) {
            System.out.printf(Locale.forLanguageTag("pt-BR"),
                "Média do frete do lote: R$ %.2f%n", media.getAsDouble());
        } else {
            System.out.println("Lote vazio: não há média de frete.");
        }

        double limite = 50.0;
        long acimaDoLimite = lote.stream()
            .filter(pacote -> pacote.calcularFreteBase() > limite)
            .count();
        System.out.println("Pacotes com frete acima de R$ 50,00: " + acimaDoLimite);

        lote.stream()
            .min(Comparator.comparingDouble(PacoteExpresso::calcularFreteBase))
            .ifPresent(pacote -> System.out.printf(Locale.forLanguageTag("pt-BR"),
                "Menor frete: %s - R$ %.2f%n",
                pacote.getCodigoEtiqueta(), pacote.calcularFreteBase()));
        System.out.println("Bloco 3 concluído.");
    }

    private static void exibirLote(String titulo, List<PacoteExpresso> pacotes) {
        System.out.println("\n" + titulo);
        for (PacoteExpresso pacote : pacotes) {
            System.out.printf(Locale.forLanguageTag("pt-BR"), "%s - R$ %.2f%n",
                pacote.getCodigoEtiqueta(), pacote.calcularFreteBase());
        }
    }
}
