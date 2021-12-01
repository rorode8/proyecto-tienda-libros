/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws_almacen;

import ent.Libro;
import ent.LibroFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

/**
 *
 * @author rorod
 */
@WebService(serviceName = "wsAlmacen")
public class wsAlmacen {

    @EJB
    private LibroFacade ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")


    @WebMethod(operationName = "getBookIDbyISBN")
    public int getBookIDbyISBN(@WebParam(name="isbn")String isbn){
        return ejbRef.getIDbyISBN(isbn);
    }

    /**
     * 
     * @param isbn
     * @return  true si el libro existe (hay un libro con ese isbn
     */
    @WebMethod(operationName = "isISBNUsed")
    public boolean isISBNUsed(@WebParam(name = "isbn") String isbn) {
        return !ejbRef.isISBN(isbn);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "addBook")    
    public boolean addBook(@WebParam(name = "isbn") String isbn) {
        Libro l  = new Libro();
        l.setIsbn(isbn);
        l.setCantidad(0);
        ejbRef.create(l);
        return true;
    }
    /**
     * 
     * @param isbn
     * @param amount positive if added, negative if buying
     * @return 
     */
    @WebMethod(operationName = "modifyStock")    
    public int modifyStock(@WebParam(name = "isbn") String isbn, @WebParam(name = "amount") int amount) {
        Libro lib = ejbRef.find(ejbRef.getIDbyISBN(isbn));
        int newQ = lib.getCantidad()+amount;
        lib.setCantidad(newQ);
        ejbRef.edit(lib);
        return newQ;
    }
    
    @WebMethod(operationName = "checkStock")    
    public int checkStock(@WebParam(name = "isbn") String isbn) {
        Libro lib = ejbRef.find(ejbRef.getIDbyISBN(isbn));
        
        return lib.getCantidad();
    }
    
    @WebMethod(operationName = "hasEnoughStock")
    public boolean hasEnoughStock(@WebParam(name="amount") int amount, @WebParam(name = "isbn") String isbn) {
        Libro lib = ejbRef.find(ejbRef.getIDbyISBN(isbn));
        if(amount>0 && lib.getCantidad()>=amount){
            lib.setCantidad(lib.getCantidad()-amount);
            ejbRef.edit(lib);
            return true;
        }else{
            return false;
        }
        
    }
    
}
