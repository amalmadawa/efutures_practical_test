package com.amal.efuturespracticaltest.ui.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.amal.efuturespracticaltest.R
import com.amal.efuturespracticaltest.data.api.RetrofitService
import com.amal.efuturespracticaltest.data.model.Scenarios
import com.amal.efuturespracticaltest.data.repo.MainRepository
import com.amal.efuturespracticaltest.databinding.ScenariosFragmentBinding
import com.amal.efuturespracticaltest.ui.adapters.ScenariosAdapter
import com.amal.efuturespracticaltest.ui.base.MyViewModelFactory
import com.amal.efuturespracticaltest.ui.interfaces.ScenariosRecyclerViewOnClickListener
import com.amal.efuturespracticaltest.ui.viewmodel.ScenariosViewModel


class ScenariosFragment : Fragment(),ScenariosRecyclerViewOnClickListener {

    private lateinit var viewModel: ScenariosViewModel
    private lateinit var binding: ScenariosFragmentBinding

    private val retrofitService = RetrofitService.getInstance()
    private val scenariosAdapter = ScenariosAdapter(this)


    private lateinit var navController: NavController;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = ScenariosFragmentBinding.inflate(layoutInflater)

        binding.recyclerview.adapter = scenariosAdapter

        navController  = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        viewModel = ViewModelProvider(
            this,
            MyViewModelFactory(MainRepository(retrofitService))
        ).get(ScenariosViewModel::class.java)

        viewModel.scenariosList.observe(viewLifecycleOwner, {
            // set scenario data to the scenario recyclerview
            scenariosAdapter.setMovieList(it)
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        // progress dialog will appear when api call ongoing
        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) {
                binding.progressCircular.visibility = View.VISIBLE;
            } else {
                binding.progressCircular.visibility = View.GONE;
            }
        })

        viewModel.getAllScenarios()

        return binding.root
    }


    override fun onScenariosRecyclerViewItemClick(scenario: Scenarios) {
        // passing selected  Scenarios id to casesFragment
        val data = Bundle();
        data.putInt("id", scenario.caseid);
        navController!!.navigate(R.id.action_scenariosFragment_to_casesFragment,data)
    }


}