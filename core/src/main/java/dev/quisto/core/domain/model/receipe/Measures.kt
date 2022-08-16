package dev.quisto.core.domain.model.receipe


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Measures(
    @SerializedName("metric")
    val metric: Metric?,
    @SerializedName("us")
    val us: Us?
)