package dev.quisto.info.presentation.adapter

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
import dev.quisto.core.domain.model.receipe.ExtendedIngredient
import dev.quisto.core.domain.model.receipe.Recipe
import dev.quisto.info.R

class IngredientListAdapter(
    private val originalIngredients: List<ExtendedIngredient?>,
    private val context: Context,
) :
    RecyclerView.Adapter<IngredientListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_ingredients, parent, false)
        return ViewHolder(view)
    }

    private var listOfIngredient: List<ExtendedIngredient?> = originalIngredients

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val ingredient = listOfIngredient[position]

        Glide.with(context)
            .load("https://spoonacular.com/cdn/ingredients_100x100/${ingredient?.image}")
            .placeholder(getShimmer())
            .error(R.drawable.placeholder)
            .centerCrop().into(viewHolder.ingImage)

        viewHolder.ingName.text = ingredient?.name?.uppercase() ?: ""
        viewHolder.ingMeasure.text = ingredient?.original?.uppercase() ?: ""
    }

    override fun getItemCount(): Int {
        return listOfIngredient.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ingImage: ImageView
        var ingName: TextView
        var ingMeasure: TextView

        init {
            ingImage = itemView.findViewById(R.id.infoCardImg)
            ingName = itemView.findViewById(R.id.infoCardtvIng)
            ingMeasure = itemView.findViewById(R.id.infoCardTvMeasure)
        }


    }

    fun updateList(newList: List<ExtendedIngredient?>) {
        newList.let {
            listOfIngredient = it
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