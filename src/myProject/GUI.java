package myProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;

/**
 * Esta clase contiene la clase main y la interface grafica de usuario.
 * @Autores  Marlon-A. Anacona - O. 2023777 <marlon.anacona@correounivalle.edu.co>
 *           Luis F Belalcazar A. 2028783 <luis.felipe.belalcazar@correounivalle.edu.co>
 * @version 1.0.0 date 18/02/2022
 * */
public class GUI extends JFrame {

    private Header header,header2;
    private JTextField texto;
    private JTextArea areaTexto;
    private FileManager fileManager;
    private Escucha escucha;
    private int palabrasaMandar;
    private Canvas canvas;
    private String MENSAJE_INICIO = "El juego trata de probar tu memoria\n" +
            "Se te presentaran 10 palabras las cuales tendrás 5 segundos en pantalla, luego desaparece y aparece la siguiente, debes memorizar las  palabras que mas puedas. Despues de esto\n" +" se te presentara un listado con el doble de palabras que se mostraron y por cada palabra debes indicar \n" +
            "si la palabras estaba o no contenida en el listado a memorizar y tendras 7 segundos\n" +
            "para responder, en caso de no hacerlo se tomara como un error";
    private Timer timer, iniciar,selecionarConteo,iniciar2;
    public static String nombreUsario;
    public static int nivel;
    private Font font;
    private JPanel palabras,botones;
    private Diccionario palabra;
    private JLabel fondo;
    private ImageIcon fondoimagen;
    private ModelWord niveles;
    private JButton jugar,ayuda,salir,si,no;
    private  int seleccionar=0;
    private int counter3;
    /**
     * Constructor of GUI class
     */
    public GUI(String usuariorr) throws IOException {

        setNombreUsuario(usuariorr);
        initGUI();

        //Default JFrame configuration
        this.setTitle("I Know That Word");
        this.setSize(500,400);
        this.setResizable(true);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }


    /**
     * This method is used to set up the default JComponent Configuration,
     * create Listener and control Objects used for the GUI class
     */
    private void initGUI() throws IOException {

        //Initializing the classes
        fileManager =new FileManager();
        niveles=new ModelWord();
        setNombreUsuario(nombreUsario);
        fileManager.escribirUsuario(nombreUsario);
        nivel=fileManager.buscarNivelUsuario(nombreUsario);
        niveles.setNivelActual(nivel);
        //Set up JFrame Container's Layout
        //Create Listener Object and Control Object
        header= new Header("¿ Que tan buena es tu memoria ?",Color.black);
        this.add(header,BorderLayout.NORTH);

        header2 = new Header("Recuerda las siguientes " + ModelWord.palabrasPorNivel / 2 + " Palabras", Color.DARK_GRAY);
        header2.setEnabled(false);
        header2.setVisible(false);


        escucha = new Escucha();
        fileManager = new FileManager();
        palabra= new Diccionario();
        //Set up JComponents

        palabras = new JPanel();
        canvas = new Canvas();
        canvas.setFocusable(true);
        add(canvas,BorderLayout.CENTER);
        botones=new JPanel();
        botones.setBackground(Color.BLUE);
        jugar=new JButton("JUGAR");
        si=new JButton("Si");
        no=new JButton("No");
        salir=new JButton("SALIR");
        ayuda=new JButton("AYUDA");
        botones.add(si);
        botones.add(salir);
        botones.add(jugar);
        botones.add(ayuda);
        botones.add(no);
        this.si.setVisible(false);
        this.no.setVisible(false);
        this.add(botones, BorderLayout.SOUTH);
        jugar.setEnabled(true);
        salir.addActionListener(escucha);
        ayuda.addActionListener(escucha);
        jugar.addActionListener(escucha);
        si.addActionListener(escucha);
        no.addActionListener(escucha);

        //create variable the type Timer

        iniciar = new Timer(1000,escucha);
        iniciar2 = new Timer(999,escucha);
        timer = new Timer(5000,escucha);
        selecionarConteo=new Timer(7000,escucha);

    }


    /**
     * This method save the username
     * @return Username
     */

    private String setNombreUsuario(String nombreUsario1){
        nombreUsario=nombreUsario1;
        return  nombreUsario;
    }

