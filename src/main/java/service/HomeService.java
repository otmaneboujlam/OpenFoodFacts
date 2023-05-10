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
import dao.MarqueDAO;
import dao.ProduitDAO;
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
	
	public void loadDataToDB() {
		List<String> lines = readFile();
		int cont =0;
		for(String line : lines) {
			if(cont>2000) {
				break;
			}
			String[] tableauInfosProduit = line.split("\\|");
			if(tableauInfosProduit.length>30) {
				continue;
			}
			Produit produit = new Produit();
			for(int i=0;i<tableauInfosProduit.length;i++) {
				if((tableauInfosProduit[i].trim().isEmpty())) {
					continue;
				}
				switch (i) {
				case 0:
					produit.setCategorie(createCategorieIfNotExist(tableauInfosProduit[0].trim()));
					break;
				case 1:
					createMarquesIfNotExist(produit.getMarques() ,tableauInfosProduit[1].trim());
					break;
				case 2 :
					produit.setNom(tableauInfosProduit[2].trim());
					break;
				case 3:
					produit.setScoreNutritionnel(createScoreNutritionnel(tableauInfosProduit[3].trim()));
					break;
				case 4:
					createIngredientsIfNotExist(produit.getIngredients() ,tableauInfosProduit[4].trim());
					break;
				case 5:
					produit.setEnergie(Float.parseFloat(tableauInfosProduit[i].trim()));
					break;
				case 6:
					produit.setGraisse(Float.parseFloat(tableauInfosProduit[i].trim()));
					break;
				case 7:
					produit.setSucres(Float.parseFloat(tableauInfosProduit[i].trim()));
					break;
				case 8:
					produit.setFibres(Float.parseFloat(tableauInfosProduit[i].trim()));
					break;
				case 9:
					produit.setProteines(Float.parseFloat(tableauInfosProduit[i].trim()));
					break;
				case 10:
					produit.setSel(Float.parseFloat(tableauInfosProduit[i].trim()));
					break;
				case 11:
					produit.setVitA(Float.parseFloat(tableauInfosProduit[i].trim()));
					break;
				case 12:
					produit.setVitD(Float.parseFloat(tableauInfosProduit[i].trim()));
					break;
				case 13:
					produit.setVitE(Float.parseFloat(tableauInfosProduit[i].trim()));
					break;
				case 14:
					produit.setVitK(Float.parseFloat(tableauInfosProduit[i].trim()));
					break;
				case 15:
					produit.setVitC(Float.parseFloat(tableauInfosProduit[i].trim()));
					break;
				case 16:
					produit.setVitB1(Float.parseFloat(tableauInfosProduit[i].trim()));
					break;
				case 17:
					produit.setVitB2(Float.parseFloat(tableauInfosProduit[i].trim()));
					break;
				case 18:
					produit.setVitPP(Float.parseFloat(tableauInfosProduit[i].trim()));
					break;
				case 19:
					produit.setVitB6(Float.parseFloat(tableauInfosProduit[i].trim()));
					break;
				case 20:
					produit.setVitB9(Float.parseFloat(tableauInfosProduit[i].trim()));
					break;
				case 21:
					produit.setVitB12(Float.parseFloat(tableauInfosProduit[i].trim()));
					break;
				case 22:
					produit.setCalcium(Float.parseFloat(tableauInfosProduit[i].trim()));
					break;
				case 23:
					produit.setMagnesium(Float.parseFloat(tableauInfosProduit[i].trim()));
					break;
				case 24:
					produit.setIron(Float.parseFloat(tableauInfosProduit[i].trim()));
					break;
				case 25:
					produit.setFer(Float.parseFloat(tableauInfosProduit[i].trim()));
					break;
				case 26:
					produit.setBetaCarotene(Float.parseFloat(tableauInfosProduit[i].trim()));
					break;
				case 27:
					if(tableauInfosProduit[27].trim().equals("1")) {
						produit.setPresenceHuilePalme(true);
					}
					break;
				case 28:
					createAllergenesIfNotExist(produit.getAllergenes() ,tableauInfosProduit[28].trim());
					break;
				case 29:
					createAdditifsIfNotExist(produit.getAdditifs() ,tableauInfosProduit[29].trim());
					break;
				default:
					break;
				}
			}
			
			produitDAO.create(produit);
			cont++;
		}
	}
	
	private String traitementCasParticulierAllergene(String string) {
		if(!(string.isEmpty())) {
			string = string.replace("*","")
					.replace("_","");
		}
		return string;
	}
	
	private String traitementCasParticulierIngredient(String string) {
		if(!(string.isEmpty())) {
			string = string.replace("*","")
					.replace("_","")
					.replaceAll("[0-9]+%", "")
					.replaceAll("\\s[0-9]+", "")
					.replaceAll("[0-9]+\\s%", "")
					.replaceAll("[0-9]+g", "")
					.replaceAll("[0-9]+\\sg", "")
					.replaceAll("^[0-9]+", "")
					.replace("{", "")
					.replace("}", "")
					.replace("+", "")
					.replace("—", "")
					.replaceAll("^'", "")
					.replace("?", "")
					.replace("%", "")
					.replace("en:", "")
					.replace("EN:", "")
					.replace("fr:", "")
					.replace("FR:", "");
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
			if(string.matches("[0-9]+")) {
				string = "";
			}
		}
		return string;
	}
	
	
	private void createAdditifsIfNotExist(List<Additif> additifs ,String string) {
		String[] tableauAdditifs = string.split(",|;|\\.");
		for(String s : tableauAdditifs) {
			s=s.trim();
			if(!(s.isEmpty())) {
				Additif additif = additifDAO.readOneByName(s);
				if(additif==null) {
					additif = new Additif();
					additif.setNom(s);
					additifDAO.create(additif);
				}
				additifs.add(additif);
			}
		}	
	}
	
	private void createAllergenesIfNotExist(List<Allergene> allergenes ,String string) {
		String[] tableauAllergenes = string.split(",|;|\\.");
		for(int i=0;i<tableauAllergenes.length;i++) {
			tableauAllergenes[i]=traitementCasParticulierAllergene(tableauAllergenes[i].trim());
		}
		for(String s : tableauAllergenes) {
			if(!(s.isEmpty())) {
				Allergene allergene = allergeneDAO.readOneByName(s);
				if(allergene==null) {
					allergene = new Allergene();
					allergene.setNom(s);
					allergeneDAO.create(allergene);
				}
				allergenes.add(allergene);
			}
		}
	}
	
	private void createIngredientsIfNotExist(List<Ingredient> ingredients ,String string) {
		String[] tableauIngredients = string.split(",|-|;|\\.|:");
		for(int i=0;i<tableauIngredients.length;i++) {
			tableauIngredients[i]=traitementCasParticulierIngredient(tableauIngredients[i].trim());
		}
		for(String s : tableauIngredients) {
			if(!(s.isEmpty()) && s.length()<256) {
				Ingredient ingredient = ingredientDAO.readOneByName(s);
				if(ingredient==null) {
					ingredient = new Ingredient();
					ingredient.setNom(s);
					ingredientDAO.create(ingredient);
				}
				ingredients.add(ingredient);
			}
		}		
	}
	
	private ScoreNutritionnel createScoreNutritionnel(String string) {
		Character key = null;
		key = string.charAt(0);
		switch (key) {
		case 'a':
			return ScoreNutritionnel.A;
		case 'b':
			return ScoreNutritionnel.B;
		case 'c':
			return ScoreNutritionnel.C;
		case 'd':
			return ScoreNutritionnel.D;
		case 'e':
			return ScoreNutritionnel.E;
		default:
			return null;
		}
	}
	
	private void createMarquesIfNotExist(List<Marque> marques ,String string) {
		String[] tableauMarques = string.split(",");
		for(String s : tableauMarques) {
			s=s.trim();
			if(!(s.isEmpty())) {
				Marque marque = marqueDAO.readOneByName(s);
				if(marque==null) {
					marque = new Marque();
					marque.setNom(s);
					marqueDAO.create(marque);
				}
				marques.add(marque);
			}
		}	
	}
	
	private Categorie createCategorieIfNotExist(String string) {
		Categorie categorie = categorieDAO.readOneByName(string);
		if(categorie==null) {
			categorie = new Categorie();
			categorie.setNom(string);
			categorieDAO.create(categorie);
		}
		return categorie;
	}
	
}

