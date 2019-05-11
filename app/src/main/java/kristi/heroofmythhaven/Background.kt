package kristi.heroofmythhaven

import android.graphics.*
import android.util.Log
import kotlin.math.abs

class Background: GameObject {
    override var velocityX: Float = 5f
    override var velocityY: Float = 0f
    override var gravity: Float = 0f
    override var time: Float = 0.5f
    override var boundingBox: Rect
    private var floorBoundingBox: Rect

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
    }

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(background1, bLocation1.x, bLocation1.y, null)
        canvas.drawBitmap(background2, bLocation2.x, bLocation2.y, null)
        //canvas.drawRect(floorBoundingBox, Paint(Color.RED))
        //canvas.drawRect(boundingBox, Paint(Color.RED))
    }

    override fun update(context: Boolean) {
        if (bLocation1.x < 0f-screenSize.x) {
            bLocation1.x = screenSize.x
            numCompletedBackgrounds++
        }
        if (bLocation2.x < 0f-screenSize.x) {
            bLocation2.x = screenSize.x
            numCompletedBackgrounds++
        }

        if (context) {
            move(bLocation1)
            move(bLocation2)
        }

    }

    override fun move(point: PointF) {
        point.x -= velocityX*time
    }

    override fun collision(pObj: Physics): Boolean{
        if (Rect.intersects(pObj.boundingBox, boundingBox)){
            val w = 0.5 * (boundingBox.width() + pObj.boundingBox.width()) // Average width
            val h = 0.5 * (boundingBox.height() + pObj.boundingBox.height()) // Average height
            val dx = boundingBox.centerX() - pObj.boundingBox.centerX() // difference of centers
            val dy = boundingBox.centerY() - pObj.boundingBox.centerY()

            if (abs(dx) <= w && abs(dy) <= h) {
                val wy = w * dy
                val hx = h * dx

                if (wy > hx) {
                    if (wy > -hx) {
                        pObj.velocityY = 0f// DOWN
//                        Log.i("HOM","DOWNNNNNN")
                    }
                    else {
                        pObj.velocityX = 0F// LEFT
//                        Log.i("HOM","LEFT")
                    }
                }
                else {
                    if (wy > -hx) {
                        pObj.velocityX = -1f // RIGHT
//                        Log.i("HOM", "RIGHT")
                    }
                    else {
                        pObj.velocityY = 0f // UP
//                        Log.i("HOM", "UP")
                    }
                }
            }
            return true
        }

        return false
    }
}