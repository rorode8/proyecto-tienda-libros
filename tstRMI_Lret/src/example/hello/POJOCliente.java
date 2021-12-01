/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example.hello;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;


/**
 *
 * @author rorod
 */
public class POJOCliente {
    //private Cliente() {}
    public static void main(String[] args) 
    {
       long lngQuienSoy;
       long sumDeltaT, sumDeltaT2, dtMin = 0,dtMax = 0;
       long lngCuantosMilisFaltan;
       
        System.out.println("Cliente:");
        if( args.length == 0)
            System.out.println("Sin argumentos");
        else
            for(int k= 0;k < args.length; k++)
                System.out.println("args[" + k + "]:" + args[k]);
       
       String host = (args.length < 1) ? null : args[0];
       long i,n,t0,t1,dt;
       int a,b,c;
       String response;
       
       n = (args.length < 2) ? 2000: Integer.parseInt(args[1]);
        
       try 
        {
             Registry registry = LocateRegistry.getRegistry(host);
             IServDisparo servDisparo = (IServDisparo) registry.lookup("ServidorDeDisparo");
             lngQuienSoy = servDisparo.quienSoy();
             lngCuantosMilisFaltan = servDisparo.deltaTEnMilis();
             System.out.println("Cliente " + lngQuienSoy + " faltan " + lngCuantosMilisFaltan  + " milisegundos");
             sumDeltaT  = 0;
             sumDeltaT2 = 0;
             
                    //Hello stub = (Hello) registry.lookup("Hello");
             Random rand = new Random();
             org.netbeans.xml.schema.ventaxmlschema.InputComplexType part1 = new org.netbeans.xml.schema.ventaxmlschema.InputComplexType();
             String isbn = "2928183";
            int idcliente = 1;
            int unidades = 1;
            int idcredito = 1;
            int precioUnidad = 1;
             part1.setIsbn(isbn);
             part1.setIdCliente(idcliente);
             part1.setPrecioUnidad(precioUnidad);
             part1.setIdCredito(idcredito);
             part1.setUnidades(unidades);
        
             aplicacionlibroonline.AplicacionLibroOnlineService1 service = new aplicacionlibroonline.AplicacionLibroOnlineService1();
        
             aplicacionlibroonline.MyVentaLibrosPortType port = service.getCasaPort1();
             
             Thread.currentThread().sleep(lngCuantosMilisFaltan);
             
             for(i= 0; i < n; i++ )
             {
               t0 = System.currentTimeMillis();
               part1.setIdCliente(rand.nextInt(1)+1);
               part1.setIdCredito(rand.nextInt(1)+1);
               
               try{
                    response =port.myVentaLibrosOperation(part1).getResponse();
                }catch(Exception e){
                    response = e.getMessage();
                }
                
               
               //response = stub.sayHello();
               
               t1 = System.currentTimeMillis();
               dt = t1 - t0;
               sumDeltaT  += dt;
               sumDeltaT2 += dt * dt;
               if( i == 0 )
               {
                   dtMin = dt;
                   dtMax = dt;
               }
               else
               {
                   if( dt < dtMin ) dtMin = dt;
                   if( dt > dtMax ) dtMax = dt;
               }
               System.out.println("Clte " + lngQuienSoy + ", vez " + i + " ... " + response);
             }
             servDisparo.acumula(sumDeltaT, sumDeltaT2, n, dtMax, dtMin);
          } 
          catch (Exception e)
          {
              System.err.println("Client exception: " + e.toString());
               e.printStackTrace();
           }
    }

    
}
//================================================================
