import de.dhbw.stuttgart.felixmichelle.unotest.unocards.CardColor
import de.dhbw.stuttgart.felixmichelle.unotest.unocards.Functions

class FunctionCard(color: CardColor, private val function: Functions) : PlayingCard(color){
    fun getFunction(): Functions{
        return function
    }

    override fun toString(): String {
        return super.toString() + ":$function"
    }

    override fun getIdentifier(): String {
        return super.getIdentifier() + "${function}_card_clipart_md"
    }
}