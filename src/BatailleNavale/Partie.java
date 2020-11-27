package BatailleNavale;

public class Partie {
	
	public BatailleNavale client1;
	public BatailleNavale client2;

	// il y a une partie pour les deux joueurs, l'objet partie instancie les boards pour les deux clients
	public Partie() {
		client1 = new BatailleNavale();
		client2 = new BatailleNavale();
	}
	
	/*public static void main(String[] args) {
		
		Partie p1 = new Partie();
		System.out.println(p1.client1.Afficher());
	}*/
}