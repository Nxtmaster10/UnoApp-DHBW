package de.dhbw.stuttgart.felixmichelle.unotest.unocards

import FunctionCard
import PlayingCard
import ValueCard

class Deck {
    val cards = mutableListOf<PlayingCard>();

    fun shuffle() {
        cards.shuffle()
    }

    fun draw(amount: Int): List<PlayingCard> {
        if (cards.size < amount) {
            initDeck()
            shuffle()
        }
        val result = cards.slice(IntRange(0, amount - 1))
        cards.subList(0, amount).clear()
        return result
    }

    fun initDeck() {
        for (i in 1..9) {
            cards.add(ValueCard(CardColor.Blue, i))
            cards.add(ValueCard(CardColor.Blue, i))
            cards.add(ValueCard(CardColor.Red, i))
            cards.add(ValueCard(CardColor.Red, i))
            cards.add(ValueCard(CardColor.Green, i))
            cards.add(ValueCard(CardColor.Green, i))
            cards.add(ValueCard(CardColor.Yellow, i))
            cards.add(ValueCard(CardColor.Yellow, i))
        }
        cards.add(ValueCard(CardColor.Blue, 0))
        cards.add(ValueCard(CardColor.Red, 0))
        cards.add(ValueCard(CardColor.Green, 0))
        cards.add(ValueCard(CardColor.Yellow, 0))

        cards.add(FunctionCard(CardColor.Blue, Functions.SKIP))
        cards.add(FunctionCard(CardColor.Blue, Functions.SKIP))
        cards.add(FunctionCard(CardColor.Blue, Functions.REVERSE))
        cards.add(FunctionCard(CardColor.Blue, Functions.REVERSE))
        cards.add(FunctionCard(CardColor.Blue, Functions.DRAW_TWO))
        cards.add(FunctionCard(CardColor.Blue, Functions.DRAW_TWO))

        cards.add(FunctionCard(CardColor.Red, Functions.SKIP))
        cards.add(FunctionCard(CardColor.Red, Functions.SKIP))
        cards.add(FunctionCard(CardColor.Red, Functions.REVERSE))
        cards.add(FunctionCard(CardColor.Red, Functions.REVERSE))
        cards.add(FunctionCard(CardColor.Red, Functions.DRAW_TWO))
        cards.add(FunctionCard(CardColor.Red, Functions.DRAW_TWO))

        cards.add(FunctionCard(CardColor.Green, Functions.SKIP))
        cards.add(FunctionCard(CardColor.Green, Functions.SKIP))
        cards.add(FunctionCard(CardColor.Green, Functions.REVERSE))
        cards.add(FunctionCard(CardColor.Green, Functions.REVERSE))
        cards.add(FunctionCard(CardColor.Green, Functions.DRAW_TWO))
        cards.add(FunctionCard(CardColor.Green, Functions.DRAW_TWO))

        cards.add(FunctionCard(CardColor.Yellow, Functions.SKIP))
        cards.add(FunctionCard(CardColor.Yellow, Functions.SKIP))
        cards.add(FunctionCard(CardColor.Yellow, Functions.REVERSE))
        cards.add(FunctionCard(CardColor.Yellow, Functions.REVERSE))
        cards.add(FunctionCard(CardColor.Yellow, Functions.DRAW_TWO))
        cards.add(FunctionCard(CardColor.Yellow, Functions.DRAW_TWO))

        for (i in 0..3) {
            cards.add(FunctionCard(CardColor.Any, Functions.WILD_DRAW_FOUR))
            cards.add(FunctionCard(CardColor.Any, Functions.WILD_DRAW_FOUR))
        }

    }

}