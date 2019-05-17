package kristi.heroofmythhaven

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PointF
import android.graphics.RectF
import android.util.Log
import kotlin.math.abs

class Monster: GameObject, CharacterAttributes {
    // Physics Interface Variables
    override var velocityY = 0f
    override var boundingBox: RectF
    override var velocityX = -VX
    override var gravity = GRAVITY
    override var time = TIME
    override var location: PointF
    override var mTime = 0f

    // CharacterAttribute interface variables
    override var hp: Int
    override var damage: Int

    private var bitMap: Bitmap
    var goingLeft = true


    constructor(bitMap: Bitmap, hp: Int, damage: Int, location: PointF) {
        this.hp = hp
        this.damage = damage
        this.bitMap = bitMap
        this.location = location
        boundingBox = RectF(location.x, location.y, location.x + bitMap.width, location.y + bitMap.height)
    }

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(bitMap, this.location.x, this.location.y, null)
    }

    override fun update(context: Boolean) {
        if (context) {
            if (goingLeft) {
                velocityX = - 2 * VX
            }
            else {
                velocityX = 0f
            }
        }
        else {
            if (goingLeft) {
                velocityX = -VX
            }
            else {
                velocityX = VX
            }
        }

        move(location)


        boundingBox.left = this.location.x
        boundingBox.right = this.location.x + bitMap.width
        boundingBox.top = this.location.y
        boundingBox.bottom = this.location.y + bitMap.height
    }

    override fun collision(pObj: Physics): Direction {
        if (RectF.intersects(pObj.boundingBox, boundingBox)){
            val w = 0.5 * (boundingBox.width() + pObj.boundingBox.width())
            val h = 0.5 * (boundingBox.height() + pObj.boundingBox.height())
            val dx = boundingBox.centerX() - pObj.boundingBox.centerX()
            val dy = boundingBox.centerY() - pObj.boundingBox.centerY()

            if (abs(dx) <= w && abs(dy) <= h) {
                val wy = w * dy
                val hx = h * dx

                if (wy > hx) {
                    if (wy > -hx) {
                        return Direction.BOTTOM
                    }
                    else { // LEFT
                        pObj.location.x += (w + dx).toFloat() + 50 //Push the object back
                        pObj.location.y -= (h - dy).toFloat()
                        goingLeft = true
                        velocityX = -VX
                        return Direction.LEFT
                    }
                }
                else {
                    if (wy > -hx) { // RIGHT
                        pObj.location.x -= (w - dx).toFloat() //Push the object back
                        pObj.location.y -= (h - dy).toFloat()
                        goingLeft = false
                        velocityX = VX
                        return Direction.RIGHT
                    }
                    else {
                        pObj.location.y -= (h - dy).toFloat()
                        if ((pObj.location.x - location.x) > 0) { //Push the object back depending on the direction the monster is currently traveling
                            pObj.location.x += (w + dx).toFloat() + 50
                            goingLeft = true
                        }
                        else {
                            pObj.location.x -= (w - dx).toFloat() + 50
                            goingLeft = false
                        }
                        return Direction.TOP
                    }

                }
            }
        }
        return Direction.NONE
    }

    override fun move(point: PointF) {
        mTime += time
        point.x += (velocityX * (time))
        point.y -= (velocityY * (mTime) - 0.5f * gravity * mTime * mTime)
    }

    override fun dealDamage(character: CharacterAttributes): Int {
        character.hp -= damage
        return character.hp
    }
}