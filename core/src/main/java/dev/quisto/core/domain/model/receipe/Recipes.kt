package dev.quisto.core.domain.model.receipe


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Recipes(
    @SerializedName("recipes")
    val recipes: List<Recipe?>?
)