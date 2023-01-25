package dad.calendar;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import dad.custom.components.CustomComponent;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class CalendarController implements Initializable {

	private static final int COLUMNS = 4;

	// model

	private IntegerProperty year = new SimpleIntegerProperty();

	// view
	private CustomComponent[] calendarComponents;

	@FXML
	private BorderPane view;

	@FXML
	private Label yearLabel;

	@FXML
	private GridPane monthPane;

	@FXML
	private Button previousButton, nextButton;

	public CalendarController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CalendarView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.calendarComponents = new CustomComponent[12];
		for (int month = 0; month < 12; month++) {
			int col = month % COLUMNS;
			int row = month / COLUMNS;
			calendarComponents[month] = new CustomComponent();
			calendarComponents[month].setMonth(month + 1);
			calendarComponents[month].yearProperty().bind(year);
			monthPane.add(calendarComponents[month], col, row);
		}

		this.yearLabel.textProperty().bind(this.year.asString());

		this.year.set(LocalDate.now().getYear());

	}

	@FXML
	void onPreviousAction(ActionEvent e) {
		int width = (int) monthPane.getWidth();
		int height = (int) monthPane.getHeight();

		SnapshotParameters param = new SnapshotParameters();
		param.setFill(Color.TRANSPARENT);

		Image prevCalendarSnapshot = monthPane.snapshot(param, new WritableImage(width, height));
		ImageView prevImageView = new ImageView(prevCalendarSnapshot);

		year.set(year.get() - 1);

		Image newCalendarSnapshot = monthPane.snapshot(param, new WritableImage(width, height));
		ImageView newImageView = new ImageView(newCalendarSnapshot);

		Pane pane = new Pane(newImageView, prevImageView);
		pane.setClip(new Rectangle(width, height));

		view.setCenter(pane);

		Duration duration = Duration.seconds(0.5);

		TranslateTransition movePrev = new TranslateTransition();
		movePrev.setNode(prevImageView);
		movePrev.setDuration(duration);
		movePrev.setFromX(0);
		movePrev.setToX(width);

		TranslateTransition moveNew = new TranslateTransition();
		moveNew.setNode(newImageView);
		moveNew.setDuration(duration);
		moveNew.setFromX(-width);
		moveNew.setToX(0);

		ParallelTransition allTransitions = new ParallelTransition();
		allTransitions.setInterpolator(Interpolator.EASE_OUT);
		allTransitions.getChildren().addAll(movePrev, moveNew);
		allTransitions.setOnFinished(p -> view.setCenter(monthPane));
		allTransitions.play();
	}

	@FXML
	void onNextAction(ActionEvent event) {
		int width = (int) monthPane.getWidth();
		int height = (int) monthPane.getHeight();

		SnapshotParameters param = new SnapshotParameters();
		param.setFill(Color.TRANSPARENT);

		Image prevCalendarSnapshot = monthPane.snapshot(param, new WritableImage(width, height));
		ImageView prevImageView = new ImageView(prevCalendarSnapshot);

		year.set(year.get() + 1);

		Image newCalendarSnapshot = monthPane.snapshot(param, new WritableImage(width, height));
		ImageView newImageView = new ImageView(newCalendarSnapshot);

		Pane pane = new Pane(newImageView, prevImageView);
		pane.setClip(new Rectangle(width, height));

		view.setCenter(pane);

		Duration duration = Duration.seconds(0.5);

		TranslateTransition movePrev = new TranslateTransition();
		movePrev.setNode(prevImageView);
		movePrev.setDuration(duration);
		movePrev.setFromX(0);
		movePrev.setToX(-width);

		TranslateTransition moveNew = new TranslateTransition();
		moveNew.setNode(newImageView);
		moveNew.setDuration(duration);
		moveNew.setFromX(width);
		moveNew.setToX(0);

		ParallelTransition allTransitions = new ParallelTransition();
		allTransitions.setInterpolator(Interpolator.EASE_OUT);
		allTransitions.getChildren().addAll(movePrev, moveNew);
		allTransitions.setOnFinished(p -> view.setCenter(monthPane));
		allTransitions.play();
	}

	public BorderPane getView() {
		return view;
	}

}
