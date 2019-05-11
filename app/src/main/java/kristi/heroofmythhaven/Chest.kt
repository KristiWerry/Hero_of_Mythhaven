package kristi.heroofmythhaven

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PointF
import android.graphics.Rect

class Chest: GameObject{
    override var velocityX: Float = 0f
    override var velocityY: Float = 0f
    override var gravity: Float = 0f
    override var time: Float = 0f

    override var boundingBox: Rect = Rect(0,0,0,0)

    private var chest: Bitmap
    private var xCoor: Float
    private var yCoor: Float

    constructor(mBitmap: Bitmap, startingPoint: PointF) {
        chest = mBitmap
        xCoor = startingPoint.x
        yCoor = startingPoint.y
    }

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(chest, xCoor, yCoor, null)
    }

    override fun update(context: Boolean) {
        if (context) {
            xCoor -= 5
        }
    }

    override fun move(point: PointF) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun trajectory(point: PointF, time: Float){

    }

    override fun collision(pObj: Physics): Boolean{
        return false
    }
}