package graphicElements;

import graphicElements.PizzeriaPages.PizzeriaHomePage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pizzeria.Customer;
import pizzeria.Database;
import pizzeria.Pizzeria;
import pizzeria.Services;
import pizzeria.pizzeriaSendMail.SendJavaMail;

import java.sql.SQLException;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

public class NewAccountPage {
	public void display(Stage window, Pizzeria pizzeria) {

		Label username = new Label(" Nome:\t  ");
		TextField nameInput = new TextField();
		nameInput.setPromptText("Your Name");
		username.setId("nomiLabel");
		HBox usernameBox = new HBox(50);
		usernameBox.getChildren().addAll(username, nameInput);
		usernameBox.setAlignment(Pos.CENTER);

		Label password = new Label(" Password: ");
		PasswordField passwordInput = new PasswordField();
		passwordInput.setPromptText("Your Password");
		password.setId("nomiLabel");
		HBox passwordBox = new HBox(50);
		passwordBox.getChildren().addAll(password, passwordInput);
		passwordBox.setAlignment(Pos.CENTER);

		Label password2 = new Label(" Conferma: ");
		PasswordField passwordInput2 = new PasswordField();
		passwordInput2.setPromptText("Confirm Password");
		password2.setId("nomiLabel");
		HBox passwordBox2 = new HBox(50);
		passwordBox2.getChildren().addAll(password2, passwordInput2);
		passwordBox2.setAlignment(Pos.CENTER);

		Label mail = new Label(" E-mail:\t  ");
		TextField mailInput = new TextField();
		mailInput.setPromptText("Your e-Mail Address");
		mail.setId("nomiLabel");
		HBox mailBox = new HBox(50);
		mailBox.getChildren().addAll(mail, mailInput);
		mailBox.setAlignment(Pos.CENTER);

		Label insertErrorLabel = new Label("");
		insertErrorLabel.setId("errorLabel");

		Button signUpButton = new Button("Registrati");
		signUpButton.setMinSize(100, 50);
		signUpButton.setOnAction(e->{
			switch(pizzeria.createAccount(mailInput.getText(),nameInput.getText(),passwordInput.getText(),passwordInput2.getText())){
				case "OK":
					Database.putCustomer(nameInput.getText().toUpperCase(), passwordInput.getText(), mailInput.getText());
					SendJavaMail newMail = new SendJavaMail();
					newMail.welcomeMail(nameInput.getText(),passwordInput.getText());
					MenuPage menuPage = new MenuPage();
					Customer customer = new Customer(nameInput.getText().toUpperCase(), passwordInput.getText());
					customer.setLoggedIn(true);
					menuPage.display(window, pizzeria, customer);
					break;
				case "SHORT":
					insertErrorLabel.setTextFill(Color.DARKRED);
					insertErrorLabel.setText("Username o password troppo brevi");
					break;
				case "EXISTING":
					insertErrorLabel.setTextFill(Color.DARKRED);
					insertErrorLabel.setText("Utente già registrato");
					break;
				case "DIFFERENT":
					insertErrorLabel.setTextFill(Color.DARKRED);
					insertErrorLabel.setText("Password non coincidente");
					break;
			}
		});

		Button backButton = createBackButton(pizzeria, window);

		HBox buttonBox = new HBox(50);
		VBox vBox = new VBox(20);
		buttonBox.getChildren().addAll(backButton,signUpButton);
		buttonBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(insertErrorLabel, buttonBox);
		vBox.setAlignment(Pos.CENTER);

		VBox layout = new VBox(20);
		layout.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(usernameBox, passwordBox, passwordBox2, mailBox, vBox);

		layout.setOnKeyPressed(ke -> {
			if(ke.getCode()== KeyCode.ENTER) {
				signUpButton.fire();
			}
		});

		layout.getStyleClass().add("layout");

		Scene scene = new Scene(layout, 880, 600);
		window.setScene(scene);
		scene.getStylesheets().addAll(this.getClass().getResource("cssStyle/loginPageStyle.css").toExternalForm());
		window.show();
	}

	private Button createBackButton(Pizzeria pizzeria, Stage window) {
		Button backButton = new Button("← Torna indietro");
		backButton.setId("backButton");
		backButton.setOnAction(e -> {
			StartPage startPage = new StartPage();
			startPage.display(window, pizzeria);
		});
		return backButton;
	}
}