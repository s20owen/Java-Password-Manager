

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/*
 * Features TO-DO see change log changelog.txt
 */

public class Controller extends ListView<String> implements Initializable {
	
	  @FXML
	  private ListView<String> list;

	  @FXML
	  private Pane tabbar;
	  
	  @FXML
	  private Button btnsave;

	  @FXML
	  private TextField tfaddress;
	  
	  @FXML
	  private TextField lbltitle;

	  @FXML
	  private TextField lblpassword;

	  @FXML
	  private TextField lblusername;

	  @FXML
	  private TextArea tfnotes;

	  @FXML
	  private Button btncopyusername;
	  
	  @FXML
	  private Button btncopypassword;

	  @FXML
	  private Button btndelete;

	  @FXML
	  private ImageView iconimage;
	  
	  
	  @FXML
	  private Button btnedit;
	  
	  @FXML
	  private MenuItem btnabout;
	  
	  @FXML
	  private Button btnAdd;
	  
	  private Image image;
	 
	  private static String imgurl = "https://logo.clearbit.com/";
	  private static String imgdefault = "images/profile.png";
	  
	  private  String name;
	  
	  private JSONParser jsonparser = new JSONParser();
		
	  private static JSONObject itemdetails = new JSONObject();
		
	  private static JSONObject item = new JSONObject();
		
	  private static JSONArray jsonList = new JSONArray();
	  
	  private ObservableList<String> listView = FXCollections.observableArrayList();
	  
	  private ObservableList<String> titleArray = FXCollections.observableArrayList();
	  private ObservableList<String> usernameArray = FXCollections.observableArrayList();
	  private ObservableList<String> passwordArray = FXCollections.observableArrayList();
	  private ObservableList<String> websiteArray = FXCollections.observableArrayList();
	  private ObservableList<String> notesArray = FXCollections.observableArrayList();
	  private ObservableList<String> imageArray = FXCollections.observableArrayList();
	  
	  private boolean editing;
	  
	  /*
	   *  Add new entry button
	   */
	  public void btnAdd(ActionEvent event) {
		  if(list.isFocused())
		  {
			list.getSelectionModel().clearSelection();  
		  }
		  clearText();
	  }
	  
	  /*
	   *  About button
	   */
	  public void btnAbout(ActionEvent event) {
		  AlertBox.display("About", "Version 1.0");
	  }
	  
	  /*
	   *  Save text fields to file & add to list view
	   */
	  public void btnClicked(ActionEvent event) throws IOException, ParseException {
		   
		  if(lbltitle.getText().isBlank() || lblpassword.getText().isBlank())
		  {
			  AlertBox.display("Warning", "One or more fields are empty");
		  }
		  else {
			  //listView.add(lbltitle.getText());
			  //list.setItems(listView);
			  
			  titleArray.add(lbltitle.getText());
			  usernameArray.add(lblusername.getText());
			  passwordArray.add(lblpassword.getText());
			  websiteArray.add(tfaddress.getText());
			  notesArray.add(tfnotes.getText());
			  imageArray.add(image.getUrl());
			  //System.out.println("item details before add " + itemdetails);
			
			  saveJSON();
		  }
		 
	  }
	  
	  
	  /*
	   * edit button
	   */
	  public void btnEdit(ActionEvent event) {
		  editing = true;
		  btndelete.setDisable(false);
		  clearText();
	  }
	  
	  /*
	   * Delete button
	   */
	  public void btnDelete(ActionEvent event) throws IOException {
		  
		 
		  titleArray.remove(list.getSelectionModel().getSelectedIndex());
		  usernameArray.remove(list.getSelectionModel().getSelectedIndex());
		  passwordArray.remove(list.getSelectionModel().getSelectedIndex());
		  websiteArray.remove(list.getSelectionModel().getSelectedIndex());
		  notesArray.remove(list.getSelectionModel().getSelectedIndex());
		  imageArray.remove(list.getSelectionModel().getSelectedIndex());
		  jsonList.remove(list.getSelectionModel().getSelectedIndex());
		  listView.remove(list.getSelectionModel().getSelectedItem());
		  
		  writeJSON();
		  btndelete.setDisable(true);
		  editing = false;
	  }
	  
	  /*
	   * Password Dialog Box
	   */
	  public void passwordDialog(ActionEvent event) throws IOException {
		  
		  FXMLLoader loader = new FXMLLoader(getClass().getResource("Password.fxml"));
		  Parent root = loader.load();
		  
		  Password passwordScene = loader.getController();
		  Stage stage = new Stage();
		  stage.setScene(new Scene(root));
		  stage.setTitle("Generate Password");
		  stage.showAndWait();
	  }
	  
	  /*
	   * Clear Text when list item is deleted
	   */
	  public void clearText() {
		  lbltitle.setText("");
		  lblpassword.setText("");
		  lblusername.setText("");
		  tfaddress.setText("");
		  tfnotes.setText("");
		  iconimage.setImage(null);
	  }
	  
	  /*
	  * Set text when list item is selected
	  */
	  public void setText() {
		  if(editing) {
			  clearText();
		  }
		  else {
			  image = new Image(imageArray.get(list.getSelectionModel().getSelectedIndex())); 
			  lbltitle.setText(titleArray.get(list.getSelectionModel().getSelectedIndex()));
			  lblpassword.setText(passwordArray.get(list.getSelectionModel().getSelectedIndex()));
			  lblusername.setText(usernameArray.get(list.getSelectionModel().getSelectedIndex()));
			  tfaddress.setText(websiteArray.get(list.getSelectionModel().getSelectedIndex()));
			  tfnotes.setText(notesArray.get(list.getSelectionModel().getSelectedIndex()));
			  iconimage.setImage(image);
		  }
		  
	  }
	  
