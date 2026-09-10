package br.com.ecommerce.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;

import br.com.ecommerce.anotacoes.AuditoriaLogistica;

public final class AuditorMetadadosReflection {

    private AuditorMetadadosReflection() {
    }

    public static void inspecionarClasse(Class<?> clazz) {
        System.out.println("\nCLASSE: " + clazz.getName());
        Class<?> superclasse = clazz.getSuperclass();
        System.out.println("Superclasse direta: "
            + (superclasse == null ? "nenhuma" : superclasse.getName()));

        if (clazz.isAnnotationPresent(AuditoriaLogistica.class)) {
            AuditoriaLogistica auditoria = clazz.getAnnotation(AuditoriaLogistica.class);
            System.out.println("Criticidade: " + auditoria.nivelCriticidade());
            System.out.println("Registrar data/hora: " + auditoria.registrarDataHora());
        } else {
            System.out.println("Sem @AuditoriaLogistica na classe.");
        }

        Field[] atributos = clazz.getDeclaredFields();
        Arrays.sort(atributos, Comparator.comparing(Field::getName));
        System.out.println("Atributos declarados:");
        for (Field atributo : atributos) {
            System.out.println("  " + Modifier.toString(atributo.getModifiers())
                + " " + atributo.getGenericType().getTypeName() + " " + atributo.getName());
        }

        Method[] metodos = clazz.getMethods();
        Arrays.sort(metodos, Comparator.comparing(Method::toGenericString));
        int quantidade = 0;
        System.out.println("Métodos públicos declarados na própria classe:");
        for (Method metodo : metodos) {
            if (metodo.getDeclaringClass().equals(clazz)) {
                quantidade++;
                System.out.println("  " + metodo.toGenericString());
                if (metodo.isAnnotationPresent(AuditoriaLogistica.class)) {
                    AuditoriaLogistica auditoria = metodo.getAnnotation(AuditoriaLogistica.class);
                    System.out.println("    @AuditoriaLogistica: " + auditoria.nivelCriticidade());
                }
            }
        }
        System.out.println("Total de métodos públicos próprios: " + quantidade);
    }
}
