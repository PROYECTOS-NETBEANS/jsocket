/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsocket.utils;

public class Paquete {
    
    private int key;
    private String msg;
    private TipoMsg tpMsg;
    
    public Paquete(String msg, int key, TipoMsg tp){
        this.key = key;
        this.msg = msg;
        this.tpMsg = tp;
    }
    public String getMsg(){
        return this.msg;
    }
    public int getKey(){
        return this.key;
    }
    public TipoMsg getTipoMsg(){
        return this.tpMsg;
    }
    
    @Override
    public String toString() {
        return this.msg;
    }
}
