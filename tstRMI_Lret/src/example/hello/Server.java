/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//================================================================
//Código del Servidor:
//================================================================
package example.hello;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
//import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Server implements Hello {

    //double muslambda_nseg = -1.0e9/150.0; // en nanosegundos, Lambda = 250 atenciones por segundo
    double muslambda_nseg; // en nanosegundos, Lambda = 250 atenciones por segundo
    long cuantos = 0;
    String nomMaq = System.getenv("COMPUTERNAME");

    public Server() {
        muslambda_nseg = 0;
    }

    public Server(int procesos) {
        muslambda_nseg = -1.0e9 / procesos;
    }

    public synchronized String sayHello() {
        cuantos++;
        System.out.println("Proporcionando el servicio no. " + cuantos);
        return "Este es el servicio no. " + cuantos + " desde " + nomMaq;
    }

    public synchronized int suma(int a, int b) {

        long retardo, retMilis;
        int retNanos;
        if (muslambda_nseg < 0.0) {
            retardo = (long) (muslambda_nseg * Math.log(Math.random()));
            retMilis = (long) (retardo * 1e-6);
            retNanos = (int) (retardo - 1e6 * retMilis);
            try {
                Thread.currentThread().sleep(retMilis, retNanos);
            } catch (Exception e) {
                System.out.println("Server: Falla al dormir o despertar...");
                e.printStackTrace();
            }
        }
        return a + b;
    }

    public static void main(String args[]) {
        String host = (args.length < 1) ? null : args[0];

        System.out.println("Server, inciando con:");
        if (args.length == 0) {
            System.out.println("Sin argumentos");
        } else {
            for (int i = 0; i < args.length; i++) {
                System.out.println("args[" + i + "]:" + args[i]);
            }
        }
        System.out.println("------------------------------------------");

        try {
            Server obj = null;
            if (args.length == 2) {

                int procesos = Integer.parseInt(args[1]);
                System.out.println("procesos: " + procesos);
                if (procesos <= 0) {
                    throw new Exception("la cantidad de procesos es menor o igual a 0");
                }
                obj = new Server(procesos);
            } else {
                obj = new Server();
            }

            Hello stub = (Hello) UnicastRemoteObject.exportObject(obj, 0);
            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry(host);
            registry.rebind("Hello", stub);
            System.err.println("Listo el servicio...");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
