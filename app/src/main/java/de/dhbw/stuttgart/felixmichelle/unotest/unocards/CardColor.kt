package de.dhbw.stuttgart.felixmichelle.unotest.unocards

enum class CardColor {
    Red, Yellow, Green, Blue, Any;

    override fun toString() : String {
        return if(this== Any) "Neutral" else this.name.lowercase()
    }
}