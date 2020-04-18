package view;
import javax.swing.JOptionPane;

import controller.*; //importando as classes do pacote;
/**
 *
 * @author Matheus Moreira
 */
public class Tela extends javax.swing.JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	SerialComLeitura serialEscrita;
    boolean status;
    /**
     * Creates new form Tela
     */
    public Tela() {
        initComponents();
        String porta = JOptionPane.showInputDialog(null, "Informe o nome de sua porta serial.", "COM1");
        serialEscrita = new SerialComLeitura(porta, 9600, 0);
        serialEscrita.habilitarEscrita(taLog);
        serialEscrita.ObterIdDaPorta();
        status = true;
    }

    private void initComponents() {

        btnLigar = new javax.swing.JButton();
        btnDesligar = new javax.swing.JButton();
        btnAjuda = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taLog = new javax.swing.JTextArea();
        lblAutor = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Projeto de EEC");
        setPreferredSize(new java.awt.Dimension(400, 500));
        setSize(new java.awt.Dimension(400, 500));
        getContentPane().setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        btnLigar.setFont(btnLigar.getFont().deriveFont(btnLigar.getFont().getStyle() | java.awt.Font.BOLD, 12));
        btnLigar.setForeground(new java.awt.Color(0, 155, 82));
        btnLigar.setText("Ligar");
        btnLigar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLigarActionPerformed(evt);
            }
        });
        getContentPane().add(btnLigar);
        btnLigar.setBounds(150, 60, 100, 25);

        btnDesligar.setFont(btnDesligar.getFont().deriveFont(btnDesligar.getFont().getStyle() | java.awt.Font.BOLD, 12));
        btnDesligar.setForeground(java.awt.Color.red);
        btnDesligar.setText("Desligar");
        btnDesligar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesligarActionPerformed(evt);
            }
        });
        getContentPane().add(btnDesligar);
        btnDesligar.setBounds(150, 90, 100, 25);
        
        btnAjuda.setFont(btnAjuda.getFont().deriveFont(btnAjuda.getFont().getStyle() | java.awt.Font.BOLD, 12));
        btnAjuda.setForeground(java.awt.Color.MAGENTA);
        btnAjuda.setText("Ajuda");
        btnAjuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAjudaActionPerformed(evt);
            }
        });
        getContentPane().add(btnAjuda);
        btnAjuda.setBounds(150, 120, 100, 25);
        

        lblTitulo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Controle do Ventilador USB");
        getContentPane().add(lblTitulo);
        lblTitulo.setBounds(20, 10, 350, 40);

        taLog.setBackground(java.awt.Color.black);
        taLog.setColumns(20);
        taLog.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        taLog.setForeground(java.awt.Color.green);
        taLog.setRows(5);
        taLog.setFocusable(false);
        jScrollPane1.setViewportView(taLog);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 180, 360, 270);

        lblAutor.setFont(lblAutor.getFont().deriveFont((lblAutor.getFont().getStyle() | java.awt.Font.ITALIC), 10));
        lblAutor.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblAutor.setText("CONSOLE                        Matheus Moreira");
        getContentPane().add(lblAutor);
        lblAutor.setBounds(14, 160, 350, 13);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLigarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLigarActionPerformed
        if( this.status ) {
            this.serialEscrita.AbrirPorta(taLog);
            this.status = !this.status;
        }    
        
    }//GEN-LAST:event_btnLigarActionPerformed

    private void btnDesligarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesligarActionPerformed
        if( !this.status ) {
            this.serialEscrita.FecharCom(taLog);
            this.status = !this.status;
        }    
    }//GEN-LAST:event_btnDesligarActionPerformed
    
    private void btnAjudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAjudaActionPerformed
        JOptionPane.showMessageDialog(null, "AJUDA"
        		+ "\n\nAntes de executar:\n1. Coloque a DLL rxtxSerial.dll na pasta System32.\nObs.:Se seu sistema for de 32bits, coloque a de 32. Se de 64, a de 64."
        		+ "\n2.Conecte a placa do Relé a sua serial."); 
    }//GEN-LAST:event_btnAjudaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Tela().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLigar;
    private javax.swing.JButton btnDesligar;
    private javax.swing.JButton btnAjuda;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAutor;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTextArea taLog;
    // End of variables declaration//GEN-END:variables
}
