package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class DeleteMemberController implements Initializable {

    @FXML
    private Button Back;

    //fxids
    @FXML
    private Button Delete;

    @FXML
    private TextField dmemberid;

    @FXML
    private TextField dfirstname;

    @FXML
    private TextField dlastname;

    @FXML
    private TableView<TableviewMember> table;

    @FXML
    private TableColumn<TableviewMember, Integer> id;

    @FXML
    private TableColumn<TableviewMember, String> first;

    @FXML
    private TableColumn<TableviewMember, String> last;

    @FXML
    private TableColumn<TableviewMember, String> address;

    @FXML
    private TableColumn<TableviewMember, Integer> phone;

    @FXML
    private TableColumn<TableviewMember, String> email;

    @FXML
    private TableColumn<TableviewMember, String> username;

    @FXML
    private TableColumn<TableviewMember, String> password;

    ObservableList<TableviewMember> list = FXCollections.observableArrayList();

    public void initialize(URL url, ResourceBundle resources) {
        table.getItems().clear();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "mypass");
            ResultSet rs = con.createStatement().executeQuery("Select * from member");
            while (rs.next()) {
                list.add(new TableviewMember(rs.getInt("member_ID"),rs.getString("firstname"),rs.getString("lastname"),rs.getString("address"),rs.getString("phone_number"),rs.getString("emailID"),rs.getString("username"),rs.getString("password")));
            }
            try {   //set properties to cells of each column of the table
                id.setCellValueFactory(new PropertyValueFactory<TableviewMember,Integer>("id"));
                first.setCellValueFactory(new PropertyValueFactory<TableviewMember,String>("first"));
                last.setCellValueFactory(new PropertyValueFactory<TableviewMember,String>("last"));
                address.setCellValueFactory(new PropertyValueFactory<TableviewMember,String>("address"));
                phone.setCellValueFactory(new PropertyValueFactory<TableviewMember,Integer>("phone"));
                email.setCellValueFactory(new PropertyValueFactory<TableviewMember,String>("email"));
                username.setCellValueFactory(new PropertyValueFactory<TableviewMember,String>("username"));
                password.setCellValueFactory(new PropertyValueFactory<TableviewMember,String>("password"));
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

    Connection myCon;
    ResultSet reg1;

    public void Deletememberclicked(ActionEvent actionEvent) {
        if(!dmemberid.getText().isBlank() && !dfirstname.getText().isBlank() && !dlastname.getText().isBlank()   ){
            String id= dmemberid.getText();
            String first=dfirstname.getText();
            String last=dlastname.getText();
            try {
                myCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "mypass");
                String reg1 = "delete from member where member_ID="+"?" ;
                PreparedStatement preparedStmt = myCon.prepareStatement(reg1);
                preparedStmt.setString (1, id);
                int res=preparedStmt.executeUpdate();
                if(res==1){
                    infoBox(" Member deleted successfully",null,"Success!!");
                    //initialize();//refresh here!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                }
                else{
                    infoBox("issue in deleting member ",null,"ERROR");
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        else{
            infoBox("please enter all details",null,"ERROR!!");
        }
    }
    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        ButtonType buttonTypeOk = new ButtonType("Ok");//button Ok is created
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);//button Cancel is created
        alert.getButtonTypes().setAll(buttonTypeOk, buttonTypeCancel);
        alert.getDialogPane().lookupButton(buttonTypeCancel).setVisible(false);
        alert.showAndWait();
    }
}