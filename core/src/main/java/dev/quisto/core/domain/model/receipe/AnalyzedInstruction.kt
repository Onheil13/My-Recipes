package dev.quisto.core.domain.model.receipe


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class AnalyzedInstruction(
    @SerializedName("name")
    val name: String?,
    @SerializedName("steps")
    val steps: List<Step?>?
)