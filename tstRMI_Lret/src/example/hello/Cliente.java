/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//================================================================
// Código del Cliente:
//================================================================
package example.hello;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
public class Cliente 
{
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
             Hello stub = (Hello) registry.lookup("Hello");
             Thread.currentThread().sleep(lngCuantosMilisFaltan);
             
             for(i= 0; i < n; i++ )
             {
               t0 = System.currentTimeMillis();
               a = (int) ( 1000.0 * (-1.0 + 2.0 * Math.random()));
               b = (int) ( 1000.0 * (-1.0 + 2.0 * Math.random()));
               c = stub.suma(a,b);
               response = "La suma de " + a + " y " + b + " es " + c;
               
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

