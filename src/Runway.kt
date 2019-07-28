import java.awt.Color
import java.awt.Rectangle

class Runway(x: Int, y: Int, length: Int, width: Int, val heading: Int): Rectangle(x, y, width, length) {

    companion object {
        val color = Color(51, 49, 45)
    }

    val length: Int
        get() = height

}