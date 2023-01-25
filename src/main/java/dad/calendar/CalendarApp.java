package dad.calendar;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class CalendarApp extends Application {

	public static Stage primaryStage;

	private CalendarController calendarController;

	@Override
	public void start(Stage primaryStage) throws Exception {

		calendarController = new CalendarController();

		CalendarApp.primaryStage = primaryStage;

		primaryStage.setTitle("Calendario");
		primaryStage.getIcons().add(new Image("/images/calendar-16x16.png"));
		primaryStage.getIcons().add(new Image("/images/calendar-32x32.png"));
		primaryStage.getIcons().add(new Image("/images/calendar-64x64.png"));
		primaryStage.setScene(new Scene(calendarController.getView()));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
