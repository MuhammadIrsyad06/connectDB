/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.b.database.ws.b;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.Persistence;
import ws.b.database.ws.b.exceptions.NonexistentEntityException;
import ws.b.database.ws.b.exceptions.PreexistingEntityException;

/**
 *
 * @author lenovo
 */
public class UntitledJpaController implements Serializable {

    public UntitledJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("ws.b_database.ws.b_jar_0.0.1-SNAPSHOTPU");
    
    public UntitledJpaController(){
    
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Untitled untitled) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(untitled);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUntitled(untitled.getId()) != null) {
                throw new PreexistingEntityException("Untitled " + untitled + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Untitled untitled) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            untitled = em.merge(untitled);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = untitled.getId();
                if (findUntitled(id) == null) {
                    throw new NonexistentEntityException("The untitled with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Untitled untitled;
            try {
                untitled = em.getReference(Untitled.class, id);
                untitled.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The untitled with id " + id + " no longer exists.", enfe);
            }
            em.remove(untitled);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Untitled> findUntitledEntities() {
        return findUntitledEntities(true, -1, -1);
    }

    public List<Untitled> findUntitledEntities(int maxResults, int firstResult) {
        return findUntitledEntities(false, maxResults, firstResult);
    }

    private List<Untitled> findUntitledEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Untitled.class));
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

    public Untitled findUntitled(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Untitled.class, id);
        } finally {
            em.close();
        }
    }

    public int getUntitledCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Untitled> rt = cq.from(Untitled.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    Untitled findUntitled(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
