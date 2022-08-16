package dev.quisto.core.domain.model.receipe


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Equipment(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("localizedName")
    val localizedName: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("temperature")
    val temperature: Temperature?
)