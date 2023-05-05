package service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import dao.JPAUtils;
import dao.ProduitDAO;
import jakarta.persistence.EntityManager;
import model.Additif;
import model.Allergene;
import model.Categorie;
import model.Ingredient;
import model.Marque;
import model.Produit;
import model.ScoreNutritionnel;

public class HomeService {
	
	private ProduitDAO produitDAO = ProduitDAO.getInstance();
	
	private static HomeService INSTANCE = new HomeService();
	private HomeService() {};
	public static HomeService getInstance() {
		return INSTANCE;
	}

	public List<String> readFile() {
		Path path = Paths.get("E:\\Diginamic\\open-food-facts.csv");
		List<String> lines =null;
		try {
			lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}
	
	public void loadDataToDB() {
		List<String> lines = readFile();
		for(String line : lines) {
			String[] tableauInfosProduit = line.split("\\|");
			String tableauInfosTotaleProduit[] = new String[30];
			for(int i=0;i<tableauInfosProduit.length;i++) {
				tableauInfosTotaleProduit[i]=tableauInfosProduit[i];
			}
			
			Categorie categorie = createCategorieIfNotExist(tableauInfosTotaleProduit[0]);
			List<Marque> marques = createMarquesIfNotExist(tableauInfosTotaleProduit[1]);
			ScoreNutritionnel scoreNutritionnel = createScoreNutritionnelIfNotExist(tableauInfosTotaleProduit[3]);
			List<Ingredient> ingredients = createIngredientsIfNotExist(tableauInfosTotaleProduit[4]);
			List<Allergene> allergenes = createAllergenesIfNotExist(tableauInfosTotaleProduit[28]);
			List<Additif> additifs = createAdditifsIfNotExist(tableauInfosTotaleProduit[29]);
			
			String nom = tableauInfosTotaleProduit[2];
			Boolean presenceHuilePalme =false;
			if(tableauInfosTotaleProduit[27].equals("1")) {
				presenceHuilePalme = true;
			}
			Float autres[] = new Float[22];
			for(int i=5;i<27;i++) {
				autres[i-5]=Float.parseFloat(tableauInfosTotaleProduit[i]);
			}
			
			Produit produit = createProduit(categorie,marques,nom,scoreNutritionnel,ingredients,allergenes,additifs,presenceHuilePalme,autres);
			
		}
	}
	
	private List<Additif> createAdditifsIfNotExist(String string) {
		List<Additif> additifs = new ArrayList<Additif>();
		//TODO: ajouter split("-") ...
		//TODO: voir cas d'exemples dans l'énoncé
		String[] tableauAdditifs = string.split(",");
		for(String s : tableauAdditifs) {
			//TODO : Vérifier si l'objet existe en base de données
			Additif additif = new Additif();
			additif.setNom(s.trim());
			additifs.add(additif);
		}
		return additifs;
	}
	
	private List<Allergene> createAllergenesIfNotExist(String string) {
		List<Allergene> allergenes = new ArrayList<Allergene>();
		//TODO: ajouter split("-") ...
		//TODO: voir cas d'exemples dans l'énoncé
		String[] tableauAllergenes = string.split(",");
		for(String s : tableauAllergenes) {
			//TODO : Vérifier si l'objet existe en base de données
			Allergene allergene = new Allergene();
			allergene.setNom(s.trim());
			allergenes.add(allergene);
		}
		return allergenes;
	}
	
	private List<Ingredient> createIngredientsIfNotExist(String string) {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		//TODO: ajouter split("-") ...
		//TODO: voir cas d'exemples dans l'énoncé
		String[] tableauIngredients = string.split(",");
		for(String s : tableauIngredients) {
			//TODO : Vérifier si l'objet existe en base de données
			Ingredient ingredient = new Ingredient();
			ingredient.setNom(s.trim());
			ingredients.add(ingredient);
		}
		return ingredients;
	}
	
	private ScoreNutritionnel createScoreNutritionnelIfNotExist(String string) {
		Character key = string.trim().charAt(0);
		//TODO : Vérifier si l'objet existe en base de données
		
		ScoreNutritionnel scoreNutritionnel = null;
		switch (key) {
		case 'a':
			scoreNutritionnel = ScoreNutritionnel.A;
			break;
		case 'b':
			scoreNutritionnel = ScoreNutritionnel.B;
			break;
		case 'c':
			scoreNutritionnel = ScoreNutritionnel.C;
			break;
		case 'd':
			scoreNutritionnel = ScoreNutritionnel.D;
			break;
		case 'e':
			scoreNutritionnel = ScoreNutritionnel.E;
			break;
		case 'f':
			scoreNutritionnel = ScoreNutritionnel.F;
			break;
		default:
			System.out.println("Produit n'a pas de scoreNutritionnel : "+string);
			break;
		}
		
		return scoreNutritionnel;
	}
	
	private List<Marque> createMarquesIfNotExist(String string) {
		List<Marque> marques = new ArrayList<Marque>();
		String[] tableauMarques = string.split(",");
		for(String s : tableauMarques) {
			//TODO : Vérifier si l'objet existe en base de données
			Marque marque = new Marque();
			marque.setNom(s.trim());
			marques.add(marque);
		}
		return marques;
	}
	
	private Categorie createCategorieIfNotExist(String string) {
		//TODO : Vérifier si l'objet existe en base de données
		
		Categorie categorie = new Categorie();
		categorie.setNom(string.trim());
		return categorie;
	}
	
	private Produit createProduit(Categorie categorie, List<Marque> marques, String nom, ScoreNutritionnel scoreNutritionnel, List<Ingredient> ingredients, List<Allergene> allergenes, List<Additif> additifs, Boolean presenceHuilePalme, Float[] autres) {
		Produit produit = new Produit();
		produit.setCategorie(categorie);
		produit.setMarques(marques);
		produit.setNom(nom);
		produit.setScoreNutritionnel(scoreNutritionnel);
		produit.setIngredients(ingredients);
		produit.setAllergenes(allergenes);
		produit.setPresenceHuilePalme(presenceHuilePalme);
		produit.setEnergie(autres[0]);
		//TODO : produit.set(autres[1-21]);
		return produit;
	}
}

