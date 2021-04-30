package mandelbrotset
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JLabel
import java.awt.BorderLayout
import java.awt.image.BufferedImage
import java.awt.Dimension
import java.awt.Toolkit
import javax.swing.WindowConstants

@main
def run() =  
    val frame = new JFrame("Mandelbrot Set")  
    var size = frame.getToolkit().getScreenSize() // get the dimensions of the computer screen
    
    val imgMandel=Mandelbrot.generate(size.width-10,size.height-10)
    val zoomPanel = new ZoomPanel(imgMandel.image)
    
    frame.getContentPane.add(zoomPanel, BorderLayout.CENTER)
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setSize(size)
    frame.setLocationRelativeTo(null)  
    frame.setVisible(true)
    