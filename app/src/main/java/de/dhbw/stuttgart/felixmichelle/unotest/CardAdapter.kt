package de.dhbw.stuttgart.felixmichelle.unotest
import PlayingCard
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class CardAdapter(
    var cards: List<PlayingCard>,
    private val onClick: (PlayingCard) -> Unit
) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    class CardViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val cardImage: ImageView = view.findViewById(R.id.card_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_item, parent, false)
        return CardViewHolder(view)
    }

    override fun getItemCount() = cards.size

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cards[position]
        val resourceId = holder.itemView.resources.getIdentifier(card.getIdentifier(), "drawable", holder.itemView.context.packageName)
        holder.cardImage.setImageResource(resourceId)

        holder.itemView.setOnClickListener {
            onClick(card)
        }
    }
}
