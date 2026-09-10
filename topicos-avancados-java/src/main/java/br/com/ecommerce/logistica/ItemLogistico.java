package br.com.ecommerce.logistica;

import br.com.ecommerce.excecoes.LogisticaException;

public abstract class ItemLogistico {

    private static int totalItensGerados;

    static {
        totalItensGerados = 0;
        System.out.println("[STATIC] Contador global inicializado uma vez na JVM.");
    }

    private final String codigoEtiqueta;
    protected double pesoKg;
    double cubagemM3;
    private boolean despachado;

    public ItemLogistico(String codigoEtiqueta, double pesoKg, double cubagemM3)
            throws LogisticaException {
        if (codigoEtiqueta == null || codigoEtiqueta.trim().length() < 5) {
            throw new LogisticaException("A etiqueta deve conter pelo menos 5 caracteres.");
        }
        this.codigoEtiqueta = codigoEtiqueta.trim();
        this.pesoKg = pesoKg;
        this.cubagemM3 = cubagemM3;
    }

    // Chamado pela subclasse somente após validar a construção completa.
    protected static void registrarItemGerado() {
        totalItensGerados++;
    }

    public static int getTotalItensGerados() {
        return totalItensGerados;
    }

    public String getCodigoEtiqueta() {
        return codigoEtiqueta;
    }

    public double getPesoKg() {
        return pesoKg;
    }

    public double getCubagemM3() {
        return cubagemM3;
    }

    public boolean isDespachado() {
        return despachado;
    }

    public void setDespachado(boolean despachado) {
        this.despachado = despachado;
    }

    public abstract double calcularFreteBase();

    @Override
    public String toString() {
        return "ItemLogistico{etiqueta='" + codigoEtiqueta
            + "', pesoKg=" + pesoKg + ", cubagemM3=" + cubagemM3
            + ", despachado=" + despachado + "}";
    }
}
