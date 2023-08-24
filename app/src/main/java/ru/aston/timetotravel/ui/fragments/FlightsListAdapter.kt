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
import ru.aston.timetotravel.ui.activity.OnItemTouchListener
import ru.aston.timetotravel.utils.Utils
import ru.aston.timetotravel.viewmodel.FlightsViewModel

class FlightsListAdapter(
    private val flightsViewModel: FlightsViewModel,
    private val onItemTouchListener: OnItemTouchListener
) :
    RecyclerView.Adapter<FlightsListAdapter.FlightViewHolder>() {

    private var flightsList: List<Flight> = listOf()

    fun updateFlightsList(newFlightsList: List<Flight>) {
        flightsList = newFlightsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.flight_item, parent, false)
        return FlightViewHolder(item)
    }

    override fun onBindViewHolder(holder: FlightViewHolder, position: Int) {
        val flight = flightsList[position]
        holder.onBind(flight)
    }

    override fun getItemCount(): Int {
        return flightsList.size
    }

    inner class FlightViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val departureCityTextView =
            itemView.findViewById<TextView>(R.id.departure_city_text_view)
        private val arrivalCityTextView =
            itemView.findViewById<TextView>(R.id.arrival_city_text_view)
        private val departureDateTextView =
            itemView.findViewById<TextView>(R.id.departure_date_text_view)
        private val arrivalDateTextView =
            itemView.findViewById<TextView>(R.id.arrival_date_text_view)
        private val priceTextView = itemView.findViewById<TextView>(R.id.price_text_view)
        private val favoriteImageView = itemView.findViewById<ImageView>(R.id.favorite_image_view)

        fun onBind(flight: Flight) {
            departureCityTextView.text = flight.startCity
            arrivalCityTextView.text = flight.endCity
            departureDateTextView.text = Utils.getFormattedDate(flight.startDate)
            arrivalDateTextView.text = Utils.getFormattedDate(flight.endDate)
            priceTextView.text =
                itemView.context.getString(R.string.price_x_rub, flight.price.toString())
            favoriteImageView.apply {
                if ((this.context as MainActivity).flightsViewModel.isLiked(flight.searchToken)) {
                    setImageResource(R.drawable.favorite_filled_48px)
                } else {
                    setImageResource(R.drawable.favorite_48px)
                }
                setOnClickListener {
                    flightsViewModel.onFavoriteIconTouched(flight.searchToken)
                    this@FlightsListAdapter.notifyItemChanged(layoutPosition)
                }
            }
            itemView.setOnClickListener {
                onItemTouchListener.goToFlightFragment(flight.searchToken)
            }
        }
    }
}