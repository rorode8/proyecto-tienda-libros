/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ent;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author rorod
 */
@Stateless
public class EntregaFacade extends AbstractFacade<Entrega> {

    @PersistenceContext(unitName = "WA_AlmacenPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EntregaFacade() {
        super(Entrega.class);
    }
    
    @Override
    public void create(Entrega entity) {
        
        getEntityManager().persist(entity);
        getEntityManager().flush();
    }
    
    /**
     * 
     * @param pedidoid
     * @return true si no hay ninguna entrega con este pedidoid, es decir es valida
     */
    public boolean isValidPedidoID(int pedidoid){
        
        List<Object> results = em.createNativeQuery("Select id from Entrega where Pedido = "+pedidoid).getResultList();
        System.out.println(results.toString());
        return results.isEmpty();
        
    }
    
}
