package kristi.heroofmythhaven

import android.graphics.*
import android.util.Log
import java.nio.file.Files.move
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
    private var velocityX = 3f
    private var gravity = 9f
    private val time = 0.5f
    private var canJump = 0

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
            resetGravity()
            if(isJumping){
                if (directions[4]) {
                    velocityX = 0f
                    trajectory(playerLocation, time)
                }
                else{
                    if (playerLocation.x >= 0) {
                        when (userInput) {
                            UserInput.RIGHT -> {
                                resetVelocity()
                            }
                            UserInput.LEFT -> {
                                velocityX = -5f
                            }
                            UserInput.NOINPUT -> {
                                velocityX = 0f
                            }
                        }
                    }
                    trajectory(playerLocation,time)
                }
            }
            else {
                mTime = 0f

                when (userInput) {
                    UserInput.NOINPUT -> {
                        velocityX = 0f
                        move()
                    }
                    UserInput.JUMP -> { //can't happen
                        // DO NOTHING
                    }
                    UserInput.RIGHT -> {
                        if (directions[4]) {
                            velocityX = 0f
                            move()
                        } else {
                            resetVelocity()
                            move()
                        }
                    }
                    UserInput.LEFT -> {
                        velocityX = -5f
                        move()
                    }
                }
            }
        }
        else if(!directions[0] && directions[1] && !directions[2] && !directions[3]) { // TOP
            resetGravity()
            when(userInput) {
                UserInput.NOINPUT -> {
                    velocityX = 0f
                    move()
                }
                UserInput.JUMP -> { //can't happen
                    // DO NOTHING
                }
                UserInput.RIGHT -> {
                    if (directions[4]) {
                        velocityX = 0f
                        move()
                    }
                    else {
                        resetVelocity()
                        move()
                    }
                }
                UserInput.LEFT -> {
                    velocityX = -5f
                    move()
                }
            }
            mTime = 0f
            isJumping = false
        }
        else if(!directions[0] && !directions[1] && !directions[2] && directions[3]) { // BOTTOM
            mTime = 0f
            isJumping = false
            gravity = 0f
            when(userInput) {
                UserInput.NOINPUT -> {
                    // DO NOTHING
                }
                UserInput.JUMP -> {
                    velocityX = 0f
                    trajectory(playerLocation, time)
                    isJumping = true
                }
                UserInput.RIGHT -> {
                    if (!directions[4]) {
                        resetVelocity()
                        move()
                    }
                }
                UserInput.LEFT -> {
                    velocityX = -5f
                    move()
                }
            }
        }
        else if(!directions[0] && !directions[1] && directions[2] && !directions[3]) { // RIGHT
            resetGravity()
            when(userInput) {
                UserInput.NOINPUT -> {
                    velocityX = 0f
                    move()
                }
                UserInput.JUMP -> { //can't happen
                    // DO NOTHING
                }
                UserInput.RIGHT -> {
                        velocityX = 0f
                        move()
                }
                UserInput.LEFT -> {
                    velocityX = -5f
                    move()
                }
            }
            mTime = 0f
            isJumping = false
        }
        else if(directions[0] && !directions[1] && !directions[2] && !directions[3]) { //LEFT
            resetGravity()
            when(userInput) {
                UserInput.NOINPUT -> {
                    velocityX = 0f
                }
                UserInput.JUMP -> { //can't happen

                }
                UserInput.RIGHT -> {
                    if (directions[4]) {
                        velocityX = 0f
                        move()
                    }
                    else {
                        resetVelocity()
                        move()
                    }
                }
                UserInput.LEFT -> {
                    velocityX = -5f
                    move()
                }
            }
            mTime = 0f
            isJumping = false
        }
        else if(!directions[0] && !directions[1] && directions[2] && directions[3]) { // BOTTOM RIGHT
            when(userInput) {
                UserInput.NOINPUT -> {
                    gravity = 0f
                }
                UserInput.JUMP -> {
                    resetGravity()
                    velocityX = 0f
                    trajectory(playerLocation, time)
                    isJumping = true
                }
                UserInput.RIGHT -> {
                    gravity = 0f
                }
                UserInput.LEFT -> {
                    velocityX = -5f
                    gravity = 0f
                    move()
                }
            }
            mTime = 0f
            isJumping = false
        }
        else if(directions[0] && !directions[1] && directions[2] && !directions[3]) { // BOTTOM LEFT
            when(userInput) {
                UserInput.NOINPUT -> {
                    gravity = 0f
                }
                UserInput.JUMP -> {
                    resetGravity()
                    velocityX = 0f
                    trajectory(playerLocation, time)
                    isJumping = true
                }
                UserInput.RIGHT -> {
                    gravity = 0f
                    if (!directions[4]) {
                        resetVelocity()
                        move()
                    }
                }
                UserInput.LEFT -> {
                    gravity = 0f
                    velocityX = 0f
                }
            }
            mTime = 0f
            isJumping = false
        }
        else if(!directions[0] && directions[1] && directions[2] && !directions[3]) { // TOP RIGHT
            resetGravity()
            when(userInput) {
                UserInput.NOINPUT -> {
                    velocityX = 0f
                    resetGravity()
                    move()
                }
                UserInput.JUMP -> { //can't happen
                    // DO NOTHING
                }
                UserInput.RIGHT -> {
                    velocityX = 0f
                    move()
                }
                UserInput.LEFT -> {
                    velocityX = -5f
                    move()
                }
            }
            mTime = 0f
            isJumping = false
        }
        else if(directions[0] && directions[1] && !directions[2] && !directions[3]) { // TOP LEFT
            resetGravity()
            when(userInput) {
                UserInput.NOINPUT -> {
                    velocityX = 0f
                    move()
                }
                UserInput.JUMP -> { //can't happen
                    // DO NOTHING
                }
                UserInput.RIGHT -> {
                    if (directions[4]) {
                        velocityX = 0f
                        move()
                    }
                    else {
                        velocityX = 5f
                        move()
                    }
                }
                UserInput.LEFT -> {
                    velocityX = 0f
                    move()
                }
            }
            mTime = 0f
            isJumping = false
        }
        else {
            //mTime = 0f
        }
//        val temp = 803.43f - player[0].height - 10
//        if (playerLocation.y >= temp) {
//            playerLocation.y = temp
//        }
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

    private fun resetVelocity() {
        velocityX = 5f
    }
    private fun resetGravity() {
        gravity = 5f
    }


    private fun move() {
        val temp = 803.43f - player[0].height
        playerLocation.x += velocityX
        if (playerLocation.x <= 0) {
            playerLocation.x = 1f
        }

        playerLocation.y += gravity
//        if (playerLocation.y >= temp) {
//            playerLocation.y = temp
//        }

//        if (playerLocation.y >= temp) {

//        }
//        else {
//            playerLocation.y = temp
//        }
    }

    override fun collision(pObj: Physics){

    }

    override fun trajectory(point: PointF, time: Float){
        // x(t) = x0 + v0 * t + 0.5g * t^2
        if (canJump == 1) {
            canJump = 0
            val temp = 803.43f - player[0].height + 1
            mTime += time
            point.x += (velocityX * (mTime))
            if (point.x <= 0) {
                point.x = 1f
            }

//        if (playerLocation.y >= temp) {
            point.y -= (18f * (mTime) - 0.5f * gravity * mTime * mTime)
//        }
//        else {
//            playerLocation.y = temp
//        }
            if (point.y >= temp) {
                point.y = temp
            }
        }
        else {
            canJump++
        }
    }
}