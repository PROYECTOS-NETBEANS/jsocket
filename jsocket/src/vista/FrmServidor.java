package vista;

import javax.swing.DefaultListModel;

import jsocket.server.JSocketServer;
import jsocket.server.OnConnectedEventServer;
import jsocket.server.OnConnectedListenerServer;
/**
 * 
 * @author Alex Limbert Yalusqui <limbertyalusqui@gmail.com>
 */
public class FrmServidor extends javax.swing.JFrame implements OnConnectedListenerServer{

    private DefaultListModel modelo = null;
    
    private DefaultListModel usuarios = null;
            
    private JSocketServer server = null;
    
    @SuppressWarnings("LeakingThisInConstructor")
    public FrmServidor() {
        
        initComponents();
        modelo = new DefaultListModel();
        usuarios = new DefaultListModel();
        server = new JSocketServer(5555);
        server.addEventListener(this);
        server.iniciarServicio();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        lstLista = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        btnEnviar = new javax.swing.JButton();
        txtMensaje = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstUsuarios = new javax.swing.JList();
        lblEstado = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        lstLista.setName(""); // NOI18N
        jScrollPane1.setViewportView(lstLista);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Usuarios Conectados");

        btnEnviar.setText(">");
        btnEnviar.setActionCommand("");
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(lstUsuarios);

        lblEstado.setText("ESTADO DE SERVIDOR:>>");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Mensajes de chat");

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(txtMensaje)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEnviar))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblEstado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(132, 132, 132))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEstado)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEnviar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed
        enviarMensaje();
    }//GEN-LAST:event_btnEnviarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        System.out.println("Count Client" + String.valueOf(JSocketServer.getClients().size()));
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // al cerrar el formulario detenemos el servicio
        server.detenerServicio();
    }//GEN-LAST:event_formWindowClosing
    /**
     * Activamos el evento de envio de mensaje
     */
    private void enviarMensaje(){
        //this.addMessageList(txtMensaje.getText());
        //servidor.onWrite();
    }
    private void removerUsuario(int key){
        for(int i = 0; i < usuarios.getSize(); i++){
            Usuario u = (Usuario) usuarios.get(i);
            if(u.getKey() == key){
                usuarios.remove(i);
                lstUsuarios.setModel(usuarios);
                this.repaint();
                return;
            }
        }
    }
    private void addMessageList(String msg){
        System.out.println("entre add msg");
        modelo.addElement(msg);
        lstLista.setModel(modelo);
    }
    private void addUsuarioList(Usuario usr){
        usuarios.addElement(usr);
        lstUsuarios.setModel(usuarios);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmServidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmServidor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEnviar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JList lstLista;
    private javax.swing.JList lstUsuarios;
    private javax.swing.JTextField txtMensaje;
    // End of variables declaration//GEN-END:variables

    @Override
    public void onServerStar(OnConnectedEventServer data) {
        this.lblEstado.setText("SERVIDOR INICIADO");
    }

    @Override
    public void onConnect(Object sender, OnConnectedEventServer data){
        System.out.println("key conect : " + String.valueOf(data.getOrigenClient()));
        this.addUsuarioList(new Usuario(data.getOrigenClient(), data.getMessageClient(), data.getMessageClient()));
    }

    @Override
    public void onDisconnect(Object sender, OnConnectedEventServer data) {
        
        if(data.getClientDisconnect()){
            System.out.println("vista.onDisconnect >> key : " + String.valueOf(data.getOrigenClient()));
            JSocketServer.removeConnectionClients(data.getOrigenClient());
            this.removerUsuario(data.getOrigenClient());
        }
    }

    @Override
    public void onRead(Object sender, OnConnectedEventServer data) {
        System.out.println("entre a mensaje : " + String.valueOf(data.getOrigenClient()));
        
        this.addMessageList(data.getMessageClient());
    }

    /*
    @Override
    public void onWrite(OnConnectedEventServer sender) {
        sender.sendMessageAll(txtMensaje.getText());
    }*/
}
class Usuario{
    private int key;
    private String userName;
    private String ip;
    /**
     * Constructor de clase
     * @param key identificador unico de usuario
     * @param userName nombre de usuario de la persona que acaba de conectarse
     * @param ip ip del cliente que se conecto
     */
    public Usuario(int key, String userName, String ip){
        this.key = key;
        this.userName = userName;
        this.ip = ip;
    }
    public int getKey(){
        return this.key;
    }
    public String getUserName(){
        return this.userName;
    }
    public String getIp(){
        return this.ip;
    }
    @Override
    public String toString(){
        return this.ip;
    }
}