/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsocket.utils;

import jsocket.utils.TipoMensaje;

public class Package {
    
    /**
     * Mensaje enviado
     */
    private String msg;
    /**
     * Hacia donde va dirigido el mensaje [privado o publico]
     */
    private TipoMensaje address;
    /**
     * Nombre de usuario a donde va dirigido el mensaje
     */
    private String nickAddress;
    /**
     * Nombre de usuario que envia el mensaje
     */
    private String nickName;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public TipoMensaje getAddress() {
        return address;
    }

    public void setAddress(TipoMensaje address) {
        this.address = address;
    }

    public String getNickAddress() {
        return nickAddress;
    }

    public void setNickAddress(String nickAddress) {
        this.nickAddress = nickAddress;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }    
}
