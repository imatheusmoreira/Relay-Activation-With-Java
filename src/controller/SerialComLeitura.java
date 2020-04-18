package controller;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class SerialComLeitura implements Runnable, SerialPortEventListener {

    public String Dadoslidos;
    public int nodeBytes;
    private int baudrate;
    private int timeout;
    private CommPortIdentifier cp;
    private SerialPort porta;
    private OutputStream saida;
    private InputStream entrada;
    private Thread threadLeitura;
    private boolean Escrita;
    private String Porta;
    protected String peso;

    
    public SerialComLeitura( String p , int b , int t ){
        this.Porta = p;
        this.baudrate = b;
        this.timeout = t;
    }

    public void habilitarEscrita(JTextArea ta) {
        this.Escrita = true;
    }
    
    public void habilitarLeitura(JTextArea ta) {
        this.Escrita = false;
    }
    
    public void ObterIdDaPorta(){
        try {
            cp = CommPortIdentifier.getPortIdentifier(Porta);
            if ( cp == null ) {
                JOptionPane.showMessageDialog(null, "Erro na porta", "Erro", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        } catch (Exception e) {
        	JOptionPane.showMessageDialog(null, "Erro ao obter o ID da porta: \n" + e, "Erro", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
  
    
    public void AbrirPorta(JTextArea ta){

        try {
            porta = (SerialPort)cp.open("SerialComLeitura", timeout);
            //configurar parÃ¢metros
            porta.setSerialPortParams(baudrate,
            SerialPort.DATABITS_8,
            SerialPort.STOPBITS_1,
            SerialPort.PARITY_NONE);
            porta.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
            
            ta.append("\n---------ABERTURA DE PORTA----------");
            ta.append("\nPORTA....: "+porta.toString());
            ta.append("\nDATA BITS: "+String.valueOf(porta.getDataBits()));
            ta.append("\nSTOP BITS: "+String.valueOf(porta.getStopBits()));
            ta.append("\nPARIDADE.: "+String.valueOf(porta.getParity()));
            ta.append("\n------------------------------------");
            ta.setCaretPosition(ta.getText().length());

        }catch(Exception e){
        	JOptionPane.showMessageDialog(null, "Erro abrindo comunicação: " + e, "Erro", JOptionPane.ERROR_MESSAGE);
        	
            ta.append("\n------------------------------------");
            ta.append("\nErro abrindo comunicão\n"+e.getMessage());
            ta.append("\n------------------------------------");
            ta.setCaretPosition(ta.getText().length());

            System.exit(1);
        }
}

    public void LerDados(){

        if (Escrita == false){
            try {
                entrada = porta.getInputStream();
            } catch (Exception e) {
                System.out.println("Erro de Stream: " + e);
                System.exit(1);
            }

            try {
                porta.addEventListener(this);
            } catch (Exception e) {
                System.out.println("Erro de listener: " + e);
                System.exit(1);

            }
            porta.notifyOnDataAvailable(true);

            try {
                threadLeitura = new Thread(this);
                threadLeitura.start();
                run();
            } catch (Exception e) {
                System.out.println("Erro de Thread: " + e);
            }
        }
    }
    
    
    public void EnviarUmaString(String msg){

        if (Escrita==true) {

            try {

                saida = porta.getOutputStream();
                System.out.println("FLUXO OK!");

            } catch (Exception e) {

                System.out.println("Erro.STATUS: " + e );

            }

            try {

                System.out.println("Enviando um byte para " + Porta );
                System.out.println("Enviando : " + msg );
                saida.write(msg.getBytes());
                Thread.sleep(100);
                saida.flush();

            } catch (Exception e) {
                System.out.println("Houve um erro durante o envio. ");
                System.out.println("STATUS: " + e );
                System.exit(1);
            }

        } 
        else {
            System.exit(1);
        }
    }

    public void run(){
        try {
            Thread.sleep(5);
        } catch (Exception e) {
            System.out.println("Erro de Thread: " + e);
        }
}

  
    public void setPeso(String peso) {
        this.peso = peso;
    }
    
    public String getPeso() {
        return this.peso;
    }


    public void serialEvent(SerialPortEvent ev) {
        StringBuffer bufferLeitura = new StringBuffer();
        
        int novoDado = 0;
        
        switch (ev.getEventType()) {

            case SerialPortEvent.BI:
            case SerialPortEvent.OE:
            case SerialPortEvent.FE:
            case SerialPortEvent.PE:
            case SerialPortEvent.CD:
            case SerialPortEvent.CTS:
            case SerialPortEvent.DSR:
            case SerialPortEvent.RI:
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
            break;
            case SerialPortEvent.DATA_AVAILABLE:

                //Novo algoritmo de leitura.

                while(novoDado != -1){

                    try{
                    	
                        novoDado = entrada.read();
                        
                        if(novoDado == -1){
                            break;
                        }

                        if('\r' == (char)novoDado){
                            bufferLeitura.append('\n');
                        }else{
                            bufferLeitura.append((char)novoDado);
                        }

                    }catch(IOException ioe){
                        System.out.println("Erro de leitura de Serial: " + ioe);
                    }

                }
                setPeso(new String(bufferLeitura));
                System.out.println(getPeso());
            break;
        }
    }
    
    public void FecharCom(JTextArea ta){
            try {
                porta.close();
                
                ta.append("\n--------FECHAMENTO DE PORTA---------");
                ta.append("\nPORTA....: "+porta.toString());
                ta.append("\n------------------------------------");
                ta.setCaretPosition(ta.getText().length());
                
            } catch (Exception e) {
                System.out.println("Erro fechando a porta: " + e);
                
                ta.append("\n------------------------------------");
                ta.append("\nErro fechando comunicação\n"+e.getMessage());
                ta.append("\n------------------------------------");
                ta.setCaretPosition(ta.getText().length());
                
                System.exit(0);
            }
    }

    public String obterPorta(){
        return Porta;
    }
    
    public int obterBaudrate(){
        return baudrate;
    }
   
    
}
