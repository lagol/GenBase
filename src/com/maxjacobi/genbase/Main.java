package com.maxjacobi.genbase;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main extends Application {

	Scene DefaultScene;
	Stage primaryStage = new Stage();
	GridPane MainContent = new GridPane();
	String ProgramVersion = new String("0.0.0");
	Label MainContentHeading = new Label();
	Label CopyrightLabel = new Label("Copyright © 2022 Max Jacobi");
	Label InfoLabelFooter = new Label("Welcome to Genbase " + ProgramVersion);
	MenuBar PrimaryMenuBar = new MenuBar();
	String LoadedFilePath = new String();
	String GedcomData = new String();
	String GedcomHEADRecord = new String();

	TreeItem GedcomHeaderHEAD = new TreeItem("HEAD");
	TreeItem GedcomHeaderHEADSOUR = new TreeItem("SOUR");
	TreeItem GedcomHeaderHEADSOURVERS = new TreeItem("VERS");
	TreeItem GedcomHeaderHEADSOURNAME = new TreeItem("NAME");
	TreeItem GedcomHeaderHEADSOURCORP = new TreeItem("CORP");
	TreeItem GedcomHeaderHEADSOURDATA = new TreeItem("DATA");
	TreeItem GedcomHeaderHEADSOURDATADATE = new TreeItem("DATE");
	TreeItem GedcomHeaderHEADSOURDATACOPR = new TreeItem("COPR");
	TreeItem GedcomHeaderHEADDEST = new TreeItem("DEST");
	TreeItem GedcomHeaderHEADDATE = new TreeItem("DATE");
	TreeItem GedcomHeaderHEADDATETIME = new TreeItem("TIME");
	TreeItem GedcomHeaderHEADSUBM = new TreeItem("SUBM");
	TreeItem GedcomHeaderHEADSUBN = new TreeItem("SUBN");
	TreeItem GedcomHeaderHEADFILE = new TreeItem("FILE");
	TreeItem GedcomHeaderHEADCOPR = new TreeItem("COPR");
	TreeItem GedcomHeaderHEADGEDC = new TreeItem("GEDC");
	TreeItem GedcomHeaderHEADGEDCVERS = new TreeItem("VERS");
	TreeItem GedcomHeaderHEADGEDCFORM = new TreeItem("FORM");
	TreeItem GedcomHeaderHEADCHAR = new TreeItem("CHAR");
	TreeItem GedcomHeaderHEADCHARVERS = new TreeItem("VERS");
	TreeItem GedcomHeaderHEADLANG = new TreeItem("LANG");
	TreeItem GedcomHeaderHEADPLAC = new TreeItem("PLAC");
	TreeItem GedcomHeaderHEADPLACFORM = new TreeItem("FORM");
	TreeItem GedcomHeaderHEADNOTE = new TreeItem("NOTE");

	public String getLoadedFilePath() {
		return LoadedFilePath;
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("GenBase");

		Menu fileMenu = new Menu("File");
		MenuItem openFile = new MenuItem("Open");
		MenuItem closeFile = new MenuItem("Close");
		MenuItem saveFile = new MenuItem("Save");
		MenuItem saveFileAs = new MenuItem("Save as");
		openFile.setOnAction( (event) -> openFile() );
		closeFile.setOnAction((event) -> closeFile() );
		fileMenu.getItems().addAll(openFile,closeFile,saveFile,saveFileAs);
		PrimaryMenuBar.getMenus().addAll(fileMenu);

		ListView<String> NavItems = new ListView<String>();
		NavItems.getItems().addAll("Dashboard","Persons","Relations","Families","Events","Places","Media","Discoveries","Sources","Archives");
		NavItems.getSelectionModel().selectFirst();
		NavItems.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
				viewContent(newValue);
			}
		});

		viewContent("Dashboard");

		MainContent.setHgap(10);
		MainContent.setVgap(10);
		MainContentHeading.setStyle("-fx-font-size: 20pt;");
		CopyrightLabel.setStyle("-fx-padding: 3 6 6 6;");
		InfoLabelFooter.setStyle("-fx-padding: 3 6 6 6;");

		SplitPane Content = new SplitPane();
		Content.getItems().addAll(NavItems, MainContent);
		Content.setDividerPositions(0.2f);

		BorderPane Footer = new BorderPane();
		Footer.setLeft(InfoLabelFooter);
		Footer.setRight(CopyrightLabel);

		BorderPane All = new BorderPane();
		All.setTop(PrimaryMenuBar);
		All.setCenter(Content);
		All.setBottom(Footer);
		Scene DefaultScene = new Scene(All, 800, 600);
		primaryStage.setScene(DefaultScene);
		primaryStage.show();
	}

	void viewContent(String whichType) {
		clearMainContent();
		switch(whichType) {
		case "Dashboard": 
			viewMainContentHeading(whichType);
			viewDashboard();
			break;
		case "Persons": 
			viewMainContentHeading(whichType);
			viewPersons();
			break;
		case "Relations": 
			viewRelations();
			break;
		case "Families": 
			viewMainContentHeading(whichType);
			viewFamilies();
			break;
		case "Events": 
			viewMainContentHeading(whichType);
			viewEvents();
			break;
		case "Places": 
			viewMainContentHeading(whichType);
			viewPlaces();
			break;
		case "Media": 
			viewMainContentHeading(whichType);
			viewMedia();
			break;
		case "Discoveries": 
			viewMainContentHeading(whichType);
			viewDiscoveries();
			break;
		case "Sources": 
			viewMainContentHeading(whichType);
			viewSources();
			break;
		case "Archives": 
			viewMainContentHeading(whichType);
			viewArchives();
			break;
		default:
			break;
		}
	}

	void clearMainContent() {
		MainContent.getChildren().clear();
		GedcomHeaderHEADSOURDATA.getChildren().clear();
		GedcomHeaderHEADSOUR.getChildren().clear();
		GedcomHeaderHEADDATE.getChildren().clear();
		GedcomHeaderHEADGEDC.getChildren().clear();
		GedcomHeaderHEADCHAR.getChildren().clear();
		GedcomHeaderHEADPLAC.getChildren().clear();
		GedcomHeaderHEAD.getChildren().clear();
	}

	void viewDashboard() {
		// Welcome Message
		Label WelcomeMessage = new Label("Welcome to GenBase " + ProgramVersion + "! We hope you are doing great today. Select one of the options from below to kick start today's genealogy experience.");
		WelcomeMessage.setWrapText(true);

		// Buttons
		Button OpenFileDashboard = new Button();
		OpenFileDashboard.setText("Open");
		OpenFileDashboard.setOnAction( (event) -> openFile() );
		Button CloseFileDashboard = new Button();
		CloseFileDashboard.setText("Close");
		CloseFileDashboard.setOnAction( (event) -> closeFile() );
		Button QuitButton = new Button();
		QuitButton.setText("Quit");
		QuitButton.setOnAction( (event) -> Platform.exit() );
		Button OutputGedcomDashboard = new Button();
		OutputGedcomDashboard.setText("Output all Gedcom Data (dev)");
		OutputGedcomDashboard.setOnAction((event) -> System.out.println(GedcomData) );

		// Labels with Information about the Gedcom file
		Label LoadedFilePathView = new Label("Loaded file:");
		Label LoadedFilePathViewLabel = new Label(LoadedFilePath);

		// TreeView with Information about the Gedcom file
		TreeView GedcomHeader = new TreeView();
		GedcomHeaderHEADSOURDATA.getChildren().addAll(GedcomHeaderHEADSOURDATADATE,GedcomHeaderHEADSOURDATACOPR);
		GedcomHeaderHEADSOUR.getChildren().addAll(GedcomHeaderHEADSOURVERS,GedcomHeaderHEADSOURNAME,GedcomHeaderHEADSOURCORP,GedcomHeaderHEADSOURDATA);
		GedcomHeaderHEADDATE.getChildren().add(GedcomHeaderHEADDATETIME);
		GedcomHeaderHEADGEDC.getChildren().addAll(GedcomHeaderHEADGEDCVERS,GedcomHeaderHEADGEDCFORM);
		GedcomHeaderHEADCHAR.getChildren().add(GedcomHeaderHEADCHARVERS);
		GedcomHeaderHEADPLAC.getChildren().add(GedcomHeaderHEADPLACFORM);
		GedcomHeaderHEAD.getChildren().addAll(GedcomHeaderHEADSOUR,GedcomHeaderHEADDEST,GedcomHeaderHEADDATE,GedcomHeaderHEADSUBM,GedcomHeaderHEADSUBN,GedcomHeaderHEADFILE,GedcomHeaderHEADCOPR,GedcomHeaderHEADGEDC,GedcomHeaderHEADCHAR,GedcomHeaderHEADLANG,GedcomHeaderHEADPLAC,GedcomHeaderHEADNOTE);
		GedcomHeader.setRoot(GedcomHeaderHEAD);
		GedcomHeader.setShowRoot(false);
		if(LoadedFilePath.equals("") == false) {
			viewGedcomHeaderOnDashboard();
		}

		// Put everything together
		MainContent.add(WelcomeMessage,0,2,5,2);
		MainContent.add(OpenFileDashboard,0,4,1,1);
		MainContent.add(CloseFileDashboard,1,4,1,1);
		MainContent.add(QuitButton,2,4,1,1);
		MainContent.add(OutputGedcomDashboard,3,4,1,1);
		MainContent.add(LoadedFilePathView,0,5,1,1);
		MainContent.add(LoadedFilePathViewLabel,1,5,5,1);
		MainContent.add(GedcomHeader,0,6,5,5);
	}

	void viewPersons() {
	}

	void viewRelations() {
	}

	void viewFamilies() {
	}

	void viewEvents() {
	}

	void viewPlaces() {
	}

	void viewMedia() {
	}

	void viewDiscoveries() {
	}

	void viewSources() {
	}
	void viewArchives () {
	}

	void viewMainContentHeading(String whichType) {
		MainContentHeading.setText(whichType);
		MainContent.add(MainContentHeading,0,0,2,2);
	}

	void openFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
			new FileChooser.ExtensionFilter("GEDCOM Files", "*.ged")
		);
		loadFile(fileChooser.showOpenDialog(primaryStage).toString());
	}

	void loadFile(String FileName) {
		LoadedFilePath = FileName;
		StringBuffer StreamGedcomDataStringBuffer = new StringBuffer();
		Path LoadedFilePathAsPath = Paths.get(LoadedFilePath);
		try(var br = Files.newBufferedReader(LoadedFilePathAsPath)) {
			String line;
			while((line=br.readLine()) != null) {
				StreamGedcomDataStringBuffer.append("\n");
				StreamGedcomDataStringBuffer.append(line);
			}
		} catch (IOException ex) {} 
		GedcomData = StreamGedcomDataStringBuffer.toString();
		viewContent("Dashboard");
	}

	void closeFile() {
		LoadedFilePath = "";
		GedcomData = "";
		GedcomHEADRecord = "";
		viewContent("Dashboard");
	}

	void viewGedcomHeaderOnDashboard() {
		int StartPosition0 = 0;
		try {
			for(;;) {
				int FoundAt0 = GedcomData.indexOf("\n0 ",StartPosition0);
				String WorkingSection0;
				if(GedcomData.indexOf("\n0 ",FoundAt0 + 1) < 0) {
					WorkingSection0 = GedcomData.substring(FoundAt0,GedcomData.length());
				} else {
					WorkingSection0 = GedcomData.substring(FoundAt0,GedcomData.indexOf("\n0 ",FoundAt0 + 1));
				}
				WorkingSection0 = WorkingSection0.trim();

				GedcomHEADRecord = WorkingSection0;

				String LastTagName0 = getGedcomTagName(WorkingSection0);
				int StartPosition1 = 0;
				try {
					for(;;) {
						int FoundAt1 = WorkingSection0.indexOf("\n1 ",StartPosition1);
						String WorkingSection1;
						if(WorkingSection0.indexOf("\n1 ",FoundAt1 + 1) < 0) {
							WorkingSection1 = WorkingSection0.substring(FoundAt1,WorkingSection0.length());
						} else {
							WorkingSection1 = WorkingSection0.substring(FoundAt1,WorkingSection0.indexOf("\n1 ",FoundAt1 + 1));
						}
						WorkingSection1 = WorkingSection1.trim();

						String LastTagName1 = getGedcomTagName(WorkingSection1);

						if(LastTagName1.equals("SOUR")) {
							String GedcomTagValue = getGedcomTagValue(WorkingSection1);
							String TreeItemTextBefore = GedcomHeaderHEADSOUR.getValue().toString();
							StringBuffer TreeItemTextAfter = new StringBuffer(TreeItemTextBefore);
							TreeItemTextAfter.append(": " + GedcomTagValue);
							GedcomHeaderHEADSOUR.setValue(TreeItemTextAfter.toString());
						}

						if(LastTagName1.equals("DEST")) {
							String GedcomTagValue = getGedcomTagValue(WorkingSection1);
							String TreeItemTextBefore = GedcomHeaderHEADDEST.getValue().toString();
							StringBuffer TreeItemTextAfter = new StringBuffer(TreeItemTextBefore);
							TreeItemTextAfter.append(": " + GedcomTagValue);
							GedcomHeaderHEADDEST.setValue(TreeItemTextAfter.toString());
						}

						if(LastTagName1.equals("DATE")) {
							String GedcomTagValue = getGedcomTagValue(WorkingSection1);
							String TreeItemTextBefore = GedcomHeaderHEADDATE.getValue().toString();
							StringBuffer TreeItemTextAfter = new StringBuffer(TreeItemTextBefore);
							TreeItemTextAfter.append(": " + GedcomTagValue);
							GedcomHeaderHEADDATE.setValue(TreeItemTextAfter.toString());
						}

						if(LastTagName1.equals("SUBM")) {
							String GedcomTagValue = getGedcomTagValue(WorkingSection1);
							String TreeItemTextBefore = GedcomHeaderHEADSUBM.getValue().toString();
							StringBuffer TreeItemTextAfter = new StringBuffer(TreeItemTextBefore);
							TreeItemTextAfter.append(": " + GedcomTagValue);
							GedcomHeaderHEADSUBM.setValue(TreeItemTextAfter.toString());
						}

						if(LastTagName1.equals("SUBN")) {
							String GedcomTagValue = getGedcomTagValue(WorkingSection1);
							String TreeItemTextBefore = GedcomHeaderHEADSUBN.getValue().toString();
							StringBuffer TreeItemTextAfter = new StringBuffer(TreeItemTextBefore);
							TreeItemTextAfter.append(": " + GedcomTagValue);
							GedcomHeaderHEADSUBN.setValue(TreeItemTextAfter.toString());
						}

						if(LastTagName1.equals("FILE")) {
							String GedcomTagValue = getGedcomTagValue(WorkingSection1);
							String TreeItemTextBefore = GedcomHeaderHEADFILE.getValue().toString();
							StringBuffer TreeItemTextAfter = new StringBuffer(TreeItemTextBefore);
							TreeItemTextAfter.append(": " + GedcomTagValue);
							GedcomHeaderHEADFILE.setValue(TreeItemTextAfter.toString());
						}

						if(LastTagName1.equals("COPR")) {
							String GedcomTagValue = getGedcomTagValue(WorkingSection1);
							String TreeItemTextBefore = GedcomHeaderHEADCOPR.getValue().toString();
							StringBuffer TreeItemTextAfter = new StringBuffer(TreeItemTextBefore);
							TreeItemTextAfter.append(": " + GedcomTagValue);
							GedcomHeaderHEADCOPR.setValue(TreeItemTextAfter.toString());
						}

						if(LastTagName1.equals("GEDC")) {
							String GedcomTagValue = getGedcomTagValue(WorkingSection1);
							String TreeItemTextBefore = GedcomHeaderHEADGEDC.getValue().toString();
							StringBuffer TreeItemTextAfter = new StringBuffer(TreeItemTextBefore);
							TreeItemTextAfter.append(": " + GedcomTagValue);
							GedcomHeaderHEADGEDC.setValue(TreeItemTextAfter.toString());
						}

						if(LastTagName1.equals("CHAR")) {
							String GedcomTagValue = getGedcomTagValue(WorkingSection1);
							String TreeItemTextBefore = GedcomHeaderHEADCHAR.getValue().toString();
							StringBuffer TreeItemTextAfter = new StringBuffer(TreeItemTextBefore);
							TreeItemTextAfter.append(": " + GedcomTagValue);
							GedcomHeaderHEADCHAR.setValue(TreeItemTextAfter.toString());
						}

						if(LastTagName1.equals("LANG")) {
							String GedcomTagValue = getGedcomTagValue(WorkingSection1);
							String TreeItemTextBefore = GedcomHeaderHEADLANG.getValue().toString();
							StringBuffer TreeItemTextAfter = new StringBuffer(TreeItemTextBefore);
							TreeItemTextAfter.append(": " + GedcomTagValue);
							GedcomHeaderHEADLANG.setValue(TreeItemTextAfter.toString());
						}

						if(LastTagName1.equals("PLAC")) {
							String GedcomTagValue = getGedcomTagValue(WorkingSection1);
							String TreeItemTextBefore = GedcomHeaderHEADPLAC.getValue().toString();
							StringBuffer TreeItemTextAfter = new StringBuffer(TreeItemTextBefore);
							TreeItemTextAfter.append(": " + GedcomTagValue);
							GedcomHeaderHEADPLAC.setValue(TreeItemTextAfter.toString());
						}

						if(LastTagName1.equals("NOTE")) {
							String GedcomTagValue = getGedcomTagValue(WorkingSection1);
							String TreeItemTextBefore = GedcomHeaderHEADNOTE.getValue().toString();
							StringBuffer TreeItemTextAfter = new StringBuffer(TreeItemTextBefore);
							TreeItemTextAfter.append(": " + GedcomTagValue);
							GedcomHeaderHEADNOTE.setValue(TreeItemTextAfter.toString());
						}

						int StartPosition2 = 0;
						try {
							for(;;) {
								int FoundAt2 = WorkingSection1.indexOf("\n2 ",StartPosition2);
								String WorkingSection2;
								if(WorkingSection1.indexOf("\n2 ",FoundAt2 + 1) < 0) {
									WorkingSection2 = WorkingSection1.substring(FoundAt2,WorkingSection1.length());
								} else {
									WorkingSection2 = WorkingSection1.substring(FoundAt2,WorkingSection1.indexOf("\n2 ",FoundAt2 + 1));
								}
								WorkingSection2 = WorkingSection2.trim();

								String LastTagName2 = getGedcomTagName(WorkingSection2);

								if(LastTagName1.equals("SOUR") && LastTagName2.equals("VERS")) {
									String GedcomTagValue = getGedcomTagValue(WorkingSection2);
									String TreeItemTextBefore = GedcomHeaderHEADSOURVERS.getValue().toString();
									StringBuffer TreeItemTextAfter = new StringBuffer(TreeItemTextBefore);
									TreeItemTextAfter.append(": " + GedcomTagValue);
									GedcomHeaderHEADSOURVERS.setValue(TreeItemTextAfter.toString());

									StartPosition2 = WorkingSection1.indexOf("\n2 ",FoundAt2 + 1);
									if(StartPosition2 < 1) {
										break;
									}

									continue;
								}

								if(LastTagName1.equals("SOUR") && LastTagName2.equals("NAME")) {
									String GedcomTagValue = getGedcomTagValue(WorkingSection2);
									String TreeItemTextBefore = GedcomHeaderHEADSOURNAME.getValue().toString();
									StringBuffer TreeItemTextAfter = new StringBuffer(TreeItemTextBefore);
									TreeItemTextAfter.append(": " + GedcomTagValue);
									GedcomHeaderHEADSOURNAME.setValue(TreeItemTextAfter.toString());

									StartPosition2 = WorkingSection1.indexOf("\n2 ",FoundAt2 + 1);
									if(StartPosition2 < 1) {
										break;
									}

									continue;
								}

								if(LastTagName1.equals("SOUR") && LastTagName2.equals("CORP")) {
									String GedcomTagValue = getGedcomTagValue(WorkingSection2);
									String TreeItemTextBefore = GedcomHeaderHEADSOURCORP.getValue().toString();
									StringBuffer TreeItemTextAfter = new StringBuffer(TreeItemTextBefore);
									TreeItemTextAfter.append(": " + GedcomTagValue);
									GedcomHeaderHEADSOURCORP.setValue(TreeItemTextAfter.toString());

									StartPosition2 = WorkingSection1.indexOf("\n2 ",FoundAt2 + 1);
									if(StartPosition2 < 1) {
										break;
									}

									continue;
								}

								if(LastTagName1.equals("SOUR") && LastTagName2.equals("DATA")) {
									String GedcomTagValue = getGedcomTagValue(WorkingSection2);
									String TreeItemTextBefore = GedcomHeaderHEADSOURDATA.getValue().toString();
									StringBuffer TreeItemTextAfter = new StringBuffer(TreeItemTextBefore);
									TreeItemTextAfter.append(": " + GedcomTagValue);
									GedcomHeaderHEADSOURDATA.setValue(TreeItemTextAfter.toString());

									StartPosition2 = WorkingSection1.indexOf("\n2 ",FoundAt2 + 1);
									if(StartPosition2 < 1) {
										break;
									}

									continue;
								}
								
								

								if(LastTagName1.equals("DATE") && LastTagName2.equals("TIME")) {
									String GedcomTagValue = getGedcomTagValue(WorkingSection2);
									String TreeItemTextBefore = GedcomHeaderHEADDATETIME.getValue().toString();
									StringBuffer TreeItemTextAfter = new StringBuffer(TreeItemTextBefore);
									TreeItemTextAfter.append(": " + GedcomTagValue);
									GedcomHeaderHEADDATETIME.setValue(TreeItemTextAfter.toString());

									StartPosition2 = WorkingSection1.indexOf("\n2 ",FoundAt2 + 1);
									if(StartPosition2 < 1) {
										break;
									} else {
										continue;
									}
								}

								if(LastTagName1.equals("GEDC") && LastTagName2.equals("VERS")) {
									String GedcomTagValue = getGedcomTagValue(WorkingSection2);
									String TreeItemTextBefore = GedcomHeaderHEADGEDCVERS.getValue().toString();
									StringBuffer TreeItemTextAfter = new StringBuffer(TreeItemTextBefore);
									TreeItemTextAfter.append(": " + GedcomTagValue);
									GedcomHeaderHEADGEDCVERS.setValue(TreeItemTextAfter.toString());

									StartPosition2 = WorkingSection1.indexOf("\n2 ",FoundAt2 + 1);
									if(StartPosition2 < 1) {
										break;
									}

									continue;
								}

								if(LastTagName1.equals("GEDC") && LastTagName2.equals("FORM")) {
									String GedcomTagValue = getGedcomTagValue(WorkingSection2);
									String TreeItemTextBefore = GedcomHeaderHEADGEDCFORM.getValue().toString();
									StringBuffer TreeItemTextAfter = new StringBuffer(TreeItemTextBefore);
									TreeItemTextAfter.append(": " + GedcomTagValue);
									GedcomHeaderHEADGEDCFORM.setValue(TreeItemTextAfter.toString());

									StartPosition2 = WorkingSection1.indexOf("\n2 ",FoundAt2 + 1);
									if(StartPosition2 < 1) {
										break;
									}

									continue;
								}

								if(LastTagName1.equals("CHAR") && LastTagName2.equals("VERS")) {
									String GedcomTagValue = getGedcomTagValue(WorkingSection2);
									String TreeItemTextBefore = GedcomHeaderHEADCHARVERS.getValue().toString();
									StringBuffer TreeItemTextAfter = new StringBuffer(TreeItemTextBefore);
									TreeItemTextAfter.append(": " + GedcomTagValue);
									GedcomHeaderHEADCHARVERS.setValue(TreeItemTextAfter.toString());

									StartPosition2 = WorkingSection1.indexOf("\n2 ",FoundAt2 + 1);
									if(StartPosition2 < 1) {
										break;
									}

									continue;
								}

								if(LastTagName1.equals("PLAC") && LastTagName2.equals("FORM")) {
									String GedcomTagValue = getGedcomTagValue(WorkingSection2);
									String TreeItemTextBefore = GedcomHeaderHEADPLACFORM.getValue().toString();
									StringBuffer TreeItemTextAfter = new StringBuffer(TreeItemTextBefore);
									TreeItemTextAfter.append(": " + GedcomTagValue);
									GedcomHeaderHEADPLACFORM.setValue(TreeItemTextAfter.toString());

									StartPosition2 = WorkingSection1.indexOf("\n2 ",FoundAt2 + 1);
									if(StartPosition2 < 1) {
										break;
									}

									continue;
								}

								if(LastTagName1.equals("NOTE") && LastTagName2.equals("CONC")) {
									String GedcomTagValue = getGedcomTagValue(WorkingSection2);
									String TreeItemTextBefore = GedcomHeaderHEADNOTE.getValue().toString();
									StringBuffer TreeItemTextAfter = new StringBuffer(TreeItemTextBefore);
									TreeItemTextAfter.append(": " + GedcomTagValue);
									GedcomHeaderHEADNOTE.setValue(TreeItemTextAfter.toString());

									StartPosition2 = WorkingSection1.indexOf("\n2 ",FoundAt2 + 1);
									if(StartPosition2 < 1) {
										break;
									}

									continue;
								}

								int StartPosition3 = 0;
								try {
									for(;;) {
										int FoundAt3 = WorkingSection2.indexOf("\n3 ",StartPosition3);
										String WorkingSection3;
										if(WorkingSection2.indexOf("\n3 ",FoundAt3 + 1) < 0) {
											WorkingSection3 = WorkingSection2.substring(FoundAt3,WorkingSection2.length());
										} else {
											WorkingSection3 = WorkingSection2.substring(FoundAt3,WorkingSection2.indexOf("\n3 ",FoundAt3 + 1));
										}
										WorkingSection3 = WorkingSection3.trim();
										
										String LastTagName3 = getGedcomTagName(WorkingSection3);

										if(LastTagName1.equals("SOUR") && LastTagName2.equals("DATA") && LastTagName3.equals("DATE")) {
											String GedcomTagValue = getGedcomTagValue(WorkingSection3);
											String TreeItemTextBefore = GedcomHeaderHEADSOURDATADATE.getValue().toString();
											StringBuffer TreeItemTextAfter = new StringBuffer(TreeItemTextBefore);
											TreeItemTextAfter.append(": " + GedcomTagValue);
											GedcomHeaderHEADSOURDATADATE.setValue(TreeItemTextAfter.toString());

											StartPosition3 = WorkingSection2.indexOf("\n3 ",FoundAt3 + 1);
											if(StartPosition3 < 1) {
												break;
											}

											continue;
										}

										if(LastTagName1.equals("SOUR") && LastTagName2.equals("DATA") && LastTagName3.equals("COPR")) {
											String GedcomTagValue = getGedcomTagValue(WorkingSection3);
											String TreeItemTextBefore = GedcomHeaderHEADSOURDATACOPR.getValue().toString();
											StringBuffer TreeItemTextAfter = new StringBuffer(TreeItemTextBefore);
											TreeItemTextAfter.append(": " + GedcomTagValue);
											GedcomHeaderHEADSOURDATACOPR.setValue(TreeItemTextAfter.toString());

											StartPosition3 = WorkingSection2.indexOf("\n3 ",FoundAt3 + 1);
											if(StartPosition3 < 1) {
												break;
											}

											continue;
										}

										int StartPosition4 = 0;
										try {
											for(;;) {
												int FoundAt4 = WorkingSection3.indexOf("\n4 ",StartPosition4);
												String WorkingSection4;
												if(WorkingSection3.indexOf("\n4 ",FoundAt4 + 1) < 0) {
													WorkingSection4 = WorkingSection3.substring(FoundAt4,WorkingSection3.length());
												} else {
													WorkingSection4 = WorkingSection3.substring(FoundAt4,WorkingSection3.indexOf("\n4 ",FoundAt4 + 1));
												}
												WorkingSection4 = WorkingSection4.trim();

												String LastTagName4 = getGedcomTagName(WorkingSection4);

												if(LastTagName1.equals("SOUR") && LastTagName2.equals("DATA") && LastTagName3.equals("COPR") && LastTagName4.equals("CONT")) {
													String GedcomTagValue = getGedcomTagValue(WorkingSection4);
													String TreeItemTextBefore = GedcomHeaderHEADSOURDATACOPR.getValue().toString();
													StringBuffer TreeItemTextAfter = new StringBuffer(TreeItemTextBefore);
													TreeItemTextAfter.append(": " + GedcomTagValue);
													GedcomHeaderHEADSOURDATACOPR.setValue(TreeItemTextAfter.toString());

													StartPosition4 = WorkingSection3.indexOf("\n4 ",FoundAt4 + 1);
													if(StartPosition4 < 1) {
														break;
													}

													continue;
												}

												StartPosition4 = WorkingSection3.indexOf("\n4 ",FoundAt4 + 1);
												if(StartPosition4 < 1) {
													break;
												}
											} 
										} catch (IndexOutOfBoundsException ex) {}


										StartPosition3 = WorkingSection2.indexOf("\n3 ",FoundAt3 + 1);
										if(StartPosition3 < 1) {
											break;
										}
									} 
								} catch (IndexOutOfBoundsException ex) {}

								StartPosition2 = WorkingSection1.indexOf("\n2 ",FoundAt2 + 1);
								if(StartPosition2 < 1) {
									break;
								}
							} 
						} catch (IndexOutOfBoundsException ex) {}

						StartPosition1 = WorkingSection0.indexOf("\n1 ",FoundAt1 + 1);
						if(StartPosition1 < 1) {
							break;
						}
					} 
				} catch (IndexOutOfBoundsException ex) {}

				String GedcomTagName = getGedcomTagName(WorkingSection0);
				if(GedcomTagName.equals("HEAD")) {
					break;
				}				

				StartPosition0 = GedcomData.indexOf("\n0 ",FoundAt0 + 2);
				if(StartPosition0 < 1) {
					break;
				}
			} 
		} catch (IndexOutOfBoundsException ex) {}
	} 

	String getGedcomTagName(String GedcomTag) {
		int firstSpaceAt = GedcomTag.indexOf(" ");
		int secondSpaceAt;
		int firstNewLineFeedAt = GedcomTag.indexOf("\n",firstSpaceAt + 1);
		if(GedcomTag.lastIndexOf(" ",firstNewLineFeedAt) == GedcomTag.indexOf(" ")) {
			secondSpaceAt = GedcomTag.indexOf("\n",firstSpaceAt + 1);
		} else {
			secondSpaceAt = GedcomTag.indexOf(" ",firstSpaceAt + 1);
		}
		return GedcomTag.substring(firstSpaceAt + 1,secondSpaceAt);
	}

	String getGedcomTagValue(String GedcomTag) {
		int firstSpaceAt = GedcomTag.indexOf(" ");
		int firstNewLineFeedAt = GedcomTag.indexOf("\n",firstSpaceAt + 1);
		if(GedcomTag.lastIndexOf(" ",firstNewLineFeedAt) == GedcomTag.indexOf(" ")) {
			return "";
		} else {
			int secondSpaceAt;
			if(GedcomTag.indexOf("\n",3) < 0) {
				secondSpaceAt = GedcomTag.length();
			} else {
				secondSpaceAt = GedcomTag.indexOf("\n",3);
			}
			firstSpaceAt = GedcomTag.indexOf(" ",3);
			return GedcomTag.substring(firstSpaceAt + 1,secondSpaceAt);
		}
	}

}