package com.ejemplo;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EstudianteService {

    public void crearEstudiante(Estudiante estudiante) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(estudiante);
            transaction.commit();
            System.out.println("Estudiante creado con éxito.");
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                try {
                    transaction.rollback();
                } catch (Exception rollbackException) {
                    System.err.println("Error al hacer rollback: " + rollbackException.getMessage());
                }
            }
            System.err.println("Error al crear estudiante: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public Estudiante obtenerEstudiantePorId(int id) {
        Estudiante estudiante = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            estudiante = session.get(Estudiante.class, id);
        } catch (Exception e) {
            System.err.println("Error al obtener el estudiante: " + e.getMessage());
            e.printStackTrace();
        }
        return estudiante;
    }

    public Estudiante leerEstudiante(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Estudiante.class, id); // Leer
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void actualizarEstudiante(Estudiante estudiante) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(estudiante); // Actualizar
            transaction.commit();
            System.out.println("Estudiante actualizado exitosamente.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void eliminarEstudiante(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Estudiante estudiante = session.get(Estudiante.class, id);
            if (estudiante != null) {
                session.delete(estudiante); // Eliminar
                System.out.println("Estudiante eliminado.");
            } else {
                System.out.println("Estudiante no encontrado.");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Estudiante> listarEstudiantes() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Estudiante", Estudiante.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public void inscribirEstudianteEnCurso(int estudianteId, int cursoId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Estudiante estudiante = session.get(Estudiante.class, estudianteId);
            Curso curso = session.get(Curso.class, cursoId);

            if (estudiante == null || curso == null) {
                System.out.println("Estudiante o Curso no encontrado.");
                return;
            }

            estudiante.getCursos().add(curso);
            curso.getEstudiantes().add(estudiante);

            session.saveOrUpdate(estudiante);
            session.saveOrUpdate(curso);

            transaction.commit();
            System.out.println("Estudiante inscrito en el curso exitosamente.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
