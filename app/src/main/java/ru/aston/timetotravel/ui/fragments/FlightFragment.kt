package ru.aston.timetotravel.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.aston.timetotravel.R
import ru.aston.timetotravel.model.Flight
import ru.aston.timetotravel.repository.FlightsRepository
import ru.aston.timetotravel.ui.activity.MainActivity
import ru.aston.timetotravel.utils.Utils
import ru.aston.timetotravel.viewmodel.FlightsViewModel
import ru.aston.timetotravel.viewmodel.ViewModelFactory

private const val SEARCH_TOKEN = "SEARCH_TOKEN"

class FlightFragment : Fragment(R.layout.fragment_flight) {
    private var searchToken: String? = null
    private lateinit var flightsViewModel: FlightsViewModel
    private lateinit var flight: Flight

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context !is MainActivity) throw java.lang.RuntimeException("Wrong context for FlightFragment")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            searchToken = it.getString(SEARCH_TOKEN)
        }
        flightsViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelFactory(FlightsRepository())
        )[FlightsViewModel::class.java]
        flight = flightsViewModel.getFlightByToken(searchToken) ?: throw Exception("No such token")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(view) {
            findViewById<Button>(R.id.back_button).setOnClickListener {
                parentFragmentManager.popBackStack()
            }
            findViewById<TextView>(R.id.flight_departure_city_text_view).text =
                flight.startCity
            findViewById<TextView>(R.id.flight_arrival_city_text_view).text =
                flight.endCity
            findViewById<TextView>(R.id.flight_departure_date_text_view).text =
                Utils.getFormattedDate(flight.startDate)
            findViewById<TextView>(R.id.flight_arrival_date_text_view).text =
                Utils.getFormattedDate(flight.endDate)

            findViewById<TextView>(R.id.flight_price_text_view).text =
                context.getString(R.string.price_x_rub, flight.price.toString())

            findViewById<ImageView>(R.id.flight_favorite_image_view).apply {
                if (flightsViewModel.isLiked(flight.searchToken)) {
                    setImageResource(R.drawable.favorite_filled_48px)
                } else {
                    setImageResource(R.drawable.favorite_48px)
                }
                setOnClickListener {
                    flightsViewModel.onFavoriteIconTouched(flight.searchToken)
                    if (flightsViewModel.isLiked(flight.searchToken)) {
                        setImageResource(R.drawable.favorite_filled_48px)
                    } else {
                        setImageResource(R.drawable.favorite_48px)
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(searchToken: String) =
            FlightFragment().apply {
                arguments = Bundle().apply {
                    putString(SEARCH_TOKEN, searchToken)
                }
            }
    }
}