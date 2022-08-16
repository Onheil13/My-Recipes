package dev.quisto.recipes.presentation.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.Shimmer.ColorHighlightBuilder
import com.facebook.shimmer.ShimmerDrawable
import dev.quisto.core.domain.model.receipe.Recipe
import dev.quisto.recipes.R

class RecipeListAdapter(
    private val originalList: List<Recipe?>,
    private val context: Context,
    private val customClickListener: CustomClickListener
) :
    RecyclerView.Adapter<RecipeListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_recipe, parent, false)
        return ViewHolder(view)
    }

    private var listOfRecipe: List<Recipe?> = originalList

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val recipe = listOfRecipe[position]

        Glide.with(context)
            .load(recipe?.image)
            .placeholder(getShimmer())
            .error(R.drawable.placeholder)
            .centerCrop().into(viewHolder.recipeImage)

        viewHolder.recipeTitle.text = recipe?.title ?: ""
        viewHolder.recipeLikes.text = recipe?.aggregateLikes.toString()
        viewHolder.recipePrice.text = String.format("%.2f", recipe?.pricePerServing ?: 0.00)
        viewHolder.recipeCuisine.text =
            recipe?.cuisines?.toList().toString().replace("[", "").replace("]", "")
        viewHolder.ClickHandler(recipe, customClickListener)


    }

    override fun getItemCount(): Int {
        return listOfRecipe.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var recipeImage: ImageView
        var recipeTitle: TextView
        var recipeLikes: TextView
        var recipePrice: TextView
        var recipeCuisine: TextView

        init {
            recipeImage = itemView.findViewById(R.id.cardImageView)
            recipeTitle = itemView.findViewById(R.id.cardTvTitle)
            recipeLikes = itemView.findViewById(R.id.cardTvLikes)
            recipePrice = itemView.findViewById(R.id.cardTvPrice)
            recipeCuisine = itemView.findViewById(R.id.cardTvCuisine)
        }


        fun ClickHandler(recipe: Recipe?, customClickListener: CustomClickListener?) {
            itemView.setOnClickListener { customClickListener!!.onItemClick(recipe) }
        }

    }

    fun updateList(newList: List<Recipe?>) {
        newList.let {
            listOfRecipe = it
            notifyDataSetChanged()

        }
    }

    private fun getShimmer(): ShimmerDrawable? {
        val shimmer = ColorHighlightBuilder()
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

    interface CustomClickListener {
        fun onItemClick(recipe: Recipe?)
    }

}