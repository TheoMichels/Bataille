package Serveur;

import java.net.ServerSocket;

import java.net.Socket;
import java.util.Scanner;

import BatailleNavale.Partie;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class Serveur {

	public static void main(String[] args) {
		
		try {
			ServerSocket ecoute = new ServerSocket(1500);
			System.out.println("Serveur lancé!");
			int id=0;	
			while(true) {
			Socket client1 = ecoute.accept();
			Socket client2 = ecoute.accept();
			new ThreadChat(id,client1, client2).start();
			id++;
				}
			}
		catch(Exception e) {
			System.out.println(e + "\nIl y a eu une erreur");
		}
	}
}