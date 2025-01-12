package com.ejemplo;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class NotaService {

    public void crearNota(Nota nota) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Verifica que el estudiante y curso estén establecidos
            if (nota.getEstudiante() == null || nota.getCurso() == null) {
                throw new IllegalArgumentException("Estudiante y Curso no pueden ser nulos.");
            }

            // Guarda la nota
            session.save(nota);

            transaction.commit();
            System.out.println("Nota creada exitosamente.");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Error al crear la nota: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Nota leerNota(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Nota.class, id); // Leer
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Nota> listarNotas() {
        Transaction transaction = null;
        List<Nota> notas = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            notas = session.createQuery("FROM Nota", Nota.class).list(); // Consultar todas las notas
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return notas;
    }

    public void actualizarNota(Nota nota) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(nota); // Actualizar
            transaction.commit();
            System.out.println("Nota actualizada exitosamente.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void eliminarNota(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Nota nota = session.get(Nota.class, id);
            if (nota != null) {
                session.delete(nota); // Eliminar
                System.out.println("Nota eliminada.");
            } else {
                System.out.println("Nota no encontrada.");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Nota> obtenerNotasPorEstudianteYCurso(int estudianteId, int cursoId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from Nota n where n.estudiante.id = :estudianteId and n.curso.id = :cursoId", Nota.class)
                    .setParameter("estudianteId", estudianteId)
                    .setParameter("cursoId", cursoId)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
