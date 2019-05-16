package kristi.heroofmythhaven

import android.graphics.*
import android.support.v4.view.ViewCompat.animate
import android.util.Log
import kotlin.collections.ArrayList
import kotlin.math.abs

class Player: GameObject, CharacterAttributes{
    // Character Attributes Interface Variables
    override var hp: Int
    override var damage: Int

    private var player: ArrayList<Bitmap>
    private var playerFrame = 0
    private var rightAttackBoundingBox: RectF
    private var leftAttackBoundingBox: RectF

    var facingRight = true
    var isWalking = false
    var isJumping = false
    var isAttacking = false
    private var clock = 0

    // Physics interface (which game object requires implementation of
    override var velocityY = 0f
    override var boundingBox: RectF
    override var velocityX = 0f
    override var gravity = GRAVITY
    override var time = TIME
    override var mTime = 0f
    override var location = PointF(0f,0f)

    constructor(mBitmap: ArrayList<Bitmap>, startingPoint: PointF, hp: Int, damage: Int) {
        player = mBitmap
        this.hp = hp
        this.damage = damage
        this.location.x = startingPoint.x
        this.location.y = startingPoint.y
        boundingBox = RectF(this.location.x, this.location.y, this.location.x + mBitmap[0].width, this.location.y + mBitmap[0].height)
        leftAttackBoundingBox = RectF(this.location.x - 50, this.location.y, this.location.x + mBitmap[0].width, this.location.y + mBitmap[0].height)
        rightAttackBoundingBox = RectF(this.location.x, this.location.y, this.location.x + mBitmap[0].width + 50, this.location.y + mBitmap[0].height)
    }

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(player[playerFrame], this.location.x, this.location.y, null)
//        canvas.drawRect(boundingBox, Paint(Color.RED))
    }

    override fun update(context: Boolean) {
//        Log.i("HOM", "PLAYER HP: " + hp)
        if (context) {
            move(location)
        }

        // Update the players bounding box
        boundingBox.left = this.location.x
        boundingBox.right = this.location.x + player[0].width
        boundingBox.top = this.location.y
        boundingBox.bottom = this.location.y + player[0].height

        //update players attack bounding box
        leftAttackBoundingBox.left = this.location.x - 50
        leftAttackBoundingBox.right = this.location.x + player[0].width
        leftAttackBoundingBox.top = this.location.y
        leftAttackBoundingBox.bottom = this.location.y + player[0].height

        rightAttackBoundingBox.left = this.location.x
        rightAttackBoundingBox.right = this.location.x + player[0].width + 50
        rightAttackBoundingBox.top = this.location.y
        rightAttackBoundingBox.bottom = this.location.y + player[0].height
    }

    fun animate(context: GameView) {
        if(isWalking && facingRight) { //walk right
             if(clock++ == 5){
                 if(playerFrame == 0) {
                     playerFrame = 1
                 }
                 else {
                     playerFrame = 0
                 }
                 clock = 0
             }
        }
        else if(isWalking && !facingRight) { //walk left
            if(clock++ == 5){
                if(playerFrame == 4) {
                    playerFrame = 5
                }
                else {
                    playerFrame = 4
                }
                clock = 0
            }
        }
        else if(isJumping && facingRight) { //jumping right
            if(clock++ == 5){
                if(playerFrame == 0) {
                    playerFrame = 3
                }
                else {
                    playerFrame = 0
                }
                clock = 0
            }
        }
        else if(isJumping && !facingRight) { //jumping left
            if(clock++ == 5){
                if(playerFrame == 4) {
                    playerFrame = 7
                }
                else {
                    playerFrame = 4
                }
                clock = 0
            }
        }
        else if(isAttacking && facingRight) { //attack right
            if(clock++ == 5){
                if(playerFrame == 0) {
                    playerFrame = 2
                }
                else {
                    playerFrame = 0
                }
                clock = 0
            }
        }
        else if(isAttacking && !facingRight) {//attack left
            if(clock++ == 5){
                if(playerFrame == 4) {
                    playerFrame = 6
                }
                else {
                    playerFrame = 4
                }
                clock = 0
            }
        }
        else {
            if(facingRight){
                playerFrame = 0
            }
            else {
                playerFrame = 4
            }
        }


    }

    fun getLocation(point: PointF) {
        point.x = this.location.x
        point.y = this.location.y
    }

    override fun move(point: PointF){
        mTime += time
        point.x += (velocityX * (time))
        point.y -= (velocityY * (mTime) - 0.5f * gravity * mTime * mTime)
    }

    override fun collision(pObj: Physics): Direction{
        val bBox: RectF

        if (facingRight) {
            bBox = rightAttackBoundingBox
        }
        else {
            bBox = leftAttackBoundingBox
        }
//
        if (RectF.intersects(pObj.boundingBox, bBox)){
            val w = 0.5 * (bBox.width() + pObj.boundingBox.width())
            val h = 0.5 * (bBox.height() + pObj.boundingBox.height())
            val dx = bBox.centerX() - pObj.boundingBox.centerX()
            val dy = bBox.centerY() - pObj.boundingBox.centerY()

            if (abs(dx) <= w && abs(dy) <= h) {
                val wy = w * dy
                val hx = h * dx

                if (wy > hx) {
                    if (wy > -hx) {
                        return Direction.BOTTOM
                    }
                    else { // LEFT
                        pObj.location.x += (w + dx).toFloat() + 50
                        pObj.location.y -= (h - dy).toFloat()
                        pObj.velocityY = -VY
                        pObj.gravity = GRAVITY
                        pObj.mTime = 0f
                        return Direction.LEFT
                    }
                }
                else {
                    if (wy > -hx) { // RIGHT
                        pObj.location.x -= (w - dx).toFloat()
                        pObj.location.y -= (h - dy).toFloat()
                        pObj.velocityY = -VY
                        pObj.gravity = GRAVITY
                        pObj.mTime = 0f
                        return Direction.RIGHT
                    }
                    else {
                        pObj.location.y -= (h - dy).toFloat()
                        if ((pObj.location.x - location.x) > 0) {
                            pObj.location.x += (w + dx).toFloat() + 50
                        }
                        else {
                            pObj.location.x -= (w - dx).toFloat() + 50
                        }
                        pObj.velocityY = -VY
                        pObj.gravity = GRAVITY
                        pObj.mTime = 0f
                        return Direction.TOP
                    }

                }
            }
        }
        return Direction.NONE
    }

    fun resetTime() {
        mTime = 0f
    }

    override fun dealDamage(character: CharacterAttributes): Int {
        character.hp -= damage
        return character.hp
    }

    fun getPlayerWidth(): Int{
        return player[0].width
    }

    fun getPlayerHeight(): Int{
        return player[0].height
    }
}