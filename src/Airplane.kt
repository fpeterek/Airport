import java.awt.geom.AffineTransform
import java.awt.image.AffineTransformOp
import java.awt.image.BufferedImage
import java.lang.Math.cos
import java.lang.Math.sin


class Airplane(sprite: BufferedImage, x: Int, y: Int, heading: Double, velocity: Int = 430) {

    val img = sprite
    var orientedImg: BufferedImage = img
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

    private fun rotateImg() {

        val locationX = img.getWidth(null) / 2.0
        val locationY = img.getHeight(null) / 2.0
        // + Math.PI because Graphics uses a different origin
        val tx = AffineTransform.getRotateInstance(headingRad + Math.PI / 2, locationX, locationY)
        val op = AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR)
        orientedImg = op.filter(img, null)

    }

    private fun rotateAircraft(desiredHeading: Double) {
        rotateImg()
    }

    fun update() {
        x += cos(headingRad) * (velocity / 350.0)
        y += sin(headingRad) * (velocity / 350.0)
    }

    init {
        rotateImg()
    }

}
