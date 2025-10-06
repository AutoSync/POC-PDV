package br.com.erick.produtos;
import java.time.LocalDateTime;

public class PrecoHistorico {
    private double valor;
    private LocalDateTime data;

    public PrecoHistorico(double valor, LocalDateTime data) {
        this.valor = valor;
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public LocalDateTime getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Valor: R$" + valor + " | Data: " + data;
    }
}
