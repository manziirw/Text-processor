package application.UI;

import application.Data.DataManager;
import application.Regex.RegexProcessor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainUI {

    private RegexProcessor regexProcessor;
    private DataManager dataManager;
    private ListView<String> listView;

    public MainUI() {
        regexProcessor = new RegexProcessor();
        dataManager = new DataManager();
        listView = new ListView<>();
    }

    public VBox createRegexSection() {
        VBox box = new VBox();
        box.setSpacing(10);

        // Add heading for the text processor section
        Label headingLabel = new Label("Text Processor");
        headingLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;"); // Optional: Style the heading

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

        box.getChildren().addAll(headingLabel, textField, regexField, searchButton, matchButton, replaceButton, replaceField, resultArea);

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

        // Add heading for the data management section
        Label headingLabel = new Label("Data Collection");
        headingLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;"); // Optional: Style the heading

        Label collectionTypeLabel = new Label("Collection Type:");
        ComboBox<String> collectionTypeComboBox = new ComboBox<>();
        collectionTypeComboBox.setItems(FXCollections.observableArrayList("List", "Set", "Map"));
        collectionTypeComboBox.setValue("List");

        TextArea collectionDisplayArea = new TextArea();
        collectionDisplayArea.setEditable(false);
        collectionDisplayArea.setId("collectionDisplayArea");

        listView.setPrefHeight(100);

        Button addButton = new Button("Add");
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");
        Button clearButton = new Button("Clear");

        Label itemOperationsLabel = new Label("Item Operations:");
        TextField itemField = new TextField();
        itemField.setPromptText("Enter item here");
        itemField.setId("itemField");

        Button findItemButton = new Button("Find Item");

        box.getChildren().addAll(
                headingLabel,  // Add the heading to the VBox
                new HBox(collectionTypeLabel, collectionTypeComboBox),
                collectionDisplayArea,
                listView,
                new HBox(addButton, editButton, deleteButton, clearButton),
                itemOperationsLabel,
                itemField,
                new HBox(findItemButton)
        );

        collectionTypeComboBox.setOnAction(e -> {
            String selectedType = collectionTypeComboBox.getValue();
            updateCollectionDisplay(selectedType, collectionDisplayArea);
        });

        addButton.setOnAction(e -> {
            String item = itemField.getText().trim();
            if (!item.isEmpty()) {
                String selectedType = collectionTypeComboBox.getValue();
                if (selectedType.equals("List")) {
                    dataManager.addToList(item);
                    updateCollectionDisplay(selectedType, collectionDisplayArea);
                } else if (selectedType.equals("Set")) {
                    if (dataManager.findInSet(item)) {
                        collectionDisplayArea.setText("Duplicate items are not allowed in a Set.");
                    } else {
                        dataManager.addToSet(item);
                        updateCollectionDisplay(selectedType, collectionDisplayArea);
                    }
                } else if (selectedType.equals("Map")) {
                    String[] keyValue = item.split("=");
                    if (keyValue.length == 2) {
                        dataManager.addToMap(keyValue[0], keyValue[1]);
                        updateCollectionDisplay(selectedType, collectionDisplayArea);
                    } else {
                        collectionDisplayArea.setText("Invalid input format for Map. Use key=value.");
                    }
                }
            } else {
                collectionDisplayArea.setText("Please enter an item.");
            }
        });

        editButton.setOnAction(e -> {
            String item = itemField.getText().trim();
            String selectedType = collectionTypeComboBox.getValue();
            String selectedItem = listView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                if (selectedType.equals("List")) {
                    int index = dataManager.getArrayList().indexOf(selectedItem);
                    dataManager.updateList(index, item);
                    updateCollectionDisplay(selectedType, collectionDisplayArea);
                } else if (selectedType.equals("Set")) {
                    dataManager.updateSet(selectedItem, item);
                    updateCollectionDisplay(selectedType, collectionDisplayArea);
                } else if (selectedType.equals("Map")) {
                    String[] keyValue = item.split("=");
                    if (keyValue.length == 2) {
                        dataManager.updateMap(keyValue[0], keyValue[1]);
                        updateCollectionDisplay(selectedType, collectionDisplayArea);
                    } else {
                        collectionDisplayArea.setText("Invalid input format for Map. Use key=value.");
                    }
                }
            } else {
                collectionDisplayArea.setText("No item selected for editing.");
            }
        });

        deleteButton.setOnAction(e -> {
            String selectedType = collectionTypeComboBox.getValue();
            String selectedItem = listView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                if (selectedType.equals("List")) {
                    int index = dataManager.getArrayList().indexOf(selectedItem);
                    dataManager.deleteFromList(index);
                    updateCollectionDisplay(selectedType, collectionDisplayArea);
                } else if (selectedType.equals("Set")) {
                    dataManager.deleteFromSet(selectedItem);
                    updateCollectionDisplay(selectedType, collectionDisplayArea);
                } else if (selectedType.equals("Map")) {
                    String[] keyValue = selectedItem.split("=");
                    if (keyValue.length == 2) {
                        dataManager.deleteFromMap(keyValue[0]);
                        updateCollectionDisplay(selectedType, collectionDisplayArea);
                    }
                }
            } else {
                collectionDisplayArea.setText("No item selected for deletion.");
            }
        });

        clearButton.setOnAction(e -> {
            String selectedType = collectionTypeComboBox.getValue();
            if (selectedType.equals("List")) {
                dataManager.clearList();
            } else if (selectedType.equals("Set")) {
                dataManager.clearSet();
            } else if (selectedType.equals("Map")) {
                dataManager.clearMap();
            }
            updateCollectionDisplay(selectedType, collectionDisplayArea);
        });

        findItemButton.setOnAction(e -> {
            String item = itemField.getText();
            String selectedType = collectionTypeComboBox.getValue();
            boolean found = false;
            if (selectedType.equals("List")) {
                found = dataManager.findInList(item);
            } else if (selectedType.equals("Set")) {
                found = dataManager.findInSet(item);
            } else if (selectedType.equals("Map")) {
                found = dataManager.findInMap(item);
            }
            collectionDisplayArea.setText(found ? "Item found in " + selectedType : "Item not found in " + selectedType);
        });

        return box;
    }

    private void updateCollectionDisplay(String collectionType, TextArea displayArea) {
        ObservableList<String> items = FXCollections.observableArrayList();
        switch (collectionType) {
            case "List":
                items.addAll(dataManager.getArrayList());
                displayArea.setText("ArrayList: " + dataManager.getArrayList().toString());
                break;
            case "Set":
                items.addAll(dataManager.getHashSet());
                displayArea.setText("HashSet: " + dataManager.getHashSet().toString());
                break;
            case "Map":
                dataManager.getHashMap().forEach((key, value) -> items.add(key + "=" + value));
                displayArea.setText("HashMap: " + dataManager.getHashMap().toString());
                break;
            default:
                displayArea.setText("");
                break;
        }
        listView.setItems(items);
    }
}
