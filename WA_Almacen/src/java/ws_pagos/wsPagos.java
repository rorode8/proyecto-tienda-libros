/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws_pagos;

import ent.Cliente;
import ent.ClienteFacade;
import ent.Credito;
import ent.CreditoFacade;
import ent.Libro;
import ent.LibroFacade;
import ent.Pedido;
import ent.PedidoFacade;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author rorod
 */
@WebService(serviceName = "wsPagos")
public class wsPagos {

    /**
     * This is a sample web service operation
     */
    @EJB
    private LibroFacade ejbLibro;
    @EJB
    private CreditoFacade ejbCredito;
    @EJB
    private PedidoFacade ejbPedido;
    @EJB
    private ClienteFacade ejbCliente;
    
    
    /**
     * 
     * @param id
     * @return true if this id belongs to a registered client
     */
    @WebMethod(operationName = "isClient")
    public boolean isClient(@WebParam(name = "id") int id) {
        
        
        Cliente ct = ejbCliente.find(id);
        
        if(ct==null){
            return false;
        }else{
            return true;
        }
        
    }
    /**
     * 
     * @param id
     * @return true if this credit is registered
     */
    @WebMethod(operationName = "isCredit")
    public boolean isCredit(@WebParam(name = "id") int id) {
        
        
        Credito cre = ejbCredito.find(id);
        
        if(cre==null){
            return false;
        }else{
            return true;
        }
        
    }
    
    /**
     * is this credit valid to use?
     * @param clientid
     * @param creditid
     * @return  False si el credito no existe o no le pertenece a ese cliente
     */
    @WebMethod(operationName = "doesCreditBelongToClient")
    public boolean doesCreditBelongToClient(@WebParam(name = "clientid") int clientid, @WebParam(name = "creditid") int creditid) {
        
        Credito cre = ejbCredito.find(creditid);
        if(!this.isClient(creditid)){
            return false;
        }
        return cre.getClienteId().getId() == clientid;
        
    }
    
    @WebMethod(operationName = "hasEnoughCredit")
    public boolean hasEnoughCredit(@WebParam(name="amount") double amount, @WebParam(name = "creditId") int creditid){
        Credito cre = ejbCredito.find(creditid);
        
        return cre.getAmount().doubleValue()>=amount;
    }
    /**
     * assumes creditId already exists
     * @param amount
     * @param creditid
     * @return 
     */
    @WebMethod(operationName = "modifyCredit")
    public double modifyCredit(@WebParam(name="amount") double amount, @WebParam(name = "creditId") int creditid){
        Credito cre = ejbCredito.find(creditid);
        double ans = cre.getAmount().doubleValue();
        cre.setAmount(BigDecimal.valueOf(ans));
        return ans;
    }
    /**
     *  asume que el libro y el cliente existen
     * @param clientid
     * @param libroid
     * @param cantidad
     * @return 
     */
    @WebMethod(operationName = "creaPedido")
    public boolean creaPedido(@WebParam(name="clientid") int clientid, @WebParam(name="libroid") int libroid, @WebParam(name="cantidad") int cantidad){
        try{
            Cliente ct = ejbCliente.find(clientid);
            Libro lib = ejbLibro.find(libroid);
            Pedido pedido = new Pedido();
            pedido.setCantidad(cantidad);
            pedido.setClienteId(ct);
            pedido.setLibroId(lib);
            System.out.println("checkPoint1");
            ejbPedido.create(pedido);
        }catch(Exception e){
            System.out.println("no se pudo crear pedido");
            return false;
        }
        
        
        return true;
    }
    @WebMethod(operationName = "altaCliente")
    public boolean altaCliente(@WebParam(name="nombre") String nombre){
        try{
            Cliente ct = new Cliente();
            ct.setNombre(nombre);
            ejbCliente.create(ct);
        }catch(Exception e){
            return false;
        }
        return true;
    }
    
    
    
    
    
    
    
}
