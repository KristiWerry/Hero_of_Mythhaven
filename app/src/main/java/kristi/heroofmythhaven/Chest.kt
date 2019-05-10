package kristi.heroofmythhaven

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PointF
import android.graphics.Rect

class Chest: GameObject{
    override var boundingBox: Rect = Rect(0,0,0,0)
    override var collisions: Array<Boolean> = arrayOf(false, false, false, false)

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

    override fun update(userInput: UserInput, directions: Array<Boolean>) {
        if (directions[4] && userInput==UserInput.RIGHT) {
            xCoor -= 5
        }
    }

    override fun trajectory(point: PointF, time: Float){

    }

    override fun collision(pObj: Physics) {

    }
}