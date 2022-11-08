// Darius Karneckij dk910
// Mingchao Huo mh1306

package library.songlibrary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.File;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class Controller {

    @FXML private TableView<Song> table;
    @FXML private TableColumn<Song, String> colName;
    @FXML private TableColumn<Song, String> colArtist;

    private ObservableList<Song> obsList;

    public ArrayList<String> names = new ArrayList<>();
    public ArrayList<String> artists = new ArrayList<>();
    public ArrayList<String> albums = new ArrayList<>();
    public ArrayList<String> years = new ArrayList<>();


    public void start() {

        colName.setCellValueFactory(new PropertyValueFactory<Song, String>("name"));
        colName.setSortType(TableColumn.SortType.ASCENDING);
        colArtist.setCellValueFactory(new PropertyValueFactory<Song, String>("artist"));
        colArtist.setSortType(TableColumn.SortType.ASCENDING);

        obsList = FXCollections.observableArrayList();
        table.setItems(obsList);
        table.getSortOrder().addAll(colName,colArtist);
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> onSelection());

        File file = new File("filename.txt");
        if(file.exists()) {

        }

    }

    @FXML private TextField addName;
    @FXML private TextField addArtist;
    @FXML private TextField addAlbum;
    @FXML private TextField addYear;
    @FXML VBox vbox;

    @FXML
    public void handleAdd(ActionEvent event) {

        String name = addName.getText().trim(); names.add(name);
        String artist = addArtist.getText().trim(); artists.add(artist);
        String album = addAlbum.getText().trim(); albums.add(album);
        String year = addYear.getText().trim(); years.add(year);

        if(name.contains("|") || artist.contains("|") || album.contains("|")) {
            Alert alertDup = new Alert(AlertType.INFORMATION);
            alertDup.setTitle("Prohibited");
            alertDup.setHeaderText("Field contains prohibited character");
            alertDup.showAndWait();
            return;
        }

        if(name == "" || artist == "") {
            Alert alertDup = new Alert(AlertType.INFORMATION);
            alertDup.setTitle("Missing");
            alertDup.setHeaderText("Missing either name or artist");
            alertDup.showAndWait();
            return;
        }

        Stage stage = (Stage) vbox.getScene().getWindow();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Add");
        alert.setHeaderText("Will be adding new song to list");
        Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.CANCEL) {
                return;
            }

        AtomicBoolean dup = new AtomicBoolean(false);
        obsList.forEach((song) -> {
            if(song.getName().equals(name) && song.getArtist().equals(artist)) {
                dup.set(true);
            }
        });
        // If adding duplicate
        if(dup.get()) {
            Alert alertDup = new Alert(AlertType.INFORMATION);
            alertDup.setTitle("Alert!");
            alertDup.setHeaderText("Attempting to add duplicate item");
            alertDup.showAndWait();
            return;
        }

        Song song = new Song(name, artist, addAlbum.getText(),addYear.getText());
        obsList.add(song);
        table.sort();
        table.getSelectionModel().select(song);
    }

    @FXML private TextField editName;
    @FXML private TextField editArtist;
    @FXML private TextField editAlbum;
    @FXML private TextField editYear;

    public void onSelection() {
        Song song = table.getSelectionModel().getSelectedItem();
        if (song == null) { return; }
        editName.setText(song.getName());
        editArtist.setText(song.getArtist());
        editAlbum.setText(song.getAlbum());
        editYear.setText(song.getYear());
    }

    @FXML
    public void handleEdit(ActionEvent event) {

        String name = editName.getText().trim();
        String artist = editArtist.getText().trim();
        String album = editAlbum.getText().trim();
        String year = editYear.getText().trim();

        if(name.contains("|") || artist.contains("|") || album.contains("|")) {
            Alert alertDup = new Alert(AlertType.INFORMATION);
            alertDup.setTitle("Prohibited");
            alertDup.setHeaderText("Field contains prohibited character");
            alertDup.showAndWait();
            return;
        }

        int selectedID = table.getSelectionModel().getSelectedIndex();
        if(selectedID < 0) {return;}

        Stage stage = (Stage) vbox.getScene().getWindow();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Add");
        alert.setHeaderText("Will be editing song");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.CANCEL) {
            return;
        }

        AtomicBoolean dup = new AtomicBoolean(false);
        obsList.forEach((song) -> {
            if(song.getName().equals(name) && song.getArtist().equals(artist)) {
                dup.set(true);
            }
        });
        // If adding duplicate
        if(dup.get()) {
            Alert alertDup = new Alert(AlertType.INFORMATION);
            alertDup.setTitle("Alert!");
            alertDup.setHeaderText("Attempting to add duplicate item");
            alertDup.showAndWait();
            return;
        }

        // If you hit edit without selection
        if(selectedID < 0) {return;}

        Song newSong = new Song(name, artist, album, year);
        table.getItems().remove(selectedID);
        obsList.add(newSong);
    }

    @FXML
    public void handleDelete(ActionEvent event) {

        Stage stage = (Stage) vbox.getScene().getWindow();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Song will be deleted from the list");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.CANCEL) {
            return;
        }

        int selectedID = table.getSelectionModel().getSelectedIndex();

        // If you hit delete without selection
        if(selectedID < 0) {return;}

        table.getItems().remove(selectedID);

        if(selectedID > obsList.size()) {
            selectedID -= 1;
        }
        table.getSelectionModel().select(selectedID);
    }
}
