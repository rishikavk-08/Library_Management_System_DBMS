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



public class ShowMemberController implements Initializable {

    @FXML
    private Button Back;


    @FXML
    private TableView <TableviewMember> table;

    @FXML
    private TableColumn<TableviewMember, Integer> id;

    @FXML
    private TableColumn<TableviewMember, String> first;

    @FXML
    private TableColumn<TableviewMember, String> last;

    @FXML
    private TableColumn<TableviewMember, String> address;

    @FXML
    private TableColumn<TableviewMember, String> phone;

    @FXML
    private TableColumn<TableviewMember, String> email;

    @FXML
    private TableColumn<TableviewMember, String> username;

    @FXML
    private TableColumn<TableviewMember, String> password;

    ObservableList<TableviewMember> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resources)  //tried backend dint work! so finished the front end part
    {

        try {
            Connection con =DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "mypass");
            ResultSet rs = con.createStatement().executeQuery("select * from member");

            while(rs.next())
            {
                list.add(new TableviewMember(rs.getInt("member_ID"),rs.getString("Firstname"),rs.getString("lastname"),rs.getString("address"),rs.getString("phone_number"),rs.getString("emailID"),rs.getString("username"),rs.getString("password")));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        id.setCellValueFactory(new PropertyValueFactory<TableviewMember,Integer>("id"));
        first.setCellValueFactory(new PropertyValueFactory<TableviewMember,String>("first"));
        last.setCellValueFactory(new PropertyValueFactory<TableviewMember,String>("last"));
        address.setCellValueFactory(new PropertyValueFactory<TableviewMember,String>("address"));
        phone.setCellValueFactory(new PropertyValueFactory<TableviewMember,String>("phone"));
        email.setCellValueFactory(new PropertyValueFactory<TableviewMember,String>("email"));
        username.setCellValueFactory(new PropertyValueFactory<TableviewMember,String>("username"));
        password.setCellValueFactory(new PropertyValueFactory<TableviewMember,String>("password"));

        table.setItems(list);

    }  //backend end here





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
