/** Una pista valida. Caso reemplaza el objeto cuando se modifica su informacion. */
public class Pista {
    private final String codigo;
    private final String descripcion;
    private final String tipoEvidencia;
    private final int nivelImportancia;
    private final int nivelConfiabilidad;

    public Pista(String codigo, String descripcion, String tipoEvidencia,
                 int nivelImportancia, int nivelConfiabilidad) {
        if (nivelImportancia < 1 || nivelImportancia > 10) {
            throw new IllegalArgumentException("La importancia debe estar entre 1 y 10.");
        }
        if (nivelConfiabilidad < 0 || nivelConfiabilidad > 100) {
            throw new IllegalArgumentException("La confiabilidad debe estar entre 0 y 100.");
        }
        this.codigo = validarTexto(codigo);
        this.descripcion = validarTexto(descripcion);
        this.tipoEvidencia = validarTexto(tipoEvidencia);
        this.nivelImportancia = nivelImportancia;
        this.nivelConfiabilidad = nivelConfiabilidad;
    }

    private String validarTexto(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("Los textos de la pista no pueden estar vacios.");
        }
        return texto.trim();
    }

    public String getCodigo() {
        return codigo;
    }

    public int getNivelImportancia() {
        return nivelImportancia;
    }

    public int getNivelConfiabilidad() {
        return nivelConfiabilidad;
    }

    @Override
    public String toString() {
        return "Codigo: " + codigo + " | Descripcion: " + descripcion
            + " | Tipo: " + tipoEvidencia + " | Importancia: " + nivelImportancia
            + " | Confiabilidad: " + nivelConfiabilidad + "%";
    }
}
