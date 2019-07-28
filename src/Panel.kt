import java.awt.Color
import java.awt.Graphics
import javax.swing.JPanel


class Panel(private val aircraft: List<Airplane>, private val runways: List<Runway>): JPanel() {

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

    override fun paint(g: Graphics?) {

        if (g == null) {
            return
        }

        super.paint(g)
        renderRunways(g)
        renderAircraft(g)
    }


}