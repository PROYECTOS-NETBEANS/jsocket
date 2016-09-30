package jsocket.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class climain {
    Socket cliente;
    ObjectInputStream stRead;
    ObjectOutputStream stWrite;
    
    public void iniciar(){
        try {
            cliente = new Socket("192.168.0.100", 9999);
            System.out.println("pase el contructor");
            
            stWrite = new ObjectOutputStream(cliente.getOutputStream());
            System.out.println("pase el write cliente");
            
            testobject test = new testobject(2, "objeto pruebas");
            
            stWrite.writeObject(test);
            stWrite.flush();
            //stWrite.close();       
            //cliente.close();
        } catch (IOException ex) {
            System.out.println("");
        }
    }
    
  public climain(){
      
  }
  public void version2(){
        try {          
          cliente = new Socket("localhost",9999);
          
          stWrite = new ObjectOutputStream(cliente.getOutputStream());
          testobject to = new testobject(1,"object from client");
          stWrite.writeObject(to);
          //stWrite.writeObject(new String("another object from the client"));
          stWrite.close();       
          cliente.close();
      } catch (IOException ex) {
          Logger.getLogger(climain.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
  public static void main(String[] arg){
    System.out.println("SISTEMA CLIENTE");
    climain main = new climain();
    main.iniciar();
    //main.version2();
  }
}

class testobject implements Serializable {
    int value ;
    String id;
    public  testobject(int v, String s ){
        this.value=v;
        this.id=s;
    }
}