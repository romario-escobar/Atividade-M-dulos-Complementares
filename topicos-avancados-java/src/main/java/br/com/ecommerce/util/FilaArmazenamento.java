package br.com.ecommerce.util;

import java.util.NoSuchElementException;

import br.com.ecommerce.anotacoes.AuditoriaLogistica;

@AuditoriaLogistica
public class FilaArmazenamento<T> {

    private class No<E> {
        private final E dado;
        private No<E> proximo;

        private No(E dado) {
            this.dado = dado;
        }
    }

    private No<T> inicio;
    private No<T> fim;
    private int tamanho;

    public void enfileirar(T elemento) {
        No<T> novo = new No<>(elemento);
        if (estaVazia()) {
            inicio = novo;
        } else {
            fim.proximo = novo;
        }
        fim = novo;
        tamanho++;
    }

    public T desenfileirar() {
        if (estaVazia()) {
            throw new NoSuchElementException("A fila está vazia.");
        }
        T dado = inicio.dado;
        inicio = inicio.proximo;
        tamanho--;
        if (estaVazia()) {
            fim = null;
        }
        return dado;
    }

    public int getTamanho() {
        return tamanho;
    }

    public boolean estaVazia() {
        return tamanho == 0;
    }
}
