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
import com.amal.efuturespracticaltest.data.model.Answers
import com.amal.efuturespracticaltest.data.model.Cases
import com.amal.efuturespracticaltest.data.repo.MainRepository
import com.amal.efuturespracticaltest.databinding.CasesFragmentBinding
import com.amal.efuturespracticaltest.ui.adapters.AnswerAdapter
import com.amal.efuturespracticaltest.ui.base.MyViewModelFactory
import com.amal.efuturespracticaltest.ui.interfaces.AnswerRecyclerViewOnClickListener
import com.amal.efuturespracticaltest.ui.viewmodel.CasesViewModel
import com.bumptech.glide.Glide

class CasesFragment : Fragment(), AnswerRecyclerViewOnClickListener {

    private lateinit var viewModel: CasesViewModel

    private lateinit var binding: CasesFragmentBinding

    private val retrofitService = RetrofitService.getInstance()
    private val answersAdapter = AnswerAdapter(this)

    private lateinit var navController: NavController;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = CasesFragmentBinding.inflate(layoutInflater)

        binding.answersView.adapter = answersAdapter

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        viewModel = ViewModelProvider(this, MyViewModelFactory(MainRepository(retrofitService))).get(
            CasesViewModel::class.java
        )

        viewModel.cases.observe(viewLifecycleOwner, {

            val case:Cases = viewModel.cases.value!![0]

            binding.caseText.text = case.text
            case.answers?.let { data ->
                // set answers when the answer array not null
                answersAdapter.setAnswerList(data)
            }

            // check whether images available. image view will hide when it's not available
            if(case.image!= null){
                binding.imageView.visibility = View.VISIBLE
                Glide.with(requireActivity()).load(case.image).into(binding.imageView)
            }else{
                binding.imageView.visibility = View.GONE
            }

            //checkAgain button will available when answers are not available
            case.answers.let {
                if(case.answers!!.isEmpty()){
                    binding.checkAgain.visibility = View.VISIBLE
                }else{
                    binding.checkAgain.visibility = View.GONE
                }
            }

            // back to Scenarios view
            binding.checkAgain.setOnClickListener {
                navController.popBackStack()
            }

        })

        viewModel.errorMessage.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) {
                binding.progressCircular.visibility = View.VISIBLE;
            } else {
                binding.progressCircular.visibility = View.GONE;
            }
        })

        viewModel.getCase(requireArguments().getInt("id"))

        return binding.root
    }

    override fun onAnswerRecyclerViewItemClick(answers: Answers) {
        viewModel.getCase(answers.caseid)
    }


}