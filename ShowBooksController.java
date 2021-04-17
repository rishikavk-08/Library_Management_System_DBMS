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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ShowBooksController implements Initializable {

    @FXML
    private TableView<TableBooks> table;

    @FXML
    private TableColumn<TableBooks, Integer> id;

    @FXML
    private TableColumn<TableBooks, String> title;

    @FXML
    private TableColumn<TableBooks, String> author;

    @FXML
    private TableColumn<TableBooks, Integer> edition;

    @FXML
    private TableColumn<TableBooks, Integer> price;

    @FXML
    private TableColumn<TableBooks, String> availability;

    @FXML
    private TableColumn<TableBooks, String> location;

    @FXML
    private Button Back;

    ObservableList<TableBooks> list = FXCollections.observableArrayList();

    //start backend here ig
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        table.getItems().clear();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "mypass");
            ResultSet rs = con.createStatement().executeQuery("Select * from books");
            while (rs.next()) {
                list.add(new TableBooks(rs.getInt("bookid"),rs.getString("title"),rs.getString("author"),rs.getInt("edition"),rs.getInt("price"),rs.getString("availability"),rs.getString("location")));
            }
            try {   //set properties to cells of each column of the table
                id.setCellValueFactory(new PropertyValueFactory<TableBooks,Integer>("id"));
                title.setCellValueFactory(new PropertyValueFactory<TableBooks,String>("title"));
                author.setCellValueFactory(new PropertyValueFactory<TableBooks,String>("author"));
                edition.setCellValueFactory(new PropertyValueFactory<TableBooks,Integer>("edition"));
                price.setCellValueFactory(new PropertyValueFactory<TableBooks,Integer>("price"));
                availability.setCellValueFactory(new PropertyValueFactory<TableBooks,String>("availability"));
                location.setCellValueFactory(new PropertyValueFactory<TableBooks,String>("location"));
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
