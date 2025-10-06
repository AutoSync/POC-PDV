package br.com.erick.produtos;

public class ItemVenda {
    private Produto produto;
    private int quantidade;
    private double subTotal;

    public ItemVenda(Produto produto, int quantidade){
        this.produto = produto;
        this.quantidade = quantidade;
        this.subTotal = produto.getPreco() * quantidade;
    }

    public Produto getProduto(){
        return this.produto;
    }
    public int getQuantidade(){
        return this.quantidade;
    }
    public double getSubTotal(){
        return this.subTotal;
    }

    public void setQuantidade(int quantidade){
        this.quantidade = quantidade;
        this.subTotal = produto.getPreco() * quantidade;
    }

    @Override
    public String toString() {
        return String.format("%s x%d - R$ %.2f", produto.getNome(), quantidade, subTotal);
    }
}
