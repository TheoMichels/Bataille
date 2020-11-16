package Serveur;

import java.io.BufferedReader;


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

public ThreadChat(int id,Socket client1, Socket client2) {
	try {
	this.id=id;
	nbid++;
	in1 = new BufferedReader(new InputStreamReader(client1.getInputStream()));
	in2 = new BufferedReader(new InputStreamReader(client2.getInputStream()));
	out1 = new PrintWriter(client1.getOutputStream(), true);
	out2 = new PrintWriter(client2.getOutputStream(), true);
	out1.println("Partie n°"+id+"\nVeuillez saisir votre nom : ");
	out2.println("Partie n°"+id+"\nVeuillez saisir votre nom : ");
	}catch (Exception e) {}
}

public void run() {
	try {
		Partie partie = new Partie();
		String nomClient1 = in1.readLine(); String nomClient2 = in2.readLine();
		String msgAcceuil1 = "\nBonjour "+ nomClient1 +" ! Bienvenue dans cette nouvelle partie de bataille navale !";String msgAcceuil2 = "\nBonjour "+ nomClient2 +" ! Bienvenue dans cette nouvelle partie de bataille navale !";
		out1.println(msgAcceuil1); out2.println(msgAcceuil2);
		
		
		String msgCreerBateau = "Veuillez saisir vos coordonnées d'emplacement de votre bateau";
		
		out1.println(msgCreerBateau);
		int coordonnee1Client1 = Integer.parseInt(in1.readLine());
		int coordonnee2Client1 = Integer.parseInt(in1.readLine());
		int coordonnee3Client1 = Integer.parseInt(in1.readLine());
		int coordonnee4Client1 = Integer.parseInt(in1.readLine());
		partie.client1.creerBateau(coordonnee1Client1,coordonnee2Client1,coordonnee3Client1,coordonnee4Client1);
		out1.println(partie.client1.Afficher());
		
		out2.println(msgCreerBateau); 
		int coordonnee2 = Integer.parseInt(in2.readLine());
		partie.client2.creerBateau(coordonnee2,3,5,3);
		out2.println(partie.client2.Afficher());
		

	
	} catch (Exception e) {}
}
}