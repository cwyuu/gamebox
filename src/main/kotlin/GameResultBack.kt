package mirai.bot

data class GameResultBack(
    val status:Int,
    val msg:String,
    val data:Array<Game>,
)
