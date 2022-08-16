package dev.quisto.recipes.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import dev.quisto.core.R as Core
import dev.quisto.core.domain.model.UiState
import dev.quisto.core.domain.model.receipe.Recipe
import dev.quisto.core.domain.model.receipe.Recipes
import dev.quisto.core.presentation.ui.dialog.LoadingDialog
import dev.quisto.core.util.RECIPE_ID
import dev.quisto.info.presentation.ui.InformationActivity
import dev.quisto.recipes.R
import dev.quisto.recipes.presentation.adapter.RecipeListAdapter
import dev.quisto.recipes.viewmodel.RecipeListViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RecipeListActivity : ComponentActivity() {

    private val recipeListViewModel: RecipeListViewModel by viewModels()

    private lateinit var loadingDialog: LoadingDialog

    private lateinit var recipeListAdapter: RecipeListAdapter
    private var listOfRecipe: List<Recipe> = listOf()

    private lateinit var noNetworkLayout: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnRetry: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_recipe_list)

        initViews()

        observeViewModel()

        customClickListeners()
    }

    private fun initViews() {
        loadingDialog = LoadingDialog(this, "Loading...")
        noNetworkLayout = findViewById(R.id.receipeNoInternet)
        btnRetry = findViewById(Core.id.recipeBtnRetry)

        recyclerView = findViewById(R.id.recipeRview)

        recipeListAdapter =
            RecipeListAdapter(listOfRecipe, this, object : RecipeListAdapter.CustomClickListener {
                override fun onItemClick(recipe: Recipe?) {
                    recipe?.let {
                        startActivity(
                            Intent(
                                this@RecipeListActivity,
                                InformationActivity::class.java
                            ).apply {
                                putExtra(RECIPE_ID, it.id)
                            },
                        )
                    }
                }
            })

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = recipeListAdapter

    }

    private fun customClickListeners() {
        btnRetry.setOnClickListener {
            recipeListViewModel.getRecipes()
        }
    }

    private fun observeViewModel() {
        recipeListViewModel.viewModelScope.launch {
            recipeListViewModel.uiState.collectLatest { state ->
                when (state) {
                    is UiState.Error -> {
                        loadingDialog.dismissDialog()
                        noNetworkLayout.visibility = View.VISIBLE
                    }
                    UiState.Idle -> {}
                    UiState.Loading -> {
                        loadingDialog.showDialog()
                        noNetworkLayout.visibility = View.GONE
                    }
                    is UiState.Success -> {
                        loadingDialog.dismissDialog()
                        noNetworkLayout.visibility = View.GONE

                        val listOfRecipe = (state.data as Recipes).recipes
                        listOfRecipe?.let { recipes ->
                            recipeListAdapter.updateList(recipes)
                        }
                    }
                }
            }
        }
    }

}