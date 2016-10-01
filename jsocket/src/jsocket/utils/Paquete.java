/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsocket.utils;
import java.io.Serializable;

public class Paquete implements Serializable{
    
    private int keyOrigen = 0;
    private int keyDestino = 0;
    private String msg = "";
    private TipoMsg tpMsg = TipoMsg.PQT_NONE;
   
    /**
     * Constructor de paquete que se enviara entre usuarios
     * @param msg Mensaje del paquete
     * @param keyOrigen Key del cliente que creo el mensaje
     * @param keyDestino Identificador del  cliente a donde se enviara el mensaje
     * @param tp Tipo de paquete
     */
    public Paquete(String msg, int keyOrigen, int keyDestino, TipoMsg tp){
        this.keyOrigen = keyOrigen;
        this.keyDestino = keyDestino;
        this.msg = msg;
        this.tpMsg = tp;
    }
    public String getMsg(){
        return this.msg;
    }
    public int getOrigen(){
        return this.keyOrigen;
    }
    public void setOrigen(int value){
        this.keyOrigen = value;
    }
    public int getDestino(){
        return this.keyDestino;
    }
    public void setDestino(int value){
        this.keyDestino = value;
    }
    public TipoMsg getTipoMsg(){
        return this.tpMsg;
    }
    
    @Override
    public String toString() {
        return this.msg;
    }
}