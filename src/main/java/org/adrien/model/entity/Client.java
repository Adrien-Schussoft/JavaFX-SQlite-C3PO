package org.adrien.model.entity;

public class Client implements IClient {
    private  String nom;
    private  String prenom;
    private  String ville;
    private  int id;

    /**
     * Default constructor
     */
    public Client() {}

    /**
     * Constructor with parameters
     * @param nom : customer's name
     * @param prenom : customer's first name
     * @param ville : city where the customer comes from.
     */
    public Client(String nom, String prenom, String ville) {

        this.nom = nom;
        this.prenom = prenom;
        this.ville = ville;
    }

    /**
     * Constructor with parameters
     * @param id
     * @param nom
     * @param prenom
     * @param ville
     */
    public Client(int id,String nom, String prenom, String ville) {

        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.ville = ville;
    }

    /**
     * Constructor with id parameter to update,search,delete...
     * @param id
     */
    public Client(int id) {

        this.id = id;
    }

    // Classics Getters and Setters
    @Override
    public String getNom() {
        return this.nom;
    }

    @Override
    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String getPrenom() {
        return this.prenom;
    }

    @Override
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Override
    public String getVille() {
        return this.ville;
    }

    @Override
    public void setVille(String ville) {
        this.ville = ville;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
