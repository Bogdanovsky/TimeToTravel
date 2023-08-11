package ru.aston.timetotravel.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import ru.aston.timetotravel.R
import ru.aston.timetotravel.model.Flight
import ru.aston.timetotravel.network.ApiState
import ru.aston.timetotravel.repository.FlightsRepository
import ru.aston.timetotravel.ui.activity.MainActivity
import ru.aston.timetotravel.viewmodel.FlightsViewModel
import ru.aston.timetotravel.viewmodel.ViewModelFactory

class FlightsListFragment : Fragment(R.layout.fragment_flights_list) {

    private lateinit var flightsViewModel: FlightsViewModel
    private lateinit var flightsListAdapter: FlightsListAdapter
    private var flights: List<Flight> = listOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context !is MainActivity) throw java.lang.RuntimeException("Wrong context for FlightsListFragment")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        flightsViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelFactory(FlightsRepository())
        )[FlightsViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        flightsViewModel.getFlights()
        flightsListAdapter = FlightsListAdapter(flights)
        view.findViewById<RecyclerView>(R.id.flights_list_recycler_view).apply {
            adapter = flightsListAdapter
            layoutManager = LinearLayoutManager(
                this@FlightsListFragment.context,
                LinearLayoutManager.VERTICAL,
                false
            )
            addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        }

        if (savedInstanceState == null) {
            lifecycleScope.launch {
                collects(view)
            }
        } else {
            flightsListAdapter.updateFlightsList(flightsViewModel.listOfFlights)
        }
    }

    private suspend fun collects(view: View) {
        repeatOnLifecycle(Lifecycle.State.CREATED) {
            val progressBar = view.findViewById<ProgressBar>(R.id.progress)
            flightsViewModel.apiState.collect {
                when (it) {
                    is ApiState.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }

                    is ApiState.Failure -> {
                        it.e.printStackTrace()
                        progressBar.visibility = View.GONE
                        Toast.makeText(requireActivity(), "${it.e.message}", Toast.LENGTH_SHORT)
                            .show()
                    }

                    is ApiState.Success -> {
                        progressBar.visibility = View.GONE
                        flights = it.data as List<Flight>
                        flightsListAdapter.updateFlightsList(flights)
                    }

                    is ApiState.Empty -> {
                        Toast.makeText(requireActivity(), "Empty", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    companion object {
        const val FLIGHTS_LIST_FRAGMENT_TAG = "FLIGHTS_LIST_FRAGMENT"

        @JvmStatic
        fun newInstance() = FlightsListFragment()
    }
}