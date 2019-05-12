package kristi.heroofmythhaven

import android.graphics.*
import android.util.Log
import java.security.AccessControlContext
import kotlin.collections.ArrayList

class Player: GameObject{
    private var player: ArrayList<Bitmap>
    private var playerLocation = PointF(0f,0f)
    private var playerFrame = 0
    private var mTime = 0f

    // Physics interface (which game object requires implementation of
    override var velocityY = 0f
    override var boundingBox: RectF
    override var velocityX = 0f
    override var gravity = 9f
    override var time = 0.5f

    constructor(mBitmap: ArrayList<Bitmap>, startingPoint: PointF) {
        player = mBitmap
        playerLocation.x = startingPoint.x
        playerLocation.y = startingPoint.y
        boundingBox = RectF(playerLocation.x, playerLocation.y, playerLocation.x + mBitmap[0].width, playerLocation.y + mBitmap[0].height)
    }

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(player[playerFrame], playerLocation.x, playerLocation.y, null)
        //canvas.drawRect(boundingBox, Paint(Color.RED))
    }

    // Ignore input location because the player already has this information
    override fun update(context: Boolean) {
        if (context) {
            move(playerLocation)
        }

        // Update the players bounding box
        boundingBox.left = playerLocation.x
        boundingBox.right = playerLocation.x + player[0].width
        boundingBox.top = playerLocation.y
        boundingBox.bottom = playerLocation.y + player[0].height
    }

    fun getLocation(point: PointF) {
        point.x = playerLocation.x
        point.y = playerLocation.y
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

    fun setPlayerLocation(newLocation: PointF) {
        playerLocation.x = newLocation.x
        playerLocation.y = newLocation.y
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