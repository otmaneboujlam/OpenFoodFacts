package controller;

import java.util.List;
import java.util.Scanner;

import dao.ProduitDAO;
import model.Additif;
import model.Allergene;
import model.Categorie;
import model.Ingredient;
import model.Marque;
import model.Produit;
import model.ScoreNutritionnel;
import service.HomeService;

public class HomeController {
	
	private HomeService homeService = HomeService.getInstance();
	
	
	private static HomeController INSTANCE = new HomeController();
	private HomeController() {};
	public static HomeController getInstance() {
		return INSTANCE;
	}
	
	public void connecte(Scanner scanner) {
		if(scanner.next().equals("yes")) {
			this.init();
		}
	}
	
	private void init() {
		homeService.loadDataToDB();
	}
}
