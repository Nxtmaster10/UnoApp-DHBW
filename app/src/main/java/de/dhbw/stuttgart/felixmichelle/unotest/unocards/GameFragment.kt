package de.dhbw.stuttgart.felixmichelle.unotest.unocards

import FunctionCard
import PlayingCard
import ValueCard
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import de.dhbw.stuttgart.felixmichelle.unotest.CardAdapter
import de.dhbw.stuttgart.felixmichelle.unotest.GameOverFragment
import de.dhbw.stuttgart.felixmichelle.unotest.R


class GameFragment : Fragment() {

    // Füge eine Liste von Karten für den Spieler hinzu
    private lateinit var playerCards: List<PlayingCard>
    private lateinit var opponentCards: MutableList<PlayingCard>
    private lateinit var deck: Deck
    private lateinit var cardsAdapter: CardAdapter
    private lateinit var lastCard: PlayingCard

    private var humanPlayerCanPlay: Boolean = true
    private var calledUno = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    fun initGame() {
        playerCards = listOf()
        deck.initDeck()
        deck.shuffle()
        lastCard = deck.draw(1).first()
        opponentCards = deck.draw(7).toMutableList()
        updateLastCardImage(requireView())
        playerCards = deck.draw(2)
        cardsAdapter.cards = playerCards
        cardsAdapter.notifyDataSetChanged()
        humanPlayerCanPlay = true
        updateOpponentCardCount()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        deck = Deck()
        playerCards = listOf()
        cardsAdapter = CardAdapter(playerCards, ::playCard)
        initGame()
        val restartGameButton: Button = view.findViewById(R.id.restart_game_button)
        restartGameButton.setOnClickListener {
            deck.cards.clear()
            playerCards = listOf()
            initGame()
        }

        val unoButton: Button = view.findViewById(R.id.uno_button)
        unoButton.setOnClickListener {
            if (playerCards.size == 1) {
                view.findViewById<TextView>(R.id.uno_correct).visibility = VISIBLE
                calledUno = true
            } else {
                view.findViewById<TextView>(R.id.uno_false).visibility = VISIBLE
                playerCards = playerCards + deck.draw(2)
                cardsAdapter.cards = playerCards
                cardsAdapter.notifyDataSetChanged()
            }
            requireView().postDelayed({
                view.findViewById<TextView>(R.id.uno_correct).visibility = INVISIBLE
                view.findViewById<TextView>(R.id.uno_false).visibility = INVISIBLE
            }, 2000)
        }

        val drawStackCard: ImageView = view.findViewById(R.id.draw_stack)
        drawStackCard.setOnClickListener {
            drawCard()
        }
        view.findViewById<RecyclerView>(R.id.player_cards_recycler_view).apply {
            adapter = cardsAdapter
        }
        // Zum Beispiel: Erstelle eine Kartenliste für den Spieler


    }

    private fun computerPlayCard() {
        // finde Karten, die nach den Computergegner-Regeln spielbar sind
        updateOpponentCardCount()
        val playableCards =
            opponentCards.filter { it is FunctionCard && (it.getColor() == CardColor.Any || it.getColor() == lastCard.getColor()) } +
                    opponentCards.filter { it is ValueCard && it.getColor() == lastCard.getColor() } +
                    opponentCards.filter { it is ValueCard && (lastCard is ValueCard && it.getCardValue() == (lastCard as ValueCard).getCardValue() || lastCard is FunctionCard && ((lastCard as FunctionCard).getFunction() == Functions.WILD_DRAW_FOUR || (lastCard as FunctionCard).getFunction() == Functions.WILD)) }
        val selectedCard = playableCards.firstOrNull()
        println(playableCards)
        println(selectedCard)
        println(opponentCards)
        if (selectedCard != null) {
            opponentCards.remove(selectedCard)
            lastCard = selectedCard
            updateLastCardImage(requireView())
            if (selectedCard is FunctionCard) {
                if (selectedCard.getFunction() == Functions.REVERSE || selectedCard.getFunction() == Functions.SKIP) {
                    humanPlayerCanPlay = false
                    requireView().postDelayed({
                        computerPlayCard()
                        humanPlayerCanPlay = true
                    }, 1000)
                }
                if (selectedCard.getFunction() == Functions.DRAW_TWO) {
                    playerCards = playerCards + deck.draw(2)
                    cardsAdapter.cards = playerCards
                    cardsAdapter.notifyDataSetChanged()
                }
                if (selectedCard.getFunction() == Functions.WILD_DRAW_FOUR) {
                    playerCards = playerCards + deck.draw(4)
                    cardsAdapter.cards = playerCards
                    cardsAdapter.notifyDataSetChanged()
                }
            }
        } else {
            opponentCards.add(deck.draw(1).first())
        }
        updateOpponentCardCount()
        if (opponentCards.size == 1) {
            requireView().findViewById<TextView>(R.id.uno_computer).visibility = VISIBLE
            requireView().postDelayed({
                requireView().findViewById<TextView>(R.id.uno_computer).visibility = INVISIBLE

            }, 2000)
        }
        if (opponentCards.size == 0) {
            showGameOver(playerCards.sumOf { if (it is ValueCard) it.getCardValue() else if (it is FunctionCard && it.getColor() == CardColor.Any) 50 else 20 })
            return
        }
    }

