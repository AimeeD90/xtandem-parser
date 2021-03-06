package de.proteinms.xtandemparser.viewer;

import java.io.File;
import javax.swing.filechooser.*;

/**
 * File filter for *.txt files.
 *
 * @author  Harald Barsnes
 */
public class TxtFileFilter extends FileFilter {

    private final static String txt = "txt";
    private final static String TXT = "TXT";

    /**
     * Accept all directories, *.txt files.
     *
     * @param f the file
     * @return boolean
     */
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = getExtension(f);
        if (extension != null) {
            if (extension.equals(TxtFileFilter.txt) ||
                    extension.equals(TxtFileFilter.TXT)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * The description of this filter
     *
     * @return String
     */
    public java.lang.String getDescription() {
        return "Text (Tab delimited) (*.txt)";
    }

    /**
     * Get the extension of a file.
     *
     * @param f the file
     * @return String - the extension of the file f
     */
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }
}
