/**
 * 
 */
package model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

/**
 * @author Otmane
 *
 */

@Entity
public enum ScoreNutritionnel {

	A(1L,'a',"excellent"),
	B(2L,'b',"bon"),
	C(3L,'c',"moyen"),
	D(4L,'d',"médiocre"),
	E(5L,'e',"mauvais");
	
	@Id
	private Long id;
	private Character code;
	private String nom;
	@OneToMany
	@JoinColumn(name = "scoreNutritionnel_id")
	private List<Produit> produits;
	
	private ScoreNutritionnel(Long id, Character code, String nom) {
		this.setId(id);
		this.setCode(code);
		this.setNom(nom);
	}
	
	public Character getCode() {
		return code;
	}
	public void setCode(Character code) {
		this.code = code;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Produit> getProduits() {
		return produits;
	}
	public void setProduits(List<Produit> produits) {
		this.produits = produits;
	}
	
}
