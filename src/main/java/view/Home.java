package view;

import java.util.Scanner;

import controller.HomeController;

public class Home {

	private HomeController homeController = HomeController.getInstance();
	
	private void init() {
		System.out.println("Page d'accueil");
		System.out.println("Voulez vous lancer le processus 'enregister les données du fichier open food facts dans la base de données' ?");
		Scanner scanner = new Scanner(System.in);
		homeController.connecte(scanner);
	}
	
	private Home() {
		this.init();
	}
	
	public static void main(String[] args) {

		Home home = new Home();
	}

}
