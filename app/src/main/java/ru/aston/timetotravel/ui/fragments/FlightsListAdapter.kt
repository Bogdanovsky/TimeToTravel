package ru.aston.timetotravel.ui.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.aston.timetotravel.R
import ru.aston.timetotravel.model.Flight
import ru.aston.timetotravel.ui.activity.MainActivity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

class FlightsListAdapter() :
    RecyclerView.Adapter<FlightsListAdapter.FlightViewHolder>() {

    private var flightsList: List<Flight> = listOf()

    fun updateFlightsList(newFlightsList: List<Flight>) {
        flightsList = newFlightsList
        notifyDataSetChanged()
    }

    class FlightViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.flight_item, parent, false)
        return FlightViewHolder(item)
    }

    override fun onBindViewHolder(holder: FlightViewHolder, position: Int) {
        val flight = flightsList[position]
        with(holder.itemView) {
            findViewById<TextView>(R.id.departure_city_text_view).text =
                flight.startCity
            findViewById<TextView>(R.id.arrival_city_text_view).text =
                flight.endCity

            val formatIn = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val formatOut = SimpleDateFormat("MMM, dd")
            try {
                val startDate: Date = formatIn.parse(flight.startDate)
                findViewById<TextView>(R.id.departure_date_text_view).text = formatOut.format(startDate)
                val endDate: Date = formatIn.parse(flight.endDate)
                findViewById<TextView>(R.id.arrival_date_text_view).text = formatOut.format(endDate)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            findViewById<TextView>(R.id.price_text_view).text =
                context.getString(R.string.price_x_rub, flight.price.toString())

            findViewById<ImageView>(R.id.favorite_image_view).apply {
                if ((this.context as MainActivity).flightsViewModel.isLiked(flight.searchToken)) {
                    setImageResource(R.drawable.favorite_filled_48px)
                } else {
                    setImageResource(R.drawable.favorite_48px)
                }
                setOnClickListener {
                    (context as MainActivity).flightsViewModel.onFavoriteIconTouched(flight.searchToken)
                    notifyItemChanged(position)
                }
            }
            setOnClickListener {
                (context as MainActivity).goToFlightFragment(flight.searchToken)
            }
        }
    }

    override fun getItemCount(): Int {
        return flightsList.size
    }

}