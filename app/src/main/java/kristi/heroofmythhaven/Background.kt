package kristi.heroofmythhaven

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Point
import android.graphics.PointF

class Background: DrawObjects {
    private var background1: Bitmap
    private var bLocation1: PointF
    private var bLocation2: PointF
    private var background2: Bitmap
    private var bList: List<Bitmap>
    var numCompletedBackgrounds = 0
    private val screenSize: PointF

    constructor(mBitmaps: List<Bitmap>, screenSize: PointF) {
        bList = mBitmaps
        background1 = bList[0] // initial background
        background2 = bList[1]
        this.screenSize = screenSize
        bLocation1 = PointF(0f,0f)
        bLocation2 = PointF(screenSize.x, 0f)
    }

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(background1, bLocation1.x, bLocation1.y, null)
        canvas.drawBitmap(background2, bLocation2.x, bLocation2.y, null)
    }

    override fun update(userInput: UserInput, location: PointF, isMiddle: Float) {
        if (bLocation1.x < 0f-screenSize.x) {
            bLocation1.x = screenSize.x
            numCompletedBackgrounds++
        }
        if (bLocation2.x < 0f-screenSize.x) {
            bLocation2.x = screenSize.x
            numCompletedBackgrounds++
        }
        if ((location.x >= isMiddle) && userInput==UserInput.RIGHT) {
            bLocation1.x -= 5
            bLocation2.x -= 5
        }
    }
}