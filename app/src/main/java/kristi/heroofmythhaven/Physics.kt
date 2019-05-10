package kristi.heroofmythhaven

import android.graphics.PointF
import android.graphics.Rect
enum class Direction {
    LEFT,
    RIGHT,
    UP,
    DOWN,
    NONE
}

interface Physics {
    var boundingBox: Rect
    var collisions: Array<Boolean>
    fun collision(pObj: Physics)
    fun trajectory(point: PointF, time: Float)
}