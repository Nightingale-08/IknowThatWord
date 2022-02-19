package myProject;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class create, compare and clear the list of words
 * @autor Marlon-A. Anacona - O. 2023777 <marlon.anacona@correounivalle.edu.co>
 * @autor Luis-F. Belalcazar - A. 2028783 <luis.felipe.belalcazar@correounivalle.edu.co>
 *  @Version v.1.0.0 date 13/01/22
 */
public class ModelWord {
    private static int aciertos;
    private  static int nivelActual;
    public static int palabrasPorNivel;
    private static ArrayList<String> palabrasQueHanSalido = new ArrayList<>() ;
    ArrayList<String> palabras= new ArrayList<>() ;
    private static  ArrayList<String> palabrasCalificar2= new ArrayList<>() ;

    /**
     *This method adds the words that are displayed in the panel.
     * @param palabrarecibida the list of words that are displayed in the panel.
     */
    public void setPalabrasEnNivel(String palabrarecibida){

        palabrasQueHanSalido.add(palabrarecibida);

    }

    /**
     * this method set level of user
     * @param nivel level of user
     * @return nivel level of user
     */
    public int setNivelActual(int nivel){
        nivelActual=nivel;;
        return nivelActual;
    }

    /**
     * This method set number of words for level
     */
    public static void setPalabrasPorNivel(){
        switch (nivelActual){
            case 1: palabrasPorNivel=20;
                break;
            case 2: palabrasPorNivel=40;
                break;
            case 3: palabrasPorNivel=50;
                break;
            case 4: palabrasPorNivel=60;
                break;
            case 5: palabrasPorNivel=70;
                break;
            case 6: palabrasPorNivel=80;
                break;
            case 7 : palabrasPorNivel=100;
                break;
            case 8 : palabrasPorNivel=120;
                break;
            case 9 :  palabrasPorNivel=140;
                break;
            case 10 :palabrasPorNivel=200;
                break;
        }

    }

    /**
     * evaluate the successes to pass the level
     * @param aciertos
     * @return
     * @throws IOException
     */
    public static boolean verificarPasoNivel(int aciertos) throws IOException {
        FileManager nivel=new FileManager();
        boolean pasoNivel= false;
        switch(nivelActual){
            case 1: if(aciertos>7) {
                pasoNivel= true;
                nivel.subirNivel();
            }
                break;
            case 2: if(aciertos>=14) {
                pasoNivel= true;
            }
                break;
            case 3: if(aciertos>=19) {
                pasoNivel= true;
            }
                break;
            case 4: if(aciertos>=24) {
                pasoNivel= true;
            }
                break;
            case 5: if(aciertos>=28) {
                pasoNivel= true;
            }
                break;
            case 6: if(aciertos>=34) {
                pasoNivel= true;
            }
            break;
            case 7: if(aciertos>=45) {
                pasoNivel= true;
            }
                break;
            case 8: if(aciertos>=54) {
                pasoNivel= true;
            }
                break;
            case 9: if(aciertos>=67) {
                pasoNivel= true;
            }
                break;
            case 10: if(aciertos==100) {
                pasoNivel= true;
            }
                break;
        }
        return pasoNivel;
    }

    /**
     * clear arraylist
     */
    public static void limpiar(){
        palabrasQueHanSalido.clear();
    }


    /**
     * This method evaluates if the word is correct or not
     * @param palabraEscrita words on the panel
     * @return
     */
    public static boolean comparar(String palabraEscrita) {

        boolean esCorrecta= false;
        for (int i = 0; i < palabrasQueHanSalido.size() ; i++) {
            if (palabrasQueHanSalido.get(i).equals(palabraEscrita)){
                    esCorrecta=true;
                    break;
            }
            }
            return esCorrecta;
    }

    /**
     * This method evaluates if the word is correct or not
     * @param respuestaUsuario
     */
    public static void setUsuarioCorreto(boolean respuestaUsuario){

        boolean respuestaCorrecta= comparar(Canvas.palabraQueSalio);
        if (respuestaUsuario== respuestaCorrecta){
            JOptionPane.showMessageDialog(null,"Respuesta Correcta");
            aciertos++;
        }else{
            JOptionPane.showMessageDialog(null,"Respuesta erronea");
        }
    }

    /**
     * This method get succesess the player
     * @return
     */
    public static int getAciertos(){
        return aciertos;
    }
    public static int setAciertos( ){
        aciertos=0;
        return aciertos;
    }

}









