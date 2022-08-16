package dev.quisto.core.domain.model.receipe


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Length(
    @SerializedName("number")
    val number: Int?,
    @SerializedName("unit")
    val unit: String?
)