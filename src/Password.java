

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class Password implements Initializable{

	@FXML
    private Spinner<Integer> spinner;
	
    @FXML
    private Button btnGenerate;
	
	private int spinnervalue = 10;
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		SpinnerValueFactory<Integer>valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 25, spinnervalue);
		spinner.setValueFactory(valueFactory);
	}

	public void generateButton(ActionEvent event) {
		
	}
}
