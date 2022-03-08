package mirai.bot

import java.lang.annotation.ElementType

data class InsertUserBack(
    val status:Int,
    val msg:String,
    val data:User,
)
