package graphicElements.customerSidePages.newOrder;

import graphicAlerts.ClosedPizzeriaAlert;
import graphicElements.customerSidePages.*;
import graphicElements.customerSidePages.loginPages.LoginAccountPage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pizzeria.*;


/**
 * Finestra iniziale che rappresenta la "Home", con le varie possibilità
 * di utilizzo del programma. Si attiva al "run" di GraphicInterface.
 */

public class MenuPage {
	private Scene scene1;

	public void display(Stage window, Pizzeria pizzeria, Customer customer) {
		Label label1 = new Label("Benvenuto");
		Label usernameLabel = new Label("");
		if (customer.isLoggedIn())
		    usernameLabel.setText(customer.getUsername());
        HBox hBox = new HBox(20);
        Button logoutButton = new Button();
        Image image = new Image("graphicElements/images/logout-128.png");
        ImageView imageView1 = new ImageView(image);
        imageView1.setFitHeight(20);
        imageView1.setFitWidth(20);
        logoutButton.setGraphic(imageView1);
        //logoutButton.setMinSize(100, 50);
        logoutButton.setOnAction(e->{
            LoginAccountPage loginAccountPage = new LoginAccountPage();
            customer.setLoggedIn(false);
            loginAccountPage.display(window, pizzeria);

        });
        hBox.getChildren().addAll(label1, usernameLabel,logoutButton);

        hBox.setAlignment(Pos.CENTER);
		StackPane stackPane = new StackPane();
		stackPane.getChildren().addAll(hBox);
		stackPane.getStyleClass().add("stackpane");

		Image image1 = new Image("graphicElements/images/banner_pizza.jpg");
		ImageView imageView = new ImageView(image1);
		imageView.setFitHeight(150);
		imageView.setFitWidth(880);
		imageView.autosize();

		StackPane spazioPane = new StackPane();
		spazioPane.setMinSize(800, 150);
		spazioPane.getChildren().add(imageView);
		spazioPane.setAlignment(Pos.CENTER);

		// makeNewOrder - login - register - myAccount (bloccato se non loggato)

		Button makeOrderButton = new Button("Nuovo Ordine");
        makeOrderButton.prefWidthProperty().bind(window.widthProperty());
        makeOrderButton.prefHeightProperty().bind(window.heightProperty());
        String checkOpen = pizzeria.checkTimeOrder();
		makeOrderButton.setOnAction(e -> {
			pizzeria.updatePizzeriaToday();
			switch (checkOpen) {
				case "OPEN":        // pizzeria aperta
					Order order = pizzeria.initializeNewOrder();
					OrderPage1 orderPage1 = new OrderPage1();
					orderPage1.display(window, order, pizzeria, customer);
					break;
				case "CLOSING":
					ClosedPizzeriaAlert.display(true);        // pizzeria in chiusura
					break;
				default:
					ClosedPizzeriaAlert.display(false);        // pizzeria già chiusa
					break;
			}
		});


		Button chiSiamoButton = new Button("Chi siamo");
		chiSiamoButton.setOnAction(event -> {
		    WhoWeArePage whoWeArePage = new WhoWeArePage();
		    whoWeArePage.display(window,pizzeria, customer);
        });
		chiSiamoButton.prefWidthProperty().bind(window.widthProperty());
        chiSiamoButton.prefHeightProperty().bind(window.heightProperty());
        //chiSiamoButton.setShape(new Rectangle(10,10));

        Button recapOrdiniButton = new Button("Ultimo ordine");
        recapOrdiniButton.prefWidthProperty().bind(window.widthProperty());
        recapOrdiniButton.prefHeightProperty().bind(window.heightProperty());
        recapOrdiniButton.setOnAction(event -> {
			OrderPage3 last = new OrderPage3();
			//TODO: bisogna fare in modo di passare questa scene1 al posto di "null"
			last.display(false, window, pizzeria.CustomerLastOrder(customer), pizzeria, scene1, customer);
		});

        Button altroButton = new Button("Il tuo profilo");
        altroButton.setOnAction(e->{
            YourProfilePage yourProfilePage = new YourProfilePage();
            yourProfilePage.display(window, pizzeria, customer);
        });
		altroButton.prefWidthProperty().bind(window.widthProperty());
        altroButton.prefHeightProperty().bind(window.heightProperty());

		GridPane gridPane = new GridPane();
		gridPane.getChildren().addAll(makeOrderButton, chiSiamoButton, recapOrdiniButton, altroButton);
		GridPane.setConstraints(makeOrderButton, 1, 1);
		GridPane.setConstraints(chiSiamoButton, 2, 1);
		GridPane.setConstraints(recapOrdiniButton, 1, 2);
		GridPane.setConstraints(altroButton, 2, 2);

		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setHgap(10);
		gridPane.setVgap(10);

		VBox layout = new VBox();
		layout.getChildren().addAll(stackPane, imageView, gridPane);
		layout.getStyleClass().add("layout");
        layout.prefWidthProperty().bind(window.widthProperty());
        layout.prefHeightProperty().bind(window.heightProperty());

        layout.setOnKeyPressed(ke -> {
            if(ke.getCode()== KeyCode.RIGHT) {
                chiSiamoButton.fire();
            }if(ke.getCode()==KeyCode.LEFT) {
            	makeOrderButton.fire();
            }
        });

		scene1 = new Scene(layout,800, 600);
		scene1.getStylesheets().addAll(this.getClass().getResource("/graphicElements/cssStyle/menuStyle.css").toExternalForm());
		//window.setResizable(false);
		window.setScene(scene1);
		window.setTitle("Wolf of Pizza");
		//window.getIcons().add(new Image("graphicElements/images/wolf_pizza.png"));
		window.show();
	}
}