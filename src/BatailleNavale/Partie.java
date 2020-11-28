package BatailleNavale;

public class Partie {
	
	public Bataille client1;
	public Bataille client2;

	// il y a une partie pour les deux joueurs, l'objet partie instancie les boards pour les deux clients
	public Partie() {
		client1 = new Bataille();
		client2 = new Bataille();
	}
	
	/*public static void main(String[] args) {
		
		Partie p1 = new Partie();
		System.out.println(p1.client1.Afficher());
	}*/
}