package dev.quisto.core.domain.model.receipe


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Step(
    @SerializedName("equipment")
    val equipment: List<Equipment?>?,
    @SerializedName("ingredients")
    val ingredients: List<Ingredient?>?,
    @SerializedName("length")
    val length: Length?,
    @SerializedName("number")
    val number: Int?,
    @SerializedName("step")
    val step: String?
)