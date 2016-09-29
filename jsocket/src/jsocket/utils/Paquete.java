/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsocket.utils;

public class Paquete {
    public int key;
    public String msg;
    
    public Paquete(String msg, int key){
        this.key = key;
        this.msg = msg;
    }
}
