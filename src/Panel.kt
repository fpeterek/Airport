import java.awt.Color
import java.awt.Graphics
import java.awt.Image
import java.io.File
import java.lang.RuntimeException
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JPanel


class Panel(private val aircraft: List<Airplane>, private val runways: List<Runway>): JPanel() {

    private val img = ImageIcon("resources/kxb-airport.png")
        .image.getScaledInstance(1200, 800, Image.SCALE_SMOOTH)

    init {
        background = Color(117, 99, 62)
    }

    private fun renderRunways(g: Graphics) {
        g.color = Runway.color
        runways.forEach { g.fillRect(it.x, it.y, it.width, it.length) }
    }

    private fun renderAircraft(g: Graphics) = aircraft.forEach {
        g.drawImage(it.orientedImg, it.x.toInt(), it.y.toInt(), null)
    }

    private fun renderBackground(g: Graphics) = g.drawImage(img, 0, 0, null)

    override fun paint(g: Graphics?) {

        if (g == null) {
            return
        }

        super.paint(g)
        renderBackground(g)
        renderRunways(g)
        renderAircraft(g)
    }


}
