import java.util.*;
import javax.swing.*;

/** c'est une classe qui va permettre de modelisé une matrice du jeu Memory avec des valeurs
  contenant des chiffres numerotées de 0 à 7.
*/

public class ModeleMemory{
  private int nbLig = 4;
  private int nbCol = 4;
  private int[][] tableau = new int[nbLig][nbCol];
  private int nbPaire = 0;
  private int score = 0;
  private int cpt_essais = 0;
  List<Integer> lister = new ArrayList<Integer>();
  /**Constructeur du ModeleMemory.
  */
  public ModeleMemory(){
    int ind = 0;
    liste();
    Collections.shuffle(lister);
    for(int i=0; i<nbLig; i++){
      for(int j=0; j<nbCol; j++){
        tableau[i][j] = lister.get(ind);
        ind++;
      }
    }
  }

  /**Permet de mettre les paires dans une liste numerotés 0 à 7.
  */

  public void liste(){
    for (int i = 0; i < 8 ;i++) {
      lister.add(i);
      lister.add(i);
    }
  }

  /**Accesseur pour le nombre de colonne.
  @return le nombre de colonne.
  */
  public int getNbCol(){
    return nbCol;
  }

  /**Accesseur pour le nombre de ligne.
  @return le nombre de ligne.
  */
  public int getNbLig(){
    return nbLig;
  }

  /**Accesseur pour le nombre de paire.
  @return le nombre de paire.
  */
  public int getNbPaire(){
    return nbPaire;
  }

  /**Accesseur pour la valeur assacoier (lig,col).
  @param lig : ligne
  @param col : colonne
  @return la valeur.
  */
  public int getVal(int lig, int col){
    return tableau[lig][col];
  }

  /**Verifie si deux images possede la meme valeurs
  @param lig1 : ligne 1
  @param lig2 : ligne 2
  @param col1 : colonne 1
  @param col2 : colone 2
  @return true si deux images sont identiques.
  */
  public boolean identique(int lig1, int col1, int lig2, int col2){
    if(tableau[lig1][col1] == tableau[lig2][col2]){
      nbPaire ++;
      return true;
    }
    return false;
  }

  /**Realise une nouvelle et initialise le score et le nombre de paire à zero
    pour une nouvelle partie.
  */
  public void nouvelleMatrice(){
    int ind = 0;
    Collections.shuffle(lister);
    for(int i=0; i<nbLig; i++){
      for(int j=0; j<nbCol; j++){
        tableau[i][j] = lister.get(ind);
        ind++;
      }
    }
    score = 0;
    nbPaire = 0;
  }

}
