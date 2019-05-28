package graphicElements;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pizzeria.Order;
import pizzeria.Pizza;
import pizzeria.Pizzeria;
import pizzeria.Services;

import java.util.ArrayList;

public class PizzeriaOrderPage {

    public static void display(Pizzeria pizzeria, Stage window) {
        ArrayList<Label> orderNameLabels = new ArrayList<>();
        ArrayList<Label> nameLabels = new ArrayList<>();
        ArrayList<Label> toppingLabels = new ArrayList<>();
        ArrayList<Label> priceLabels = new ArrayList<>();

        GridPane gridPane;

        fillLabelsAndButtons(pizzeria, orderNameLabels, nameLabels, toppingLabels, priceLabels);
        gridPane = setGridPaneContraints(pizzeria, orderNameLabels, nameLabels, toppingLabels, priceLabels);

        for (int i=0; i<pizzeria.getOrders().size(); i++) {
            System.out.println(pizzeria.getOrders().get(i));
            for (int j=0; j<pizzeria.getOrders().get(i).getOrderedPizze().size(); j++) {
                System.out.println(pizzeria.getOrders().get(i).getOrderedPizze().get(j).getMaiuscName());
            }
        }

        Scene scene = new Scene(gridPane, 880, 600);
        window.setScene(scene);
        window.show();

    }

    private static void fillLabelsAndButtons (Pizzeria pizzeria, ArrayList<Label> orderNameLabels, ArrayList<Label> nomiLabels, ArrayList<Label> ingrLabels, ArrayList<Label> prezziLabels) {
        for (int i = 0; i<pizzeria.getOrders().size(); i++) {
            orderNameLabels.add(i, new Label(pizzeria.getOrders().get(i).getOrderCode()));
            for (int j=0; j<pizzeria.getOrders().get(i).getOrderedPizze().size(); j++) {
                nomiLabels.add(j, new Label(Services.getCamelName(pizzeria.getOrders().get(i).getOrderedPizze().get(j))));
                ingrLabels.add(j, new Label(pizzeria.getOrders().get(i).getOrderedPizze().get(j).getDescription()));
                prezziLabels.add(i, new Label(pizzeria.getOrders().get(i).getOrderedPizze().get(j).getPrice() + " €"));
                //countPizzeLabels.add(i, new Label());
                //countPizzeLabels.get(i).setText("" + pizzeria.getMenu().get(pizzaMenu.getMaiuscName()).getCount());
            }
        }
    }

    private static GridPane setGridPaneContraints (Pizzeria pizzeria, ArrayList<Label> orderNameLabels, ArrayList<Label> nameLabels, ArrayList<Label> toppingLabels, ArrayList<Label> priceLabels) {
        GridPane gridPane = new GridPane();

        for (int i = 0; i < pizzeria.getOrders().size(); i++) {
            gridPane.getChildren().add(orderNameLabels.get(i));
            for (int j=0; j<pizzeria.getOrders().get(i).getOrderedPizze().size(); j++) {
                gridPane.getChildren().add(nameLabels.get(j));
                gridPane.getChildren().add(toppingLabels.get(j));
                gridPane.getChildren().add(priceLabels.get(j));
            }
        }
        for (int i = 0; i < pizzeria.getOrders().size(); i++) {
            GridPane.setConstraints(orderNameLabels.get(i), 0, i+1);
            for (int j=0; j<pizzeria.getOrders().get(i).getOrderedPizze().size(); j++) {
                GridPane.setConstraints(nameLabels.get(j), 1, j+1);
                GridPane.setConstraints(toppingLabels.get(j), 2, j+1);
                GridPane.setConstraints(priceLabels.get(j), 3, j+1);
            }

        }

        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(1);
        gridPane.setVgap(30);



        return gridPane;
    }
}
