package myProject;
import javax.swing.*;
import java.awt.*;

/**
 * This class writes with colors the word of game
 * @autor Marlon-A. Anacona - O. 2023777 <marlon.anacona@correounivalle.edu.co>
 * @autor Luis-F. Belalcazar - A. 2028783 <luis.felipe.belalcazar@correounivalle.edu.co>
 *  @Version v.1.0.0 date 13/01/22
 */
public class Canvas extends JPanel {
    private String texto;
    private Font font;
    private int step;
    public static String palabraQueSalio;
    public static int i;
    private Diccionario palabra=new Diccionario();
    private ModelWord palabraACalificar=new ModelWord();
    public Canvas(){
        setBackground(Color.LIGHT_GRAY);
        font = new Font(Font.DIALOG,Font.BOLD,27);
        step=2;

    }

    /**
     * this method return number of words to repeat
     * @param i number of words to repeat
     * @return i number of words to repeat
     */
    public int setI(int i){
        this.i=i;
        return this.i;
    }

    /**
     * this method writes the words of the game on the panel
     * @param g panel chart data
     */

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.setFont(font);
        switch (step) {

            case 1: g.setColor(Color.RED);
                palabraQueSalio=this.palabra.getFrase();
                g.drawString(palabraQueSalio,150,150);

                break;
            case 2:
                g.setColor(Color.RED);
                g.drawString( "BIENVENIDO",150,150);

                break;
            case 3:g.setColor(Color.CYAN);
                g.drawString("Draw Rect",20,22);
                g.drawRect(5,40,90,55);
                g.fillRect(100,40,90,55);
                g.setColor(Color.ORANGE);
                g.drawRoundRect(290,40,90,55,20,20);
                g.fillRoundRect(195,40,90,55,50,50);
                g.setColor(Color.MAGENTA);
                g.draw3DRect(5,100,90,55,true);
                g.fill3DRect(100,100,90,55,false);
                break;
            case 4:g.setColor(Color.RED);
                palabraQueSalio=this.palabra.getfraseMostrar(i);
                g.drawString(palabraQueSalio,150,150);

                break;
            case 5:g.setColor(Color.GREEN);
                g.drawString("Draw Images",20,22);
                ImageIcon image = new ImageIcon(getClass().getResource("analisisClases/paolaLogo.png"));
                g.drawImage(image.getImage(),7,40,90,90,null);
                break;
            default:g.setColor(Color.BLUE);
                g.drawString("The End!!",50,22);
                break;

        }
    }

    /**
     * This method writes the count for the start of the game to the panel
     * @param g panel chart data
     * @param contador number the count
     */
    public void conteo(Graphics g,int contador){
        super.paintComponent(g);
        g.setFont(font);
        String segundo=""+contador;
        g.setColor(Color.RED);
        g.drawString( segundo,150,150);
        g.drawLine(150,155, 320,155);
    }

    public void setStep(int step){
        this.step=step;

    }
}

