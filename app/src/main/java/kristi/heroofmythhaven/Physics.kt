package kristi.heroofmythhaven

import android.graphics.PointF
import android.graphics.Rect

enum class Direction {
    TOP,
    BOTTOM,
    LEFT,
    RIGHT,
    NONE
}

interface Physics {
    var velocityX: Float
    var velocityY: Float
    var gravity: Float
    var time: Float
    var boundingBox: Rect
    //var collisionDirection: Direction

    fun collision(pObj: Physics): Boolean // Should have the side effect of setting the collisionDirection variable with which side of the object a collision was detected
    fun move(point: PointF) // Maybe have this reutnr something? TBD
}