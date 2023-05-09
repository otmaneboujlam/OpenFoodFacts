package service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import dao.AdditifDAO;
import dao.AllergeneDAO;
import dao.CategorieDAO;
import dao.IngredientDAO;
import dao.JPAUtils;
import dao.MarqueDAO;
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
	private AdditifDAO additifDAO = AdditifDAO.getInstance();
	private AllergeneDAO allergeneDAO = AllergeneDAO.getInstance();
	private CategorieDAO categorieDAO = CategorieDAO.getInstance();
	private IngredientDAO ingredientDAO = IngredientDAO.getInstance();
	private MarqueDAO marqueDAO = MarqueDAO.getInstance();
	
	private static HomeService INSTANCE = new HomeService();
	private HomeService() {};
	public static HomeService getInstance() {
		return INSTANCE;
	}

	private List<String> readFile() {
		Path path = Paths.get("E:\\Diginamic\\open-food-facts.csv");
		List<String> lines =null;
		try {
			lines = Files.readAllLines(path, StandardCharsets.UTF_8);
			lines.remove(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}
	
	private String traitementCasParticulier(String string) {
		if(string == null || string.isEmpty() || string.isBlank()) {
			return null;
		}		
		string = string.replace("*","");
		string = string.replace("_","");
		string = string.replaceAll("[0-9]+%", "");
		string = string.replaceAll("\\s[0-9]+", "");
		string = string.replaceAll("[0-9]+\\s%", "");
		string = string.replaceAll("[0-9]+g", "");
		string = string.replaceAll("[0-9]+\\sg", "");
		string = string.replaceAll("^[0-9]+", "");
		string = string.replaceAll("^'", "");
		string = string.replace("?", "");
		string = string.replace("%", "");
		string = string.replace("en:", "");
		string = string.replace("EN:", "");
		string = string.replace("fr:", "");
		string = string.replace("FR:", "");
		if(string.contains("(") && string.contains(")")) {
			string = string.replace(string.substring(string.indexOf("(")+1, string.indexOf(")")), "");
		}
		if(string.contains("[") && string.contains("]")) {
			string = string.replace(string.substring(string.indexOf("[")+1, string.indexOf("]")), "");
		}
		string = string.replace("(","");
		string = string.replace(")","");
		string = string.replace("[", "");
		string = string.replace("]", "");
		string = string.trim();
		if(string.matches("[0-9]+")) {
			return null;
		}
		if(string != null && !(string.isEmpty()) && !(string.isBlank())) {
			return string;
		}
		return null;
	}
	
	public void loadDataToDB() {
		EntityManager em = JPAUtils.getInstance().getEntityManager();
		List<String> lines = readFile();
//		int cont =0;
		for(String line : lines) {
//			if(cont>1000) {
//				break;
//			}
			String[] tableauInfosProduit = line.split("\\|");
			if(tableauInfosProduit.length>30) {
				continue;
			}
			String tableauInfosTotaleProduit[] = new String[30];
			for(int i=0;i<tableauInfosProduit.length;i++) {
				tableauInfosTotaleProduit[i]=tableauInfosProduit[i].trim();
			}
			em.getTransaction().begin();
			Produit produit = new Produit();
			Categorie categorie = createCategorieIfNotExist(tableauInfosTotaleProduit[0]);
			createMarquesIfNotExist(produit.getMarques() ,tableauInfosTotaleProduit[1]);
			ScoreNutritionnel scoreNutritionnel = createScoreNutritionnel(tableauInfosTotaleProduit[3]);
			createIngredientsIfNotExist(produit.getIngredients() ,tableauInfosTotaleProduit[4]);
			createAllergenesIfNotExist(produit.getAllergenes() ,tableauInfosTotaleProduit[28]);
			createAdditifsIfNotExist(produit.getAdditifs() ,tableauInfosTotaleProduit[29]);
			
			String nom = tableauInfosTotaleProduit[2];
			Boolean presenceHuilePalme =false;
			if(tableauInfosTotaleProduit[27]!=null && tableauInfosTotaleProduit[27].equals("1")) {
				presenceHuilePalme = true;
			}
			Float autres[] = new Float[22];
			for(int i=5;i<27;i++) {
				if(tableauInfosTotaleProduit[i].equals(null) || tableauInfosTotaleProduit[i]=="") {
					autres[i-5]=null;
				}
				else {
					autres[i-5]=Float.parseFloat(tableauInfosTotaleProduit[i]);	
				}
			}
			
			setProduitAttributs(produit ,categorie,nom,scoreNutritionnel,presenceHuilePalme,autres);
			produitDAO.create(produit);
			em.getTransaction().commit();
//			cont++;
		}
		em.close();
	}
	
	private void createAdditifsIfNotExist(List<Additif> additifs ,String string) {
		if(string != null && !(string.isEmpty()) && !(string.isBlank())) {
			String[] tableauAdditifs = string.split(",|;|\\.");
			for(int i=0;i<tableauAdditifs.length;i++) {
				tableauAdditifs[i] = traitementCasParticulier(tableauAdditifs[i]);
			}
			for(String s : tableauAdditifs) {
				if(s != null && !(s.isEmpty()) && !(s.isBlank())) {
					Additif additifDB = additifDAO.readOne(s);
					if(additifDB!=null) {
						additifs.add(additifDB);
					}
					else {
						Additif additif = new Additif();
						additif.setNom(s);
						additifDAO.create(additif);
						additifs.add(additif);
					}
				}
			}
		}
	}
	
	private void createAllergenesIfNotExist(List<Allergene> allergenes ,String string) {
		if(string != null && !(string.isEmpty()) && !(string.isBlank())) {
			String[] tableauAllergenes = string.split(",|;|\\.");
			for(int i=0;i<tableauAllergenes.length;i++) {
				tableauAllergenes[i] = traitementCasParticulier(tableauAllergenes[i]);
			}
			for(String s : tableauAllergenes) {
				if(s != null && !(s.isEmpty()) && !(s.isBlank())) {
					Allergene allergeneDB = allergeneDAO.readOne(s);
					if(allergeneDB!=null) {
						allergenes.add(allergeneDB);
					}
					else {
						Allergene allergene = new Allergene();
						allergene.setNom(s);
						allergeneDAO.create(allergene);
						allergenes.add(allergene);
					}
				}
			}
		}
	}
	
	private void createIngredientsIfNotExist(List<Ingredient> ingredients ,String string) {
		if(string != null && !(string.isEmpty()) && !(string.isBlank())) {
			String[] tableauIngredients = string.split(",|-|;|\\.|:");
			for(int i=0;i<tableauIngredients.length;i++) {
				tableauIngredients[i] = traitementCasParticulier(tableauIngredients[i]);
			}
			
			for(String s : tableauIngredients) {
				if(s != null && !(s.isEmpty()) && !(s.isBlank()) && s.length()<256) {
					Ingredient ingredientDB = ingredientDAO.readOne(s);
					if(ingredientDB!=null) {
						ingredients.add(ingredientDB);
					}
					else {
						Ingredient ingredient = new Ingredient();
						ingredient.setNom(s);
						ingredientDAO.create(ingredient);
						ingredients.add(ingredient);
					}
				}
			}
		}
	}
	
	private ScoreNutritionnel createScoreNutritionnel(String string) {
		Character key = null;
		if(string != null && !(string.isEmpty()) && !(string.isBlank())) {
			key = string.charAt(0);
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
			default:
				break;
			}
			return scoreNutritionnel;
		}
		else {
			return null;
		}
	}
	
	private void createMarquesIfNotExist(List<Marque> marques ,String string) {
		if(string != null && !(string.isEmpty()) && !(string.isBlank())) {
			String[] tableauMarques = string.split(",");
			for(int i=0;i<tableauMarques.length;i++) {
				tableauMarques[i] = traitementCasParticulier(tableauMarques[i]);
			}
			for(String s : tableauMarques) {
				if(s != null && !(s.isEmpty()) && !(s.isBlank())) {
					Marque marqueDB = marqueDAO.readOne(s);
					if(marqueDB!=null) {
						marques.add(marqueDB);
					}
					else {
						Marque marque = new Marque();
						marque.setNom(s);
						marqueDAO.create(marque);
						marques.add(marque);
					}
				}
			}
		}
	}
	
	private Categorie createCategorieIfNotExist(String string) {
		if(string == null || string.isEmpty() || string.isBlank()) {
			return null;
		}
		Categorie categorieDB = categorieDAO.readOne(string);
		if(categorieDB!=null) {
			return categorieDB;
		}
		else {
			Categorie categorie = new Categorie();
			categorie.setNom(string);
			categorieDAO.create(categorie);
			return categorie;
		}
	}
	
	private void setProduitAttributs(Produit produit ,Categorie categorie, String nom, ScoreNutritionnel scoreNutritionnel, Boolean presenceHuilePalme, Float[] autres) {
		produit.setCategorie(categorie);
		produit.setNom(nom);
		produit.setScoreNutritionnel(scoreNutritionnel);
		produit.setPresenceHuilePalme(presenceHuilePalme);
		produit.setEnergie(autres[0]);
		produit.setGraisse(autres[1]);
		produit.setSucres(autres[2]);
		produit.setFibres(autres[3]);
		produit.setProteines(autres[4]);
		produit.setSel(autres[5]);
		produit.setVitA(autres[6]);
		produit.setVitD(autres[7]);
		produit.setVitE(autres[8]);
		produit.setVitK(autres[9]);
		produit.setVitC(autres[10]);
		produit.setVitB1(autres[11]);
		produit.setVitB2(autres[12]);
		produit.setVitPP(autres[13]);
		produit.setVitB6(autres[14]);
		produit.setVitB9(autres[15]);
		produit.setVitB12(autres[16]);
		produit.setCalcium(autres[17]);
		produit.setMagnesium(autres[18]);
		produit.setIron(autres[19]);
		produit.setFer(autres[20]);
		produit.setBetaCarotene(autres[21]);
	}
}

