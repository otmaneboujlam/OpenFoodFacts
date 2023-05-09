package view;

import java.util.Scanner;

import controller.HomeController;

public class Home {

	private HomeController homeController = HomeController.getInstance();
	
	private void init() {
		System.out.println("Page d'accueil");
		System.out.println("Voulez vous lancer le processus 'enregister les données du fichier open food facts dans la base de données' ?");
		Scanner scanner = new Scanner(System.in);
		long debut = System.currentTimeMillis();
		homeController.connecte(scanner);
		long fin = System.currentTimeMillis();
		System.out.println("Temps écoulé en millisecondes :" + (fin - debut));
		System.out.println("Temps écoulé en minutes :" + (fin - debut)/60000);
	}
	
	private Home() {
		this.init();
	}
	
	public static void main(String[] args) {

		new Home();
	}

}
