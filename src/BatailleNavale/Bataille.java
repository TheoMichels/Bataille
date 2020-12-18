package BatailleNavale;

public class Bataille {
	
	int[][] matrice;
	public static char[] conv= {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
	
	// l'objet BatailleNavale cree un tableau vide pour un joueur en particulier 
	public Bataille() {
		matrice = new int[10][10];
		for(int j=0;j<matrice.length;j++) {
			for(int i=0;i<matrice.length;i++) {
				matrice[i][j] = 0;
			}
		}
	}
	
	// affiche le tableau, les 0 correspondent a des emplacements vides et les 1 correspondent a un bateau
	public String Afficher() {
		String grille = "Voici votre grille :\n\n";
		for(int j=0;j<matrice.length;j++) {
			for(int i=0;i<matrice.length;i++) {
			grille += matrice[i][j];
			}
			grille += "\n";
		}
		return grille;
	}
	
	// permet de convertir une coordonnee type "A1" en un tableau de deux entiers 
	public int[] convert(String coord) {
        int[] s = new int[2];
        for(int i = 0;i<10;i++) {
            if (conv[i]== coord.charAt(0)) s[0] = i;
        }
        s[1] = Character.getNumericValue(coord.charAt(1));
        return s;
    }
	
	// cree un bateau en fonction des coordonnees que le joueur choisi
	public String creerBateau(String coordX, String coordY, int tour) {
		
		// conversion des coordonnees literales en coordonnees numeriques 
		int[] x = convert(coordX);
		int[] y = convert(coordY);
		
		// initiation de deux variables utiles plus tard
		String msgErreur = null;
		int compteur = 0;
		
		// verifie si les coordonnees sont diagonales -> refus des coordonnees le cas echeant 
		if ((x[0] != y[0]) & (x[1] != y[1])) { 
			msgErreur = "Les coordonnees sont en diagonales, vous n'avez pas le droit de placer un bateau de cette facon.";
		}
		
		// sinon la methode continue
		else {
			// cas ou les coordonnes sont verticales 
				if(x[0] == y[0]) {
					// cas ou on est dans le premier tour : si les coordonnes ne creent pas un bateau de taille 2 -> refus des coordonnees 
					if (tour==0 && ((y[1] - x[1])!= 1)) {
						msgErreur = "Votre bateau ne fait pas la bonne taille.";
					}
					// cas ou on est dans le deuxieme tour : si les coordonnes ne creent pas un bateau de taille 3 -> refus des coordonnees 
					else if (tour==1 && ((y[1] - x[1]) != 2)) {
						msgErreur = "Votre bateau ne fait pas la bonne taille.";
					}
					// cas ou on est dans le troisieme tour : si les coordonnes ne creent pas un bateau de taille 5 -> refus des coordonnees 
					else if (tour==2 && ((y[1] - x[1]) != 4)) {
						msgErreur = "Votre bateau ne fait pas la bonne taille.";
					}
					// sinon la methode continue
					else {
						// verifie le nombre de cases libres dans les emplacements indiques par le jour : incrementation du compteur le cas echeant
						// verification en amont afin de ne pas commencer a placer un bateau alors qu'il y a deja certaines cases de remplies 
						for(int i=x[1];i<y[1]+1;i++) {
							if (matrice[x[0]][i] == 0) {
								compteur++;
							}
						}
						// si compteur correspond à la taille du bateau les valeurs peuvent s'incrémenter 
						if((tour == 0 && compteur == 2) || (tour == 1 && compteur == 3) || (tour == 2 && compteur == 5))  {
							for(int i=x[1];i<y[1]+1;i++) {
									matrice[x[0]][i] = 1;
							}
						}
						// si le compteur ne correspond pas à la taille du bateau alors msg d'erreur
						else {
							msgErreur = "Il y a deja un bateau a cet emplacement.";
						}
						
					}
				}
				
				// cas ou les coordonnes sont horizontales
				else if(x[1] == y[1]) {
					// cas ou on est dans le premier tour : si les coordonnes ne creent pas un bateau de taille 2 -> refus des coordonnees 
					if (tour==0 && ((y[0] - x[0]) != 1)) {
						msgErreur = "Votre bateau ne fait pas la bonne taille.";
					}
					// cas ou on est dans le deuxieme tour : si les coordonnes ne creent pas un bateau de taille 3 -> refus des coordonnees 
					else if (tour==1 && ((y[0] - x[0]) != 2)) {
						msgErreur = "Votre bateau ne fait pas la bonne taille.";
					}
					// cas ou on est dans le troisieme tour : si les coordonnes ne creent pas un bateau de taille 5 -> refus des coordonnees 
					else if (tour==2 && ((y[0] - x[0]) != 4)) {
						msgErreur = "Votre bateau ne fait pas la bonne taille.";
					}
					// sinon la methode continue
					else {
						// verifie le nombre de cases libres dans les emplacements indiques par le jour : incrementation du compteur le cas echeant
						// verification en amont afin de ne pas commencer a placer un bateau alors qu'il y a deja certaines cases de remplies
						for(int i=x[0];i<y[0]+1;i++) {
							if (matrice[i][x[1]] == 0) {
								compteur++;
							}
						}
						// si compteur correspond à la taille du bateau les valeurs peuvent s'incrémenter
						if((tour == 0 && compteur == 2) || (tour == 1 && compteur == 3) || (tour == 2 && compteur == 5)) {
							for(int i=x[0];i<y[0]+1;i++) {
								matrice[i][x[1]] = 1;
							}
						}
						// si le compteur ne correspond pas à la taille du bateau alors msg d'erreur
						else {
							msgErreur = "Il y a deja un bateau a cet emplacement.";
						}
					}
				}
				
		}
		return msgErreur;
	}
	
	
	// permet de savoir s'il y a un bateau a l'emplacement demande
	public boolean testPosition(String coord) {
		boolean position;
		int[] x = convert(coord);

		if(matrice[x[0]][x[1]]!=1) {
			position = false;
		}
		else {
			position = true;
		}
		return position;
	}
	
	// permet d'attaquer une position et de retourner un booleen permettant de savoir si un bateau a ete touche
	public boolean attaquePosition(String coord) {
		boolean position;
		int[] x = convert(coord);

		if(matrice[x[0]][x[1]]==0) {
			position = false;
		}
		else {
			matrice[x[0]][x[1]] = 0;
			position = true;
		}
		return position;
	}
	
	// méthode qui parcourt toute la matrice et qui incrémente un compteur dès qu'une case est vide
	// si le compteur est égal à 100, cela veut dire que la matrice est entièrement vide, et la methode retourne un booléen true si la matrice est bien vide
	public boolean testFin() {
		boolean test = false;
		int compteur = 0;
		for(int j=0;j<matrice.length;j++) {
			for(int i=0;i<matrice.length;i++) {
				if (matrice[i][j]==0) { 
					compteur++; 
				}
			}
		}
		if (compteur == 100) { 
			test = true;
		}
		return test;
	}
	
	// main permettant de tester les methodes 
	
	/*public static void main(String[] args) {
		BatailleNavale test = new BatailleNavale();
		
		String msg1 = test.creerBateau("A1","A2", 0);
		String grille = test.Afficher();
		System.out.println(grille);
		System.out.println(msg1);
		
		
		String msg2 = test.creerBateau("A2","C2", 1);
		grille = test.Afficher();
		System.out.println(grille);
		System.out.println(msg2);
		
		
		String msg3 = test.creerBateau("A1","A2", 2);
		grille = test.Afficher();
		System.out.println(grille);
		System.out.println(msg1);
	}*/
}
