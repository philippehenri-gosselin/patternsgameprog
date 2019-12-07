/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap05.ai06.rules.commands;

import examples.chap05.ai06.rules.actions.Action;
import examples.chap05.ai06.state.State;
import java.net.URL;
import java.util.Queue;
import javax.swing.JOptionPane;
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

public class LoadLevelCommand implements Command {

    private String fileName;

    private int pacmanSpeed = 2;
    
    private int ghostSpeed = 2;
    
    public LoadLevelCommand(String fileName,int pacmanSpeed,int ghostSpeed) {
        this.fileName = fileName;
        this.pacmanSpeed = pacmanSpeed;
        this.ghostSpeed = ghostSpeed;
    }
    
    public class LoadLevelHandler extends DefaultHandler 
    {
        State state;
        private int width;
        private int height;
        private int[][] level;
        private int x;
        private int y;
        
        public LoadLevelHandler(State state) {
            this.state = state;
        }
        
        public void startElement (String uri, String localName,
                              String qName, Attributes attributes)
           throws SAXException {
            if (qName.equals("layer")) {
                width = Integer.parseInt(attributes.getValue("width"));
                height = Integer.parseInt(attributes.getValue("height"));
                level = new int[height][width];
                x = 0;
                y = 0;
            }
            else if (qName.equals("tile")) {
                int id = Integer.parseInt(attributes.getValue("gid"));
                level[y][x] = id;
                x ++;
                if (x >= width) {
                    x = 0;
                    y ++;
                    if (y > height) {
                        throw new SAXException("Error in file");
                    }
                }
            }
        }
        public void endElement (String uri, String localName, String qName)
            throws SAXException
        {
            if (qName.equals("layer")) {
                state.init(level, width, height, pacmanSpeed, ghostSpeed);
            }
        }
        
    }
    
    public void execute(Queue<Action> actions, State state) {
        LoadLevelHandler loadLevelHandler = new LoadLevelHandler(state);
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser saxParser = spf.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            xmlReader.setContentHandler(loadLevelHandler);
            URL fileURL = this.getClass().getClassLoader().getResource(fileName);
            xmlReader.parse(fileURL.toString());
        }
        catch(Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error when loading "+fileName, "Error", JOptionPane.ERROR_MESSAGE);
        }
        state.notityStateChanged();        
    }
}
