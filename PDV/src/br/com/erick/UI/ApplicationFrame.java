package br.com.erick.UI;

import br.com.erick.produtos.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ApplicationFrame extends JFrame {
    private List<Produto> produtos;
    private List<ItemVenda> itensVenda;
    private DefaultTableModel tableModel;
    private JTable tableItens;
    private JLabel lblTotal;
    private JTextField txtCodigo;
    private JTextField txtQuantidade;
    private DecimalFormat df = new DecimalFormat("#,##0.00");

    public ApplicationFrame() {
        inicializarProdutos();
        itensVenda = new ArrayList<>();
        configurarJanela();
        criarComponentes();
        pack();
        setLocationRelativeTo(null); // Centralizar na tela
    }

    private void inicializarProdutos() {
        produtos = new ArrayList<>();
        // Produtos de exemplo
        produtos.add(new Produto("001", "Café Premium 500g", 12.90, 50));
        produtos.add(new Produto("002", "Arroz Integral 1kg", 8.50, 30));
        produtos.add(new Produto("003", "Feijão Carioca 1kg", 7.80, 40));
        produtos.add(new Produto("004", "Açúcar Cristal 1kg", 4.20, 60));
        produtos.add(new Produto("005", "Óleo de Soja 900ml", 9.90, 25));
        produtos.add(new Produto("006", "Sabão em Pó 1kg", 11.50, 35));
        produtos.add(new Produto("007", "Leite Integral 1L", 4.75, 20));
        produtos.add(new Produto("008", "Pão de Forma 500g", 6.30, 15));
    }

    private void configurarJanela() {
        setTitle("Sistema PDV - Ponto de Venda");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setPreferredSize(new Dimension(800, 600));
    }

    private void criarComponentes() {
        // Painel superior - Entrada de dados
        add(criarPainelEntrada(), BorderLayout.NORTH);

        // Painel central - Itens da venda
        add(criarPainelItens(), BorderLayout.CENTER);

        // Painel inferior - Total e botões
        add(criarPainelInferior(), BorderLayout.SOUTH);
    }

    private JPanel criarPainelEntrada() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Entrada de Produtos"));

        // Linha 1 - Código do produto
        JPanel linha1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        linha1.add(new JLabel("Código:"));
        txtCodigo = new JTextField(10);
        txtCodigo.addActionListener(e -> adicionarProduto());
        linha1.add(txtCodigo);

        linha1.add(new JLabel("Quantidade:"));
        txtQuantidade = new JTextField(3);
        txtQuantidade.setText("1");
        txtQuantidade.addActionListener(e -> adicionarProduto());
        linha1.add(txtQuantidade);

        JButton btnAdicionar = new JButton("Adicionar");
        btnAdicionar.addActionListener(e -> adicionarProduto());
        linha1.add(btnAdicionar);

        // Linha 2 - Busca rápida
        JPanel linha2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        linha2.add(new JLabel("Buscar:"));
        JTextField txtBusca = new JTextField(15);
        txtBusca.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                buscarProduto(txtBusca.getText());
            }
        });
        linha2.add(txtBusca);

        panel.add(linha1);
        panel.add(linha2);

        return panel;
    }

    private JPanel criarPainelItens() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Itens da Venda"));

        // Tabela de itens
        String[] colunas = {"Produto", "Quantidade", "Preço Unit.", "Subtotal"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabela não editável
            }
        };

        tableItens = new JTable(tableModel);
        tableItens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tableItens);

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel criarPainelInferior() {
        JPanel panel = new JPanel(new BorderLayout());

        // Painel do total
        JPanel panelTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelTotal.add(new JLabel("Total da Venda:"));
        lblTotal = new JLabel("R$ 0,00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotal.setForeground(Color.BLUE);
        panelTotal.add(lblTotal);

        // Painel de botões
        JPanel panelBotoes = new JPanel(new FlowLayout());

        JButton btnRemover = new JButton("Remover Item");
        btnRemover.addActionListener(e -> removerItem());
        panelBotoes.add(btnRemover);

        JButton btnFinalizar = new JButton("Finalizar Venda");
        btnFinalizar.setBackground(new Color(50, 150, 50));
        btnFinalizar.setForeground(Color.WHITE);
        btnFinalizar.addActionListener(e -> finalizarVenda());
        panelBotoes.add(btnFinalizar);

        JButton btnCancelar = new JButton("Cancelar Venda");
        btnCancelar.setBackground(new Color(200, 50, 50));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.addActionListener(e -> cancelarVenda());
        panelBotoes.add(btnCancelar);

        panel.add(panelTotal, BorderLayout.NORTH);
        panel.add(panelBotoes, BorderLayout.SOUTH);

        return panel;
    }

    private void adicionarProduto() {
        String codigo = txtCodigo.getText().trim();
        String strQuantidade = txtQuantidade.getText().trim();

        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o código do produto!");
            txtCodigo.requestFocus();
            return;
        }

        int quantidade;
        try {
            quantidade = Integer.parseInt(strQuantidade);
            if (quantidade <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida!");
            txtQuantidade.requestFocus();
            return;
        }

        // Buscar produto
        Produto produto = null;
        for (Produto p : produtos) {
            if (p.getSKU().equals(codigo)) {
                produto = p;
                break;
            }
        }

        if (produto == null) {
            JOptionPane.showMessageDialog(this, "Produto não encontrado!");
            txtCodigo.selectAll();
            txtCodigo.requestFocus();
            return;
        }

        if (produto.getEstoque() < quantidade) {
            JOptionPane.showMessageDialog(this,
                    "Estoque insuficiente! Disponível: " + produto.getEstoque());
            return;
        }

        // Verificar se produto já está na venda
        for (ItemVenda item : itensVenda) {
            if (item.getProduto().getSKU().equals(codigo)) {
                item.setQuantidade(item.getQuantidade() + quantidade);
                atualizarTabela();
                limparCampos();
                return;
            }
        }

        // Adicionar novo item
        ItemVenda novoItem = new ItemVenda(produto, quantidade);
        itensVenda.add(novoItem);
        atualizarTabela();
        limparCampos();
    }

    private void removerItem() {
        int selectedRow = tableItens.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um item para remover!");
            return;
        }

        itensVenda.remove(selectedRow);
        atualizarTabela();
    }

    private void finalizarVenda() {
        if (itensVenda.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum item na venda!");
            return;
        }

        double total = calcularTotal();

        // Simular formas de pagamento
        String[] opcoes = {"Dinheiro", "Cartão Débito", "Cartão Crédito", "PIX"};
        String formaPagamento = (String) JOptionPane.showInputDialog(
                this,
                "Total: R$ " + df.format(total) + "\nSelecione a forma de pagamento:",
                "Finalizar Venda",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );

        if (formaPagamento != null) {
            // Atualizar estoque
            for (ItemVenda item : itensVenda) {
                Produto produto = item.getProduto();
                produto.setEstoque(produto.getEstoque() - item.getQuantidade());
            }

            String mensagem = String.format(
                    "Venda finalizada com sucesso!\n" +
                            "Total: R$ %s\n" +
                            "Forma de Pagamento: %s\n" +
                            "Itens vendidos: %d",
                    df.format(total), formaPagamento, itensVenda.size()
            );

            JOptionPane.showMessageDialog(this, mensagem);
            cancelarVenda(); // Limpar para nova venda
        }
    }

    private void cancelarVenda() {
        itensVenda.clear();
        atualizarTabela();
        txtCodigo.setText("");
        txtQuantidade.setText("1");
        txtCodigo.requestFocus();
    }

    private void buscarProduto(String termo) {
        if (termo.isEmpty()) return;

        for (Produto produto : produtos) {
            if (produto.getSKU().equals(termo) ||
                    produto.getNome().toLowerCase().contains(termo.toLowerCase())) {

                txtCodigo.setText(produto.getSKU());
                txtQuantidade.requestFocus();
                return;
            }
        }
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);

        for (ItemVenda item : itensVenda) {
            Object[] rowData = {
                    item.getProduto().getNome(),
                    item.getQuantidade(),
                    df.format(item.getProduto().getPreco()),
                    df.format(item.getSubTotal())
            };
            tableModel.addRow(rowData);
        }

        atualizarTotal();
    }

    private void atualizarTotal() {
        double total = calcularTotal();
        lblTotal.setText("R$ " + df.format(total));
    }

    private double calcularTotal() {
        return itensVenda.stream()
                .mapToDouble(ItemVenda::getSubTotal)
                .sum();
    }

    private void limparCampos() {
        txtCodigo.setText("");
        txtQuantidade.setText("1");
        txtCodigo.requestFocus();
    }
}