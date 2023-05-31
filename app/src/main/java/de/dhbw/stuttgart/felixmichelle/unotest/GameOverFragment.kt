package de.dhbw.stuttgart.felixmichelle.unotest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class GameOverFragment : Fragment() {

    private var points: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            points = it.getInt(ARG_POINTS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_over, container, false)

        view.findViewById<TextView>(R.id.points_text).text = points.toString()

        view.findViewById<Button>(R.id.send_email_button).setOnClickListener {
            sendScoreEmail(points)
        }

        return view
    }

    private fun sendScoreEmail(points: Int) {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_SUBJECT, "My game score")
            putExtra(Intent.EXTRA_TEXT, "I scored $points points in my last game!")
        }
        startActivity(Intent.createChooser(emailIntent, "Send email using..."))
    }

    companion object {
        private const val ARG_POINTS = "points"

        @JvmStatic
        fun newInstance(points: Int) = GameOverFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_POINTS, points)
            }
        }
    }
}
