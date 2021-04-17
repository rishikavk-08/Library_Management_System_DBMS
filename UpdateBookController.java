package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class UpdateBookController implements Initializable  {

    @FXML
    private TextField UbookID;
    @FXML
    private TextField Utitle;
    @FXML
    private TextField Uauthor;
    @FXML
    private ChoiceBox option;
    @FXML
    private Button apply;
    @FXML
    private TextField data;
    @FXML
    private Button updateButton;
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

    @FXML
    private Button Update;
    ObservableList<TableBooks> list = FXCollections.observableArrayList();

    ObservableList<String> choiceBox = FXCollections.observableArrayList("Title","Author","Edition","Price","Availability","Location");

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        table.getItems().clear();
        option.setValue("Title");
        option.setItems(choiceBox);
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


    Connection myCon;
    ResultSet reg1;

    public void apply(ActionEvent event) throws SQLException{
        if(!UbookID.getText().isBlank() && !Utitle.getText().isBlank() && !Uauthor.getText().isBlank()   ){
            String id= UbookID.getText();
            String tile=Utitle.getText();
            String auth=Uauthor.getText();
            //String op=option.getText();
            String op=(String)option.getValue();
            String d=data.getText();
            try {
                myCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "mypass");
                String reg1 = "Update books set "+op+"='"+d+"' where bookid=? ";
                PreparedStatement preparedStmt = myCon.prepareStatement(reg1);
                preparedStmt.setString (1, id);
                int res=preparedStmt.executeUpdate();
                if(res==1){
                    infoBox(" Book updated successfully",null,"Success!!");
                    //initialize();//refresh here!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                }
                else{
                    infoBox("issue in updating book ",null,"ERROR");
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

    public void Updateclicked() {
        infoBox("Your details are successfully updated",null,"Success");
    }
}
