/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ent;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author rorod
 */
@Stateless
public class LibroFacade extends AbstractFacade<Libro> {

    @PersistenceContext(unitName = "WA_AlmacenPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public boolean isISBN(String isbn){
        
        List<Object> results = em.createNativeQuery("Select id from Libro where isbn = '"+isbn+"'").getResultList();
        System.out.println(results.toString());
        return results.isEmpty();
        
    }
    
    public int getIDbyISBN(String isbn){
        
        List<Object> results = em.createNativeQuery("Select id from Libro where isbn = '"+isbn+"'").getResultList();
        
        return Integer.parseInt(results.get(0).toString());
        
    }

    public LibroFacade() {
        super(Libro.class);
    }
    
    
    
}
