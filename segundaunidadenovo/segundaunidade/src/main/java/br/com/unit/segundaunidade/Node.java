package br.com.unit.segundaunidade;

public class Node {
    int valor;
    Node esquerda, direita;
    int altura;

    public Node(int valor) {
        this.valor = valor;
        esquerda = direita = null;
        altura = 1;
    }

    public int getFatorBalanceamento() {
        int hEsq = (esquerda == null) ? 0 : esquerda.altura;
        int hDir = (direita == null) ? 0 : direita.altura;
        return hEsq - hDir;
    }
}

