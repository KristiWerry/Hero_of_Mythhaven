package kristi.heroofmythhaven

import android.graphics.PointF
import android.graphics.Rect
import android.graphics.RectF

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
    var mTime: Float
    var boundingBox: RectF
    var location: PointF

    fun collision(pObj: Physics): Direction  //
    fun move(point: PointF) // Maybe have this return something? TBD
}