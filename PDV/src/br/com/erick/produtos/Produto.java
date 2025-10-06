package br.com.erick.produtos;
import java.util.*;
import javax.xml.crypto.Data;
import java.time.LocalDateTime;

public class Produto {
    private String SKU;
    private String nome;
    private double preco;
    private int estoque;
    private List<PrecoHistorico> historico_preco;

    public Produto(String SKU, String nome, double preco, int estoque){
        this.SKU = SKU;
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
        this.historico_preco = new ArrayList<>();
        this.historico_preco.add(new PrecoHistorico(preco, LocalDateTime.now()));
    }

    // Publics

    public String getSKU(){
        return this.SKU;
    }
    public String getNome(){
        return this.nome;
    }
    public double getPreco(){
        return this.preco;
    }
    public int getEstoque(){
        return this.estoque;
    }
    public void setEstoque(int estoque){
        this.estoque = estoque;
    }
    public void setPreco(double preco){
        this.preco = preco;
        this.historico_preco.add(new PrecoHistorico(preco, LocalDateTime.now()));
    }

    @Override
    public String toString() {
        return String.format("%s - %s - R$ %.2f", SKU, nome, preco);
    }

}
