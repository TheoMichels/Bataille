package BatailleNavale;

public class BatailleNavale {
	
	int[][] matrice;
	
	// l'objet BatailleNavale instancie un tableau pour un joueur en particulier 
	public BatailleNavale() {
		matrice = new int[10][10];
		for(int j=0;j<matrice.length;j++) {
			for(int i=0;i<matrice.length;i++) {
				matrice[i][j] = 0;
			}
		}
	}
	
	// affiche le tableau, les 0 correspondent � des emplacements vides et les 1 correspondent a un bateau
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
	
	// cree un bateau en fonction des coordonnees que le joueur choisi
	public boolean creerBateau(int x1, int y1, int x2, int y2) {
		boolean coordonneesBonnes = true;
		if ((x1 != x2) & (y1 != y2)) { 
			coordonneesBonnes = false;
		}
			if(x1 == x2) {
				if ((y2 - y1)>5) {
					coordonneesBonnes = false;
				}
				else {
					for(int i=y1;i<y2+1;i++) {
						if (matrice[x1][i] == 1) {
							coordonneesBonnes = false;
						}
						else {
							matrice[x1][i] = 1;
						}
					}
				}
			}
			else if(y1 == y2) {
				if ((x2 - x1)>5) {
					coordonneesBonnes = false;
				}
				else {
					for(int i=x1;i<x2+1;i++) {
						if (matrice[i][y1] == 1) {
							coordonneesBonnes = false;
						}
						else {
							matrice[i][y1] = 1;
						}
					}
				}
			}
		return coordonneesBonnes;
	}
	
	// permet de parcourir l'emplacement d'un bateau 
	public boolean testBateau(int x1, int y1, int x2, int y2) {
		int compteur = 0;
		boolean bateauCoule = true;
			if(x1 == x2) {
				for(int i=y1;i<y2+1;i++) {
					if (matrice[x1][i] == 0) {
						compteur++;
					}
						else {
							matrice[x1][i] = 1;
						}
					}
				}
			
			else if(y1 == y2) {
				for(int i=y1;i<y2+1;i++) {
					if (matrice[x1][i] == 0) {
						compteur++;
					}
						else {
							matrice[x1][i] = 1;
						}
					}
				}
		return bateauCoule;
	}
	
	// permet de savoir si il y a un bateau a l'emplacement demande
	public boolean testPosition(int x, int y) {
		boolean position;
		if(matrice[x][y]!=1) {
			position = false;
		}
		else {
			position = true;
		}
		return position;
	}
	
	// permet d'attaque une position et de retourner un booleen permettant de savoir si un bateau a ete touche
		public boolean attaquePosition(int x, int y) {
			boolean position;
			if(matrice[x][y]!=1) {
				position = false;
			}
			else {
				position = true;
				matrice[x][y] = 0;
			}
			return position;
		}
		
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
	public static void main(String[] args) {
		BatailleNavale test = new BatailleNavale();
		test.creerBateau(0,1,0,5);
		test.creerBateau(2,3,5,3);
		String grille = test.Afficher();
		System.out.println(grille);
		System.out.println(test.testPosition(0,9));
	}
}