    private fun showGameOver(points: Int) {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainerView2, GameOverFragment.newInstance(points))
            .addToBackStack(null)
            .commit()
    }

    private fun canPlayCard(card: PlayingCard): Boolean {
        return card.getColor() == CardColor.Any || lastCard.getColor() == CardColor.Any || lastCard.getColor() == card.getColor() ||
                card is ValueCard && lastCard is ValueCard && card.getCardValue() == (lastCard as ValueCard).getCardValue() || card is FunctionCard && lastCard is FunctionCard && card.getFunction() == (lastCard as FunctionCard).getFunction()

    }

    private fun updateLastCardImage(view: View) {
        val lastCardImage: ImageView = view.findViewById(R.id.last_card_image)

        val resourceId =
            resources.getIdentifier(lastCard.getIdentifier(), "drawable", context?.packageName)
        lastCardImage.setImageResource(resourceId)
    }

    private fun updateOpponentCardCount() {
        val remainingCardView: TextView = requireView().findViewById(R.id.remainingCards)
        remainingCardView.text = getString(R.string.opponent_cards, opponentCards.size)

    }

    // Ziehe eine Karte und füge sie dem Adapter hinzu
    private fun drawCard() {
        if (humanPlayerCanPlay) {
            humanPlayerCanPlay = false
            val newCard = deck.draw(1).first()
            playerCards = playerCards + newCard
            cardsAdapter.cards = playerCards
            cardsAdapter.notifyDataSetChanged()
            requireView().postDelayed({
                computerPlayCard()
                humanPlayerCanPlay = true
            }, 2000) // 2000 milliseconds delay
        }
    }

    // Überprüfe, ob die Karte gespielt werden kann; wenn ja, spiele die Karte
    private fun playCard(card: PlayingCard) {
        if (canPlayCard(card) && humanPlayerCanPlay) {
            humanPlayerCanPlay = false
            lastCard = card
            println(lastCard)
            println(lastCard.getIdentifier())
            updateLastCardImage(requireView())

            // Entferne die Karte aus der Hand und dem Adapter des Spielers
            playerCards = playerCards.filter { it != card }
            cardsAdapter.cards = playerCards
            cardsAdapter.notifyDataSetChanged()
            if (playerCards.isEmpty()) {
                showGameOver(opponentCards.sumOf { if (it is ValueCard) it.getCardValue() else if (it is FunctionCard && it.getColor() == CardColor.Any) 50 else 20 })
                return
            }

            if (card is FunctionCard) {
                if (card.getFunction() == Functions.DRAW_TWO) {
                    opponentCards.addAll(deck.draw(2))

                }
                if (card.getFunction() == Functions.WILD_DRAW_FOUR) {
                    opponentCards.addAll(deck.draw(4))

                }
            }
            if (!(card is FunctionCard && (card.getFunction() == Functions.REVERSE || card.getFunction() == Functions.SKIP))) {
                requireView().postDelayed({
                    if (playerCards.size == 1 && !calledUno) {
                        playerCards = playerCards + deck.draw(2)
                        cardsAdapter.cards = playerCards
                        cardsAdapter.notifyDataSetChanged()
                    }
                    computerPlayCard()
                    humanPlayerCanPlay = true
                }, 2000) // 2000 milliseconds delay
            } else {
                humanPlayerCanPlay = true
                requireView().postDelayed({
                    if (playerCards.size == 1 && !calledUno) {
                        playerCards = playerCards + deck.draw(2)
                        cardsAdapter.cards = playerCards
                        cardsAdapter.notifyDataSetChanged()
                    }
                }, 2000)
            }
            updateOpponentCardCount()


        }

    }

}
