/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsocket.utils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
 

public class jlist implements ActionListener {

    JFrame f;

    JPanel p;

    JList list;

    JButton b;

    ArrayList alist;

    DefaultListModel model;
 

    public jlist(){

        f = new JFrame();

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        p = new JPanel();
 

        model = new DefaultListModel();
 

        Map data = new HashMap();

        data.put(1011,"Nike Blue Trainers");

        data.put(1012,"Red Cap");

        data.put(1013,"Sport's Watch");
 

        alist = new ArrayList();

        Iterator it = data.keySet().iterator();

        int x=0;

        while(it.hasNext()){

            Integer id = (Integer)it.next();

            Map m = new HashMap();

            m.put(id,data.get(id));

            alist.add(x,m);

            model.add(x,data.get(id));

        }
 

        list = new JList(model);
 

        JScrollPane sp = new JScrollPane(list,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        

        b = new JButton("OK");

        b.addActionListener(this);
 

        p.add(sp);

        p.add(b);
 

        f.getContentPane().add(p);

        f.setSize(300,300);

        f.setVisible(true);

    }
 

    public static void main(String[] args) {

        new jlist();

    }

    public void actionPerformed(ActionEvent e){

        if(e.getSource()==b){

            int idx = list.getSelectedIndex();

            Map m = (Map)alist.get(idx);

            Integer pid = (Integer)m.keySet().toArray()[0];

            JOptionPane.showMessageDialog(f,"Product id: "+pid);

        }

    }

}