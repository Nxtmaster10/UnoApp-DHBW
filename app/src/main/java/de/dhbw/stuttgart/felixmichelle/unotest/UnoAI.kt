package de.dhbw.stuttgart.felixmichelle.unotest

import FunctionCard
import PlayingCard
import ValueCard
import de.dhbw.stuttgart.felixmichelle.unotest.unocards.CardColor
import de.dhbw.stuttgart.felixmichelle.unotest.unocards.Deck
import de.dhbw.stuttgart.felixmichelle.unotest.unocards.Functions

class UnoAI(deck: Deck, initialCards: MutableList<PlayingCard>) {
    private val handCards = initialCards
    fun playTurn(currentCard: PlayingCard): PlayingCard? {

        val playableCards =
            listOf<PlayingCard?>(
                handCards.find { it is FunctionCard && (it.getColor() == CardColor.Any || it.getColor() == currentCard.getColor()) },
                handCards.find { it is ValueCard && it.getColor() == currentCard.getColor() },
                handCards.find { it is ValueCard && (currentCard is ValueCard && it.getCardValue() == currentCard.getCardValue() || currentCard is FunctionCard && (currentCard.getFunction() == Functions.WILD_DRAW_FOUR || currentCard.getFunction() == Functions.WILD)) })
        return playableCards.firstOrNull()
    }
}