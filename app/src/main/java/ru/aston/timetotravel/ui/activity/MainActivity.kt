package ru.aston.timetotravel.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import ru.aston.timetotravel.R
import ru.aston.timetotravel.databinding.ActivityMainBinding
import ru.aston.timetotravel.ui.fragments.FlightFragment
import ru.aston.timetotravel.ui.fragments.FlightsListFragment

class MainActivity : AppCompatActivity(), OnItemTouchListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
