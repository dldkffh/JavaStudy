package zebal;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImagePanel extends JPanel
{
    Image img, img2;
    int x, y;
    double scaleRatio = 1.0;
    double angle;
    static final int LEFT = 0;
    static final int RIGHT = 1;
    
    public ImagePanel(String fileName)
    {
        try {
            InputStream in = getClass().getResourceAsStream(fileName);
            BufferedImage bi = ImageIO.read(in);
            img = bi;
            img2 = img;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        setBorder(BorderFactory.createLineBorder(Color.yellow));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img2, x, y, this);
    }
    
    public void scale()
    {
        try {
            int w = (int) (img.getWidth(this)*scaleRatio);
            int h = (int) (img.getHeight(this)*scaleRatio);
            BufferedImage bi = null;
            bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = bi.createGraphics();
            g2d.setTransform(AffineTransform.getScaleInstance(scaleRatio, scaleRatio));
            g2d.drawImage(img, 0, 0, this);
            g2d.dispose();
            img2 = bi;
            repaint();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void increaseScale()
    {
        scaleRatio *= 1.05;
        scale();
    }
    
    public void decreaseScale()
    {
        scaleRatio /= 1.05;
        scale();
    }
    
    public void rotate(int direction)
    {
        if(direction==LEFT) angle -= 1;
        else angle += 1;

        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = img.getWidth(this);
        int h = img.getHeight(this);
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        BufferedImage bi = null;
        bi = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bi.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(Math.toRadians(angle), x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, this);
        g2d.setColor(Color.RED);
        g2d.drawRect(0, 0, newWidth - 1, newHeight - 1);
        g2d.dispose();
        
        img2 = bi;
        repaint();
    }
    
    public void move(int x, int y)
    {
        this.x += x;
        this.y += y;
        repaint();
    }
    
    public void save() throws IOException{
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new PngSaveFilter());
        fileChooser.addChoosableFileFilter(fileChooser.getChoosableFileFilters()[0]);
        fileChooser.showSaveDialog(null);
        File saveFile = fileChooser.getSelectedFile();

        if(!saveFile.getName().toLowerCase().endsWith(".png")) {
            saveFile = new File(saveFile.getAbsolutePath()+".png");
        }
        //JOptionPane.showMessageDialog(null, fileName);
        ImageIO.write((BufferedImage)img2, "PNG", saveFile);
        JOptionPane.showMessageDialog(this, "���� ���� ����");
    }
}
