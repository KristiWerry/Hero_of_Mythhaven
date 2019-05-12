package kristi.heroofmythhaven

import android.graphics.*

class Chest: GameObject{
    override var location: PointF
    override var velocityX: Float = VX
    override var velocityY: Float = 0f
    override var gravity: Float = 0f
    override var time: Float = TIME

    override var boundingBox = RectF(0f,0f,0f,0f)
    private var chest: Bitmap

    constructor(mBitmap: Bitmap, startingPoint: PointF) {
        chest = mBitmap
        location = startingPoint
    }

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(chest, location.x, location.y, null)
    }

    override fun update(context: Boolean) {
        if (context) {
            location.x -= velocityX * time
        }
    }

    override fun move(point: PointF) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun collision(pObj: Physics): Direction{
        return Direction.NONE
    }
}