package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ShowTransactionController implements Initializable {

    @FXML
    private TableView<TableTransaction> table;

    @FXML
    private TableColumn<TableTransaction, Integer> tid;

    @FXML
    private TableColumn<TableTransaction, Integer> memberid;

    @FXML
    private TableColumn<TableTransaction, Integer> bookid;

    @FXML
    private TableColumn<TableTransaction, String> issue;

    @FXML
    private TableColumn<TableTransaction, String> returndate;

    @FXML
    private TableColumn<TableTransaction, Integer> price;

    @FXML
    private Button Back;

    ObservableList<TableTransaction> list = FXCollections.observableArrayList();

    //start backend
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        table.getItems().clear();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "mypass");
            ResultSet rs = con.createStatement().executeQuery("Select * from Transactions");
            while (rs.next()) {
                list.add(new TableTransaction(rs.getInt("T_ID"),rs.getInt("M_ID"),rs.getInt("Book_ID"),rs.getString("issue_date"),rs.getString("return_date"),rs.getInt("price")));
            }
            try {   //set properties to cells of each column of the table
                tid.setCellValueFactory(new PropertyValueFactory<TableTransaction,Integer>("tid"));
                bookid.setCellValueFactory(new PropertyValueFactory<TableTransaction,Integer>("bookid"));
                memberid.setCellValueFactory(new PropertyValueFactory<TableTransaction,Integer>("memberid"));
                issue.setCellValueFactory(new PropertyValueFactory<TableTransaction,String>("issue"));
                returndate.setCellValueFactory(new PropertyValueFactory<TableTransaction,String>("returndate"));
                price.setCellValueFactory(new PropertyValueFactory<TableTransaction,Integer>("price"));
                table.setItems(list);
                rs.close();
            } catch (Exception e) { //Exception is handled
                e.printStackTrace();
            }
        } catch (SQLException throwables) { //SQLException thrown by next()
            throwables.printStackTrace();
        }
    }

    public void Backclicked()
    {
        Stage stage = (Stage) Back.getScene().getWindow();
        stage.close();
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
            Stage loginStage = new Stage();
            loginStage.setTitle("Librarian Page");
            loginStage.setScene(new Scene(root, 703, 547));
            loginStage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }
    }

}
