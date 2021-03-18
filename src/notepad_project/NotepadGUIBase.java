package notepad_project;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;  
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JFileChooser;

/**
 *
 * @author Eng.Aya
 */
public class NotepadGUIBase extends BorderPane {

    protected final MenuBar menuBar;
    protected final Menu filemenu;
    protected final MenuItem NewItem;
    protected final MenuItem OpenItem0;
    protected final MenuItem SaveItem1;
    protected final MenuItem ExitItem2;
    protected final Menu editmenu;
    protected final MenuItem cutItem;
    protected final MenuItem copyItem;
    protected final MenuItem pasteItem;
    protected final MenuItem deleteItem;
    protected final MenuItem selecetItem;
    protected final Menu helpmenu;
    protected final MenuItem aboutItem;
    protected final TextArea textArea;
    public String str;
    public NotepadGUIBase(Stage primary) 
    {

        int fileToOpen;
        int fileToSave;
       
        JFileChooser fileOpen;
        JFileChooser fileSave;
        menuBar = new MenuBar();
        filemenu = new Menu();
        NewItem = new MenuItem();
        OpenItem0 = new MenuItem();
        SaveItem1 = new MenuItem();
        ExitItem2 = new MenuItem();
        editmenu = new Menu();
        cutItem = new MenuItem();
        copyItem = new MenuItem();
        pasteItem = new MenuItem();
        deleteItem = new MenuItem();
        selecetItem = new MenuItem();
        helpmenu = new Menu();
        aboutItem = new MenuItem();
        textArea = new TextArea();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        BorderPane.setAlignment(menuBar, javafx.geometry.Pos.CENTER);

        filemenu.setMnemonicParsing(false);
        filemenu.setText("File");

        NewItem.setMnemonicParsing(false);
        NewItem.setText("New");
        NewItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
         
                 NewItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) 
            {
              if (! textArea.getText().isEmpty())
              {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                                "Do you want to save changes?",
                                ButtonType.YES,ButtonType.NO,ButtonType.CANCEL);

                alert.setTitle("Confirm");
                alert.showAndWait();

                if (alert.getResult() == ButtonType.YES) {
                   try {
                       FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Save As");
                        File f = fileChooser.showSaveDialog(primary);

                        if (f != null) {
                             PrintWriter savedText = new PrintWriter(f);
                             BufferedWriter out = new BufferedWriter(savedText);
                             out.write(textArea.getText());
                             out.close();
                         }
                   }
                   catch (IOException ex) {
                        ex.printStackTrace();
                   }
                   
                   textArea.clear();
                }
                
                if (alert.getResult() == ButtonType.NO) {
                        textArea.clear();
                }
                    return;
              }

       
            }
       });
        OpenItem0.setMnemonicParsing(false);
        OpenItem0.setText("Open");
        OpenItem0.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        OpenItem0.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e ) 
            {
                FileChooser fc = new FileChooser();
                File file = fc.showOpenDialog(primary);
                if (file != null) {
                    try {
                        FileInputStream fs = new FileInputStream(file);
                        int size = (int) file.length();
                        byte[] data = new byte[size];
                        fs.read(data);
                        textArea.setText(new String(data));
                    } catch (FileNotFoundException ex) {

                    } catch (IOException ex) {

                    }
                }
            }

        } );
        SaveItem1.setMnemonicParsing(false);
        SaveItem1.setText("Save");
        SaveItem1.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));

        SaveItem1.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e ) 
            {
                FileChooser fc = new FileChooser();
                fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt") );
                File file = fc.showSaveDialog(primary);
               if (file != null) {
                    try {
                        FileOutputStream fs = new FileOutputStream(file);
                        int size = (int) file.length();
                        byte[] data = new byte[size];
                       ObservableList<CharSequence> paragraph = textArea.getParagraphs();
                          Iterator<CharSequence>  iter = paragraph.iterator();
     
                      BufferedWriter bf = new BufferedWriter(new FileWriter(file));
                       while(iter.hasNext())
                       {
                           CharSequence seq = iter.next();
                              bf.append(seq);
                             bf.newLine();
                               }
                          bf.flush();
                           bf.close();
                            } catch (FileNotFoundException ex) {
                          ex.printStackTrace();
                    } catch (IOException ex) {
                          ex.printStackTrace();
                    }
                 
                     
                }
            }
        });
           
        ExitItem2.setMnemonicParsing(false);
        ExitItem2.setText("Exit");
        ExitItem2.setAccelerator( new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));
        ExitItem2.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
                       public void handle(ActionEvent e) 
            {
        
                if (textArea.getText().isEmpty()) {
                      primary.close();
                     
                }
                else{
                    Alert alert = new Alert(
                    Alert.AlertType.CONFIRMATION,"Do you want to save changes?",
                    ButtonType.YES,ButtonType.NO,ButtonType.CANCEL);

                    alert.setTitle("Confirm");
                    alert.showAndWait();

                    if (alert.getResult() == ButtonType.YES) {
                                try {
                                       FileChooser fileChooser = new FileChooser();
                                       fileChooser.setTitle("Save As");
                                       File f = fileChooser.showSaveDialog(primary);
                                       PrintWriter savedText = new PrintWriter(f);
                                       BufferedWriter out = new BufferedWriter(savedText);
                                        out.write(textArea.getText());
                                        out.close(); 
                                    }
                                catch (IOException ex) {
                                       ex.printStackTrace();
                                }
               
                                primary.close();
                     }
                    
                    if (alert.getResult() == ButtonType.NO) {
                            
                            primary.close();
                    }
                }
            }   
         });

        editmenu.setMnemonicParsing(false);
        editmenu.setText("Edit");

        cutItem.setMnemonicParsing(false);
        cutItem.setText("Cut");
          cutItem.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e ) 
            {
                str =  textArea.getSelectedText();
                 textArea.replaceSelection("");
                
            }
        });
         
        copyItem.setMnemonicParsing( false);
        copyItem.setText("Copy");
       
         copyItem.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e ) 
            {
               str =  textArea.getSelectedText();  
            }
        });
         
        pasteItem.setMnemonicParsing(false);
        pasteItem.setText("Paste");
        pasteItem.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e ) 
            {
                 int caretPosition = textArea.getCaretPosition();
                  textArea.insertText(caretPosition, str);
                  str= "";
            }
        });
        deleteItem.setMnemonicParsing( false);
        deleteItem.setText( "Delete");
        deleteItem.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e ) 
            {
                  textArea.replaceSelection("");
            }
        });
        selecetItem.setMnemonicParsing(false);
        selecetItem.setText("Select All");
        selecetItem.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e ) 
            {
                  textArea.selectAll();
            }
        });
        helpmenu.setMnemonicParsing(false);
        helpmenu.setText("Help");

        aboutItem.setMnemonicParsing(false);
        aboutItem.setText("About");
        aboutItem.setAccelerator( new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));
                aboutItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) 
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("About");
                alert.setHeaderText(" This is my simple Notepad Project");
                Image image = null;
                try {
                    image = new Image(new FileInputStream("D:\\javaLab\\yoka.jpg"));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(NotepadGUIBase.class.getName()).log(Level.SEVERE, null, ex);
                }
                ImageView imageView;
                imageView = new ImageView(image);
                alert.setGraphic(imageView);

                alert.showAndWait();
               
            }
        });
        setTop(menuBar);

        BorderPane.setAlignment(textArea, javafx.geometry.Pos.CENTER);

        textArea.setPrefHeight(
                374.0);
        textArea.setPrefWidth(
                600.0);
        setCenter(textArea);

        filemenu.getItems()
                .add(NewItem);
        filemenu.getItems()
                .add(OpenItem0);
        filemenu.getItems()
                .add(SaveItem1);
        filemenu.getItems()
                .add(ExitItem2);
        menuBar.getMenus()
                .add(filemenu);
        editmenu.getItems()
                .add(cutItem);
        editmenu.getItems()
                .add(copyItem);
        editmenu.getItems()
                .add(pasteItem);
        editmenu.getItems()
                .add(deleteItem);
        editmenu.getItems()
                .add(selecetItem);
        menuBar.getMenus()
                .add(editmenu);
        helpmenu.getItems()
                .add(aboutItem);
        menuBar.getMenus()
                .add(helpmenu);

    }
}
