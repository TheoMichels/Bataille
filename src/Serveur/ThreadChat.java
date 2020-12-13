package Serveur;

import java.io.BufferedReader;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import BatailleNavale.Bataille;
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


public void creerBateauClient(BufferedReader in, PrintWriter out, Bataille client,int tour) {
	try {
		String msgErreur;
		String coordonneeXClient;
		String coordonneeYClient;
		
		String msgCreerBateau = "Veuillez saisir vos coordonnees d'emplacement de votre bateau n°"+(tour+1)+".";
		String msgTour1 = "Votre premier bateau doit avoir une taille de 2 :";
		String msgTour2 = "Votre deuxieme bateau doit avoir une taille de 3 :";
		String msgTour3 = "Votre troisieme bateau doit avoir une taille de 5 :";
		
		out.println(msgCreerBateau);
		
		// change l'indication de taille du bateau en fonction du tour
		if(tour==0) {
			out.println(msgTour1);
		}
		else if(tour==1) {
			out.println(msgTour2);
		}
		else if(tour==2) {
			out.println(msgTour3);
		}
		
		//commence une boucle qui s'exécute tant qu'un bateau n'est pas correctement cree 
		do {
			coordonneeXClient = in.readLine();
			coordonneeYClient = in.readLine();
			
			msgErreur = client.creerBateau(coordonneeXClient, coordonneeYClient, tour);
			
			if (msgErreur=="Les coordonnees sont en diagonales, vous n'avez pas le droit de placer un bateau de cette facon.") {
				out.println(msgErreur+" Veuillez saisir à nouveau les coordonnees :");
			}
			else if (msgErreur=="Votre bateau ne fait pas la bonne taille.") {
				out.println(msgErreur+" Veuillez saisir à nouveau les coordonnees :");
			}
			else if (msgErreur=="Il y a deja un bateau a cet emplacement.") {
				out.println(msgErreur+" Veuillez saisir à nouveau les coordonnees :");
			}
			else if (msgErreur=="Il y a un probleme, veuillez recommencer.") {
				out.println(msgErreur+" Veuillez saisir à nouveau les coordonnees :");
			}
			else if (msgErreur==null){ 
				out.println(client.Afficher());
			}
		} while(msgErreur != null);
		
	} catch (NumberFormatException | IOException e) { e.printStackTrace();}
}

public void attaqueClient(BufferedReader in, PrintWriter out,PrintWriter outAdversaire, Bataille client) {
		try {
			String msgAttaque = "Veuillez choisir une coordonnee d'attaque :";
			out.println(msgAttaque);
			String coordonneeClient = in.readLine();
			if (client.attaquePosition(coordonneeClient) ==true) { 
				out.println("Touche !");  
				outAdversaire.println(nomClient1+" vous a touche !");
			}
			else { 
				out.println("Loupe !");
				outAdversaire.println(nomClient1+" vous a loupe !");
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
			creerBateauClient(in1, out1, partie.client1, i);
			out1.println("Vous avez place votre bateau numero "+(i+1)+"\n");
			creerBateauClient(in2, out2, partie.client2, i);
			out2.println("Vous avez place votre bateau numero "+(i+1)+"\n");
		}
		
		// boucle qui execute 10 tours (nombre minimum de tours a realiser avant qu'une partie puisse potentiellement se terminer) -> evite la verification des matrices a chaque tour 
		for(int i=0; i<10; i++) {
			//attaque du client 1 vers le client 2
			attaqueClient(in1, out1, out2, partie.client2);
			//attaque du client 2 vers le client 1
			attaqueClient(in2, out2, out1, partie.client1);
		}
		
		// boucle qui continue a executer des tours tant que personne n'a gagne (verification complete des matrices a chaque tour)
		while (partie.client1.testFin()==false & partie.client2.testFin() == false) {
			//attaque du client 1 vers le client 2
			attaqueClient(in1, out1, out2, partie.client2);
			//attaque du client 2 vers le client 1
			attaqueClient(in2, out2, out1, partie.client1);
		}

		// message de fin personnalise en fonction de quel joueur a gagne 
		if (partie.client2.testFin() == true & partie.client1.testFin() == false) {
			out1.println("Bravo "+ nomClient1 +" ! Vous avez battu "+ nomClient2+ " !");
			out2.println("Desole "+ nomClient2 +" ! "+ nomClient1+ " vous a battu !");
		}
		else if (partie.client2.testFin() == false & partie.client1.testFin() == true) {
			out2.println("Bravo "+ nomClient2 +" ! Vous avez battu "+ nomClient1+ " !");
			out1.println("Desole "+ nomClient1 +" ! "+ nomClient2+ " vous a battu !");
		}
		else if (partie.client2.testFin() == true & partie.client1.testFin() == true) {
			String msgFin = "Wow ! Vous avez tous les deux fini de couler les bateaux adverses en meme temps, il y a egalite !";
			out2.println(msgFin);
			out1.println(msgFin);
		}
	
	} catch (Exception e) {}
}
}