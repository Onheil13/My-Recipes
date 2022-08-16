package dev.quisto.info.presentation.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import dagger.hilt.android.AndroidEntryPoint
import dev.quisto.core.domain.model.UiState
import dev.quisto.core.domain.model.receipe.ExtendedIngredient
import dev.quisto.core.domain.model.receipe.Recipe
import dev.quisto.core.presentation.ui.dialog.LoadingDialog
import dev.quisto.core.util.HtmlToText.parseHtml
import dev.quisto.core.util.RECIPE_ID
import dev.quisto.core.util.TimeParser.parseTime
import dev.quisto.info.R
import dev.quisto.info.presentation.adapter.IngredientListAdapter
import dev.quisto.info.viewmodel.InformationViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import dev.quisto.core.R as Core

@AndroidEntryPoint
class InformationActivity : AppCompatActivity() {

    private val informationViewModel: InformationViewModel by viewModels()

    private lateinit var ingredientListAdapter: IngredientListAdapter
    private var listOfIngredient: List<ExtendedIngredient> = listOf()

    private lateinit var loadingDialog: LoadingDialog

    var recipeId: Int = 0

    lateinit var recipe: Recipe

    private lateinit var btnRetry: Button
    private lateinit var backBtn: ImageButton
    private lateinit var imgHeader: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var scrollView: ScrollView
    private lateinit var tvTitle: TextView
    private lateinit var tvLikes: TextView
    private lateinit var tvServing: TextView
    private lateinit var tvPrice: TextView
    private lateinit var tvTime: TextView
    private lateinit var tvSummary: TextView
    private lateinit var tvIngNo: TextView
    private lateinit var noNetworkLayout: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_information)

        recipeId = intent.getIntExtra(RECIPE_ID, 0)

        initViews()

        customClickListeners()

        observeViewModel()

        informationViewModel.getRecipes(recipeId = recipeId)
    }

    private fun initViews() {
        loadingDialog = LoadingDialog(this, "Loading...")
        btnRetry = findViewById(Core.id.recipeBtnRetry)
        backBtn = findViewById(R.id.btnBack)
        imgHeader = findViewById(R.id.infoImgHeader)
        noNetworkLayout = findViewById(R.id.infoNoInternet)
        scrollView = findViewById(R.id.infoSView)
        tvTitle = findViewById(R.id.tvInfoTitle)
        tvLikes = findViewById(R.id.infoTvLikes)
        tvServing = findViewById(R.id.infoTvServing)
        tvPrice = findViewById(R.id.infoTvPrice)
        tvTime = findViewById(R.id.infoTvTime)
        tvSummary = findViewById(R.id.infoTvSummary)
        tvIngNo = findViewById(R.id.infoTvIngredients)
        recyclerView = findViewById(R.id.infoRView)

        ingredientListAdapter = IngredientListAdapter(listOfIngredient, this)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.adapter = ingredientListAdapter

    }

    private fun customClickListeners() {
        btnRetry.setOnClickListener {
            informationViewModel.getRecipes(recipeId = recipeId)
        }
        backBtn.setOnClickListener {
            onBackPressed()
        }
    }

    private fun observeViewModel() {
        informationViewModel.viewModelScope.launch {
            informationViewModel.uiState.collectLatest { state ->
                when (state) {
                    is UiState.Error -> {
                        loadingDialog.dismissDialog()
                        scrollView.visibility = View.GONE
                        noNetworkLayout.visibility = View.VISIBLE
                    }
                    UiState.Idle -> TODO()
                    UiState.Loading -> {
                        loadingDialog.showDialog()

                    }
                    is UiState.Success -> {
                        val responseData = state.data as Recipe
                        showResponseData(recipe = responseData)
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showResponseData(recipe: Recipe) {

        Glide.with(this)
            .load(recipe.image)
            .placeholder(getShimmer())
            .error(R.drawable.placeholder)
            .centerCrop().into(imgHeader)

        tvTitle.text = recipe.title ?: ""
        tvLikes.text = "${recipe.aggregateLikes ?: 0} Likes"
        tvServing.text = "${recipe.servings ?: 0} Serving"
        tvPrice.text = "${String.format("%.2f", recipe.pricePerServing ?: 0.00)} Price"
        tvTime.text = (recipe.preparationMinutes ?: 0).parseTime()
        tvSummary.parseHtml(recipe.summary ?: "")
        tvIngNo.text = "Ingredients(${recipe.extendedIngredients?.size ?: 0})"

        ingredientListAdapter.updateList(recipe.extendedIngredients!!)

        loadingDialog.dismissDialog()
        noNetworkLayout.visibility = View.GONE
        scrollView.visibility = View.VISIBLE

    }


    private fun getShimmer(): ShimmerDrawable? {
        val shimmer = Shimmer.ColorHighlightBuilder()
            .setBaseAlpha(0.7f) //the alpha of the underlying children
            .setHighlightAlpha(0.6f) // the shimmer alpha amount
            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
            .setBaseColor(Color.GRAY)
            .setHighlightColor(Color.LTGRAY)
            .setAutoStart(true)
            .build()
        val shimmerDrawable = ShimmerDrawable()
        shimmerDrawable.setShimmer(shimmer)
        return shimmerDrawable
    }

}