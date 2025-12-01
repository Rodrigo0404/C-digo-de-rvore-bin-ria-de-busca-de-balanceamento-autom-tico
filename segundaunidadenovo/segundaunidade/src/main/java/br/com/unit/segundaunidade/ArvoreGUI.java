package br.com.unit.segundaunidade;

import javax.swing.*;
import java.awt.*;

public class ArvoreGUI extends JFrame {
    private ArvoreAVL arvore = new ArvoreAVL();
    private JTextField campoValor, campoNo;
    private JTextArea areaResultado;
    private PainelArvore painel;
    private boolean espelhada = false;
    private boolean mostrarFB = true;

    public ArvoreGUI() {
        setTitle("Árvore AVL - Balanceamento Automático");
        setSize(950, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        
        JPanel painelTopo = new JPanel();
        campoValor = new JTextField(5);
        JButton botaoInserir = new JButton("Inserir");
        JButton botaoInOrdem = new JButton("Em Ordem");
        JButton botaoPreOrdem = new JButton("Pré-Ordem");
        JButton botaoPosOrdem = new JButton("Pós-Ordem");
        JButton botaoEspelhar = new JButton("Espelhar");
        JButton botaoToggleFB = new JButton("Mostrar/Ocultar FB");

        painelTopo.add(new JLabel("Valor:"));
        painelTopo.add(campoValor);
        painelTopo.add(botaoInserir);
        painelTopo.add(botaoInOrdem);
        painelTopo.add(botaoPreOrdem);
        painelTopo.add(botaoPosOrdem);
        painelTopo.add(botaoEspelhar);
        painelTopo.add(botaoToggleFB);

        add(painelTopo, BorderLayout.NORTH);

        
        areaResultado = new JTextArea(10, 50);
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(areaResultado), BorderLayout.SOUTH);

        
        painel = new PainelArvore(arvore);
        add(painel, BorderLayout.CENTER);

        
        JPanel painelLateral = new JPanel();
        painelLateral.setLayout(new GridLayout(6, 1, 5, 5));
        campoNo = new JTextField(5);
        JButton botaoInfoNo = new JButton("Info Nó");
        JButton botaoInfoArvore = new JButton("Info Árvore");
        JButton botaoInfoAVL = new JButton("Ver Procedimentos");
        JButton botaoLimpar = new JButton("Limpar Árvore");

        painelLateral.add(new JLabel("Consultar Nó:"));
        painelLateral.add(campoNo);
        painelLateral.add(botaoInfoNo);
        painelLateral.add(botaoInfoArvore);
        painelLateral.add(botaoInfoAVL);
    

        add(painelLateral, BorderLayout.WEST);

        
        botaoInserir.addActionListener(e -> {
            try {
                int valor = Integer.parseInt(campoValor.getText());
                arvore.inserir(valor);
                campoValor.setText("");
                painel.repaint();
                areaResultado.setText("Última operação:\n" + arvore.getUltimaRotacao());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Digite um número válido!");
            }
        });

        
        botaoInOrdem.addActionListener(e -> areaResultado.setText("Em Ordem:\n" + arvore.emOrdem()));
        botaoPreOrdem.addActionListener(e -> areaResultado.setText("Pré-Ordem:\n" + arvore.preOrdem()));
        botaoPosOrdem.addActionListener(e -> areaResultado.setText("Pós-Ordem:\n" + arvore.posOrdem()));

        
        botaoEspelhar.addActionListener(e -> {
            espelhada = !espelhada;
            painel.setEspelhada(espelhada);
        });

        
        botaoToggleFB.addActionListener(e -> {
            mostrarFB = !mostrarFB;
            painel.setMostrarFB(mostrarFB);
        });

        
        botaoInfoNo.addActionListener(e -> {
            try {
                int valor = Integer.parseInt(campoNo.getText());
                Node n = arvore.buscar(arvore.getRaiz(), valor);
                if (n == null) {
                    areaResultado.setText("Nó " + valor + " não encontrado.");
                } else {
                    Node pai = arvore.pai(arvore.getRaiz(), n);
                    StringBuilder sb = new StringBuilder();
                    sb.append("═══ Info do Nó ").append(valor).append(" ═══\n");
                    sb.append("Altura do nó: ").append(n.altura).append("\n");
                    sb.append("Profundidade: ").append(arvore.profundidade(arvore.getRaiz(), n)).append("\n");
                    sb.append("Nível: ").append(arvore.profundidade(arvore.getRaiz(), n)).append("\n");
                    sb.append("Grau: ").append(arvore.grau(n)).append("\n");
                    sb.append("É folha? ").append(arvore.isFolha(n) ? "Sim" : "Não").append("\n");
                    sb.append("Pai: ").append(pai == null ? "Nenhum (é raiz)" : pai.valor).append("\n");
                    sb.append("Filho esquerdo: ").append(n.esquerda == null ? "Nenhum" : n.esquerda.valor).append("\n");
                    sb.append("Filho direito: ").append(n.direita == null ? "Nenhum" : n.direita.valor).append("\n");
                    sb.append("Fator de Balanceamento: ").append(n.getFatorBalanceamento()).append("\n");
                    areaResultado.setText(sb.toString());
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Digite um número válido!");
            }
        });

        
        botaoInfoArvore.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            sb.append("═══ Info da Árvore ═══\n");
            sb.append("Raiz: ").append(arvore.getRaiz() == null ? "Nenhuma" : arvore.getRaiz().valor).append("\n");
            sb.append("Altura da árvore: ").append(arvore.alturaArvore()).append("\n");
            sb.append("Grau máximo: ").append(arvore.grauMaximo(arvore.getRaiz())).append("\n");
            sb.append("Está balanceada? ").append(arvore.estaBalanceada(arvore.getRaiz()) ? "Sim (AVL)" : "Não").append("\n");
            areaResultado.setText(sb.toString());
        });

        
        botaoInfoAVL.addActionListener(e -> {
            if (arvore.getRaiz() == null) {
                areaResultado.setText("Árvore vazia.");
            } else {
                areaResultado.setText(
                    "═══ Última Operação de Balanceamento ═══\n\n" +
                    arvore.getUltimaRotacao()
                );
            }
        });

        
        botaoLimpar.addActionListener(e -> {
            arvore = new ArvoreAVL();
            painel = new PainelArvore(arvore);
            painel.setEspelhada(espelhada);
            painel.setMostrarFB(mostrarFB);
            remove(getContentPane().getComponent(2));
            add(painel, BorderLayout.CENTER);
            revalidate();
            repaint();
            areaResultado.setText("Árvore limpa com sucesso!");
        });

        
        campoValor.addActionListener(e -> botaoInserir.doClick());
        campoNo.addActionListener(e -> botaoInfoNo.doClick());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ArvoreGUI().setVisible(true));
    }
}


