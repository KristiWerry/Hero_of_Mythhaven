package kristi.heroofmythhaven

import android.graphics.*
import android.util.Log
import kotlin.collections.ArrayList

class Player: GameObject{

    override var boundingBox: Rect
    private var player: ArrayList<Bitmap>
    private var playerLocation = PointF(0f,0f)
    private var playerFrame: Int = 0
    override var collisions: Array<Boolean> = arrayOf(false, false, false, false, false) // LEFT, UP, RIGHT, DOWN, Background collision
    private var isJumping = false
    private var velocityY = -5f
    private var mTime = 0f
    private val velocityX = 5f
    private val gravity = 9f
    private val time = 0.5f

    constructor(mBitmap: ArrayList<Bitmap>, startingPoint: PointF) {
        player = mBitmap
        playerLocation.x = startingPoint.x
        playerLocation.y = startingPoint.y
        boundingBox = Rect(playerLocation.x.toInt(), playerLocation.y.toInt(), (playerLocation.x + mBitmap[0].width).toInt(), (playerLocation.y + mBitmap[0].height).toInt())
        Log.i("HOM", "CENTER X OF PLAYER: " + boundingBox.centerX())

    }

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(player[playerFrame], playerLocation.x, playerLocation.y, null)
        //canvas.drawRect(boundingBox, Paint(Color.RED))
    }

    // Ignore input location because the player already has this information
    override fun update(userInput: UserInput, directions: Array<Boolean>) {

        if(!directions[0] && !directions[1] && !directions[2] && !directions[3]) { // No Collisions
            if(isJumping){
                if (directions[4]) {
                    playerLocation.y += gravity
                }
                else{
                    trajectory(playerLocation,time)
                }
            }
            else {
                mTime = 0f

                when (userInput) {
                    UserInput.NOINPUT -> {
                        playerLocation.y += gravity
                    }
                    UserInput.JUMP -> { //can't happen
                        // DO NOTHING
                    }
                    UserInput.RIGHT -> {
                        if (directions[4]) {
                            playerLocation.y += gravity
                        } else {
                            playerLocation.x += velocityX
                            playerLocation.y += gravity
                        }
                    }
                    UserInput.LEFT -> {
                        if (playerLocation.x >= 0) {
                            playerLocation.x -= velocityX
                        }
                        playerLocation.y += gravity
                    }
                }
            }
        }
        else if(!directions[0] && directions[1] && !directions[2] && !directions[3]) { // TOP
            when(userInput) {
                UserInput.NOINPUT -> {
                    playerLocation.y += gravity
                }
                UserInput.JUMP -> { //can't happen
                    // DO NOTHING
                }
                UserInput.RIGHT -> {
                    if (directions[4]) {
                        playerLocation.y += gravity
                    }
                    else {
                        playerLocation.x += velocityX
                        playerLocation.y += gravity
                    }
                }
                UserInput.LEFT -> {
                    if (playerLocation.x >= 0) {
                        playerLocation.x -= velocityX
                    }
                    playerLocation.y += gravity
                }
            }
            mTime = 0f
            isJumping = false
        }
        else if(!directions[0] && !directions[1] && !directions[2] && directions[3]) { // BOTTOM
            mTime = 0f
            isJumping = false
            when(userInput) {
                UserInput.NOINPUT -> {
                    // DO NOTHING
                }
                UserInput.JUMP -> {
                    trajectory(playerLocation, time)
                    isJumping = true
                }
                UserInput.RIGHT -> {
                    if (!directions[4]) {
                        playerLocation.x += velocityX
                    }
                }
                UserInput.LEFT -> {
                    if (playerLocation.x >= 0) {
                        playerLocation.x -= velocityX
                    }
                }
            }
        }
        else if(!directions[0] && !directions[1] && directions[2] && !directions[3]) { // RIGHT
            when(userInput) {
                UserInput.NOINPUT -> {
                    playerLocation.y += gravity
                }
                UserInput.JUMP -> { //can't happen
                    // DO NOTHING
                }
                UserInput.RIGHT -> {
                    if (directions[4]) {
                        playerLocation.y += gravity
                    }
                    else {
                        playerLocation.x += velocityX
                        playerLocation.y += gravity
                    }
                }
                UserInput.LEFT -> {
                    if (playerLocation.x >= 0) {
                        playerLocation.x -= velocityX
                    }
                    playerLocation.y += gravity
                }
            }
            mTime = 0f
            isJumping = false
        }
        else if(directions[0] && !directions[1] && !directions[2] && !directions[3]) { //LEFT
            when(userInput) {
                UserInput.NOINPUT -> {
                    playerLocation.y += gravity
                }
                UserInput.JUMP -> { //can't happen
                    // DO NOTHING
                }
                UserInput.RIGHT -> {
                    if (directions[4]) {
                        playerLocation.y += gravity
                    }
                    else {
                        playerLocation.x += velocityX
                        playerLocation.y += gravity
                    }
                }
                UserInput.LEFT -> {
                    if (playerLocation.x >= 0) {
                        playerLocation.x -= velocityX
                    }
                    playerLocation.y += gravity
                }
            }
            mTime = 0f
            isJumping = false
        }
        else if(!directions[0] && !directions[1] && directions[2] && directions[3]) { // BOTTOM RIGHT
            when(userInput) {
                UserInput.NOINPUT -> {
                    // Do NOTHING
                }
                UserInput.JUMP -> {
                    trajectory(playerLocation, time)
                    isJumping = true
                }
                UserInput.RIGHT -> {
                    // DO NOTHING
                }
                UserInput.LEFT -> {
                    if (playerLocation.x >= 0) {
                        playerLocation.x -= velocityX
                    }
                }
            }
            mTime = 0f
            isJumping = false
        }
        else if(directions[0] && !directions[1] && directions[2] && !directions[3]) { // BOTTOM LEFT
            when(userInput) {
                UserInput.NOINPUT -> {
                    // Do NOTHING
                }
                UserInput.JUMP -> {
                    trajectory(playerLocation, time)
                    isJumping = true
                }
                UserInput.RIGHT -> {
                    if (!directions[4]) {
                        playerLocation.x += velocityX
                    }
                }
                UserInput.LEFT -> {
                    // DO NOTHING
                }
            }
            mTime = 0f
            isJumping = false
        }
        else if(!directions[0] && directions[1] && directions[2] && !directions[3]) { // TOP RIGHT
            when(userInput) {
                UserInput.NOINPUT -> {
                    playerLocation.y += gravity
                }
                UserInput.JUMP -> { //can't happen
                    // DO NOTHING
                }
                UserInput.RIGHT -> {
                    playerLocation.x += velocityX
                    playerLocation.y += gravity
                }
                UserInput.LEFT -> {
                    if (playerLocation.x >= 0) {
                        playerLocation.x -= velocityX
                    }
                    playerLocation.y += gravity
                }
            }
            mTime = 0f
            isJumping = false
        }
        else if(directions[0] && directions[1] && !directions[2] && !directions[3]) { // TOP LEFT
            when(userInput) {
                UserInput.NOINPUT -> {
                    playerLocation.y += gravity
                }
                UserInput.JUMP -> { //can't happen
                    // DO NOTHING
                }
                UserInput.RIGHT -> {
                    if (directions[4]) {
                        playerLocation.y += gravity
                    }
                    else {
                        playerLocation.x += velocityX
                        playerLocation.y += gravity
                    }
                }
                UserInput.LEFT -> {
                    playerLocation.y += gravity
                }
            }
            mTime = 0f
            isJumping = false
        }
        else {
            //mTime = 0f
        }

        //Log.i("HOM", "Jump Coor: "+playerLocation.y)
        boundingBox.left = playerLocation.x.toInt()
        boundingBox.right = (playerLocation.x + player[0].width).toInt()
        boundingBox.top = playerLocation.y.toInt()
        boundingBox.bottom = playerLocation.y.toInt() + player[0].height
    }

    fun getLocation(point: PointF) {
        point.x = playerLocation.x
        point.y = playerLocation.y
    }

    override fun collision(pObj: Physics){

    }

    override fun trajectory(point: PointF, time: Float){
        // x(t) = x0 + v0 * t + 0.5g * t^2
        mTime += time
        point.x += (velocityX * (mTime))
        point.y -= (20f * (mTime) - 0.5f*gravity*mTime*mTime)
    }
}