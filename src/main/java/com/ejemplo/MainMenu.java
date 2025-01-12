package com.ejemplo;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class MainMenu {

    private static final Scanner scanner = new Scanner(System.in);
    private static final EstudianteService estudianteService = new EstudianteService();
    private static final CursoService cursoService = new CursoService();
    private static final NotaService notaService = new NotaService();

    public static void main(String[] args) {
        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero("Selecciona una opción: ");

            switch (opcion) {
                case 1 -> crearEstudiante();
                case 2 -> listarEstudiantes();
                case 3 -> actualizarEstudiante();
                case 4 -> eliminarEstudiante();
                case 5 -> inscribirEstudianteEnCurso();
                case 6 -> crearCurso();
                case 7 -> listarCursos();
                case 8 -> actualizarCurso();
                case 9 -> eliminarCurso();
                case 10 -> crearNota();
                case 11 -> listarNotasPorEstudianteYCurso();
                case 12 -> actualizarNota();
                case 13 -> eliminarNota();
                case 14 -> consultarNotasPorEstudianteYCurso();
                case 15 -> listarEstudiantesYNotasPorCurso();
                case 16 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción no válida. Inténtalo de nuevo.");
            }

        } while (opcion != 16);
    }

    private static void mostrarMenu() {
        System.out.println("\n=== Menú Principal ===");
        System.out.println("1. Crear estudiante");
        System.out.println("2. Listar estudiantes");
        System.out.println("3. Actualizar estudiante");
        System.out.println("4. Eliminar estudiante");
        System.out.println("5. Inscribir estudiante en curso");
        System.out.println("6. Crear curso");
        System.out.println("7. Listar cursos");
        System.out.println("8. Actualizar curso");
        System.out.println("9. Eliminar curso");
        System.out.println("10. Agregar nota");
        System.out.println("11. Listar notas por estudiante y curso");
        System.out.println("12. Actualizar nota");
        System.out.println("13. Eliminar nota");
        System.out.println("14. Consultar notas de un estudiante en un curso específico");
        System.out.println("15. Listar estudiantes inscritos en un curso y sus respectivas notas");
        System.out.println("16. Salir");
    }

    private static void crearEstudiante() {
        String nombre = leerCadena("Nombre: ");
        String apellido = leerCadena("Apellido: ");
        String email = leerCadena("Email: ");

        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(nombre);
        estudiante.setApellido(apellido);
        estudiante.setEmail(email);

        estudianteService.crearEstudiante(estudiante);
        System.out.println("Estudiante creado exitosamente.");
    }

    private static void listarEstudiantes() {
        estudianteService.listarEstudiantes().forEach(e ->
                System.out.println(e.getId() + ". " + e.getNombre() + " " + e.getApellido() + " - " + e.getEmail()));
    }

    private static void actualizarEstudiante() {
        listarEstudiantes();
        int id = leerEntero("ID del estudiante a actualizar: ");
        Estudiante estudiante = estudianteService.leerEstudiante(id);
        if (estudiante != null) {
            String nombre = leerCadena("Nuevo nombre: ");
            String apellido = leerCadena("Nuevo apellido: ");
            String email = leerCadena("Nuevo email: ");
            estudiante.setNombre(nombre);
            estudiante.setApellido(apellido);
            estudiante.setEmail(email);
            estudianteService.actualizarEstudiante(estudiante);
            System.out.println("Estudiante actualizado exitosamente.");
        } else {
            System.out.println("Estudiante no encontrado.");
        }
    }

    private static void eliminarEstudiante() {
        listarEstudiantes();
        int id = leerEntero("ID del estudiante a eliminar: ");
        estudianteService.eliminarEstudiante(id);
    }

    private static void inscribirEstudianteEnCurso() {
        listarEstudiantes();
        int estudianteId = leerEntero("ID del estudiante: ");
        listarCursos();
        int cursoId = leerEntero("ID del curso: ");
        estudianteService.inscribirEstudianteEnCurso(estudianteId, cursoId);
    }

    private static void crearCurso() {
        String nombre = leerCadena("Nombre del curso: ");
        String descripcion = leerCadena("Descripción del curso: ");

        Curso curso = new Curso();
        curso.setNombre(nombre);
        curso.setDescripcion(descripcion);

        cursoService.crearCurso(curso);
        System.out.println("Curso creado exitosamente.");
    }

    private static void listarCursos() {
        cursoService.listarCursos().forEach(c ->
                System.out.println(c.getId() + ". " + c.getNombre() + " - " + c.getDescripcion()));
    }

    private static void actualizarCurso() {
        listarCursos();
        int id = leerEntero("ID del curso a actualizar: ");
        Curso curso = cursoService.leerCurso(id);
        if (curso != null) {
            String nombre = leerCadena("Nuevo nombre: ");
            String descripcion = leerCadena("Nueva descripción: ");
            curso.setNombre(nombre);
            curso.setDescripcion(descripcion);
            cursoService.actualizarCurso(curso);
            System.out.println("Curso actualizado exitosamente.");
        } else {
            System.out.println("Curso no encontrado.");
        }
    }

    private static void eliminarCurso() {
        listarCursos();
        int id = leerEntero("ID del curso a eliminar: ");
        cursoService.eliminarCurso(id);
    }

    private static void crearNota() {

            listarEstudiantes();
            System.out.println("Ingrese el ID del estudiante:");
            int estudianteId = scanner.nextInt();
            Estudiante estudiante = estudianteService.obtenerEstudiantePorId(estudianteId);
            if (estudiante == null) {
                System.out.println("El estudiante no existe.");
                return;
            }
            listarCursos();
            System.out.println("Ingrese el ID del curso:");
            int cursoId = scanner.nextInt();
            Curso curso = cursoService.obtenerCursoPorId(cursoId);
            if (curso == null) {
                System.out.println("El curso no existe.");
                return;
            }

            System.out.println("Ingrese el valor de la nota:");
            double valor = scanner.nextDouble();

            System.out.println("Ingrese la fecha de la nota (yyyy-MM-dd):");
            String fechaStr = scanner.next();
            Date fecha = Date.valueOf(fechaStr); // Convierte a formato SQL Date

            Nota nota = new Nota();
            nota.setEstudiante(estudiante);
            nota.setCurso(curso);
            nota.setValor(valor);
            nota.setFecha(fecha);

            notaService.crearNota(nota);
        }



    private static void listarNotasPorEstudianteYCurso() {
        listarEstudiantes();
        int estudianteId = leerEntero("ID del estudiante: ");
        listarCursos();
        int cursoId = leerEntero("ID del curso: ");
        notaService.obtenerNotasPorEstudianteYCurso(estudianteId, cursoId).forEach(n ->
                System.out.println("Nota: " + n.getValor() + " | Fecha: " + n.getFecha()));
    }

    private static void actualizarNota() {
        listarEstudiantes();
        int estudianteId = leerEntero("ID del estudiante: ");
        listarCursos();
        int cursoId = leerEntero("ID del curso: ");

        List<Nota> notas = notaService.obtenerNotasPorEstudianteYCurso(estudianteId, cursoId);
        if (notas.isEmpty()) {
            System.out.println("No se encontraron notas para el estudiante en este curso.");
            return;
        }

        System.out.println("Notas encontradas:");
        notas.forEach(n -> System.out.println("ID: " + n.getId() + " | Nota: " + n.getValor() + " | Fecha: " + n.getFecha()));

        int notaId = leerEntero("ID de la nota a actualizar: ");
        Nota nota = notaService.leerNota(notaId);

        if (nota != null) {
            double nuevoValor = leerDouble("Nuevo valor de la nota: ");
            nota.setValor(nuevoValor);
            notaService.actualizarNota(nota);
            System.out.println("Nota actualizada exitosamente.");
        } else {
            System.out.println("Nota no encontrada.");
        }
    }

    public static void eliminarNota() {
        Scanner scanner = new Scanner(System.in);
        NotaService notaService = new NotaService();

        // Listar todas las notas disponibles
        System.out.println("Lista de notas disponibles:");
        List<Nota> notas = notaService.listarNotas();
        if (notas.isEmpty()) {
            System.out.println("No hay notas disponibles para eliminar.");
            return;
        }

        // Mostrar las notas con sus IDs y atributos disponibles
        for (Nota nota : notas) {
            System.out.println("ID: " + nota.getId() +
                    ", Curso ID: " + nota.getCurso().getId() +
                    ", Estudiante ID: " + nota.getEstudiante().getId());
        }

        // Solicitar al usuario el ID a eliminar
        System.out.println("Ingrese el ID de la nota que desea eliminar:");
        int id = scanner.nextInt();

        // Llamar al método para eliminar la nota
        notaService.eliminarNota(id);
    }

    private static void consultarNotasPorEstudianteYCurso() {
        listarEstudiantes();
        int estudianteId = leerEntero("ID del estudiante: ");
        listarCursos();
        int cursoId = leerEntero("ID del curso: ");

        List<Nota> notas = notaService.obtenerNotasPorEstudianteYCurso(estudianteId, cursoId);
        if (notas.isEmpty()) {
            System.out.println("No se encontraron notas para el estudiante en este curso.");
        } else {
            System.out.println("Notas encontradas:");
            notas.forEach(n -> System.out.println("Nota: " + n.getValor() + " | Fecha: " + n.getFecha()));
        }
    }

    private static void listarEstudiantesYNotasPorCurso() {
        listarCursos();
        int cursoId = leerEntero("ID del curso: ");

        Curso curso = cursoService.leerCurso(cursoId);
        if (curso == null) {
            System.out.println("Curso no encontrado.");
            return;
        }

        System.out.println("Estudiantes inscritos en el curso: " + curso.getNombre());
        curso.getEstudiantes().forEach(estudiante -> {
            System.out.println("Estudiante: " + estudiante.getNombre() + " " + estudiante.getApellido());
            List<Nota> notas = notaService.obtenerNotasPorEstudianteYCurso(estudiante.getId(), cursoId);
            notas.forEach(nota -> System.out.println("  Nota: " + nota.getValor() + " | Fecha: " + nota.getFecha()));
        });
    }

    private static String leerCadena(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.print("Por favor, ingresa un número válido: ");
            scanner.next();
        }
        int numero = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        return numero;
    }

    private static double leerDouble(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextDouble()) {
            System.out.print("Por favor, ingresa un número válido: ");
            scanner.next();
        }
        double numero = scanner.nextDouble();
        scanner.nextLine(); // Limpiar buffer
        return numero;
    }
}
