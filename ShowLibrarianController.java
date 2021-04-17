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

public class ShowLibrarianController implements Initializable {

    @FXML
    private TableView<LibrarianTable> table;

    @FXML
    private TableColumn<LibrarianTable, String> id;

    @FXML
    private TableColumn<LibrarianTable, String> first;

    @FXML
    private TableColumn<LibrarianTable, String> last;

    @FXML
    private TableColumn<LibrarianTable, String> shift;

    @FXML
    private TableColumn<LibrarianTable, String> user;

    @FXML
    private TableColumn<LibrarianTable, String> pass;

    @FXML
    private Button Back;

    ObservableList<LibrarianTable> list = FXCollections.observableArrayList();

    //start backend here
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        table.getItems().clear();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "mypass");
            ResultSet rs = con.createStatement().executeQuery("Select * from librarians");
            while (rs.next()) {
                list.add(new LibrarianTable(rs.getString("librarian_ID"),rs.getString("f_name"),rs.getString("l_name"),rs.getString("shift"),rs.getString("username"),rs.getString("password")));
            }
            try {   //set properties to cells of each column of the table
                id.setCellValueFactory(new PropertyValueFactory<LibrarianTable,String>("id"));
                first.setCellValueFactory(new PropertyValueFactory<LibrarianTable,String>("first"));
                last.setCellValueFactory(new PropertyValueFactory<LibrarianTable,String>("last"));
                shift.setCellValueFactory(new PropertyValueFactory<LibrarianTable,String>("shift"));
                user.setCellValueFactory(new PropertyValueFactory<LibrarianTable,String>("user"));
                pass.setCellValueFactory(new PropertyValueFactory<LibrarianTable,String>("pass"));
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
