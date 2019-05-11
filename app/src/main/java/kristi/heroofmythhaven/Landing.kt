package kristi.heroofmythhaven

import android.graphics.*
import android.util.Log
import kotlin.math.abs

class Landing: GameObject{
    // Physics Interface
    override var velocityX: Float = 5f
    override var velocityY: Float = 0f
    override var gravity: Float = 0f
    override var time: Float = 0.5f
    override var boundingBox: Rect

    private var landing: Bitmap
    private var location: PointF

    constructor(mBitmap: Bitmap, startingPoint: PointF) {
        landing = mBitmap
        location = PointF(startingPoint.x, startingPoint.y)
        boundingBox = Rect(location.x.toInt(), location.y.toInt(), (location.x + mBitmap.width).toInt(), (location.y + mBitmap.height).toInt())
    }

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(landing, location.x, location.y, null)
    }

    override fun update(context: Boolean) {
        if (context) {
            move(location)
        }
        boundingBox.left = location.x.toInt()
        boundingBox.right = (location.x + landing.width).toInt()
    }

    override fun move(point: PointF) {
        point.x -= velocityX*time
    }

    override fun collision(pObj: Physics): Boolean{
        if (Rect.intersects(pObj.boundingBox, boundingBox)){
            val w = 0.5 * (boundingBox.width() + pObj.boundingBox.width())
            val h = 0.5 * (boundingBox.height() + pObj.boundingBox.height())
            val dx = boundingBox.centerX() - pObj.boundingBox.centerX()
            val dy = boundingBox.centerY() - pObj.boundingBox.centerY()
            if (abs(dx) <= w && abs(dy) <= h) {
                val wy = w * dy
                val hx = h * dx

                if (wy > hx) {
                    if (wy > -hx) {
                        pObj.velocityY = 1f // DOWN
                        pObj.gravity = 0f
                    }
                    else {
                        pObj.velocityX = 1f // LEFT
                    }
                }
                else {
                    if (wy > -hx) {
                        pObj.velocityX = -1f // RIGHT
                    }
                    else {
                        pObj.velocityY = 1f // UP
                    }

                }
            }
            return true
        }
        return false
    }
}