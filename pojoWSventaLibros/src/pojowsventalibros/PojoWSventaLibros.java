/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojowsventalibros;

import aplicacionlibroonline.OperationFault;
import org.netbeans.xml.schema.ventaxmlschema.OutputComplexType;

/**
 *
 * @author rorod
 */
public class PojoWSventaLibros {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String isbn = "2328183";
        int idcliente = 1;
        int unidades = 1;
        int idcredito = 1;
        int precioUnidad = 1;
        
        org.netbeans.xml.schema.ventaxmlschema.InputComplexType part1 = new org.netbeans.xml.schema.ventaxmlschema.InputComplexType();
        part1.setIsbn(isbn);
        part1.setIdCliente(idcliente);
        part1.setPrecioUnidad(precioUnidad);
        part1.setIdCredito(idcredito);
        part1.setUnidades(unidades);
        
        aplicacionlibroonline.AplicacionLibroOnlineService1 service = new aplicacionlibroonline.AplicacionLibroOnlineService1();
        aplicacionlibroonline.MyVentaLibrosPortType port = service.getCasaPort1();
        for(int i = 0; i<10; i++){
            try{
                System.out.println(port.myVentaLibrosOperation(part1).getResponse());
        }catch(Exception e){
            
            System.out.println(e.toString());
            System.out.println(e.getMessage());
        }
        }
        
        
        
        
    }

    private static OutputComplexType myVentaLibrosOperation(org.netbeans.xml.schema.ventaxmlschema.InputComplexType part1) throws OperationFault {
        aplicacionlibroonline.AplicacionLibroOnlineService1 service = new aplicacionlibroonline.AplicacionLibroOnlineService1();
        aplicacionlibroonline.MyVentaLibrosPortType port = service.getCasaPort1();
        return port.myVentaLibrosOperation(part1);
    }
    
}
