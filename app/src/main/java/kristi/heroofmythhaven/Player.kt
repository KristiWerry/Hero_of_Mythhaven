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
    private var canJump = 0

    // Physics interface (which game object requires implementation of
    override var velocityY = 0f
    override var boundingBox: Rect
    override var velocityX = 0f
    override var gravity = 9f
    override var time = 0.5f

    constructor(mBitmap: ArrayList<Bitmap>, startingPoint: PointF) {
        player = mBitmap
        playerLocation.x = startingPoint.x
        playerLocation.y = startingPoint.y
        boundingBox = Rect(playerLocation.x.toInt(), playerLocation.y.toInt(), (playerLocation.x + mBitmap[0].width).toInt(), (playerLocation.y + mBitmap[0].height).toInt())
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
        boundingBox.left = playerLocation.x.toInt()
        boundingBox.right = (playerLocation.x + player[0].width).toInt()
        boundingBox.top = playerLocation.y.toInt()
        boundingBox.bottom = playerLocation.y.toInt() + player[0].height
    }

    fun getLocation(point: PointF) {
        point.x = playerLocation.x
        point.y = playerLocation.y
    }

    override fun move(point: PointF){
        val temp = 803.43f - player[0].height + 1
        mTime += time

        point.x += (velocityX * (time))
        if (point.x <= 0) {
            point.x = 1f
        }

        point.y -= (velocityY * (time) - 0.5f * gravity * time * time)

        if (point.y >= temp) {
            point.y = temp
        }
    }

    override fun collision(pObj: Physics): Boolean{
        return false
    }

    fun resetTime() {
        mTime = 0f
    }
}