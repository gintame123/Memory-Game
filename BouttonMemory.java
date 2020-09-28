import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**Classe qui modélise un boutton.
*/
public class BouttonMemory extends JButton implements ActionListener{
  private int lig;
  private int col;
  private VueMemory maVue;
  private Timer time;
  private boolean clickable = true;

  /**Constructeur d'un Boutton.
  @param lig : ligne du boutton selectionné
  @param col : colonne du boutton selectionné
  @param maVue : class VueMemory()
  */
  public BouttonMemory(int lig, int col, VueMemory maVue){
    this.lig = lig;
    this.col = col;
    this.maVue = maVue;
    this.addActionListener(this);
  }

  /**Permet de mettre un bouton non clicable.
  @param c : initialisation du click a true ou false
  */
  public void setClickable(boolean c){
    clickable = c;
  }

  /**Réalise la fonction retourneImage() lors d'un click sur un bouton.
  @param e : une action 
  */
  public void actionPerformed(ActionEvent e){
    if(clickable && maVue.getVerifier()== false){
      maVue.retourneImage(lig, col);
    }
  }

}
