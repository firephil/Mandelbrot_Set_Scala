package mandelbrotset

import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.MouseInfo
import java.awt.Point
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener
import java.awt.event.MouseWheelEvent
import java.awt.event.MouseWheelListener
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage
import javax.swing.JPanel


// converted from java : 
// https://github.com/Thanasis1101/Zoomable-Java-Panel/blob/master/src/zoomable/panel/MainPanel.java

class ZoomPanel(img:BufferedImage) extends JPanel with MouseWheelListener 
                                                    with MouseListener 
                                                    with MouseMotionListener { 
    var image:BufferedImage = img
    var zoomFactor:Double = 1
    var prevZoomFactor:Double = 1
    var zoomer:Boolean = false
    var dragger:Boolean = false
    var released:Boolean = false
    var xOffset:Double = 0
    var yOffset:Double = 0
    var xDiff:Double = 0
    var yDiff:Double = 0
    var startPoint : Point = null
    addMouseWheelListener(this)
    addMouseMotionListener(this)
    addMouseListener(this)
    
    override def paint(g:Graphics ) = {
        super.paint(g)

        var g2:Graphics2D = g.asInstanceOf[Graphics2D]

        if (zoomer) {
            var at = new AffineTransform()

            var xRel = MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX()
            var yRel = MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY()

            var zoomDiv = zoomFactor / prevZoomFactor

            xOffset = (zoomDiv) * (xOffset) + (1 - zoomDiv) * xRel
            yOffset = (zoomDiv) * (yOffset) + (1 - zoomDiv) * yRel

            at.translate(xOffset, yOffset)
            at.scale(zoomFactor, zoomFactor)
            prevZoomFactor = zoomFactor
            g2.transform(at)
            zoomer = false
        }

        if (dragger) {
            var at = new AffineTransform()
            at.translate(xOffset + xDiff, yOffset + yDiff)
            at.scale(zoomFactor, zoomFactor)
            g2.transform(at)

            if (released) {
                xOffset = xOffset + xDiff
                yOffset = yOffset + yDiff
                dragger = false
            }

        }

        // All drawings go here
        
        g2.drawImage(image, 0, 0, this)

    }

    
    override def mouseWheelMoved(e:MouseWheelEvent) = {

        zoomer = true

        //Zoom in
        if (e.getWheelRotation() < 0) {
            zoomFactor = zoomFactor * 1.1
            repaint()
        }
        //Zoom out
        if (e.getWheelRotation() > 0) {
            zoomFactor = zoomFactor / 1.1
            repaint()
        }
    }

    override def  mouseDragged(e:MouseEvent) = {
        var curPoint:Point  = e.getLocationOnScreen()
        xDiff = curPoint.x - startPoint.x
        yDiff = curPoint.y - startPoint.y
        dragger = true
        repaint()

    }

    
    override def  mouseMoved(e:MouseEvent)={}   
    override def  mouseClicked(e:MouseEvent)={}
    
    override def  mousePressed(e:MouseEvent)= {
        released = false
        startPoint = MouseInfo.getPointerInfo().getLocation()
    }

    override def  mouseReleased(e:MouseEvent)= {
        released = true
        repaint()
    }
   
    override def  mouseEntered(e:MouseEvent)={}   
    override def  mouseExited(e:MouseEvent)={}

}