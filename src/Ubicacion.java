/** Representa un lugar investigado. */
public class Ubicacion {
    private final String codigo;
    private final String nombre;
    private final String direccion;
    private int nivelRiesgo;
    private String estado;

    public Ubicacion(String codigo, String nombre, String direccion,
                     int nivelRiesgo, String estado) {
        this.codigo = validarTexto(codigo);
        this.nombre = validarTexto(nombre);
        this.direccion = validarTexto(direccion);
        actualizar(nivelRiesgo, estado);
    }

    private String validarTexto(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("Los textos de la ubicacion no pueden estar vacios.");
        }
        return texto.trim();
    }

    // Se validan TODOS los valores antes de modificar el objeto.
    public void actualizar(int nivelRiesgo, String estado) {
        if (nivelRiesgo < 1 || nivelRiesgo > 10) {
            throw new IllegalArgumentException("El riesgo debe estar entre 1 y 10.");
        }
        String estadoValido = validarTexto(estado);
        this.nivelRiesgo = nivelRiesgo;
        this.estado = estadoValido;
    }

    public int getNivelRiesgo() {
        return nivelRiesgo;
    }

    @Override
    public String toString() {
        return "Codigo: " + codigo + " | Nombre: " + nombre
            + " | Direccion: " + direccion + " | Riesgo: " + nivelRiesgo
            + " | Estado: " + estado;
    }
}