    /**
     * This method return the username
     * @return Username
     */
    public static String getNombreUsario(){
        return nombreUsario;
    }

    /**
     * Main process of the Java program
     * @param args Object used in order to send input data from command line when
     *             the program is execute by console.
     *             Enter the username
     *
     */
    public static void main(String[] args){
        EventQueue.invokeLater(() -> {
            try {
                String nombreUsu=JOptionPane.showInputDialog("Introduzca el nombre del usuarop");
                GUI miProjectGUI = new GUI(nombreUsu);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * inner class that extends an Adapter Class or implements Listeners used by GUI class
     */
    private class Escucha implements ActionListener {
        /*
            instructs the buttons and game time to carry out the program
             */
        private Random random;
        private int counter,counter2;
        public Escucha(){
            random = new Random();
            counter=0;

            counter2=0;

        }


        @Override

        public void actionPerformed(ActionEvent e) {
        //If to press the button "Salir", exit program
            if(e.getSource()==salir){
                JOptionPane.showMessageDialog(null,"Usuiario: "+nombreUsario +"\n Nivel Conseguido: "+nivel);
                System.exit(0);
            }
         //If to press the button "ayuda", You can read the instruccions of the game
            if(e.getSource()==ayuda){
                JOptionPane.showMessageDialog(null,MENSAJE_INICIO);
            }
            //If to press the button "jugar", start game with a counting up to 5 seconds
            if(e.getSource()==jugar){

                iniciar.start();

                JOptionPane.showMessageDialog(null,"El juego iniciará");
                //header.setVisible(false);
               ModelWord.setPalabrasPorNivel();
                header.setText("Recuerda las SIguientes " +ModelWord.palabrasPorNivel/2 + " Palabras");
                jugar.setEnabled(false);
                timer.start();


            }else {
                //Decide if the word came out or not in the round of words
                if(e.getSource()==si){
                    ModelWord.setUsuarioCorreto(true);

                    ModelWord.getAciertos();
                    System.out.println(ModelWord.getAciertos());
                    si.setEnabled(false);
                    no.setEnabled(false);



                }else {
                    if(e.getSource()==no){
                    ModelWord.setUsuarioCorreto(false);
                    ModelWord.getAciertos();
                    System.out.println(ModelWord.getAciertos());
                        si.setEnabled(false);
                        no.setEnabled(false);


                }
                }
            }
//Start generating the words depending on the level the user is at

            if(e.getSource()==iniciar){
                counter2++;
                canvas.conteo(getGraphics(),counter2);
                if(counter2<=4){

                }else{
                    timer.start();
                    counter2=0;
                    iniciar.stop();

                }
            }else{

            }
            if(e.getSource()==iniciar2){
                counter2++;
                canvas.conteo(getGraphics(),counter2);
                if(counter2<=6){

                }else{
                    selecionarConteo.start();
                    iniciar2.stop();

                }
            }else{

            }

            switch (nivel)
            {
                case 1:
                    palabrasaMandar=0;
                    if(e.getSource()==timer){

                        counter++;
                        canvas.setStep(1);
                        canvas.paintComponent(getGraphics());

                        if(counter<=9){

                        }else{
                            timer.stop();
                            JOptionPane.showMessageDialog(null,"AHORA VAMOS A COMPROBAR LAS PALABRAS");
                            iniciar2.start();
                            selecionarConteo.start();
                            seleccionar=1;
                            si.setVisible(true);
                            no.setVisible(true);
                            jugar.setVisible(false);
                            ayuda.setVisible(false);
                        }
                    }else{
                        counter=0;

                    }
                    if(e.getSource()==selecionarConteo){
                        canvas.setI(counter3);
                        counter3++;
                        canvas.setStep(4);

                        canvas.paintComponent(getGraphics());

                        if(counter3<=19){
                            si.setEnabled(true);
                            no.setEnabled(true);
                        }else{

                            try {
                                if ( ModelWord.getAciertos()>=7)
                                {
                                     ModelWord.verificarPasoNivel(ModelWord.getAciertos());
                                    selecionarConteo.stop();
                                    counter3=0;
                                    JOptionPane.showMessageDialog(null,"Has terminado con exito el nivel");
                                    ModelWord.setAciertos();
                                    palabra.limpiar();
                                    ModelWord.limpiar();
                                    dispose();
                                    GUI miGUI=new GUI(nombreUsario);

                                }else{
                                    selecionarConteo.stop();
                                    counter3=0;
                                    ModelWord.setAciertos();
                                    palabra.limpiar();
                                    ModelWord.limpiar();
                                    JOptionPane.showMessageDialog(null,"Has terminado con fracaso el nivel");
                                    GUI miGUI=new GUI(nombreUsario);
                                    dispose();

                                }

                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }


                        }
                    }else{

                    }

                    break;
                case 2:
                    if(e.getSource()==timer){

                        counter++;
                        canvas.setStep(1);
                        canvas.paintComponent(getGraphics());
                        if(counter<=19){

                        }else{
                            timer.stop();
                            JOptionPane.showMessageDialog(null,"AHORA VAMOS A COMPROBAR LAS PALABRAS");
                            selecionarConteo.start();

                            si.setVisible(true);
                            no.setVisible(true);
                            jugar.setVisible(false);
                            ayuda.setVisible(false);
                        }
                    }else{

                        counter=0;
                    }
                    if(e.getSource()==selecionarConteo){
                        canvas.setI(counter3);
                        counter3++;
                        canvas.setStep(4);

                        canvas.paintComponent(getGraphics());

                        if(counter3<=39){
                            si.setEnabled(true);
                            no.setEnabled(true);
                        }else{
                            try {
                                if ( ModelWord.getAciertos()>=14)
                                {
                                    ModelWord.verificarPasoNivel(ModelWord.getAciertos());
                                    selecionarConteo.stop();
                                    counter3=0;
                                    JOptionPane.showMessageDialog(null,"Has terminado con exito el nivel");
                                    ModelWord.setAciertos();
                                    palabra.limpiar();
                                    ModelWord.limpiar();
                                    dispose();
                                    GUI miGUI=new GUI(nombreUsario);

                                }else{
                                    selecionarConteo.stop();
                                    counter3=0;
                                    ModelWord.setAciertos();
                                    palabra.limpiar();
                                    ModelWord.limpiar();

                                    JOptionPane.showMessageDialog(null,"Has terminado con fracaso el nivel");
                                    GUI miGUI=new GUI(nombreUsario);
                                    dispose();

                                }

                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }

                        }
                    }else{

                    }

                    break;
                case 3:
                    if(e.getSource()==timer){

                        counter++;
                        canvas.setStep(1);
                        canvas.paintComponent(getGraphics());
                        if(counter<=24){

                        }else{
                            timer.stop();
                            JOptionPane.showMessageDialog(null,"AHORA VAMOS A COMPROBAR LAS PALABRAS");
                            selecionarConteo.start();
                            si.setVisible(true);
                            no.setVisible(true);
                            jugar.setVisible(false);
                            ayuda.setVisible(false);
                        }
                    }else{
                        counter=0;

                    }
                    if(e.getSource()==selecionarConteo){
                        canvas.setI(counter3);
                        counter3++;
                        canvas.setStep(4);

                        canvas.paintComponent(getGraphics());

                        if(counter3<=49){
                            si.setEnabled(true);
                            no.setEnabled(true);
                        }else{
                            try {
                                if ( ModelWord.getAciertos()>=19)
                                {
                                    ModelWord.verificarPasoNivel(ModelWord.getAciertos());
                                    selecionarConteo.stop();
                                    counter3=0;
                                    JOptionPane.showMessageDialog(null,"Has terminado con exito el nivel");
                                    ModelWord.setAciertos();
                                    palabra.limpiar();
                                    ModelWord.limpiar();

                                    dispose();
                                    GUI miGUI=new GUI(nombreUsario);

                                }else{
                                    selecionarConteo.stop();
                                    counter3=0;
                                    ModelWord.setAciertos();
                                    palabra.limpiar();
                                    ModelWord.limpiar();

                                    JOptionPane.showMessageDialog(null,"Has terminado con fracaso el nivel");
                                    GUI miGUI=new GUI(nombreUsario);
                                    dispose();

                                }

                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }

                        }
                    }else{

                    }

                    break;
                case 4:
                    if(e.getSource()==timer){

                        counter++;
                        canvas.setStep(1);
                        canvas.paintComponent(getGraphics());
                        if(counter<=29){

                        }else{
                            timer.stop();
                            JOptionPane.showMessageDialog(null,"AHORA VAMOS A COMPROBAR LAS PALABRAS");
                            selecionarConteo.start();
                            si.setVisible(true);
                            no.setVisible(true);
                            jugar.setVisible(false);
                            ayuda.setVisible(false);
                        }
                    }else{

                        counter=0;

                    }if(e.getSource()==selecionarConteo){
                    canvas.setI(counter3);
                    counter3++;
                    canvas.setStep(4);

                    canvas.paintComponent(getGraphics());

                    if(counter3<=59){
                        si.setEnabled(true);
                        no.setEnabled(true);
                    }else{

                        try {
                            if ( ModelWord.getAciertos()>=24)
                            {
                                ModelWord.verificarPasoNivel(ModelWord.getAciertos());
                                selecionarConteo.stop();
                                counter3=0;
                                JOptionPane.showMessageDialog(null,"Has terminado con exito el nivel");
                                ModelWord.setAciertos();
                                palabra.limpiar();
                                ModelWord.limpiar();

                                dispose();
                                GUI miGUI=new GUI(nombreUsario);

                            }else{
                                selecionarConteo.stop();
                                counter3=0;
                                ModelWord.setAciertos();
                                palabra.limpiar();
                                ModelWord.limpiar();

                                JOptionPane.showMessageDialog(null,"Has terminado con fracaso el nivel");
                                GUI miGUI=new GUI(nombreUsario);
                                dispose();

                            }

                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                    }
                }else{

                }

                    break;
                case 5:
                    if(e.getSource()==timer){

                        counter++;
                        canvas.setStep(1);
                        canvas.paintComponent(getGraphics());
                        if(counter<=34){

                        }else{
                            timer.stop();
                            JOptionPane.showMessageDialog(null,"AHORA VAMOS A COMPROBAR LAS PALABRAS");
                            selecionarConteo.start();
                            si.setVisible(true);
                            no.setVisible(true);
                            jugar.setVisible(false);
                            ayuda.setVisible(false);
                        }
                    }else{

                        counter=0;

                    }
                    if(e.getSource()==selecionarConteo){
                        canvas.setI(counter3);
                        counter3++;
                        canvas.setStep(4);

                        canvas.paintComponent(getGraphics());

                        if(counter3<=69){
                            si.setEnabled(true);
                            no.setEnabled(true);
                        }else{
                            try {
                                if ( ModelWord.getAciertos()>=28)
                                {
                                    ModelWord.verificarPasoNivel(ModelWord.getAciertos());
                                    selecionarConteo.stop();
                                    counter3=0;
                                    JOptionPane.showMessageDialog(null,"Has terminado con exito el nivel");
                                    ModelWord.setAciertos();
                                    palabra.limpiar();
                                    ModelWord.limpiar();

                                    dispose();
                                    GUI miGUI=new GUI(nombreUsario);

                                }else{
                                    selecionarConteo.stop();
                                    counter3=0;
                                    ModelWord.setAciertos();
                                    palabra.limpiar();
                                    ModelWord.limpiar();

                                    JOptionPane.showMessageDialog(null,"Has terminado con fracaso el nivel");
                                    GUI miGUI=new GUI(nombreUsario);
                                    dispose();

                                }

                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }

                        }
                    }else{

                    }

                    break;
                case 6:
                    if(e.getSource()==timer){

                        counter++;
                        canvas.setStep(1);
                        canvas.paintComponent(getGraphics());
                        if(counter<=39){

                        }else{
                            timer.stop();
                            JOptionPane.showMessageDialog(null,"AHORA VAMOS A COMPROBAR LAS PALABRAS");
                            selecionarConteo.start();
                            si.setVisible(true);
                            no.setVisible(true);
                            jugar.setVisible(false);
                            ayuda.setVisible(false);
                        }
                    }else{
                        counter=0;

                    }
                    if(e.getSource()==selecionarConteo){
                        canvas.setI(counter3);
                        counter3++;
                        canvas.setStep(4);

                        canvas.paintComponent(getGraphics());

                        if(counter3<=79){
                            si.setEnabled(true);
                            no.setEnabled(true);
                        }else{
                            try {
                                if ( ModelWord.getAciertos()>=34)
                                {
                                    ModelWord.verificarPasoNivel(ModelWord.getAciertos());
                                    selecionarConteo.stop();
                                    counter3=0;
                                    JOptionPane.showMessageDialog(null,"Has terminado con exito el nivel");
                                    ModelWord.setAciertos();
                                    palabra.limpiar();
                                    ModelWord.limpiar();

                                    dispose();
                                    GUI miGUI=new GUI(nombreUsario);

                                }else{
                                    selecionarConteo.stop();
                                    counter3=0;
                                    ModelWord.setAciertos();
                                    palabra.limpiar();
                                    ModelWord.limpiar();

                                    JOptionPane.showMessageDialog(null,"Has terminado con fracaso el nivel");
                                    GUI miGUI=new GUI(nombreUsario);
                                    dispose();

                                }

                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }

                        }
                    }else{

                    }
                    break;
                case 7:
                    if(e.getSource()==timer){

                        counter++;
                        canvas.setStep(1);
                        canvas.paintComponent(getGraphics());
                        if(counter<=49){

                        }else{
                            timer.stop();
                            JOptionPane.showMessageDialog(null,"AHORA VAMOS A COMPROBAR LAS PALABRAS");
                            selecionarConteo.start();
                            si.setVisible(true);
                            no.setVisible(true);
                            jugar.setVisible(false);
                            ayuda.setVisible(false);
                        }
                    }else{

                        counter=0;

                    } if(e.getSource()==selecionarConteo){
                    canvas.setI(counter3);
                    counter3++;
                    canvas.setStep(4);

                    canvas.paintComponent(getGraphics());

                    if(counter3<=99){
                        si.setEnabled(true);
                        no.setEnabled(true);
                    }else{
                        try {
                            if ( ModelWord.getAciertos()>=45)
                            {
                                ModelWord.verificarPasoNivel(ModelWord.getAciertos());
                                selecionarConteo.stop();
                                counter3=0;
                                JOptionPane.showMessageDialog(null,"Has terminado con exito el nivel");
                                ModelWord.setAciertos();
                                palabra.limpiar();
                                ModelWord.limpiar();

                                dispose();
                                GUI miGUI=new GUI(nombreUsario);

                            }else{
                                selecionarConteo.stop();
                                counter3=0;
                                ModelWord.setAciertos();
                                palabra.limpiar();
                                ModelWord.limpiar();

                                JOptionPane.showMessageDialog(null,"Has terminado con fracaso el nivel");
                                GUI miGUI=new GUI(nombreUsario);
                                dispose();

                            }

                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                    }
                }else{

                }

                    break;
                case 8:
                    if(e.getSource()==timer){

                        counter++;
                        canvas.setStep(1);
                        canvas.paintComponent(getGraphics());
                        if(counter<=59){

                        }else{
                            timer.stop();
                            JOptionPane.showMessageDialog(null,"AHORA VAMOS A COMPROBAR LAS PALABRAS");
                            selecionarConteo.start();
                            si.setVisible(true);
                            no.setVisible(true);
                            jugar.setVisible(false);
                            ayuda.setVisible(false);
                        }
                    }else{

                        counter=0;

                    }
                    if(e.getSource()==selecionarConteo){
                        canvas.setI(counter3);
                        counter3++;
                        canvas.setStep(4);

                        canvas.paintComponent(getGraphics());

                        if(counter3<=119){
                            si.setEnabled(true);
                            no.setEnabled(true);
                        }else{
                            try {
                                if ( ModelWord.getAciertos()>=54)
                                {
                                    ModelWord.verificarPasoNivel(ModelWord.getAciertos());
                                    selecionarConteo.stop();
                                    counter3=0;
                                    JOptionPane.showMessageDialog(null,"Has terminado con exito el nivel");
                                    ModelWord.setAciertos();
                                    palabra.limpiar();
                                    ModelWord.limpiar();

                                    dispose();
                                    GUI miGUI=new GUI(nombreUsario);

                                }else{
                                    selecionarConteo.stop();
                                    counter3=0;
                                    ModelWord.setAciertos();
                                    palabra.limpiar();
                                    ModelWord.limpiar();

                                    JOptionPane.showMessageDialog(null,"Has terminado con fracaso el nivel");
                                    GUI miGUI=new GUI(nombreUsario);
                                    dispose();

                                }

                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }

                        }
                    }else{

                    }
                    break;
                case 9:
                    if(e.getSource()==timer){

                        counter++;
                        canvas.setStep(1);
                        canvas.paintComponent(getGraphics());
                        if(counter<=69){

                        }else{
                            timer.stop();
                            JOptionPane.showMessageDialog(null,"AHORA VAMOS A COMPROBAR LAS PALABRAS");
                            selecionarConteo.start();
                            si.setVisible(true);
                            no.setVisible(true);
                            jugar.setVisible(false);
                            ayuda.setVisible(false);
                        }
                    }else{

                        counter=0;

                    }
                    if(e.getSource()==selecionarConteo){
                        canvas.setI(counter3);
                        counter3++;
                        canvas.setStep(4);

                        canvas.paintComponent(getGraphics());

                        if(counter3<=139){
                            si.setEnabled(true);
                            no.setEnabled(true);
                        }else{
                            try {
                                if ( ModelWord.getAciertos()>=67)
                                {
                                    ModelWord.verificarPasoNivel(ModelWord.getAciertos());

                                    JOptionPane.showMessageDialog(null,"Has terminado con exito el nivel");
                                    selecionarConteo.stop();
                                    counter3=0;
                                    ModelWord.setAciertos();
                                    palabra.limpiar();
                                    ModelWord.limpiar();

                                    dispose();
                                    GUI miGUI=new GUI(nombreUsario);

                                }else{
                                    selecionarConteo.stop();
                                    counter3=0;
                                    ModelWord.setAciertos();
                                    palabra.limpiar();
                                    ModelWord.limpiar();

                                    JOptionPane.showMessageDialog(null,"Has terminado con fracaso el nivel");

                                    GUI miGUI=new GUI(nombreUsario);
                                    dispose();

                                }

                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }

                        }
                    }else{

                    }break;
                case 10:
                    if(e.getSource()==timer){

                        counter++;
                        canvas.setStep(1);
                        canvas.paintComponent(getGraphics());
                        if(counter<=99){

                        }else{
                            timer.stop();
                            JOptionPane.showMessageDialog(null,"AHORA VAMOS A COMPROBAR LAS PALABRAS");
                            selecionarConteo.start();
                            si.setVisible(true);
                            no.setVisible(true);
                            jugar.setVisible(false);
                            ayuda.setVisible(false);
                        }
                    }else{

                        counter=0;

                    } if(e.getSource()==selecionarConteo){
                    canvas.setI(counter3);
                    counter3++;
                    canvas.setStep(4);

                    canvas.paintComponent(getGraphics());

                    if(counter3<=199){
                        si.setEnabled(true);
                        no.setEnabled(true);
                    }else{
                        try {
                            if ( ModelWord.getAciertos()>=100)
                            {
                                ModelWord.verificarPasoNivel(ModelWord.getAciertos());
                                JOptionPane.showMessageDialog(null,"Has terminado con exito el nivel");
                                selecionarConteo.stop();
                                counter3=0;
                                ModelWord.setAciertos();
                                palabra.limpiar();
                                ModelWord.limpiar();

                                dispose();
                                GUI miGUI=new GUI(nombreUsario);

                            }else{

                                JOptionPane.showMessageDialog(null,"Has terminado con fracaso el nivel");
                                selecionarConteo.stop();
                                counter3=0;
                                ModelWord.setAciertos();
                                palabra.limpiar();
                                ModelWord.limpiar();

                                GUI miGUI=new GUI(nombreUsario);
                                dispose();

                            }

                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                    }
                }else{

                }
                    break;

            }

        }
    }
}


