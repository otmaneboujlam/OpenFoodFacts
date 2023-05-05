/**
 * 
 */
package model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

/**
 * @author Otmane
 *
 */

@Entity
public class Produit {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nom;
	@ManyToMany
	private List<Ingredient> ingredients = new ArrayList<Ingredient>();
	@ManyToOne
	private ScoreNutritionnel scoreNutritionnel;
	@ManyToMany
	private List<Allergene> allergenes = new ArrayList<Allergene>();
	@ManyToMany
	private List<Additif> additifs = new ArrayList<Additif>();
	@ManyToOne
	private Categorie categorie;
	@ManyToMany
	private List<Marque> marques = new ArrayList<Marque>();
	private Float energie;
	private Float graisse;
	private Float sucres;
	private Float fibres;
	private Float proteines;
	private Float sel;
	private Float vitA;
	private Float vitD;
	private Float vitE;
	private Float vitK;
	private Float vitC;
	private Float vitB1;
	private Float vitB2;
	private Float VitPP;
	private Float vitB6;
	private Float vitB9;
	private Float vitB12;
	private Float calcium;
	private Float magnesium;
	private Float iron;
	private Float fer;
	private Float betaCarotene;
	private Boolean presenceHuilePalme;
	
	
	public List<Ingredient> getIngredients() {
		return ingredients;
	}
	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	public ScoreNutritionnel getScoreNutritionnel() {
		return scoreNutritionnel;
	}
	public void setScoreNutritionnel(ScoreNutritionnel scoreNutritionnel) {
		this.scoreNutritionnel = scoreNutritionnel;
	}
	public List<Allergene> getAllergenes() {
		return allergenes;
	}
	public void setAllergenes(List<Allergene> allergenes) {
		this.allergenes = allergenes;
	}
	public List<Additif> getAdditifs() {
		return additifs;
	}
	public void setAdditifs(List<Additif> additifs) {
		this.additifs = additifs;
	}
	public Categorie getCategorie() {
		return categorie;
	}
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
	public List<Marque> getMarques() {
		return marques;
	}
	public void setMarques(List<Marque> marques) {
		this.marques = marques;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Boolean getPresenceHuilePalme() {
		return presenceHuilePalme;
	}
	public void setPresenceHuilePalme(Boolean presenceHuilePalme) {
		this.presenceHuilePalme = presenceHuilePalme;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Float getEnergie() {
		return energie;
	}
	public void setEnergie(Float energie) {
		this.energie = energie;
	}
	public Float getGraisse() {
		return graisse;
	}
	public void setGraisse(Float graisse) {
		this.graisse = graisse;
	}
	public Float getSucres() {
		return sucres;
	}
	public void setSucres(Float sucres) {
		this.sucres = sucres;
	}
	public Float getFibres() {
		return fibres;
	}
	public void setFibres(Float fibres) {
		this.fibres = fibres;
	}
	public Float getProteines() {
		return proteines;
	}
	public void setProteines(Float proteines) {
		this.proteines = proteines;
	}
	public Float getSel() {
		return sel;
	}
	public void setSel(Float sel) {
		this.sel = sel;
	}
	public Float getVitA() {
		return vitA;
	}
	public void setVitA(Float vitA) {
		this.vitA = vitA;
	}
	public Float getVitD() {
		return vitD;
	}
	public void setVitD(Float vitD) {
		this.vitD = vitD;
	}
	public Float getVitE() {
		return vitE;
	}
	public void setVitE(Float vitE) {
		this.vitE = vitE;
	}
	public Float getVitK() {
		return vitK;
	}
	public void setVitK(Float vitK) {
		this.vitK = vitK;
	}
	public Float getVitC() {
		return vitC;
	}
	public void setVitC(Float vitC) {
		this.vitC = vitC;
	}
	public Float getVitB1() {
		return vitB1;
	}
	public void setVitB1(Float vitB1) {
		this.vitB1 = vitB1;
	}
	public Float getVitB2() {
		return vitB2;
	}
	public void setVitB2(Float vitB2) {
		this.vitB2 = vitB2;
	}
	public Float getVitPP() {
		return VitPP;
	}
	public void setVitPP(Float vitPP) {
		VitPP = vitPP;
	}
	public Float getVitB6() {
		return vitB6;
	}
	public void setVitB6(Float vitB6) {
		this.vitB6 = vitB6;
	}
	public Float getVitB9() {
		return vitB9;
	}
	public void setVitB9(Float vitB9) {
		this.vitB9 = vitB9;
	}
	public Float getVitB12() {
		return vitB12;
	}
	public void setVitB12(Float vitB12) {
		this.vitB12 = vitB12;
	}
	public Float getCalcium() {
		return calcium;
	}
	public void setCalcium(Float calcium) {
		this.calcium = calcium;
	}
	public Float getMagnesium() {
		return magnesium;
	}
	public void setMagnesium(Float magnesium) {
		this.magnesium = magnesium;
	}
	public Float getIron() {
		return iron;
	}
	public void setIron(Float iron) {
		this.iron = iron;
	}
	public Float getFer() {
		return fer;
	}
	public void setFer(Float fer) {
		this.fer = fer;
	}
	public Float getBetaCarotene() {
		return betaCarotene;
	}
	public void setBetaCarotene(Float betaCarotene) {
		this.betaCarotene = betaCarotene;
	}
	
	
	
}
