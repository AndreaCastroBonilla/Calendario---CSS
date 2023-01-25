package dad.custom.components;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class CustomComponent extends GridPane implements Initializable {

	private static final DateFormat FORMATTER = new SimpleDateFormat("MMMM");

	// model
	private IntegerProperty month = new SimpleIntegerProperty();
	private IntegerProperty year = new SimpleIntegerProperty();

	// view
	@FXML
	private Label monthLabel;

	@FXML
	private List<Label> daysLabelList;

	public CustomComponent() {
		super();
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CustomComponent.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.month.addListener((o, ov, nv) -> onDateChanged(o, ov, nv));
		this.year.addListener((o, ov, nv) -> onDateChanged(o, ov, nv));

	}

	private void onDateChanged(ObservableValue<? extends Number> o, Number ov, Number nv) {

		int first = DateUtils.firstDay(year.get(), month.get()) - 1;
		int last = DateUtils.lastDay(year.get(), month.get());
		for (int i = 0; i < first; i++) {
			daysLabelList.get(i).setText("");
			daysLabelList.get(i).getStyleClass().remove("today");
		}
		for (int i = first, j = 1; i < first + last; i++, j++) {
			daysLabelList.get(i).setText("" + j);
			if (LocalDate.of(year.get(), month.get(), j).equals(LocalDate.now())) {
				daysLabelList.get(i).getStyleClass().add("today");
			} else {
				daysLabelList.get(i).getStyleClass().remove("today");
			}
		}
		for (int i = first + last; i < daysLabelList.size(); i++) {
			daysLabelList.get(i).setText("");
			daysLabelList.get(i).getStyleClass().remove("today");
		}
		Date day = DateUtils.newDate(year.get(), month.get(), 1);
		monthLabel.setText(FORMATTER.format(day));

	}

	public final IntegerProperty monthProperty() {
		return this.month;
	}

	public final int getMonth() {
		return this.monthProperty().get();
	}

	public final void setMonth(final int month) {
		this.monthProperty().set(month);
	}

	public final IntegerProperty yearProperty() {
		return this.year;
	}

	public final int getYear() {
		return this.yearProperty().get();
	}

	public final void setYear(final int year) {
		this.yearProperty().set(year);
	}

}
