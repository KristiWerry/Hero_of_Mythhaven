package kristi.heroofmythhaven

import android.graphics.*
import android.util.Log
import kotlin.math.abs

class Landing:GameObject{
    override var boundingBox: Rect
    override var collisions: Array<Boolean> = arrayOf(false, false, false, false)

    private var landing: Bitmap
    private var xCoor: Float
    private var yCoor: Float

    constructor(mBitmap: Bitmap, startingPoint: PointF) {
        landing = mBitmap
        xCoor = startingPoint.x
        yCoor = startingPoint.y
        boundingBox = Rect(xCoor.toInt(), yCoor.toInt(), (xCoor + mBitmap.width).toInt(), (yCoor + mBitmap.height).toInt())
    }

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(landing, xCoor, yCoor, null)
    }

    override fun update(userInput: UserInput,  directions: Array<Boolean>) {
        if (directions[4] && userInput == UserInput.RIGHT && !directions[2]) {
            xCoor -= 5
            boundingBox.left = xCoor.toInt()
            boundingBox.right = (xCoor + landing.width).toInt()
        }
    }

    override fun trajectory(point: PointF, time: Float){
        // Do Nothings
    }

    override fun collision(pObj: Physics){
        if (boundingBox.intersect(pObj.boundingBox)){
            val w = 0.5 * (boundingBox.width() + pObj.boundingBox.width())
            val h = 0.5 * (boundingBox.height() + pObj.boundingBox.height())
            val dx = boundingBox.centerX() - pObj.boundingBox.centerX()
            val dy = boundingBox.centerY() - pObj.boundingBox.centerY()
            if (abs(dx) <= w && abs(dy) <= h) {
                val wy = w * dy
                val hx = h * dx

                if (wy > hx) {
                    if (wy > -hx) {
                        pObj.collisions[1] = true
                    }
                    else {
                        pObj.collisions[0] = true
                    }
                }
                else {
                    if (wy > -hx) {
                        pObj.collisions[2] = true
                    }
                    else {
                        pObj.collisions[3] = true
                    }

                }
            }
        }
    }
}