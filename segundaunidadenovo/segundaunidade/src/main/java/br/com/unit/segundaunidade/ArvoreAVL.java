package br.com.unit.segundaunidade;

public class ArvoreAVL {
    private Node raiz;
    private String ultimaRotacao = "Nenhuma";

    public Node getRaiz() {
        return raiz;
    }

    public String getUltimaRotacao() {
        return ultimaRotacao;
    }

    public void inserir(int valor) {
        raiz = inserirRec(raiz, valor);
    }

    private Node inserirRec(Node no, int valor) {
        if (no == null) return new Node(valor);

        if (valor < no.valor) {
            no.esquerda = inserirRec(no.esquerda, valor);
        } else if (valor > no.valor) {
            no.direita = inserirRec(no.direita, valor);
        } else {
            return no; 
        }

        
        no.altura = 1 + Math.max(altura(no.esquerda), altura(no.direita));

        
        int fb = no.getFatorBalanceamento();

       
        if (fb > 1 && valor < no.esquerda.valor) {
            ultimaRotacao = "Rotação Simples à Direita (pivô: " + no.valor + ")";
            return rotacaoDireita(no);
        }

        
        if (fb < -1 && valor > no.direita.valor) {
            ultimaRotacao = "Rotação Simples à Esquerda (pivô: " + no.valor + ")";
            return rotacaoEsquerda(no);
        }

       
        if (fb > 1 && valor > no.esquerda.valor) {
            ultimaRotacao = "Rotação Dupla à Direita (pivô: " + no.valor + ")";
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        
        if (fb < -1 && valor < no.direita.valor) {
            ultimaRotacao = "Rotação Dupla à Esquerda (pivô: " + no.valor + ")";
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    private int altura(Node n) {
        return (n == null) ? 0 : n.altura;
    }

    private Node rotacaoDireita(Node y) {
        Node x = y.esquerda;
        Node T2 = x.direita;

        x.direita = y;
        y.esquerda = T2;

        y.altura = 1 + Math.max(altura(y.esquerda), altura(y.direita));
        x.altura = 1 + Math.max(altura(x.esquerda), altura(x.direita));

        return x;
    }

    private Node rotacaoEsquerda(Node x) {
        Node y = x.direita;
        Node T2 = y.esquerda;

        y.esquerda = x;
        x.direita = T2;

        x.altura = 1 + Math.max(altura(x.esquerda), altura(x.direita));
        y.altura = 1 + Math.max(altura(y.esquerda), altura(y.direita));

        return y;
    }

    
    public String emOrdem() {
        StringBuilder sb = new StringBuilder();
        emOrdemRec(raiz, sb);
        return sb.toString();
    }

    private void emOrdemRec(Node n, StringBuilder sb) {
        if (n != null) {
            emOrdemRec(n.esquerda, sb);
            sb.append(n.valor).append(" ");
            emOrdemRec(n.direita, sb);
        }
    }

    public String preOrdem() {
        StringBuilder sb = new StringBuilder();
        preOrdemRec(raiz, sb);
        return sb.toString();
    }

    private void preOrdemRec(Node n, StringBuilder sb) {
        if (n != null) {
            sb.append(n.valor).append(" ");
            preOrdemRec(n.esquerda, sb);
            preOrdemRec(n.direita, sb);
        }
    }

    public String posOrdem() {
        StringBuilder sb = new StringBuilder();
        posOrdemRec(raiz, sb);
        return sb.toString();
    }

    private void posOrdemRec(Node n, StringBuilder sb) {
        if (n != null) {
            posOrdemRec(n.esquerda, sb);
            posOrdemRec(n.direita, sb);
            sb.append(n.valor).append(" ");
        }
    }
    public Node buscar(Node atual, int valor) {
        if (atual == null || atual.valor == valor) return atual;
        if (valor < atual.valor) return buscar(atual.esquerda, valor);
        else return buscar(atual.direita, valor);
    }

    
    public int alturaArvore() {
        return altura(raiz);
    }

    
    public int profundidade(Node raiz, Node alvo) {
        if (raiz == null || alvo == null) return -1;
        if (raiz == alvo) return 0;
        int esq = profundidade(raiz.esquerda, alvo);
        if (esq >= 0) return esq + 1;
        int dir = profundidade(raiz.direita, alvo);
        if (dir >= 0) return dir + 1;
        return -1;
    }

   
    public int grau(Node n) {
        if (n == null) return -1;
        int grau = 0;
        if (n.esquerda != null) grau++;
        if (n.direita != null) grau++;
        return grau;
    }

    
    public Node pai(Node raiz, Node alvo) {
        if (raiz == null || raiz == alvo) return null;
        if (raiz.esquerda == alvo || raiz.direita == alvo) return raiz;
        Node esq = pai(raiz.esquerda, alvo);
        if (esq != null) return esq;
        return pai(raiz.direita, alvo);
    }

    
    public boolean isFolha(Node n) {
        return (n != null && n.esquerda == null && n.direita == null);
    }

    
    public int grauMaximo(Node n) {
        if (n == null) return 0;
        int grauAtual = grau(n);
        int esq = grauMaximo(n.esquerda);
        int dir = grauMaximo(n.direita);
        return Math.max(grauAtual, Math.max(esq, dir));
    }

    
    public boolean estaBalanceada(Node n) {
        if (n == null) return true;
        int fb = n.getFatorBalanceamento();
        if (Math.abs(fb) > 1) return false;
        return estaBalanceada(n.esquerda) && estaBalanceada(n.direita);
    }
}

