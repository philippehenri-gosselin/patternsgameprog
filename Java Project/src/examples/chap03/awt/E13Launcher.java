/*
 * Code example from the book "Learn Design Patterns with Game Programming"
 * Copyrights © 2019 Philippe-Henri Gosselin. All rights reserved.
 */

package examples.chap03.awt;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class E13Launcher {
    
    private static class StreamPrinter implements Runnable {

        private final InputStream inputStream;

        StreamPrinter(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public void run() {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            try {
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static boolean run(String className,boolean enableOpenGL) {
        try {
            ArrayList<String> argumentList = new ArrayList<String>();
            String javaHome = System.getProperty("java.home");
            argumentList.add( new File(javaHome, "bin/java").getPath() );
            argumentList.add("-cp");
            argumentList.add( System.getProperty("java.class.path") );
            if (enableOpenGL) {
                argumentList.add("-Dsun.java2d.opengl=True");
            }
            argumentList.add(className);

            ProcessBuilder pb = new ProcessBuilder(argumentList);
            pb.directory(new File(System.getProperty("user.dir")));

            Process p = pb.start();
            StreamPrinter outputStream = new StreamPrinter(p.getInputStream());
            StreamPrinter inputStream = new StreamPrinter(p.getErrorStream());
            new Thread(outputStream).start();
            new Thread(inputStream).start();

            p.waitFor();

            return p.exitValue() == 0;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static void main(String[] args)
    {
        int selectedOption = JOptionPane.showConfirmDialog(null,
            "Launch with OpenGL ?", 
            "Launch settings", 
            JOptionPane.YES_NO_OPTION
        );
        run("examples.chap03.awt.E11Bench",selectedOption == JOptionPane.YES_OPTION);
    }    
}
