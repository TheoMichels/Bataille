package Serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import BatailleNavale.BatailleNavale;
import BatailleNavale.Partie;

public class ThreadChat extends Thread{
int id;
BufferedReader in1;
BufferedReader in2;
PrintWriter out1;
PrintWriter out2;
static PrintWriter[] outs=new PrintWriter[100]; 
static int nbid=0;
Partie partie;
String nomClient1;
String nomClient2;

public ThreadChat(int id,Socket client1, Socket client2) {
	try {
	this.id=id;
	nbid++;
	in1 = new BufferedReader(new InputStreamReader(client1.getInputStream()));
	in2 = new BufferedReader(new InputStreamReader(client2.getInputStream()));
	out1 = new PrintWriter(client1.getOutputStream(), true);
	out2 = new PrintWriter(client2.getOutputStream(), true);
	out1.println("[Partie numero "+id+"]\nVeuillez saisir votre nom : ");
	out2.println("[Partie numero "+id+"]\nVeuillez saisir votre nom : ");
	
	}catch (Exception e) {}
}


public void creerBateauClient(BufferedReader in, PrintWriter out, BatailleNavale client) {
	try {
		String msgCreerBateau = "Veuillez saisir vos coordonnees d'emplacement de votre bateau";
		out.println(msgCreerBateau);
		boolean coordonneesOK;
		do {
			String coordonneeXClient = in.readLine();
			String coordonneeYClient = in.readLine();
			if (client.creerBateau(coordonneeXClient, coordonneeYClient) == false) {
				out.println("Vos coordonnees ne sont pas bonnes, veuillez les saisir a� nouveau :");
				coordonneesOK = false;
			}
			else { 
				client.creerBateau(coordonneeXClient, coordonneeYClient);
				out.println(client.Afficher());
				coordonneesOK = true;
			}
		} while(coordonneesOK == false);
	} catch (NumberFormatException | IOException e) { e.printStackTrace();}
}

public void attaqueClient(BufferedReader in, PrintWriter out1,PrintWriter out2, BatailleNavale client) {
		try {
			String msgAttaque = "Veuillez choisir une coordonnee d'attaque :";
			out1.println(msgAttaque);
			String coordonneeClient = in.readLine();
			if (client.attaquePosition(coordonneeClient) ==true) { 
				out1.println("Touche !");  
				out2.println(nomClient1+" vous a touche !");
			}
			else { 
				out1.println("Loupe !");
				out2.println(nomClient1+" vous a loupe !");
			}
				
		} catch (NumberFormatException | IOException e) {e.printStackTrace();}		
}


public void run() {
	try {
		// initialisation de la partie et acceuil
		partie = new Partie();
		nomClient1 = in1.readLine(); out1.println("En attente de l'autre joueur...");
		nomClient2 = in2.readLine(); out2.println("En attente de l'autre joueur...");
		String msgAcceuil1 = "\nBonjour "+ nomClient1 +" ! Bienvenue dans cette nouvelle partie de bataille navale !\n";String msgAcceuil2 = "\nBonjour "+ nomClient2 +" ! Bienvenue dans cette nouvelle partie de bataille navale !\n";
		out1.println(msgAcceuil1); out2.println(msgAcceuil2);
		
		// creation de 3 bateaux pour les deux clients
		for (int i=0; i<3; i++) {
			creerBateauClient(in1, out1, partie.client1);
			out1.println("Vous avez place votre bateau numero "+(i+1));
			creerBateauClient(in2, out2, partie.client2);
			out2.println("Vous avez place votre bateau numero "+(i+1));
		}
		
		// boucle qui s'arrête lorsque un des deux clients n'a plus de bateau
		while (partie.client1.testFin()==false & partie.client2.testFin() == false) {
		attaqueClient(in1, out1, out2, partie.client1);
		attaqueClient(in2, out2, out1, partie.client2);
		}

		if (partie.client2.testFin() == true) {
			out1.println("Bravo "+ nomClient2 +" ! Vous avez battu "+ nomClient1+ " !");
			out2.println("Desole "+ nomClient1 +" ! "+ nomClient2+ " vous a battu !");
		}
		else if (partie.client1.testFin() == true) {
			out2.println("Bravo "+ nomClient1 +" ! Vous avez battu "+ nomClient2+ " !");
			out1.println("Desole "+ nomClient2 +" ! "+ nomClient1+ " vous a battu !");
		}
	
	} catch (Exception e) {}
}
}