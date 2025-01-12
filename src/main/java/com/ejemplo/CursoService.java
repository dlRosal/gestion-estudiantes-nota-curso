package com.ejemplo;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CursoService {

    public void crearCurso(Curso curso) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(curso); // Crear
            transaction.commit();
            System.out.println("Curso creado exitosamente.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    public Curso obtenerCursoPorId(int id) {
        Curso curso = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            curso = session.get(Curso.class, id);
        } catch (Exception e) {
            System.err.println("Error al obtener el curso: " + e.getMessage());
            e.printStackTrace();
        }
        return curso;
    }


    public Curso leerCurso(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Curso.class, id); // Leer
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void actualizarCurso(Curso curso) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(curso); // Actualizar
            transaction.commit();
            System.out.println("Curso actualizado exitosamente.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void eliminarCurso(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Curso curso = session.get(Curso.class, id);
            if (curso != null) {
                session.delete(curso); // Eliminar
                System.out.println("Curso eliminado.");
            } else {
                System.out.println("Curso no encontrado.");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Curso> listarCursos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Curso", Curso.class).list(); // Listar todos
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
