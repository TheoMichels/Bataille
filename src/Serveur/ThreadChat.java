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


public void creerBateauClient1() {
	try {
		String msgCreerBateau = "Veuillez saisir vos coordonnees d'emplacement de votre bateau";
		out1.println(msgCreerBateau);
		boolean coordonneesOK;
		do {
			int coordonnee1Client1 = Integer.parseInt(in1.readLine());
			int coordonnee2Client1 = Integer.parseInt(in1.readLine());
			int coordonnee3Client1 = Integer.parseInt(in1.readLine());
			int coordonnee4Client1 = Integer.parseInt(in1.readLine());
			if (partie.client1.creerBateau(coordonnee1Client1,coordonnee2Client1,coordonnee3Client1,coordonnee4Client1) == false) {
				out1.println("Vos coordonnees ne sont pas bonnes, veuillez les saisir a  nouveau :");
				coordonneesOK = false;
			}
			else { 
				partie.client1.creerBateau(coordonnee1Client1,coordonnee2Client1,coordonnee3Client1,coordonnee4Client1);
				out1.println(partie.client1.Afficher());
				coordonneesOK = true;
			}
		} while(coordonneesOK == false);
	} catch (NumberFormatException | IOException e) { e.printStackTrace();}
}

public void creerBateauClient2() {
	try {
		String msgCreerBateau = "Veuillez saisir vos coordonnees d'emplacement de votre bateau";
		out2.println(msgCreerBateau);
		boolean coordonneesOK;
		do {
		int coordonnee1Client2 = Integer.parseInt(in2.readLine());
		int coordonnee2Client2 = Integer.parseInt(in2.readLine());
		int coordonnee3Client2 = Integer.parseInt(in2.readLine());
		int coordonnee4Client2 = Integer.parseInt(in2.readLine());
		if (partie.client2.creerBateau(coordonnee1Client2,coordonnee2Client2,coordonnee3Client2,coordonnee4Client2) == false) {
			out2.println("Vos coordonnees ne sont pas bonnes, veuillez les saisir a  nouveau :");
			coordonneesOK = false;
		}
		else { 
			partie.client2.creerBateau(coordonnee1Client2,coordonnee2Client2,coordonnee3Client2,coordonnee4Client2);
			out2.println(partie.client2.Afficher());
			coordonneesOK = true;
		}
	} while(coordonneesOK == false);
	} catch (NumberFormatException | IOException e) { e.printStackTrace();}
}

public void attaqueClient1() {
		try {
			String msgAttaque = "Veuillez choisir une coordonnee d'attaque :";
			out1.println(msgAttaque);
			int coordonnee1PosClient1 = Integer.parseInt(in1.readLine());
			int coordonnee2PosClient1 = Integer.parseInt(in1.readLine());
			if (partie.client2.attaquePosition(coordonnee1PosClient1, coordonnee2PosClient1) ==true) { 
				out1.println("Touche !");  
				out2.println(nomClient1+" vous a touche !");
			}
			else { 
				out1.println("Loupe !");
				out2.println(nomClient1+" vous a loupe !");
			}
				
		} catch (NumberFormatException | IOException e) {e.printStackTrace();}		
}

public void attaqueClient2() {
	try {
		String msgAttaque = "Veuillez choisir une coordonnee d'attaque :";
		out2.println(msgAttaque);
		int coordonnee1PosClient2 = Integer.parseInt(in2.readLine());
		int coordonnee2PosClient2 = Integer.parseInt(in2.readLine());
		if (partie.client1.attaquePosition(coordonnee1PosClient2, coordonnee2PosClient2) ==true) { 
			out2.println("Touche !"); 
			out1.println(nomClient2+" vous a touche !");
		}
		else { 
			out2.println("Loupe !");
			out1.println(nomClient2+" vous a loupe !");
		}
			
	} catch (NumberFormatException | IOException e) {e.printStackTrace();}		
}

public void run() {
	try {
		// initialisation de la partie et acceuil
		partie = new Partie();
		nomClient1 = in1.readLine(); nomClient2 = in2.readLine();
		String msgAcceuil1 = "\nBonjour "+ nomClient1 +" ! Bienvenue dans cette nouvelle partie de bataille navale !\n";String msgAcceuil2 = "\nBonjour "+ nomClient2 +" ! Bienvenue dans cette nouvelle partie de bataille navale !\n";
		out1.println(msgAcceuil1); out2.println(msgAcceuil2);
		
		// creation de 3 bateaux pour les deux clients
		for (int i=0; i<3; i++) {
			creerBateauClient1();
			out1.println("Vous avez place votre bateau numero "+(i+1));
			creerBateauClient2();
			out2.println("Vous avez place votre bateau numero "+(i+1));
		}
		
		// boucle qui s'arrÃªte lorsque un des deux clients n'a plus de bateau
		while (partie.client1.testFin()==false & partie.client2.testFin() == false) {
		attaqueClient1();
		attaqueClient2();
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