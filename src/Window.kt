import java.awt.Dimension
import javax.swing.JFrame

class Window(panel: Panel, width: Int, height: Int): JFrame("KXB - Airport") {

    init {
        add(panel)
        defaultCloseOperation = EXIT_ON_CLOSE

        size = Dimension(width, height)
        isResizable = false
        isVisible = true
    }

}
