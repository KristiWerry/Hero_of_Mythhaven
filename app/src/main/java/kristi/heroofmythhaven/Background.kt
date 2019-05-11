package kristi.heroofmythhaven

import android.graphics.*
import android.util.Log
import kotlin.math.abs
import kotlin.math.floor

class Background: GameObject {
    override var boundingBox: Rect
    private var floorBoundingBox: Rect
    override var collisions: Array<Boolean> = arrayOf(false, false, false, false)

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
        boundingBox = Rect((screenSize.x/2).toInt(), 0, screenSize.x.toInt(), screenSize.y.toInt())
        floorBoundingBox = Rect(0, (screenSize.y * 0.79f).toInt(), screenSize.x.toInt(), screenSize.y.toInt()) //left, top, right, bottom
        Log.i("HOM", "TOP OF FLOOR: " + screenSize.y * 0.79f)
        Log.i("HOM", "BOTTOM OF FLOOR: " + screenSize.y)
    }

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(background1, bLocation1.x, bLocation1.y, null)
        canvas.drawBitmap(background2, bLocation2.x, bLocation2.y, null)
        canvas.drawRect(floorBoundingBox, Paint(Color.RED))
        //canvas.drawRect(boundingBox, Paint(Color.RED))
    }

    override fun update(userInput: UserInput, directions: Array<Boolean>) {
        if (bLocation1.x < 0f-screenSize.x) {
            bLocation1.x = screenSize.x
            numCompletedBackgrounds++
        }
        if (bLocation2.x < 0f-screenSize.x) {
            bLocation2.x = screenSize.x
            numCompletedBackgrounds++
        }
        if (userInput == UserInput.RIGHT && directions[4] && !directions[2]) {
            bLocation1.x -= 5
            bLocation2.x -= 5
        }

    }

    override fun trajectory(point: PointF, time: Float){

    }

    override fun collision(pObj: Physics){
        detectCollisions(pObj, boundingBox)
        detectCollisions(pObj, floorBoundingBox)
    }

    private fun detectCollisions(pObj: Physics, bBox: Rect) {
        if (Rect.intersects(pObj.boundingBox, bBox)){
            val w = 0.5 * (bBox.width() + pObj.boundingBox.width()) // Average width
            val h = 0.5 * (bBox.height() + pObj.boundingBox.height()) // Average height
            val dx = bBox.centerX() - pObj.boundingBox.centerX() // difference of centers
            val dy = bBox.centerY() - pObj.boundingBox.centerY()

            if (abs(dx) <= w && abs(dy) <= h) {
                val wy = w * dy
                val hx = h * dx

                if (wy > hx) {
                    if (wy > -hx) {
                        pObj.collisions[3] = true // UP
//                        Log.i("HOM","DOWNNNNNN")
                    }
                    else {
                        pObj.collisions[0] = true // LEFT
//                        Log.i("HOM","LEFT")
                    }
                }
                else {
                    if (wy > -hx) {
                        pObj.collisions[4] = true // RIGHT
//                        Log.i("HOM", "RIGHT")
                    }
                    else {
                        pObj.collisions[1] = true // DOWN
//                        Log.i("HOM", "UP")
                    }
                }
            }
        }
    }
}