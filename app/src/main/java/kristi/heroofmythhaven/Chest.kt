package kristi.heroofmythhaven

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PointF

class Chest: DrawObjects{

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

    override fun update(userInput: UserInput, location: PointF, isMiddle: Float) {
        if ((location.x >= isMiddle) && userInput==UserInput.RIGHT) {
            xCoor -= 5
        }
    }
}