	  /*
	   * JSON write after save
	   */
	  @SuppressWarnings("unchecked")
	  public void saveJSON() throws IOException, ParseException {
		  
		  itemdetails.put("Title", lbltitle.getText());
		  itemdetails.put("Username", lblusername.getText());
		  itemdetails.put("Password", lblpassword.getText());
		  itemdetails.put("Website", tfaddress.getText());
		  itemdetails.put("Icon", image.getUrl());
		  itemdetails.put("Notes", tfnotes.getText());
		  
		  item.put("Item", itemdetails);
		  jsonList.add(item);
		  
		 
		  try(FileWriter file = new FileWriter("items.json")){
			  file.write(jsonList.toJSONString());
			  file.flush();
			  //clearText();
			  itemdetails.clear();
			  jsonList.clear();
			  listView.clear();
			  readJSON();
			  clearText();
			 
			  
		  }catch (IOException e) {
			  e.printStackTrace();
		  }
		  
	  }
	  
	  /*
	   * JSON write after delete
	   */
	  @SuppressWarnings("unchecked")
	  public void writeJSON() throws IOException {
		  
		  if(lbltitle.getText() == "") {
			 
			
		  }else {
			  itemdetails.put("Title", lbltitle.getText());
			  itemdetails.put("Username", lblusername.getText());
			  itemdetails.put("Password", lblpassword.getText());
			  itemdetails.put("Website", tfaddress.getText());
			  itemdetails.put("Icon", image.getUrl());
			  itemdetails.put("Notes", tfnotes.getText());
			 
			  item.put("Item", itemdetails);
			  jsonList.add(item);
			  System.out.println("File saved " + jsonList);
			  System.out.println("title Array" + titleArray);
			  
		  }
		  
		  try(FileWriter file = new FileWriter("items.json")){
			  file.write(jsonList.toJSONString());
			  file.flush();
			  file.close();
			  clearText();
			  
		  }catch (IOException e) {
			  e.printStackTrace();
		  }
		  
	  }
	  
	/*
	 * read JSON File
	 */
	@SuppressWarnings("unchecked")
	public void readJSON() throws FileNotFoundException, IOException, ParseException {
		try (FileReader reader = new FileReader("items.json"))
		{
			Object obj = jsonparser.parse(reader);
			jsonList = (JSONArray) obj;
			 
			System.out.println("Json array " + jsonList);
			jsonList.forEach(item -> parseItemObject((JSONObject) item));
		}
		catch (FileNotFoundException e) {
			System.out.println(e);
		}
		catch (IOException e) {
			System.out.println(e);
		}
		catch (ParseException e) {
			System.out.println(e);
		}
	}
	
	/*
	 * parse JSON objects
	 */
	private void parseItemObject(JSONObject item) {
		JSONObject itemObject = (JSONObject) item.get("Item");
		
		
		String title = (String) itemObject.get("Title");
		String username = (String) itemObject.get("Username");
		String password = (String) itemObject.get("Password");
		String website = (String) itemObject.get("Website");
		String icon = (String) itemObject.get("Icon");
		String notes = (String) itemObject.get("Notes");
		
		name = title;
		listView.add(name);
		list.setItems(listView);
		
		titleArray.add(title);
		usernameArray.add(username);
		passwordArray.add(password);
		websiteArray.add(website);
		notesArray.add(notes);
		imageArray.add(icon);
		
	}
	
	/*
	 *  initialize check if file exists
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		iconimage.setCache(true);
		btndelete.setDisable(true);
		
		
		list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				
				setText();
			}
			
		});
		
		// loads icon on title label focus property
		lbltitle.focusedProperty().addListener((ChangeListener<? super Boolean>) new ChangeListener<Boolean>()
		{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		        if (newPropertyValue)
		        {
		            //System.out.println("Textfield on focus");
		        }
		        else
		        {
		        	image = new Image(imgurl + lbltitle.getText() + ".com"); 
		  		  
					if(image.isError()) {
						//System.out.println("error loading img " + image.getUrl());
						image = new Image(imgdefault);
						
						iconimage.setImage(image);
					}else {
						iconimage.setImage(image);
						//System.out.println("image successfully loaded from " + image.getUrl());
					}
		            //System.out.println("Textfield out of focus");
		        }
		    }
		});
		
		// if file exists 
		File f = new File("items.json");
		if(f.exists())
		{
			//System.out.println("file exists " + f.getAbsolutePath());
			try {
				readJSON();
				
						/*
						 * list view cell factory needs adjusting
						 */
				 		list.setCellFactory(listView -> new ListCell<String>() {
			        	private ImageView imageView = new ImageView();
			        	
			        	 @Override
			        	 public void updateItem(String name, boolean empty)
			        	 {
			        		 super.updateItem(name, empty);
			        		if(empty)
			        		{
			        			setText(null);
			        			setGraphic(null);
			        		}
			        		else
			        		{

			        			Image image = new Image(imgdefault);
				        		imageView.setPreserveRatio(true);
				                imageView.setFitHeight(25);
				        		imageView.setImage(image);
				        		setText(name);
				        		setGraphic(imageView);

			        		}
			        	 }
			         });
				 
			}
			catch (ParseException | IOException e) {
				System.out.println(e);
			}
			
		}
		// if file doesn't exist
		else {
			//System.out.println("file does not exist");
			AlertBox.display("Warning", "It seems you don't have any passwords..Yet!");
			/*
			 * if file doesn't exist, make an empty json array and write the file
			 */
			try {
				jsonList.add("");
				jsonList.remove(0);
				FileWriter file = new FileWriter("items.json");
				file.write(jsonList.toJSONString());
				file.flush();
				file.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			  
		}
            
        
		
	}
	

}
