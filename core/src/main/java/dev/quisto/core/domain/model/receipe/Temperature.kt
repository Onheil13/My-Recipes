package dev.quisto.core.domain.model.receipe


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Temperature(
    @SerializedName("number")
    val number: Double?,
    @SerializedName("unit")
    val unit: String?
)