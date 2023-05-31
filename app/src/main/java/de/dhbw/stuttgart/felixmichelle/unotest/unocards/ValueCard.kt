import de.dhbw.stuttgart.felixmichelle.unotest.unocards.CardColor

class ValueCard(color: CardColor, private val value: Int) : PlayingCard(color) {
    fun getCardValue() : Int{
        return value;
    }

    override fun toString(): String {
        return super.toString() + ":" + getCardValue()
    }

    override fun getIdentifier(): String{
        return super.getIdentifier() + "${value}_card_clipart_md"
    }
}