import de.dhbw.stuttgart.felixmichelle.unotest.unocards.CardColor

open class PlayingCard (private val color: CardColor){

    override fun toString(): String {
        return color.toString()
    }
    fun getColor(): CardColor {
        return color
    }

    open fun getIdentifier():String{
        return if(color != CardColor.Any) "${color.toString().lowercase()}_" else ""
    }
}