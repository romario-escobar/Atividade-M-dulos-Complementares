package br.com.ecommerce.logistica;

import java.util.Date;

import br.com.ecommerce.anotacoes.AuditoriaLogistica;
import br.com.ecommerce.excecoes.EstoqueInvalidoException;
import br.com.ecommerce.excecoes.LogisticaException;

@AuditoriaLogistica(nivelCriticidade = "ALTA")
public class PacoteExpresso extends ItemLogistico {

    private final boolean entregaNoMesmoDia;
    private final Date dataCriacao;
    private final java.sql.Date dataRegistro;

    public PacoteExpresso(String codigoEtiqueta, double pesoKg,
                          double cubagemM3, boolean entregaNoMesmoDia)
            throws LogisticaException {
        super(codigoEtiqueta, pesoKg, cubagemM3);
        if (!Double.isFinite(pesoKg) || pesoKg <= 0) {
            throw new EstoqueInvalidoException("O peso deve ser finito e maior que zero.");
        }
        if (!Double.isFinite(cubagemM3) || cubagemM3 < 0) {
            throw new EstoqueInvalidoException("A cubagem deve ser finita e não negativa.");
        }
        this.entregaNoMesmoDia = entregaNoMesmoDia;
        this.dataCriacao = new Date();
        this.dataRegistro = new java.sql.Date(dataCriacao.getTime());
        registrarItemGerado();
    }

    public PacoteExpresso(String codigoEtiqueta, double pesoKg)
            throws LogisticaException {
        this(codigoEtiqueta, pesoKg, 0.05, false);
    }

    public boolean isEntregaNoMesmoDia() {
        return entregaNoMesmoDia;
    }

    public Date getDataCriacao() {
        return new Date(dataCriacao.getTime());
    }

    public java.sql.Date getDataRegistro() {
        return new java.sql.Date(dataRegistro.getTime());
    }

    @Override
    @AuditoriaLogistica(nivelCriticidade = "ALTA")
    public double calcularFreteBase() {
        // Regra didática: R$ 10 fixos + R$ 5/kg + R$ 100/m³ + R$ 20 de urgência.
        return 10.0 + pesoKg * 5.0 + cubagemM3 * 100.0
            + (entregaNoMesmoDia ? 20.0 : 0.0);
    }

    @Override
    public String toString() {
        return super.toString() + " PacoteExpresso{entregaNoMesmoDia="
            + entregaNoMesmoDia + ", dataCriacao=" + dataCriacao
            + ", dataRegistro=" + dataRegistro + "}";
    }
}
