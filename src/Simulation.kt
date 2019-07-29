import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.File
import java.lang.RuntimeException
import javax.imageio.ImageIO
import javax.swing.Timer
import kotlin.random.Random

class Simulation: ActionListener {

    companion object {
        private fun readSprite(filename: String)
                = ImageIO.read(File(filename)) ?: throw RuntimeException("Missing resource '$filename'")
    }

    private val sprites = arrayOf(
        readSprite("resources/a220.png"),
        readSprite("resources/a320.png"),
        readSprite("resources/a330.png"),
        readSprite("resources/a340.png"),
        readSprite("resources/a350.png"),
        readSprite("resources/a380.png"),

        readSprite("resources/avrorj.png"),

        readSprite("resources/b707.png"),
        readSprite("resources/b737.png"),
        readSprite("resources/b747.png"),
        readSprite("resources/b757.png"),
        readSprite("resources/b777.png"),
        readSprite("resources/b787.png"),

        readSprite("resources/c172.png"),
        readSprite("resources/citation.png"),

        readSprite("resources/dash8.png"),

        readSprite("resources/md80.png")
    )

    private val aircraft = mutableListOf<Airplane>()

    private val runways = listOf<Runway>(
        //Runway(100, 50, 450, 25, 16),
        //Runway(175, 75, 350, 25, 16)
    )

    private val panel = Panel(aircraft, runways)
    private val win = Window(panel, 1200, 800)
    private val timer = Timer(15, this)
    private val chance = 4

    private fun randAircraft(): Airplane {
        val sprite = sprites.random()
        /////////////////////////////
        //                         //
        //           (27)          //
        //            N            //
        //            |            //
        //  (18) W ---+--- E (00)  //
        //            |            //
        //            S            //
        //           (09)          //
        //                         //
        /////////////////////////////

        val northbound = Random.nextBoolean()
        val southbound = !northbound
        val westbound = Random.nextBoolean()
        val eastbound = !westbound

        val fixedVert = Random.nextBoolean()
        val fixedHor = !fixedVert
        val x = if (fixedVert) { Random.nextInt(-50, 1250) } else { if (eastbound) -50 else 1250 }
        val y = if (fixedHor) { Random.nextInt(-50, 850) } else { if (southbound) -50 else 850 }

        val hBounds = when {
            eastbound && northbound -> Pair(270, 360)
            eastbound && southbound -> Pair(0, 90)
            westbound && northbound -> Pair(180, 270)
            westbound && southbound -> Pair(90, 180)
            else -> Pair(0, 360)
        }

        val roughHeading = Random.nextInt(hBounds.first * 10, hBounds.second * 10)
        val heading = (roughHeading % 3600) / 10.0

        val velocity = Random.nextInt(200, 450)

        return Airplane(sprite, x, y, heading, velocity)

    }

    private fun removeDistantAircraft() = aircraft.removeIf { it.x < -50 || it.x > 1250 || it.y < -50 || it.y > 850 }

    private fun generatePlane() {
        if (aircraft.size < 10 && Random.nextInt(1000) < chance) {
            aircraft.add(randAircraft())
        }
    }

    private fun update() {
        generatePlane()
        aircraft.forEach { it.update() }
        removeDistantAircraft()
    }

    override fun actionPerformed(e: ActionEvent?) {
        update()
        panel.repaint()
    }

    private fun finalize() {
        timer.stop()
    }

    init {
        timer.start()
    }

}
