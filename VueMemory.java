import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**Classe qui modelise une interface comportant une grille de BouttonMemory().
*/
public class VueMemory{
  private ImageIcon TAB_IMAGES[] = {
    new ImageIcon(VueMemory.class.getResource("images/Bird.gif")),
    new ImageIcon(VueMemory.class.getResource("images/Bird2.gif")),
    new ImageIcon(VueMemory.class.getResource("images/Cat.gif")),
    new ImageIcon(VueMemory.class.getResource("images/Cat2.gif")),
    new ImageIcon(VueMemory.class.getResource("images/Dog.gif")),
    new ImageIcon(VueMemory.class.getResource("images/Dog2.gif")),
    new ImageIcon(VueMemory.class.getResource("images/Rabbit.gif")),
    new ImageIcon(VueMemory.class.getResource("images/Pig.gif")),
    new ImageIcon(VueMemory.class.getResource("images/Inconnu.gif")),
    new ImageIcon(VueMemory.class.getResource("images/Rien.gif"))};

  private BouttonMemory[][] grille;
  private ModeleMemory modele;
  private VueMemory vue;
  private int lig1 = -1;
  private int col1 = -1;
  private int lig2 = -1;
  private int col2 = -1;
  private Timer timer;
  private int le_score;
  private int essai;
  private int decouvert = 8;
  private boolean cacher = true;
  private boolean verifier = false;


  /**Constructeur d'une VueMemory.
  @param modele : class modele()
  */
  public VueMemory(ModeleMemory modele){
    this.modele = modele;
    this.grille = new BouttonMemory[modele.getNbLig()][modele.getNbCol()];
    JMenu menu;
    JMenuBar menuBar;
    JMenuItem menuItem, menuItem1;

    
    JFrame fen = new JFrame("Jeu Memory");
    fen.getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT));

    Dimension dimEcran = fen.getToolkit().getScreenSize();;
    fen.setLocation(((int)dimEcran.getWidth()/2),((int)dimEcran.getHeight()/2));


    menuBar = new JMenuBar();
    menu = new JMenu("Jeu");
    menuBar.add(menu);
    menuItem = new JMenuItem("Nouvelle Partie");
    menu.add(menuItem);
    menuItem1 = new JMenuItem("Escape");
    menu.add(menuItem1);



    ActionListener nouvellePartie = new ActionListener(){
      /**
      écoute l'évènement pour lancer une nouvelle Partie.
      @param e : une action de la part de l'utilisateur
      @return rien
      */
      public void actionPerformed(ActionEvent e) {
        nouvellePartie();
      }
    };
    menuItem.addActionListener(nouvellePartie);

    ActionListener escape = new ActionListener(){
      /**
      écoute l'évènement pour quitter le jeu.
      @param e : une action de la part de l'utilisateur
      @return rien
      */
      public void actionPerformed(ActionEvent e){
        fen.setVisible(false);
        fen.dispose();
      }
    };
    menuItem1.addActionListener(escape);


    JOptionPane.showMessageDialog(fen,
     "Fait par Ayoub Chakhite\n"+
     "Etudiant : \n"+
     "La faculté de Jean Perrin \n"+
     "année 2019/2020",
     " Informations ",
     JOptionPane.INFORMATION_MESSAGE);

    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(modele.getNbLig(), modele.getNbCol()));
    for(int i = 0; i < modele.getNbLig(); i++){
      for(int j = 0; j < modele.getNbCol(); j++){
        BouttonMemory image = new BouttonMemory(i,j, this);
        image.setIcon(TAB_IMAGES[8]);
        grille[i][j] = image;
        panel.add(image);
      }
    }

    JPanel panel1 = new JPanel();
    JLabel score = new JLabel();
    JLabel nbEssais = new JLabel();
    panel1.setLayout(new GridLayout(1, 2));
    panel1.add(score);
    panel1.add(nbEssais);

    fen.setLayout(new BorderLayout());
    fen.getContentPane().add(panel, BorderLayout.CENTER);
    fen.getContentPane().add(panel1, BorderLayout.SOUTH);
    nbEssais.setText("Nombre d'essais : " + 0);

    ActionListener verification = new ActionListener() {
      /**
      elle permet d'ajouter des points et le nombre d'essai
      puis elle vérifie si c'est les bonnes images et d'afficher une message lorsqu'on gagne
      @param e : une action
      @return rien
      */
      public void actionPerformed(ActionEvent e) {
        essai++;
        nbEssais.setText("Nombre d'essais : " + essai);
        if(modele.identique(lig1 ,col1, lig2, col2)){
          grille[lig1][col1].setClickable(false);
          grille[lig2][col2].setClickable(false);
          grille[lig1][col1].setIcon(TAB_IMAGES[9]);
          grille[lig2][col2].setIcon(TAB_IMAGES[9]);
          le_score += 10;
          decouvert--;
          score.setText("Votre score est de : " + le_score);
        }else{
          le_score--;
          score.setText("Votre score est de : " + le_score);
          grille[lig1][col1].setIcon(TAB_IMAGES[8]);
          grille[lig2][col2].setIcon(TAB_IMAGES[8]);
        }
        if (decouvert == 0) {
          JOptionPane d = new JOptionPane();
          d.showMessageDialog( fen, "vous avez gagnez en " + essai + " essais.",
      "Victory", JOptionPane.INFORMATION_MESSAGE);
        }
        lig1 = -1;
        lig2 = -1;
        col1 = -1;
        col2 = -1;
        timer.stop();
        verifier = false;
      }
    };
    timer = new Timer(2000, verification);

    fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    fen.pack();
    fen.setVisible(true);
    fen.setJMenuBar(menuBar);
  }

  /**Permet de dévoiler deux images cachées.
  @param li : ligne de l'image selection
  @param co : colonne de l'image selection
  */
  public void retourneImage(int li, int co){
    if(lig1 == -1){
      lig1 = li;
      col1 = co;
      grille[lig1][col1].setIcon(TAB_IMAGES[modele.getVal(lig1,col1)]);
    }else{
      lig2 = li;
      col2 = co;
      grille[lig2][col2].setIcon(TAB_IMAGES[modele.getVal(lig2,col2)]);
      verifier = true;
      timer.start();
    }
  }

  /**Permet de recommencer une partie.
  */
  public void nouvellePartie(){
    modele.nouvelleMatrice();
    for(int i = 0; i < modele.getNbLig(); i++){
      for(int j = 0; j < modele.getNbCol(); j++){
        grille[i][j].setIcon(TAB_IMAGES[8]);
        grille[i][j].setClickable(true);
        lig1 = -1;
        lig2 = -1;
        col1 = -1;
        col2 = -1;
      }
    }
    le_score = 0;
    essai = 0;
    decouvert = 8;
  }

  /**accesseur pour le la vérification.
  @return false si la verification n'a pas était faite.
  */
  public boolean getVerifier(){
    return verifier;
  }

}
