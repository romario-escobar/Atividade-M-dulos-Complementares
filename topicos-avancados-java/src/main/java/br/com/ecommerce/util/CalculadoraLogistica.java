package br.com.ecommerce.util;

import java.util.Locale;

import br.com.ecommerce.anotacoes.AuditoriaLogistica;
import br.com.ecommerce.logistica.StatusRastreio;

public final class CalculadoraLogistica {

    private CalculadoraLogistica() {
    }

    @AuditoriaLogistica(nivelCriticidade = "ALTA")
    public static double calcularCustoFinal(double valorBase, double... taxasAdicionais) {
        double total = valorBase;
        for (double taxa : taxasAdicionais) {
            total += taxa;
        }
        return total;
    }

    public static String formatarResumoOperacao(String codigo, StatusRastreio status,
                                                double valorFinal) {
        return String.format(
            Locale.forLanguageTag("pt-BR"),
            "Etiqueta: %s | Status: %s | Prazo: %d dia(s) útil(eis) | Total: R$ %.2f",
            codigo, status, status.getPrazoDiasUteis(), valorFinal
        );
    }
}
