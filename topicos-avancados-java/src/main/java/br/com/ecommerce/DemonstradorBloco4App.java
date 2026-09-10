package br.com.ecommerce;

import br.com.ecommerce.logistica.PacoteExpresso;
import br.com.ecommerce.util.AuditorMetadadosReflection;
import br.com.ecommerce.util.FilaArmazenamento;

public class DemonstradorBloco4App {

    public static void main(String[] args) {
        System.out.println("=== BLOCO 4: MEMÓRIA, WRAPPERS E REFLECTION ===");
        String literal1 = "LOGISTICA";
        String literal2 = "LOGISTICA";
        String novaString = new String("LOGISTICA");
        System.out.println("Literais: == " + (literal1 == literal2));
        System.out.println("Literal e new String: == " + (literal1 == novaString));
        System.out.println("Literal e new String: equals " + literal1.equals(novaString));
        String original = "Carga";
        String alterada = original.concat(" expedida");
        System.out.println("String original preservada: " + original);
        System.out.println("Nova String: " + alterada);

        Integer pequeno1 = 100;
        Integer pequeno2 = 100;
        Integer grande1 = 500;
        Integer grande2 = 500;
        System.out.println("Integer 100: == " + (pequeno1 == pequeno2));
        System.out.println("Integer 100: equals " + pequeno1.equals(pequeno2));
        System.out.println("Integer 500: == " + (grande1 == grande2));
        System.out.println("Integer 500: equals " + grande1.equals(grande2));
        System.out.println("O cache inclui -128 a 127; fora dessa faixa, == pode variar com a JVM.");

        StringBuilder manifesto = new StringBuilder("MANIFESTO DE CARGA\n");
        for (int i = 1; i <= 3; i++) {
            manifesto.append("Registro ").append(i)
                .append(" | Etiqueta MAN00").append(i)
                .append(" | Status PENDENTE").append(System.lineSeparator());
        }
        System.out.print(manifesto.toString());

        System.out.println("'7' é dígito: " + Character.isDigit('7'));
        System.out.println("'A' é maiúscula: " + Character.isUpperCase('A'));
        System.out.println("Texto convertido para inteiro: " + Integer.parseInt("42"));
        System.out.println("Float.NaN: " + Float.NaN);
        System.out.println("Float.POSITIVE_INFINITY: " + Float.POSITIVE_INFINITY);

        AuditorMetadadosReflection.inspecionarClasse(FilaArmazenamento.class);
        AuditorMetadadosReflection.inspecionarClasse(PacoteExpresso.class);
        System.out.println("Bloco 4 concluído.");
    }
}
