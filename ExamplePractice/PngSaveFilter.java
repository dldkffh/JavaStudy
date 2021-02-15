package zebal;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class PngSaveFilter extends FileFilter
{
     public boolean accept(File f)
    {
        if(f.isDirectory())
        {
            return true;
        }
        return f.getName().endsWith(".png");
    }
 
    public String getDescription()
    {
        return "PNG files (*.png)";
    }
}
