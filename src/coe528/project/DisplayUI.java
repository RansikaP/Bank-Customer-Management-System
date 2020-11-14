package coe528.project;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 *
 * @author Ransika Perera
 */
public abstract class DisplayUI {
    static Scene welcomeScene, accountScene, manageScene;
    static Stage window;
    static String userName;
    static Customer currentUser;
    static Manager m = Manager.getInstance();
    
    public static void welcome(Stage primaryStage) {
        window = primaryStage;
        Label welcomeLabel = new Label("Welcome");
        welcomeLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        Label userLabel = new Label("Username:");
        Label passLabel = new Label("Password:");
        Label roleLabel = new Label("Role:");
        
        TextField userField = new TextField();
        PasswordField passField = new PasswordField();
        
        ComboBox roleCB = new ComboBox();
        roleCB.getItems().addAll("Customer", "Manager");
        roleCB.setValue("Customer");
        
        Button loginbtn = new Button("Login");
        loginbtn.setOnAction(e -> {
            userName = userField.getText();
            currentUser = User.login(userName, passField.getText(), roleCB.getSelectionModel().getSelectedIndex());
            if (roleCB.getSelectionModel().getSelectedIndex() == 1 && userName.equals("admin") && passField.getText().equals("admin")) 
                manage();
            else if (currentUser != null) {
                account();
            } else
                alertWindow("Error", "Invalid Username, Password or Role");
        });
        
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(loginbtn);
        
        GridPane layoutWelcome = new GridPane();
        layoutWelcome.setMinSize(300, 275);
        layoutWelcome.setPadding(new Insets(25, 25, 25, 25));
        layoutWelcome.setVgap(10); 
        layoutWelcome.setHgap(10);
        layoutWelcome.setAlignment(Pos.CENTER);
        
        layoutWelcome.add(welcomeLabel, 0, 0, 2, 1);
        layoutWelcome.add(userLabel, 0, 1);
        layoutWelcome.add(passLabel, 0, 2);
        layoutWelcome.add(roleLabel, 0, 3);
        layoutWelcome.add(userField, 1, 1);
        layoutWelcome.add(passField, 1, 2);
        layoutWelcome.add(roleCB, 1, 3);
        layoutWelcome.add(hbBtn, 1, 4); 
        
        welcomeScene = new Scene(layoutWelcome);

        window.setTitle("Hello World!");
        window.setScene(welcomeScene);
        window.show();
    }
    
    public static void account() {
        Button okBtn = new Button("Ok");
        okBtn.setVisible(false);        
        Button cancelBtn = new Button("Cancel");        
        cancelBtn.setVisible(false);        
        TextField inputField = new TextField();
        inputField.setVisible(false);
        Label label = new Label();
        label. setVisible(false);
        cancelBtn.setOnAction(e -> {
            okBtn.setVisible(false);
            cancelBtn.setVisible(false);
            inputField.setVisible(false);
            label.setVisible(false);
        });
        
        HBox inputLayoutOne = new HBox(okBtn, cancelBtn);
        inputLayoutOne.setAlignment(Pos.CENTER);
        inputLayoutOne.setSpacing(10);
        VBox inputLayoutTwo = new VBox(label, inputField, inputLayoutOne);
        inputLayoutTwo.setAlignment(Pos.CENTER);
        inputLayoutTwo.setSpacing(10);
        
        Button depositBtn = new Button("Deposit");
        depositBtn.setOnAction(e -> {
            okBtn.setVisible(true);
            cancelBtn.setVisible(true);
            inputField.setVisible(true);
            label.setVisible(true);
            label.setText("Enter Amount to Deposit");
            okBtn.setOnAction(k -> {
                okBtn.setVisible(false);
                cancelBtn.setVisible(false);
                inputField.setVisible(false);
                label.setVisible(false);
                try {
                    currentUser.deposit(Double.parseDouble(inputField.getText()));
                } catch (IllegalArgumentException exception) {
                    alertWindow("Error", "Invalid Deposit Amount");
                }
            });
        });
        Button withdrawBtn = new Button("Withdraw");
        withdrawBtn.setOnAction(e -> {
            okBtn.setVisible(true);
            cancelBtn.setVisible(true);
            inputField.setVisible(true);
            label.setVisible(true);
            label.setText("Enter Amount to Withdraw");
            okBtn.setOnAction(k -> {
                okBtn.setVisible(false);
                cancelBtn.setVisible(false);
                inputField.setVisible(false);
                label.setVisible(false);
                try {
                    currentUser.withdraw(Double.parseDouble(inputField.getText()));
                } catch (IllegalArgumentException exception) {
                    alertWindow("Error", "Invalid Withdrawal Amount");
                }
            });
        });
        Button balanceBtn = new Button("Get Balance");
        balanceBtn.setOnAction(e -> {
            label.setVisible(true);
            label.setText(Double.toString(currentUser.getBalance()));
            okBtn.setVisible(true);               
            cancelBtn.setVisible(false);
            inputField.setVisible(false);           
            okBtn.setOnAction(k -> {
                okBtn.setVisible(false);
                label.setVisible(false);
            }); 
        });        
        Button purchaseBtn = new Button("Complete Online Purchase");
        purchaseBtn.setOnAction(e -> {
            okBtn.setVisible(true);
            cancelBtn.setVisible(true);
            inputField.setVisible(true);
            label.setVisible(true);
            label.setText("Enter Purchase Amount");
            okBtn.setOnAction(k -> {
                okBtn.setVisible(false);
                cancelBtn.setVisible(false);
                inputField.setVisible(false);
                label.setVisible(false);
                try {
                    currentUser.onlinePurchase(Double.parseDouble(inputField.getText()));
                } catch (IllegalArgumentException exception) {
                    alertWindow("Error", "Invalid Purchase Amount");
                }
            });
        
        });
        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> {
            User.logout(userName, currentUser.getBalance(), currentUser.getLevel());
            window.setScene(welcomeScene);
        });        
        
