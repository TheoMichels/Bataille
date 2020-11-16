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
	
	// affiche le tableau, les 0 correspondent � des emplacements vides et les 1 correspondent � un bateau
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
	
	// cr�� un bateau en fonction des coordonn�es que le joueur choisi
	public boolean creerBateau(int x1, int y1, int x2, int y2) {
		boolean coordonneesBonnes = true;
		if ((x1 != x2) & (y1 != y2)) { 
			System.out.println("Veuillez sélectionner à nouveau car vos coordonnées sont fausses : ");
			coordonneesBonnes = false;
		}
		else {
			if(x1 == x2) {
				for(int i=y1;i<y2+1;i++) {
					matrice[x1][i] = 1;
				}
			}
			if(y1 == y2) {
				for(int i=x1;i<x2+1;i++) {
					matrice[i][y1] = 1;
				}
			}
		}
		return coordonneesBonnes;
	}
	
	// permet de savoir si il y a un bateau � l'emplacement demand�
	public boolean testPosition(int x, int y) {
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
			 if (matrice[i][j]==0) { compteur++; }
			}
		}
		if (compteur == 100) { test = true;}
		return test;
	}
	
	// main permettant de tester les m�thodes 
	public static void main(String[] args) {
		BatailleNavale test = new BatailleNavale();
		test.creerBateau(0,1,0,5);
		test.creerBateau(2,3,5,3);
		String grille = test.Afficher();
		System.out.println(grille);
		System.out.println(test.testPosition(0,9));
	}
}
