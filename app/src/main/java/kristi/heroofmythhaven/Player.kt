package kristi.heroofmythhaven

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PointF

class Player: DrawObjects{

    private var player: Bitmap
    private var xCoor: Float
    private var yCoor: Float

    constructor(mBitmap: Bitmap, startingPoint: PointF) {
        player = mBitmap
        xCoor = startingPoint.x
        yCoor = startingPoint.y
    }

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(player, xCoor, yCoor, null)
    }

    // Ignore input location because the player already has this information
    override fun update(userInput: UserInput, location: PointF, isMiddle: Float) {
        if ((location.x<isMiddle) && location.x >= 0) {
            when (userInput) {
                UserInput.LEFT -> {
                    xCoor -= 5
                }
                UserInput.RIGHT -> {
                    xCoor += 5
                }
                else -> {}//Do nothing
            }
        }
        else if(location.x == isMiddle && userInput == UserInput.LEFT){
            xCoor -= 5
        }
        else if(location.x <= 0f && userInput == UserInput.RIGHT){
            xCoor += 5
        }
        else {
        }
    }

    fun getLocation(point: PointF) {
        point.x = xCoor
        point.y = yCoor
    }
}