import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/** Driver: solicita entradas y muestra resultados; Caso administra la investigacion. */
public class Main {
    private static final Scanner entrada = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            System.out.println("AGENCIA DE DETECTIVES - CASO MISTERIOSO");
            Caso caso = leerCaso();
            boolean continuar = true;
            while (continuar) {
                mostrarMenu();
                try {
                    int opcion = leerEntero("Seleccione una opcion: ");
                    switch (opcion) {
                        case 1: {
                            // La instancia anterior se reemplaza solo al terminar la lectura.
                            caso = leerCaso();
                            System.out.println("Nuevo caso creado sin ubicaciones ni pistas.");
                            break;
                        }
                        case 2: {
                            int posicion = leerEntero("Posicion del arreglo (0 a 4): ");
                            Ubicacion ubicacion = leerUbicacion();
                            caso.registrarUbicacion(posicion, ubicacion);
                            System.out.println("Ubicacion registrada.");
                            break;
                        }
                        case 3:
                            System.out.println(caso.listarUbicaciones());
                            break;
                        case 4:
                            System.out.println(caso.consultarUbicacion(leerEntero("Posicion (0 a 4): ")));
                            break;
                        case 5: {
                            int posicion = leerEntero("Posicion (0 a 4): ");
                            System.out.println(caso.consultarUbicacion(posicion));
                            int riesgo = leerEntero("Nuevo riesgo (1 a 10): ");
                            String estado = leerTexto("Nuevo estado: ");
                            caso.modificarUbicacion(posicion, riesgo, estado);
                            System.out.println("Ubicacion modificada.");
                            break;
                        }
                        case 6:
                            caso.descartarUbicacion(leerEntero("Posicion (0 a 4): "));
                            System.out.println("Ubicacion descartada.");
                            break;
                        case 7:
                            caso.registrarPista(leerPista());
                            System.out.println("Pista registrada.");
                            break;
                        case 8:
                            System.out.println(caso.listarPistas());
                            break;
                        case 9:
                            System.out.println(caso.buscarPista(leerTexto("Codigo de la pista: ")));
                            break;
                        case 10: {
                            String codigo = leerTexto("Codigo actual de la pista: ");
                            System.out.println(caso.buscarPista(codigo));
                            System.out.println("Ingrese todos los datos actualizados. Puede repetir el codigo actual.");
                            Pista nueva = leerPista();
                            caso.modificarPista(codigo, nueva);
                            System.out.println("Pista modificada.");
                            break;
                        }
                        case 11:
                            caso.eliminarPista(leerTexto("Codigo de la pista: "));
                            System.out.println("Pista eliminada.");
                            break;
                        case 12:
                            System.out.println(caso.generarReporte());
                            break;
                        case 13:
                            continuar = false;
                            System.out.println("Programa finalizado.");
                            break;
                        default:
                            System.out.println("Opcion invalida. Ingrese un numero entre 1 y 13.");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Dato invalido: " + e.getMessage());
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Posicion invalida: " + e.getMessage());
                } catch (IllegalStateException e) {
                    System.out.println("Operacion no disponible: " + e.getMessage());
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("\nLa entrada se ha cerrado. Fin del programa.");
        } finally {
            // Se libera el recurso tanto al salir normalmente como ante una excepcion.
            entrada.close();
        }
    }

    private static String leerTexto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = entrada.nextLine().trim();
            if (!texto.isEmpty()) {
                return texto;
            }
            System.out.println("El texto no puede estar vacio.");
        }
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return entrada.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Entrada incorrecta. Ingrese un numero entero.");
            } finally {
                // Consume la linea: retira el dato incorrecto o el salto tras el entero.
                if (entrada.hasNextLine()) {
                    entrada.nextLine();
                }
            }
        }
    }

    private static Caso leerCaso() {
        System.out.println("\n--- Datos del caso ---");
        String nombre = leerTexto("Nombre del caso: ");
        String codigo = leerTexto("Codigo del caso: ");
        String detective = leerTexto("Detective responsable: ");
        return new Caso(nombre, codigo, detective);
    }

    private static Ubicacion leerUbicacion() {
        String codigo = leerTexto("Codigo de la ubicacion: ");
        String nombre = leerTexto("Nombre de la ubicacion: ");
        String direccion = leerTexto("Direccion o descripcion: ");
        int riesgo = leerEntero("Nivel de riesgo (1 a 10): ");
        String estado = leerTexto("Estado (por ejemplo: pendiente, investigada o descartada): ");
        return new Ubicacion(codigo, nombre, direccion, riesgo, estado);
    }

    private static Pista leerPista() {
        String codigo = leerTexto("Codigo de la pista: ");
        String descripcion = leerTexto("Descripcion: ");
        String tipo = leerTexto("Tipo de evidencia: ");
        int importancia = leerEntero("Importancia (1 a 10): ");
        int confiabilidad = leerEntero("Confiabilidad (0 a 100): ");
        return new Pista(codigo, descripcion, tipo, importancia, confiabilidad);
    }

    private static void mostrarMenu() {
        System.out.println("\n========== MENU ==========");
        System.out.println("1. Nuevo caso");
        System.out.println("2. Registrar ubicacion");
        System.out.println("3. Consultar ubicaciones");
        System.out.println("4. Consultar una ubicacion");
        System.out.println("5. Modificar ubicacion");
        System.out.println("6. Descartar ubicacion");
        System.out.println("7. Registrar pista");
        System.out.println("8. Consultar pistas");
        System.out.println("9. Buscar pista");
        System.out.println("10. Modificar pista");
        System.out.println("11. Eliminar pista");
        System.out.println("12. Mostrar reporte de investigacion");
        System.out.println("13. Salir");
    }
}
