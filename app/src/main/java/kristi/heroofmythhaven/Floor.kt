package kristi.heroofmythhaven

import android.graphics.*
import android.util.Log
import kotlin.math.abs

class Floor: GameObject {
    override var location: PointF
    override var velocityX: Float = 0f
    override var velocityY: Float = 0f
    override var gravity: Float = 0f
    override var time: Float = 0f
    override var boundingBox: RectF
    override var mTime = 0f

    constructor(mBitmaps: List<Bitmap>, screenSize: PointF) {
        location = PointF(0f, screenSize.y * 0.79f)
        boundingBox = RectF(-300f, screenSize.y * 0.79f, screenSize.x, screenSize.y + 300f)
    }

    override fun collision(pObj: Physics): Direction {
        if (RectF.intersects(pObj.boundingBox, boundingBox)){
            val w = 0.5 * (boundingBox.width() + pObj.boundingBox.width()) // Average width
            val h = 0.5 * (boundingBox.height() + pObj.boundingBox.height()) // Average height
            val dx = boundingBox.centerX() - pObj.boundingBox.centerX() // difference of centers
            val dy = boundingBox.centerY() - pObj.boundingBox.centerY()

            if (abs(dx) <= w && abs(dy) <= h) {
                val wy = w * dy
                val hx = h * dx

                if (wy > hx) {
                    if (wy > -hx) {
                        pObj.velocityY = 0f// BOTTOM
                        pObj.gravity = 0f
                        pObj.location.y -= (h - dy).toFloat()
//                        Log.i("HOM","DOWNNNNNN------------------------------------------------------")
//                        if(pObj.location.y + pObj.boundingBox.height()> boundingBox.top) { //testing to get player not to fall into ground
//                            Log.i("HOM", "Player Location "+ (pObj.location.y + pObj.boundingBox.height()))
//                            Log.i("HOM", "Bounding Box top"+ boundingBox.top)
//                            Log.i("HOM", "Bounding Box height"+ boundingBox.height())
//                            pObj.location.y = boundingBox.top - pObj.boundingBox.height()
//                        }
                        return Direction.BOTTOM
                    }
                    else {
                        pObj.velocityX = 0f// LEFT
//                        Log.i("HOM","LEFT")
                        return Direction.LEFT
                    }
                }
                else {
                    if (wy > -hx) {
                        pObj.velocityX = 0f // RIGHT
//                        Log.i("HOM", "RIGHT")
                        return Direction.RIGHT
                    }
                    else {
                        pObj.velocityY = 0f // TOP
//                        Log.i("HOM", "UP")
                        return Direction.TOP
                    }
                }
            }
        }
        return Direction.NONE
    }


    override fun move(point: PointF) {
    }

    override fun draw(canvas: Canvas) {
        //canvas.drawRect(boundingBox, Paint(Color.RED))
    }

    override fun update(context: Boolean) {
        //Log.i("HOM", "FLOOR LOCATION: " + boundingBox.top)
    }
}