package myProject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
/**
 * This class create, compare and clear the list of words
 * @autor Marlon-A. Anacona - O. 2023777 <marlon.anacona@correounivalle.edu.co>
 * @autor Luis-F. Belalcazar - A. 2028783 <luis.felipe.belalcazar@correounivalle.edu.co>
 *  @Version v.1.0.0 date 13/01/22
 */
public class Diccionario {

    private ArrayList<String> diccionario= new ArrayList<String>();
    private ModelWord mandarPalabra= new ModelWord();
    private ArrayList<String> palabrasHanSalido= new ArrayList<String>();
    private ArrayList<String> palabrastotalesNivel= new ArrayList<>() ;
    private ArrayList<String> palabrasCalificar2= new ArrayList<>() ;
    private String pal;
    String pal3;

    /**
     * create a list with a username the usuarios . txt
     */
    public Diccionario(){
        FileManager fileManager= new FileManager();
        diccionario=fileManager.lecturaFile();
    }

    /**
     * this method create a list the words on the panel
     * @return pal
     */
    public String getFrase(){
        Random aleatorio= new Random();

        pal=diccionario.get(aleatorio.nextInt(diccionario.size()));
        while(palabrasHanSalido.contains(pal)){
            pal=diccionario.get(aleatorio.nextInt(diccionario.size()));
        }
        palabrasHanSalido.add(pal);
        mandarPalabra.setPalabrasEnNivel(pal);
        return pal;

    }

    /**
     * this method displays the word
     * @return
     */
    public String getFrasenuevas(){
        Random aleatorio= new Random();

        pal=diccionario.get(aleatorio.nextInt(diccionario.size()));
        return pal;

    }

    public  ArrayList<String> getPalabrasHanSalido(){

        return palabrasHanSalido;
    }

    /**
     * this method adds new words from those that have already come out
     */
    public void agregarPalabras(){
        Random aleatorioPalabra=new Random();


        String  pal2 ;
        String pal1;

        palabrastotalesNivel=palabrasHanSalido;
        int tamaño=palabrasHanSalido.size();
        for (int i=0;i<tamaño;i++){

            do{
                pal2 = getFrasenuevas();

            }while (palabrasHanSalido.contains(pal2));

            palabrastotalesNivel.add(pal2);

        }
        Collections.shuffle(palabrastotalesNivel);
        System.out.println(palabrastotalesNivel);
    }

    /**
     * This method returns the words that have come out and the deception to show them on the screen
     * @param i
     * @return pal
     */
    public String getfraseMostrar(int i){

        Random aleatorio= new Random();
        if(i<=0){
            agregarPalabras();
        }
        pal3 = palabrastotalesNivel.get(aleatorio.nextInt(palabrastotalesNivel.size()));
        do {
            pal3 = palabrastotalesNivel.get(aleatorio.nextInt(palabrastotalesNivel.size()));
        }while (palabrasCalificar2.contains(pal3));
        palabrasCalificar2.add(pal3);
        return pal3;

    }

    /**
     * clear all arraylist
     */
    public void limpiar(){
        palabrasHanSalido.clear();
        palabrasCalificar2.clear();
        palabrastotalesNivel.clear();
    }

}
