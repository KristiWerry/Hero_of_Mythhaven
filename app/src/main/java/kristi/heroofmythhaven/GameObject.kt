package kristi.heroofmythhaven

import android.graphics.Canvas
import android.graphics.PointF

interface GameObject: Physics{
    fun draw(canvas: Canvas)
    fun update(userInput: UserInput, directions: Array<Boolean>)
}