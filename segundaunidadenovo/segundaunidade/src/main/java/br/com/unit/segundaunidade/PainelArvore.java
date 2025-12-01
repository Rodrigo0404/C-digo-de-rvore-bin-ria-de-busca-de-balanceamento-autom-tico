package br.com.unit.segundaunidade;

import javax.swing.*;
import java.awt.*;

class PainelArvore extends JPanel {
    private ArvoreAVL arvore;
    private boolean espelhada = false;
    private boolean mostrarFB = true;

    public PainelArvore(ArvoreAVL arvore) {
        this.arvore = arvore;
        setBackground(Color.WHITE);
    }

    public void setEspelhada(boolean espelhada) {
        this.espelhada = espelhada;
        repaint();
    }

    public void setMostrarFB(boolean mostrar) {
        this.mostrarFB = mostrar;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (arvore.getRaiz() != null) {
            desenharArvore(g, arvore.getRaiz(), getWidth() / 2, 50, getWidth() / 4);
        }
    }

    private void desenharArvore(Graphics g, Node no, int x, int y, int espacamento) {
        if (no == null) return;

        int fb = no.getFatorBalanceamento();
        Color cor = Color.GREEN;
        if (Math.abs(fb) > 1) cor = Color.RED;
        else if (Math.abs(fb) == 1) cor = Color.ORANGE;

        if (!espelhada) {
            if (no.esquerda != null) {
                g.setColor(Color.GRAY);
                g.drawLine(x, y, x - espacamento, y + 50);
                desenharArvore(g, no.esquerda, x - espacamento, y + 50, espacamento / 2);
            }
            if (no.direita != null) {
                g.setColor(Color.GRAY);
                g.drawLine(x, y, x + espacamento, y + 50);
                desenharArvore(g, no.direita, x + espacamento, y + 50, espacamento / 2);
            }
        } else {
            if (no.direita != null) {
                g.setColor(Color.GRAY);
                g.drawLine(x, y, x - espacamento, y + 50);
                desenharArvore(g, no.direita, x - espacamento, y + 50, espacamento / 2);
            }
            if (no.esquerda != null) {
                g.setColor(Color.GRAY);
                g.drawLine(x, y, x + espacamento, y + 50);
                desenharArvore(g, no.esquerda, x + espacamento, y + 50, espacamento / 2);
            }
        }

        g.setColor(cor);
        g.fillOval(x - 15, y - 15, 30, 30);
        g.setColor(Color.BLACK);
        g.drawOval(x - 15, y - 15, 30, 30);
        g.setColor(Color.WHITE);
        g.drawString(Integer.toString(no.valor), x - 5, y + 5);

        if (mostrarFB) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 10));
            g.drawString("FB:" + fb, x - 12, y - 18);
        }
    }
}

