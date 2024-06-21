package com.ikhwanma.pokedex.pokemon.ui.home

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.metrics.performance.JankStats
import androidx.metrics.performance.PerformanceMetricsState
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.ikhwanma.base_ui.BaseFragment
import com.ikhwanma.base_ui.gone
import com.ikhwanma.base_ui.show
import com.ikhwanma.common.source.Resource
import com.ikhwanma.pokedex.feature.pokemon.R
import com.ikhwanma.pokedex.feature.pokemon.databinding.FragmentHomeBinding
import com.ikhwanma.pokedex.pokemon.di.pokemonModule
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(), View.OnClickListener {
    override val viewModel: HomeViewModel by viewModel()
    enum class MODE{
        LIST, GRID
    }
    private var mode: MODE? = null
    private lateinit var snapHelper: PagerSnapHelper

    private lateinit var jankStats: JankStats
    private val jankFrameListener = JankStats.OnFrameListener { frameData ->
        if (frameData.isJank) Log.v("JankStatsSample", frameData.toString())
    }
    private lateinit var metricsStateHolder: PerformanceMetricsState.Holder

    private val adapterPaging by lazy {
        PokemonPagingAdapter()
    }
    private val adapter by lazy {
        PokemonAdapter(){

        }
    }

    override fun loadInjectionModule() {
        loadKoinModules(pokemonModule)
    }

    override fun getViewBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun setUpObserver() {
        super.setUpObserver()
        snapHelper = PagerSnapHelper()
        mode = MODE.LIST
        setupData()

        metricsStateHolder = PerformanceMetricsState.getHolderForHierarchy(binding.root)

        jankStats = JankStats.createAndTrack(requireActivity().window, jankFrameListener)

        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                // check if JankStats is initialized and skip adding state if not
                val metricsState = metricsStateHolder.state ?: return

                when (newState) {
                    RecyclerView.SCROLL_STATE_DRAGGING -> {
                        metricsState.putState("RecyclerView", "Dragging")
                    }
                    RecyclerView.SCROLL_STATE_SETTLING -> {
                        metricsState.putState("RecyclerView", "Settling")
                    }
                    else -> {
                        metricsState.removeState("RecyclerView")
                    }
                }
            }
        }

        binding.rvPokemon.addOnScrollListener(scrollListener)

        metricsStateHolder.state?.putState("Fragment", javaClass.simpleName)
    }

    override fun setupListener() {
        super.setupListener()
        with(binding){
            btnGrid.setOnClickListener(this@HomeFragment)
        }
    }

    private fun setupData(){
        viewModel.getListPokemon(20, 0).observe(viewLifecycleOwner){
            when(it){
                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    binding.rvPokemon.gone()
                }
                is Resource.Success -> {
                    with(binding){
                        rvPokemon.show()
                        val data = it.data
                        if (data != null){
                            adapter.addListItems(data)
                            rvPokemon.adapter = adapter
                            rvPokemon.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                            snapHelper.attachToRecyclerView(binding.rvPokemon)
                        }
                    }
                }
            }
        }
    }

    private fun setupPagingData(){
        binding.rvPokemon.gone()
        viewModel.getListPokemonPaging().observe(viewLifecycleOwner) { listPokemon ->
            lifecycleScope.launch {
                snapHelper.attachToRecyclerView(null)
                binding.rvPokemon.adapter = adapterPaging
                binding.rvPokemon.layoutManager = GridLayoutManager(requireContext(), 3)
                binding.rvPokemon.show()
                adapterPaging.apply {
                    addLoadStateListener { combinedLoadStates ->
                        combinedLoadStates.decideOnState(
                            showLoading = { },
                            showEmptyState = { },
                            showError = {
                                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                            },
                        )
                    }
                    submitData(listPokemon)
                    loadStateFlow.distinctUntilChanged()
                    loadStateFlow
                        .collectLatest {
                            if (it.refresh is LoadState.NotLoading) {
                                if (itemCount == 0) {
                                    Toast.makeText(
                                        requireContext(),
                                        "Data kosong",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                }
            }
        }
    }

    private inline fun CombinedLoadStates.decideOnState(
        showLoading: (Boolean) -> Unit,
        showEmptyState: (Boolean) -> Unit,
        showError: (String) -> Unit
    ) {
        showLoading(refresh is LoadState.Loading)

        showEmptyState(
            source.append is LoadState.NotLoading
                    && source.append.endOfPaginationReached
                    && adapterPaging.itemCount == 0
        )

        val errorState = source.append as? LoadState.Error
            ?: source.prepend as? LoadState.Error
            ?: source.refresh as? LoadState.Error
            ?: append as? LoadState.Error
            ?: prepend as? LoadState.Error
            ?: refresh as? LoadState.Error

        errorState?.let { showError(it.error.toString()) }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_grid -> {
                when(mode){
                    MODE.LIST -> {
                        mode = MODE.GRID
                        setupPagingData()
                    }
                    MODE.GRID -> {
                        mode = MODE.LIST
                        setupData()
                    }
                    null -> {

                    }
                }
            }
        }
    }

}