package de.dhbw.stuttgart.felixmichelle.unotest.unocards

enum class Functions {
    SKIP, REVERSE, DRAW_TWO, WILD_DRAW_FOUR, WILD;

    override fun toString() : String {
        return  this.name.lowercase()
    }
}