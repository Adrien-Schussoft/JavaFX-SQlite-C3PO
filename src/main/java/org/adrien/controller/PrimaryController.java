package org.adrien.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.adrien.model.dao.IDao;
import org.adrien.model.entity.Client;
import org.adrien.model.entity.IClient;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static org.adrien.controller.RegExpMatching.isValidInput;

public class PrimaryController implements Initializable {

    @FXML
    private TableView<IClient> lst_clients;
    @FXML
    private TableColumn<IClient, String> col_prenom;
    @FXML
    private TableColumn<IClient, String> col_nom;
    @FXML
    private Button btn_sauver;
    @FXML
    private Button btn_annuler;
    @FXML
    private Button btn_supprimer;
    @FXML
    private Button btn_update;
    @FXML
    private Button btn_ok;
    @FXML
    private TextField text_prenom;
    @FXML
    private TextField text_nom;
    @FXML
    private TextField text_ville;
    @FXML
    private VBox vbox_form;
    @FXML
    private Label label_error;
    @FXML
    private Label label_details;
    @FXML
    private BorderPane borderPane;

    ObservableList<IClient> model = FXCollections.observableArrayList();
    IDao clientIDao = BeanFactory.getClientDao();
    IClient client = BeanFactory.getClient();
    Method methodList = BeanFactory.getList();
    ArrayList<IClient> clientArrayList = (ArrayList<IClient>) methodList.invoke(clientIDao);

    public PrimaryController() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
    }

    /**
     * Initialize TableView and data before the launch.
     * @param url
     * @param rb
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for (int i =0;i<clientArrayList.size();i++){
            model.add(clientArrayList.get(i));
        }
        lst_clients.setEditable(false);
        // Jonction du tableau avec les données
        col_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        // Indique au TableView le model à observer pour trouver les données
        lst_clients.setItems(model);
        getDataModelSelected();
        vbox_form.setVisible(false);
        label_error.setVisible(false);
    }

    /**
     * Save event to save data.
     * @param event
     * @throws ClassNotFoundException,NoSuchMethodException,IllegalAccessException,InvocationTargetException
     */
    @FXML
    private void save(ActionEvent event) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException, SQLException, InstantiationException {
        if(!vbox_form.isVisible()){
            clearInputs();
            setVboxVisible();
        }else{
            if(isValidInput(text_nom.getText()) && isValidInput(text_prenom.getText()) && isValidInput(text_ville.getText())){
                label_error.setVisible(false);
                IClient client = BeanFactory.getClient();
                client.setNom(text_nom.getText());
                client.setPrenom(text_prenom.getText());
                client.setVille(text_ville.getText());
                Method methodInsert = BeanFactory.getInsert();
                client = (Client) methodInsert.invoke(clientIDao,client);
                model.add(client);
                model.clear();
                clientArrayList = (ArrayList<IClient>) methodList.invoke(clientIDao);
                model.addAll(clientArrayList);
            }
            else{
                label_error.setText("Saisie incorrecte");
                label_error.setVisible(true);
                System.out.println("Saisie invalide");
            }
        }
    }

    /**
     * update event to update data.
     * @param event
     * @throws ClassNotFoundException,NoSuchMethodException,IllegalAccessException,InvocationTargetException
     */
    @FXML
    private void update(ActionEvent event) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, ClassNotFoundException {
        if(!vbox_form.isVisible()){
            setVboxVisible();
        }else {
            if(isValidInput(text_nom.getText()) && isValidInput(text_prenom.getText()) && isValidInput(text_ville.getText())) {
                label_error.setVisible(false);
                client.setId(lst_clients.getSelectionModel().getSelectedItem().getId());
                client.setNom(text_nom.getText());
                client.setPrenom(text_prenom.getText());
                client.setVille(text_ville.getText());
                Method methodUpdate = BeanFactory.getUpdate();
                client = (IClient) methodUpdate.invoke(clientIDao,client);
                model.clear();
                clientArrayList = (ArrayList<IClient>) methodList.invoke(clientIDao);
                model.addAll(clientArrayList);
            }else{
                label_error.setText("Saisie incorrecte");
                label_error.setVisible(true);
                System.out.println("Saisie invalide");
            }
        }
    }

    /**
     * delete event to delete data.
     * @param event
     * @throws ClassNotFoundException,NoSuchMethodException,IllegalAccessException,InvocationTargetException
     */
    @FXML
    private void delete(ActionEvent event) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, ClassNotFoundException {
        if(!vbox_form.isVisible()){
            setVboxVisible();
        }else {
            client.setId(lst_clients.getSelectionModel().getSelectedItem().getId());
            System.out.println(client.getId());
            Method methodDelete = BeanFactory.getDelete();
            client = (IClient) methodDelete.invoke(clientIDao,client);
            model.clear();
            clientArrayList = (ArrayList<IClient>) methodList.invoke(clientIDao);
            model.addAll(clientArrayList);
            clearInputs();
        }
    }

    /**
     * Cancel the user's input.
     * @param event
     */
    @FXML
    private void annuler(ActionEvent event) {
    clearInputs();
    setVboxUnvisible();
    }

    private void clearInputs(){
        text_prenom.clear();
        text_nom.clear();
        text_ville.clear();
    }

    /**
     * Get the information of the selected row in the textFields areas.
     */
    @FXML
    private void getDataModelSelected(){
         lst_clients.setOnMouseClicked(event1 -> {
             try {
                 Method methodFindById = BeanFactory.getFindById();
                 client = (IClient) methodFindById.invoke(clientIDao,lst_clients.getSelectionModel().getSelectedItem().getId());
             } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException throwables) {
                 throwables.printStackTrace();
             }
             text_nom.setText(client.getNom());
             text_prenom.setText(client.getPrenom());
             text_ville.setText(client.getVille());
        });
    }

    @FXML
    private void setVboxVisible(){
       vbox_form.setVisible(true);
    }
    @FXML
    private void setVboxUnvisible(){
        vbox_form.setVisible(false);
    }
}