package com.spec.cameraapp.ui.utils.transformations

data class TransformationItem(
    var id:Int,
    var icon:Int,
    var type:String
)

enum class TransformationType{
    ROUNDED_CORNER,
    CIRCLE_CROP,
    SQUARE_CROP,
    BLUR,
    GRAY_SCALE,
    COLOR_FILTER,
    MASK
}