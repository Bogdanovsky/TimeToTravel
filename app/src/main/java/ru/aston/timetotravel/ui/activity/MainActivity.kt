package ru.aston.timetotravel.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import ru.aston.timetotravel.R
import ru.aston.timetotravel.databinding.ActivityMainBinding
import ru.aston.timetotravel.network.RetrofitClient
import ru.aston.timetotravel.repository.FlightsRepository
import ru.aston.timetotravel.ui.fragments.FlightFragment
import ru.aston.timetotravel.ui.fragments.FlightsListFragment
import ru.aston.timetotravel.viewmodel.FlightsViewModel
import ru.aston.timetotravel.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity(), OnItemTouchListener {
    private lateinit var binding: ActivityMainBinding
    lateinit var flightsViewModel: FlightsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        flightsViewModel = ViewModelProvider(
            this,
            ViewModelFactory(FlightsRepository(RetrofitClient.retrofit))
        )[FlightsViewModel::class.java]
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.container, FlightsListFragment.newInstance())
        }
    }

    override fun goToFlightFragment(searchToken: String) {
        supportFragmentManager.commit {
            addToBackStack("FlightFragment")
            setReorderingAllowed(true)
            replace(R.id.container, FlightFragment.newInstance(searchToken))
        }
    }
}
