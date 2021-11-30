/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws_entregas;

import ent.Entrega;
import ent.EntregaFacade;
import ent.Pedido;
import ent.PedidoFacade;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author rorod
 */
@WebService(serviceName = "wsEntregas")
public class wsEntregas {

    /**
     * This is a sample web service operation
     */
    
    @EJB
    private PedidoFacade ejbPedido;
    @EJB
    private EntregaFacade ejbEntrega;
    
        
    /**
     * 
     * @param pedidoid
     * @return  true si existe este pedido
     */
    @WebMethod(operationName= "isPedido")
    public boolean isPedido(@WebParam(name = "pedidoid") int pedidoid){
        Pedido pedido = ejbPedido.find(pedidoid);
        
        if(pedido==null){
            return false;
        }else{
            return true;
        }
    }
    /**
     * asume que el pedidoid existe y que los dias son positivos
     * @param pedidoid
     * @param dias
     * @return true si no existe una entrega para este pedidoid. Afirmado que se creó la entrega
     */
    @WebMethod(operationName= "createEntrega")
    public int createEntrega(@WebParam(name = "pedidoid") int pedidoid, @WebParam(name = "dias") int dias){
        int idx = -1;
        if(!ejbEntrega.isValidPedidoID(pedidoid)){ //este pedido ya se entrego
            idx = -1;
        }else{
            Entrega ent = new Entrega();
            ent.setDias(dias);
            ent.setPedido(ejbPedido.find(pedidoid));
            ejbEntrega.create(ent);
            idx = ent.getId();
        }
        return idx;
    }
    
    
}
