package application.UI;

import application.Data.DataManager;
import application.Regex.RegexProcessor;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainUI {

    private RegexProcessor regexProcessor;
    private DataManager dataManager;

    public MainUI() {
        regexProcessor = new RegexProcessor();
        dataManager = new DataManager();
    }

    public VBox createRegexSection() {
        VBox box = new VBox();
        box.setSpacing(10);

        TextField textField = new TextField();
        textField.setPromptText("Enter text here");
        textField.setId("textField");

        TextField regexField = new TextField();
        regexField.setPromptText("Enter regex pattern here");
        regexField.setId("regexField");

        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setId("resultArea");

        Button searchButton = new Button("Search");
        Button matchButton = new Button("Match");
        Button replaceButton = new Button("Replace");

        TextField replaceField = new TextField();
        replaceField.setPromptText("Enter replacement text here");
        replaceField.setId("replaceField");

        box.getChildren().addAll(textField, regexField, searchButton, matchButton, replaceButton, replaceField, resultArea);

        searchButton.setOnAction(e -> {
            String text = textField.getText();
            String regex = regexField.getText();
            String result = regexProcessor.search(text, regex);
            resultArea.setText(result);
        });

        matchButton.setOnAction(e -> {
            String text = textField.getText();
            String regex = regexField.getText();
            boolean result = regexProcessor.match(text, regex);
            resultArea.setText(result ? "Match found" : "No match");
        });

        replaceButton.setOnAction(e -> {
            String text = textField.getText();
            String regex = regexField.getText();
            String replacement = replaceField.getText();
            String result = regexProcessor.replace(text, regex, replacement);
            resultArea.setText(result);
        });

        return box;
    }

    public VBox createDataSection() {
        VBox box = new VBox();
        box.setSpacing(10);

        Label collectionTypeLabel = new Label("Collection Type:");
        ComboBox<String> collectionTypeComboBox = new ComboBox<>();
        collectionTypeComboBox.setItems(FXCollections.observableArrayList("List", "Set", "Map"));
        collectionTypeComboBox.setValue("List");

        Label currentCollectionLabel = new Label("Current Collection:");
        ComboBox<String> currentCollectionComboBox = new ComboBox<>();
        currentCollectionComboBox.setItems(FXCollections.observableArrayList("List1", "List2", "Set1", "Map1"));
        currentCollectionComboBox.setValue("List1");

        TextArea collectionDisplayArea = new TextArea();
        collectionDisplayArea.setEditable(false);
        collectionDisplayArea.setId("collectionDisplayArea");

        Button addButton = new Button("Add");
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");
        Button clearButton = new Button("Clear");

        Label itemOperationsLabel = new Label("Item Operations:");
        TextField itemField = new TextField();
        itemField.setPromptText("Enter item here");
        itemField.setId("itemField");

        Button addItemButton = new Button("Add to Collection");
        Button updateItemButton = new Button("Update Selected Item");
        Button findItemButton = new Button("Find Item");

        box.getChildren().addAll(
                new HBox(collectionTypeLabel, collectionTypeComboBox),
                new HBox(currentCollectionLabel, currentCollectionComboBox),
                collectionDisplayArea,
                new HBox(addButton, editButton, deleteButton, clearButton),
                itemOperationsLabel,
                itemField,
                new HBox(addItemButton, updateItemButton, findItemButton)
        );

        addButton.setOnAction(e -> {
            String item = itemField.getText().trim();
            if (!item.isEmpty()) {
                dataManager.addToList(item);
                collectionDisplayArea.setText("ArrayList: " + dataManager.getArrayList().toString());
            } else {
                collectionDisplayArea.setText("Please enter an item.");
            }
        });

        editButton.setOnAction(e -> {
            if (!dataManager.getArrayList().isEmpty()) {
                String item = itemField.getText();
                dataManager.updateList(0, item);
                collectionDisplayArea.setText("ArrayList: " + dataManager.getArrayList().toString());
            } else {
                collectionDisplayArea.setText("ArrayList is empty.");
            }
        });

        deleteButton.setOnAction(e -> {
            if (!dataManager.getArrayList().isEmpty()) {
                dataManager.deleteFromList(0);
                collectionDisplayArea.setText("ArrayList: " + dataManager.getArrayList().toString());
            } else {
                collectionDisplayArea.setText("ArrayList is empty.");
            }
        });

        clearButton.setOnAction(e -> {
            dataManager.clearList();
            collectionDisplayArea.setText("ArrayList cleared.");
        });

        addItemButton.setOnAction(e -> {
            String item = itemField.getText();
            dataManager.addToSet(item);
            collectionDisplayArea.setText("HashSet: " + dataManager.getHashSet().toString());
        });

        updateItemButton.setOnAction(e -> {
            String item = itemField.getText();
            dataManager.updateMap("key", item);
            collectionDisplayArea.setText("HashMap: " + dataManager.getHashMap().toString());
        });

        findItemButton.setOnAction(e -> {
            String item = itemField.getText();
            boolean found = dataManager.findInList(item);
            collectionDisplayArea.setText(found ? "Item found in list" : "Item not found in list");
        });

        return box;
    }
}
