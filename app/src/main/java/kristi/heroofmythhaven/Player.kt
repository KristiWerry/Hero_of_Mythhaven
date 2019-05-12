package kristi.heroofmythhaven

import android.graphics.*
import kotlin.collections.ArrayList

class Player: GameObject{
    override var location = PointF(0f,0f)
    private var player: ArrayList<Bitmap>
    private var playerFrame = 0
    private var mTime = 0f

    // Physics interface (which game object requires implementation of
    override var velocityY = 0f
    override var boundingBox: RectF
    override var velocityX = 0f
    override var gravity = GRAVITY
    override var time = TIME

    constructor(mBitmap: ArrayList<Bitmap>, startingPoint: PointF) {
        player = mBitmap
        this.location.x = startingPoint.x
        this.location.y = startingPoint.y
        boundingBox = RectF(this.location.x, this.location.y, this.location.x + mBitmap[0].width, this.location.y + mBitmap[0].height)
    }

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(player[playerFrame], this.location.x, this.location.y, null)
        //canvas.drawRect(boundingBox, Paint(Color.RED))
    }

    // Ignore input location because the player already has this information
    override fun update(context: Boolean) {
        if (context) {
            move(this.location)
        }

        // Update the players bounding box
        boundingBox.left = this.location.x
        boundingBox.right = this.location.x + player[0].width
        boundingBox.top = this.location.y
        boundingBox.bottom = this.location.y + player[0].height
    }

    fun getLocation(point: PointF) {
        point.x = this.location.x
        point.y = this.location.y
    }

    override fun move(point: PointF){
        mTime += time
        //Log.i("HOM", "MTIME: " + mTime)
        point.x += (velocityX * (time))
        point.y -= (velocityY * (mTime) - 0.5f * gravity * mTime * mTime)
    }

    override fun collision(pObj: Physics): Direction{
        return Direction.NONE
    }

    fun resetTime() {
        mTime = 0f
    }

    fun getPlayerWidth(): Int{
        return player[0].width
    }

    fun getPlayerHeight(): Int{
        return player[0].height
    }
}