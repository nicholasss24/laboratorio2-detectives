import java.util.ArrayList;
import java.util.Locale;

/** Administra las ubicaciones, las pistas y los calculos de una investigacion. */
public class Caso {
    private final String nombre;
    private final String codigo;
    private final String detective;
    private final Ubicacion[] ubicaciones;
    private final ArrayList<Pista> pistas;

    public Caso(String nombre, String codigo, String detective) {
        this.nombre = validarTexto(nombre);
        this.codigo = validarTexto(codigo);
        this.detective = validarTexto(detective);
        ubicaciones = new Ubicacion[5]; // Sus cinco posiciones empiezan en null.
        pistas = new ArrayList<Pista>();
    }

    private String validarTexto(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe ingresar un texto no vacio.");
        }
        return texto.trim();
    }

    private void validarPosicion(int posicion) {
        if (posicion < 0 || posicion >= ubicaciones.length) {
            throw new IndexOutOfBoundsException("La posicion debe estar entre 0 y 4.");
        }
    }

    public void registrarUbicacion(int posicion, Ubicacion ubicacion) {
        validarPosicion(posicion);
        if (ubicaciones[posicion] != null) {
            throw new IllegalStateException("La posicion ya esta ocupada.");
        }
        if (ubicacion == null) {
            throw new IllegalArgumentException("La ubicacion no puede ser null.");
        }
        ubicaciones[posicion] = ubicacion;
    }

    public Ubicacion consultarUbicacion(int posicion) {
        validarPosicion(posicion);
        if (ubicaciones[posicion] == null) {
            throw new IllegalStateException("La posicion esta vacia.");
        }
        return ubicaciones[posicion];
    }

    public String listarUbicaciones() {
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < ubicaciones.length; i++) {
            if (ubicaciones[i] != null) {
                resultado.append("Posicion ").append(i).append(": ")
                    .append(ubicaciones[i]).append("\n");
            }
        }
        return resultado.length() == 0 ? "No hay ubicaciones registradas." : resultado.toString();
    }

    public void modificarUbicacion(int posicion, int riesgo, String estado) {
        consultarUbicacion(posicion).actualizar(riesgo, estado);
    }

    public void descartarUbicacion(int posicion) {
        consultarUbicacion(posicion); // Comprueba que exista antes de descartarla.
        ubicaciones[posicion] = null;
    }

    private int buscarIndicePista(String codigo) {
        String codigoValido = validarTexto(codigo);
        for (int i = 0; i < pistas.size(); i++) {
            if (pistas.get(i).getCodigo().equalsIgnoreCase(codigoValido)) {
                return i;
            }
        }
        return -1;
    }

    public void registrarPista(Pista pista) {
        if (pista == null) {
            throw new IllegalArgumentException("La pista no puede ser null.");
        }
        if (buscarIndicePista(pista.getCodigo()) != -1) {
            throw new IllegalArgumentException("Ya existe una pista con ese codigo.");
        }
        pistas.add(pista);
    }

    public Pista buscarPista(String codigo) {
        int indice = buscarIndicePista(codigo);
        if (indice == -1) {
            throw new IllegalArgumentException("No existe una pista con ese codigo.");
        }
        return pistas.get(indice);
    }

    public String listarPistas() {
        if (pistas.isEmpty()) {
            return "No hay pistas registradas.";
        }
        StringBuilder resultado = new StringBuilder();
        for (Pista pista : pistas) {
            resultado.append(pista).append("\n");
        }
        return resultado.toString();
    }

    // Permite cambiar TODOS los datos, incluso el codigo, sin permitir duplicados.
    public void modificarPista(String codigoActual, Pista nuevaPista) {
        int indice = buscarIndicePista(codigoActual);
        if (indice == -1) {
            throw new IllegalArgumentException("No existe una pista con ese codigo.");
        }
        if (nuevaPista == null) {
            throw new IllegalArgumentException("La nueva pista no puede ser null.");
        }
        int otroIndice = buscarIndicePista(nuevaPista.getCodigo());
        if (otroIndice != -1 && otroIndice != indice) {
            throw new IllegalArgumentException("Otra pista ya utiliza el nuevo codigo.");
        }
        pistas.set(indice, nuevaPista);
    }

    public void eliminarPista(String codigo) {
        int indice = buscarIndicePista(codigo);
        if (indice == -1) {
            throw new IllegalArgumentException("No existe una pista con ese codigo.");
        }
        pistas.remove(indice);
    }

    public int contarUbicaciones() {
        int cantidad = 0;
        for (Ubicacion ubicacion : ubicaciones) {
            if (ubicacion != null) {
                cantidad++;
            }
        }
        return cantidad;
    }

    public int contarEspaciosDisponibles() {
        return ubicaciones.length - contarUbicaciones();
    }

    public Ubicacion ubicacionMayorRiesgo() {
        Ubicacion mayor = null;
        for (Ubicacion ubicacion : ubicaciones) {
            if (ubicacion != null && (mayor == null
                    || ubicacion.getNivelRiesgo() > mayor.getNivelRiesgo())) {
                mayor = ubicacion;
            }
        }
        return mayor;
    }

    public int contarPistas() {
        return pistas.size();
    }

    public Pista pistaMayorImportancia() {
        Pista mayor = null;
        for (Pista pista : pistas) {
            if (mayor == null || pista.getNivelImportancia() > mayor.getNivelImportancia()) {
                mayor = pista;
            }
        }
        return mayor;
    }

    public Pista pistaMayorConfiabilidad() {
        Pista mayor = null;
        for (Pista pista : pistas) {
            if (mayor == null || pista.getNivelConfiabilidad() > mayor.getNivelConfiabilidad()) {
                mayor = pista;
            }
        }
        return mayor;
    }

    public double promedioImportancia() {
        if (pistas.isEmpty()) {
            throw new IllegalStateException("No hay pistas para calcular el promedio.");
        }
        double suma = 0;
        for (Pista pista : pistas) {
            suma += pista.getNivelImportancia();
        }
        return suma / pistas.size();
    }

    public String generarReporte() {
        StringBuilder reporte = new StringBuilder();
        reporte.append("=== REPORTE DE INVESTIGACION ===\n")
            .append("Caso: ").append(nombre).append(" | Codigo: ").append(codigo)
            .append(" | Detective: ").append(detective).append("\n")
            .append("Ubicaciones registradas: ").append(contarUbicaciones()).append("\n")
            .append("Espacios disponibles: ").append(contarEspaciosDisponibles()).append("\n");
        Ubicacion mayorRiesgo = ubicacionMayorRiesgo();
        reporte.append("Mayor riesgo: ")
            .append(mayorRiesgo == null ? "No hay ubicaciones." : mayorRiesgo).append("\n")
            .append("Pistas registradas: ").append(contarPistas()).append("\n");
        if (pistas.isEmpty()) {
            reporte.append("Mayor importancia: No hay pistas.\n")
                .append("Mayor confiabilidad: No hay pistas.\n")
                .append("Promedio de importancia: No disponible.\n");
        } else {
            reporte.append("Mayor importancia: ").append(pistaMayorImportancia()).append("\n")
                .append("Mayor confiabilidad: ").append(pistaMayorConfiabilidad()).append("\n")
                .append("Promedio de importancia: ")
                .append(String.format(Locale.ROOT, "%.2f", promedioImportancia())).append("\n");
        }
        return reporte.toString();
    }
}
