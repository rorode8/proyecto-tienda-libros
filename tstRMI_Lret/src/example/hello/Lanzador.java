/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example.hello;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Rafael
 */
public class Lanzador
{
    public static void main(String[] args)
    {
        String line;
        String strCmd;
        
        int j;
        int    numCltes     = 1;
        String strClaseClte = "Cliente";
        int    numSol       = 5;
        String strHost      = "localhost";
        
        int cuantos_fin;
        
        if(args.length > 0) numCltes     = Integer.parseInt(args[0]);
        if(args.length > 1) strClaseClte = args[1];
        if(args.length > 2) numSol       = Integer.parseInt(args[2]);
        if(args.length > 3) strHost      = args[3];
        
        ArrayList<Process>        process    = new ArrayList<Process>();
        ArrayList<BufferedReader> br         = new ArrayList<BufferedReader>();
        boolean cont_resp[]                  = new boolean[numCltes];
        
        Path currentRelativePath = Paths.get("");
        String strCWD = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current absolute path is:" + strCWD);
        String strJAR = strCWD + "\\tstRMI.jar";
        
        try
        {           
            //strCmd = "java -jar \"D:\\user\\Materias\\SCE\\SCE_202103\\Sesion_03\\proyHolaMundo\\dist\\proyHolaMundo.jar\"";
            strCmd = "java -Djava.rmi.server.codebase=file:" + strJAR + " -jar " + strJAR + " " + strClaseClte + " " + strHost + " " + numSol;
            System.out.println(strCmd);
            for(j=0;j<numCltes;j++)
            {
                process.add(Runtime.getRuntime().exec( strCmd ));
                System.out.println("process " + j + " successfully started");
                br.add(new BufferedReader(
                      new InputStreamReader(process.get(j).getInputStream())));
                cont_resp[j] = true;
            }
            cuantos_fin = 0;     
            while ( cuantos_fin < numCltes )
            {
               for(j = 0; j < numCltes; j++)
               {
                  if(cont_resp[j])
                  {
                    line = br.get(j).readLine();
                    if( line != null)             
                    {    
                       System.out.println(line);
                       if( process.get(j).waitFor(1,TimeUnit.MILLISECONDS))
                       {
                             cont_resp[j] = false;
                             cuantos_fin++;
                       }
                    }
                  }
               }  
            }         
            System.out.println("Los clientes terminaron de ejecutar");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}    
