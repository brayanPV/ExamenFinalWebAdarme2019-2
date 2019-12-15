/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import DTO.Amigo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Horario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author estudiante
 */
public class AmigoJpaController implements Serializable {

    public AmigoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Amigo amigo) throws PreexistingEntityException, Exception {
        if (amigo.getHorarioList() == null) {
            amigo.setHorarioList(new ArrayList<Horario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Horario> attachedHorarioList = new ArrayList<Horario>();
            for (Horario horarioListHorarioToAttach : amigo.getHorarioList()) {
                horarioListHorarioToAttach = em.getReference(horarioListHorarioToAttach.getClass(), horarioListHorarioToAttach.getIdHorario());
                attachedHorarioList.add(horarioListHorarioToAttach);
            }
            amigo.setHorarioList(attachedHorarioList);
            em.persist(amigo);
            for (Horario horarioListHorario : amigo.getHorarioList()) {
                Amigo oldCodigoOfHorarioListHorario = horarioListHorario.getCodigo();
                horarioListHorario.setCodigo(amigo);
                horarioListHorario = em.merge(horarioListHorario);
                if (oldCodigoOfHorarioListHorario != null) {
                    oldCodigoOfHorarioListHorario.getHorarioList().remove(horarioListHorario);
                    oldCodigoOfHorarioListHorario = em.merge(oldCodigoOfHorarioListHorario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAmigo(amigo.getCodigo()) != null) {
                throw new PreexistingEntityException("Amigo " + amigo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Amigo amigo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Amigo persistentAmigo = em.find(Amigo.class, amigo.getCodigo());
            List<Horario> horarioListOld = persistentAmigo.getHorarioList();
            List<Horario> horarioListNew = amigo.getHorarioList();
            List<String> illegalOrphanMessages = null;
            for (Horario horarioListOldHorario : horarioListOld) {
                if (!horarioListNew.contains(horarioListOldHorario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Horario " + horarioListOldHorario + " since its codigo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Horario> attachedHorarioListNew = new ArrayList<Horario>();
            for (Horario horarioListNewHorarioToAttach : horarioListNew) {
                horarioListNewHorarioToAttach = em.getReference(horarioListNewHorarioToAttach.getClass(), horarioListNewHorarioToAttach.getIdHorario());
                attachedHorarioListNew.add(horarioListNewHorarioToAttach);
            }
            horarioListNew = attachedHorarioListNew;
            amigo.setHorarioList(horarioListNew);
            amigo = em.merge(amigo);
            for (Horario horarioListNewHorario : horarioListNew) {
                if (!horarioListOld.contains(horarioListNewHorario)) {
                    Amigo oldCodigoOfHorarioListNewHorario = horarioListNewHorario.getCodigo();
                    horarioListNewHorario.setCodigo(amigo);
                    horarioListNewHorario = em.merge(horarioListNewHorario);
                    if (oldCodigoOfHorarioListNewHorario != null && !oldCodigoOfHorarioListNewHorario.equals(amigo)) {
                        oldCodigoOfHorarioListNewHorario.getHorarioList().remove(horarioListNewHorario);
                        oldCodigoOfHorarioListNewHorario = em.merge(oldCodigoOfHorarioListNewHorario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = amigo.getCodigo();
                if (findAmigo(id) == null) {
                    throw new NonexistentEntityException("The amigo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Amigo amigo;
            try {
                amigo = em.getReference(Amigo.class, id);
                amigo.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The amigo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Horario> horarioListOrphanCheck = amigo.getHorarioList();
            for (Horario horarioListOrphanCheckHorario : horarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Amigo (" + amigo + ") cannot be destroyed since the Horario " + horarioListOrphanCheckHorario + " in its horarioList field has a non-nullable codigo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(amigo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Amigo> findAmigoEntities() {
        return findAmigoEntities(true, -1, -1);
    }

    public List<Amigo> findAmigoEntities(int maxResults, int firstResult) {
        return findAmigoEntities(false, maxResults, firstResult);
    }

    private List<Amigo> findAmigoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Amigo.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Amigo findAmigo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Amigo.class, id);
        } finally {
            em.close();
        }
    }

    public int getAmigoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Amigo> rt = cq.from(Amigo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
