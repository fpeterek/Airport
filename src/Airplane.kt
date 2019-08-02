import java.awt.geom.AffineTransform
import java.awt.image.AffineTransformOp
import java.awt.image.BufferedImage
import java.lang.Math.cos
import java.lang.Math.sin


class Airplane(sprites: Pair<BufferedImage, BufferedImage>, x: Int, y: Int, heading: Double, velocity: Int = 430) {

    private val yellowImg = sprites.first
    private val redImg = sprites.second
    private var orientedRed = redImg
    private var orientedYellow = yellowImg
    var img: BufferedImage = orientedYellow
        private set

    var x = x.toDouble()
        private set
    var y = y.toDouble()
        private set

    var velocity = velocity
        private set
    var verticalSpeed = 0
        private set
    var altitude = 100
        private set
    val maxSteer = 0.0
    var fuel = 0
        private set
    var heading = heading
        private set

    val headingRad
        get() = Math.toRadians(heading)

    val tailwindLimit = 10
    val crosswindLimit = 25
    val mtow = 450
    val vMax = 450
    val vStall = 100
    val vLanding = 120

    var selected = false
        private set

    private fun rotateImg(image: BufferedImage): BufferedImage {

        val locationX = image.getWidth(null) / 2.0
        val locationY = image.getHeight(null) / 2.0
        // + Math.PI because Graphics uses a different origin
        val tx = AffineTransform.getRotateInstance(headingRad + Math.PI / 2, locationX, locationY)
        val op = AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR)
        return op.filter(image, null)

    }

    private fun rotateImages() {
        orientedRed = rotateImg(orientedRed)
        orientedYellow = rotateImg(orientedYellow)
        img = if (selected) orientedRed else orientedYellow
    }

    private fun rotateAircraft(desiredHeading: Double) {
        heading = desiredHeading
        rotateImages()
    }

    fun update() {
        x += cos(headingRad) * (velocity / 350.0)
        y += sin(headingRad) * (velocity / 350.0)
    }

    fun pointIntersects(x: Int, y: Int)
            = this.x <= x && x <= this.x + this.img.width &&
              this.y <= y && y <= this.y + this.img.height

    fun onSelect() {
        selected = true
        img = orientedRed
    }

    fun onDeselect() {
        selected = false
        img = orientedYellow
    }

    init {
        rotateImages()
    }

}
