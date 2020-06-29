package org.adrien.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.adrien.model.dao.IDao;
import org.adrien.model.entity.IClient;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
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

    Alert alert = new Alert(Alert.AlertType.NONE);

    Boolean save = false;
    Boolean update = false;
    Boolean delete = false;

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
     * @param
     * @throws ClassNotFoundException,NoSuchMethodException,IllegalAccessException,InvocationTargetException
     */
    @FXML
    private void save() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException, SQLException, InstantiationException {
        if(!vbox_form.isVisible()){
            clearInputs();
            setVboxVisible();
            save = true;
            setAction();
        }else{
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setTitle("Save confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Client "+text_nom.getText()+" "+text_prenom.getText()+" saved !");
            if(vbox_form.isVisible() && save == true && isValidInput(text_nom.getText()) && isValidInput(text_prenom.getText()) && isValidInput(text_ville.getText())){
                label_error.setVisible(false);
                IClient client = BeanFactory.getClient();
                client.setNom(text_nom.getText());
                client.setPrenom(text_prenom.getText());
                client.setVille(text_ville.getText());
                Method methodInsert = BeanFactory.getInsert();
                client = (IClient) methodInsert.invoke(clientIDao,client);
                model.add(client);
                model.clear();
                clientArrayList = (ArrayList<IClient>) methodList.invoke(clientIDao);
                model.addAll(clientArrayList);
                alert.showAndWait();
                annuler();
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
     * @param
     * @throws ClassNotFoundException,NoSuchMethodException,IllegalAccessException,InvocationTargetException
     */
    @FXML
    private void update() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, ClassNotFoundException {
        if(!vbox_form.isVisible()){
            setVboxVisible();
            update = true;
            setAction();
        }else {
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setTitle("Update confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Client updated !");
            if (isValidInput(text_nom.getText()) && isValidInput(text_prenom.getText()) && isValidInput(text_ville.getText())) {
                label_error.setVisible(false);
                client.setId(lst_clients.getSelectionModel().getSelectedItem().getId());
                client.setNom(text_nom.getText());
                client.setPrenom(text_prenom.getText());
                client.setVille(text_ville.getText());
                Method methodUpdate = BeanFactory.getUpdate();
                client = (IClient) methodUpdate.invoke(clientIDao, client);
                model.clear();
                clientArrayList = (ArrayList<IClient>) methodList.invoke(clientIDao);
                model.addAll(clientArrayList);
                alert.showAndWait();
                annuler();
            } else {
                label_error.setText("Saisie incorrecte");
                label_error.setVisible(true);
                System.out.println("Saisie invalide");
            }
        }
    }

    /**
     * delete event to delete data.
     * @param
     * @throws ClassNotFoundException,NoSuchMethodException,IllegalAccessException,InvocationTargetException
     */
    @FXML
    private void delete() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, ClassNotFoundException {
        if(!vbox_form.isVisible()){
            setVboxVisible();
            delete = true;
            setAction();
        }else {
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete confirmation");
            alert.setHeaderText(lst_clients.getSelectionModel().getSelectedItem().getNom()+" "+lst_clients.getSelectionModel().getSelectedItem().getPrenom()+" will be deleted.");
            alert.setContentText(null);
            Optional<ButtonType> result = alert.showAndWait();
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Client "+lst_clients.getSelectionModel().getSelectedItem().getNom()+" "+lst_clients.getSelectionModel().getSelectedItem().getPrenom()+" deleted !");
            if (result.get() == ButtonType.OK) {
                client.setId(lst_clients.getSelectionModel().getSelectedItem().getId());
                System.out.println(client.getId());
                Method methodDelete = BeanFactory.getDelete();
                client = (IClient) methodDelete.invoke(clientIDao, client);
                model.clear();
                clientArrayList = (ArrayList<IClient>) methodList.invoke(clientIDao);
                model.addAll(clientArrayList);
                clearInputs();
                alert.showAndWait();
                annuler();
            }
        }
    }

    /**
     * Cancel the user's input.
     * @param
     */
    @FXML
    private void annuler() {
        save = false;
        update = false;
        delete = false;
        setAction();
        clearInputs();
        setVboxUnvisible();
        btn_sauver.setDisable(false);
        btn_update.setDisable(false);
        btn_supprimer.setDisable(false);
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
             } catch (NullPointerException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException throwables) {
                 throwables.printStackTrace();
             }
             text_nom.setText(client.getNom());
             text_prenom.setText(client.getPrenom());
             text_ville.setText(client.getVille());
        });
    }

    @FXML
    private void setAction(){

        System.out.println("Bool save = "+save.booleanValue());
        System.out.println("Bool update = "+update.booleanValue());
        System.out.println("Bool delete = "+delete.booleanValue());

        if(save == true){
            System.out.println("save");
            btn_sauver.setStyle("-fx-background-color: forestgreen");
            allSetDisable();
        }
        else if(update == true){
            System.out.println("update");
            btn_update.setStyle("-fx-background-color: forestgreen");
            allSetDisable();
        }
        else if(delete == true){
            System.out.println("delete");
            btn_supprimer.setStyle("-fx-background-color: forestgreen");
            allSetDisable();
        }
       if(save == false){
            btn_sauver.setStyle("-fx-background-color: #E66A6A");
        }
        if(update == false){
            btn_update.setStyle("-fx-background-color: #E66A6A");
        }
        if(delete == false){
            btn_supprimer.setStyle("-fx-background-color: #E66A6A");
        }

    }

    private void allSetDisable(){
        btn_supprimer.setDisable(true);
        btn_supprimer.setOpacity(1);
        btn_sauver.setDisable(true);
        btn_sauver.setOpacity(1);
        btn_update.setDisable(true);
        btn_update.setOpacity(1);
    }

    @FXML
    private void action(){
        btn_ok.setOnMouseClicked (event1 -> {
            if(save == true){
                try {
                    save();
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException | SQLException | InstantiationException e) {
                    e.printStackTrace();
                }
            }
            else if(update == true){
                try {
                    update();
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else if(delete == true){
                try {
                    delete();
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
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