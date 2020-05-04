package a4_dohuu;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import javafx.beans.Observable;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Khang Do, 2020
 */
public class GameCollectionSaverFXMLController implements Initializable {

    @FXML
    private ListView lstGameCollection, lstNumberPlayer;

    @FXML
    private ComboBox<GameRecord> ddlGameName;

    @FXML
    private Button btnLoadItem, btnSave;

    @FXML
    private TextArea txtGameRecord;

    private String directoryName = "src/a4_dohuu/game_collections";

    /**
     * Returns a list of file in a sub-directory to display in ListView
     *
     * @param directoryName path of the directory
     * @return list file to be displayed
     * @throws IOException if file is not found
     */
    public ObservableList<File> listFilesAndFolder(String directoryName)
            throws IOException {

        File directory = new File(directoryName);

        //Update the file in sub-directory into ListView
        //Only file with the extension is .csv
        File[] fList = directory.listFiles(new FilenameFilter() {
            public boolean accept(File directory, String name) {
                return name.toLowerCase().endsWith(".csv");
            }
        });
        ObservableList<File> obsFileList = FXCollections.
                observableArrayList(fList);

        //Display only the file name instead of a full path 
        lstGameCollection.setCellFactory(lv -> new ListCell<File>() {
            @Override
            protected void updateItem(File file, boolean empty) {
                super.updateItem(file, empty);
                setText(file == null ? null : file.getName());
            }
        });
        return obsFileList;
    }

