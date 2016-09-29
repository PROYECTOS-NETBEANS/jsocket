/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jsocket.utils;

/**
 * 
 * @author Alex Limbert Yalusqui <limbertyalusqui@gmail.com>
 */
public enum TipoMensaje {
    
    MSG_PUBLICO("Mensaje para todos"),
    MSG_PRIVADO("Mensaje privado");
    
    private String msg = "";
    
    private TipoMensaje(String msg){
        this.msg = msg;
    }
}