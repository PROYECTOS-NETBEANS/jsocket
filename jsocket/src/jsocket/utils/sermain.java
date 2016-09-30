package jsocket.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class sermain {
    ServerSocket server;
    Socket scliente;
    
    ObjectInputStream stRead; 
    ObjectOutputStream stWrite;
    
    public sermain(){
        
    }
    public void iniciar(){
        try {
            server = new ServerSocket(9999);
            scliente = server.accept();
            System.out.println("passe accep");
            stRead = new ObjectInputStream(scliente.getInputStream());
            System.out.println("pase tsread");
            
            
            System.out.println("esperando mensaje");
            testobject data = (testobject) stRead.readObject();
            
            System.out.println("objecto recibido : " + data.id);
            
            scliente.close();
            server.close();
            
        } catch (IOException ex) {
            System.out.println("errroe : " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("no class found");
        }
       
    }
    public void version2(){
        
        try {
            server = new ServerSocket(9999);
            scliente = server.accept();           
            stRead = new ObjectInputStream(scliente.getInputStream());
            testobject to = (testobject) stRead.readObject();
            if (to!=null){
                System.out.println(to.id);
            }
            //System.out.println((String) stRead.readObject());
            scliente.close();
            server.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }
    public static void main(String[] arg){
        System.out.println("SISTEMA SERVIDOR");
        sermain main = new sermain();
        main.iniciar();
        //main.version2();
    }
}