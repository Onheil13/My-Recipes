package dev.quisto.info.domain.use_case

import dev.quisto.info.domain.repository.InformationRepository

class GetInformation(
    private val informationRepository: InformationRepository
) {
    suspend operator fun invoke(recipeId: Int) =
        informationRepository.getInformation(recipeId = recipeId)
}