        VBox optionsLayout = new VBox(depositBtn, withdrawBtn, balanceBtn, purchaseBtn, logoutBtn);
        optionsLayout.setSpacing(10);
        optionsLayout.setAlignment(Pos.CENTER_LEFT);       
        
        HBox accountLayout = new HBox(optionsLayout, inputLayoutTwo);
        accountLayout.setAlignment(Pos.CENTER);
        accountLayout.setSpacing(25);
                
        accountScene = new Scene(accountLayout, 400, 200);
        window.setScene(accountScene);        
    }

    private static void manage() {
        Label userLabel = new Label("Username:");
        userLabel.setVisible(false);
        Label passLabel = new Label("Password:");
        passLabel.setVisible(false);
        TextField userField = new TextField();
        userField.setVisible(false);
        TextField passField = new TextField();
        passField.setVisible(false);
        Button okBtn = new Button("Ok");
        okBtn.setVisible(false);        
        Button cancelBtn = new Button("Cancel");        
        cancelBtn.setVisible(false);
        
        HBox one = new HBox(userLabel, userField);
        one.setSpacing(25);
        one.setAlignment(Pos.CENTER_RIGHT);
        HBox two = new HBox(passLabel, passField);
        two.setSpacing(25);
        two.setAlignment(Pos.CENTER);
        HBox three = new HBox(okBtn, cancelBtn);
        three.setSpacing(25);
        three.setAlignment(Pos.CENTER);
        
        VBox inputLayout = new VBox(one, two, three);
        inputLayout.setSpacing(25);
        inputLayout.setAlignment(Pos.CENTER);
        
        Button addBtn = new Button("Add Customer");
        addBtn.setOnAction(e -> {
            userLabel.setVisible(true);
            passLabel.setVisible(true);
            userField.setVisible(true);
            passField.setVisible(true);
            okBtn.setVisible(true);        
            cancelBtn.setVisible(true);
            okBtn.setOnAction(k -> {
                userLabel.setVisible(false);
                passLabel.setVisible(false);
                userField.setVisible(false);
                passField.setVisible(false);
                okBtn.setVisible(false);        
                cancelBtn.setVisible(false);
                try {
                    m.addCusomter(userField.getText(), passField.getText());
                } catch (Exception exception) {
                    alertWindow("Error", "Customer File Could not be Created");
                }

            });            
        });        
        Button deleteBtn = new Button("Remove Customer");
        deleteBtn.setOnAction(e -> {
            userLabel.setVisible(true);
            userField.setVisible(true);
            okBtn.setVisible(true);        
            cancelBtn.setVisible(true);
            okBtn.setOnAction(k -> {
                userLabel.setVisible(false);
                userField.setVisible(false);
                okBtn.setVisible(false);        
                cancelBtn.setVisible(false);
                try {
                    m.deleteCustomer(userField.getText());
                } catch (Exception exception) {
                    alertWindow("Error", "Customer File Could not be Deleted");
                }
                
            });        
        });
        
        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> window.setScene(welcomeScene));
        
        VBox optionsLayout = new VBox(addBtn, deleteBtn, logoutBtn);
        optionsLayout.setSpacing(25);
        optionsLayout.setAlignment(Pos.CENTER);
        
        HBox manageLayout = new HBox(optionsLayout, inputLayout);
        manageLayout.setSpacing(25);
        manageLayout.setAlignment(Pos.CENTER);
        
        manageScene = new Scene(manageLayout, 400, 200);
        window.setScene(manageScene);  
    }
    
    private static void alertWindow(String title, String message) {
        Stage alert = new Stage();
        
        alert.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250.0);
        
        Label label = new Label(message);
        Button close = new Button("Ok");
        close.setOnAction(e -> alert.close());
        
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, close);
        layout.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(layout);
        alert.setScene(scene);
        alert.showAndWait();
    }
}