    public void saveGameRecord(String fileName, GameRecord game) throws
            IOException {
        File file = new File(fileName);
        try ( PrintWriter fileOut = new PrintWriter(new BufferedWriter(new 
        FileWriter(file, true)))) {

            fileOut.println(game.toString());
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO            
            lstGameCollection.setItems(listFilesAndFolder(directoryName));
        } catch (IOException ex) {
            Logger.getLogger(GameCollectionSaverFXMLController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

        btnLoadItem.setOnMouseClicked((MouseEvent e) -> {
            ArrayList<GameRecord> gameList = new ArrayList<>();
            File fileName = (File) lstGameCollection.getSelectionModel()
                    .getSelectedItem();

            //Store any GameRecord to an ArrayList
            ArrayList<String> temp = new ArrayList<>();
            if (fileName.exists()) {
                Scanner sc;
                try {
                    sc = new Scanner(fileName);
                    while (sc.hasNextLine()) {
                        Scanner scLine = new Scanner(sc.nextLine());
                        scLine.useDelimiter(",");
                        while (scLine.hasNext()) {
                            temp.add(scLine.next());
                        }
                        //Create a game record to store the file input
                        GameRecord game = new GameRecord();
                        game.setGameName(temp.get(0));
                        game.setMinNumPlayer(Integer.parseInt(temp.get(1)));
                        game.setMaxNumPlayer(Integer.parseInt(temp.get(2)));
                        game.setPrice(Double.parseDouble(temp.get(3)));
                        game.setDescription(temp.get(4));
                        gameList.add(game);
                        temp.removeAll(temp);
                    }
                    ObservableList<Integer> items = FXCollections
                            .observableArrayList(
                                    1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
                    lstNumberPlayer.setItems(items);
                    lstNumberPlayer.getSelectionModel().setSelectionMode
        (SelectionMode.MULTIPLE);

                    //Adds a listener to get the game record that match 
                    //the users' interest
                    ObservableList<Integer> numList = lstNumberPlayer
                            .getSelectionModel().getSelectedItems();
                    numList.addListener(new InvalidationListener() {
                        @Override
                        public void invalidated(Observable obs) {
                            ObservableList<Integer> numIndices
                                    = lstNumberPlayer.getSelectionModel()
                                            .getSelectedIndices();
                            int min = 10;
                            int max = 1;
                            for (int i : numIndices) {
                                if (items.get(i) > max) {
                                    max = items.get(i);
                                }
                                if (items.get(i) < min) {
                                    min = items.get(i);
                                }
                            }
                            System.out.println("Max: " + max + " Min: " + min);
                            ObservableList<GameRecord> gameDdl = FXCollections
                                    .observableArrayList();
                            for (GameRecord list : gameList) {
                                if (list.getMinNumPlayer() <= min && list
                                        .getMaxNumPlayer() >= max) {
                                    gameDdl.add(list);
                                }
                            }
                            //Display a notice that there is no game fit the 
                            //criteria and clear the drop-down list and the 
                            //text area
                            if (gameDdl.size() == 0) {
                                Alert alert = new Alert(Alert.AlertType
                                        .INFORMATION);
                                alert.setContentText("There is no game fit "
                                        + "your criteria!");
                                alert.showAndWait();
                                ddlGameName.setButtonCell(new ListCell
                                        <GameRecord>() {
                                    @Override
                                    protected void updateItem(GameRecord game,
                                            boolean empty) {

                                        super.updateItem(game, empty);
                                        setText(null);
                                    }
                                });
                                txtGameRecord.clear();
                            }
                            //Set the drop-down list with the list of 
                            //game records
                            ddlGameName.setItems(gameDdl);
                            //Set the popup list with game name only
                            ddlGameName.setCellFactory(lv -> new ListCell
                                    <GameRecord>() {
                                @Override
                                protected void updateItem(GameRecord game, 
                                        boolean empty) {
                                    super.updateItem(game, empty);
                                    setText(game == null ? null : game
                                            .getGameName());
                                }
                            });

                            //Set the button area with the game name only//                            
                            ddlGameName.setButtonCell(new ListCell
                                    <GameRecord>() {
                                @Override
                                protected void updateItem(GameRecord game, 
                                        boolean empty) {
                                    game = (GameRecord) 
                                            ddlGameName.getSelectionModel()
                                                    .getSelectedItem();
                                    super.updateItem(game, empty);
                                    //SetText null if the selected item is null
                                    setText(game == null ? null : 
                                            game.getGameName());
                                }
                            });

                            //Display the selected game record message in 
                            //the text area
                            //Show the Save button
                            ddlGameName.setOnAction(e -> {
                                GameRecord selectedGame = (GameRecord) 
                                        ddlGameName.getSelectionModel()
                                                .getSelectedItem(); 
                                //SetText null if the selected item is null
                                txtGameRecord.setText(selectedGame == null 
                                        ? null : selectedGame.toString());                                                                
                                
                                
                                btnSave.setOnAction(eh -> {
                                    try {
                                        String fileName = String.format
        ("mygamesfor%dto%dplayers.txt", selectedGame.getMinNumPlayer(), 
                selectedGame.getMaxNumPlayer());
                                String filePath = String.format
        ("src/a4_dohuu/game_collections/%s", fileName);
                                        //Displays a confirmation message to 
                                        //save the file and clear the text area
                                        //and drop-down list.
                                        Alert alert = 
                                                new Alert(Alert.AlertType
                                                        .CONFIRMATION, 
                                                        "Are you sure to save "
                                                                + "the file " 
                                                                + fileName + 
                                                                " ?", 
                                                        ButtonType.YES, 
                                                        ButtonType.NO);
                                        alert.setTitle("Save file");
                                        alert.setHeaderText(null);
                                        Optional<ButtonType> result = 
                                                alert.showAndWait();

                                        if (result.get() == ButtonType.YES) {
                                            saveGameRecord(filePath, 
                                                    selectedGame);
                                            ddlGameName.setButtonCell(
                                                    new ListCell<GameRecord>() {
                                                @Override
                                                protected void updateItem(
                                                        GameRecord game, 
                                                        boolean empty) {

                                                    super.updateItem(game, 
                                                            empty);
                                                    setText(null);
                                                }
                                            });
                                            ddlGameName.setCellFactory(lv -> 
                                                    new ListCell<GameRecord>() {
                                                @Override
                                                protected void updateItem(
                                                        GameRecord game, 
                                                        boolean empty) {
                                                    super.updateItem(game, 
                                                            empty);
                                                    //Keep the drop-down list
                                                    setText(game == null ? 
                                                            null : 
                                                            game.getGameName());
                                                }
                                            });
                                            //Clear the drop-down list
//                                            gameDdl.removeAll(gameDdl);
                                            txtGameRecord.clear();
                                        }
                                    } catch (IOException ex) {
                                        Logger.getLogger
        (GameCollectionSaverFXMLController.class.getName())
                                                .log(Level.SEVERE, null, ex);
                                    }

                                });
                            });
                        }
                    });
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(GameCollectionSaverFXMLController.class
                            .getName()).log(Level.SEVERE, null, ex);
                    //Display a friendly error message that let user choose
                    //another file
                } catch (IllegalArgumentException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("File Content Error");
                    alert.setHeaderText("Game Record Not Found");
                    alert.setContentText(fileName.getName() + " doesn't "
                            + "contain any Game Record. "
                            + "Please select another file!");
                    alert.showAndWait();
                }
            }
        });
    }
}
