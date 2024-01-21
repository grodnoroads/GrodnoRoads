package com.egoriku.grodnoroads.settings.faq.data.repository

import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.settings.faq.data.dto.FaqDTO
import com.egoriku.grodnoroads.settings.faq.domain.model.FAQ
import com.egoriku.grodnoroads.settings.faq.domain.repository.FaqRepository
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.orderBy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

internal class FaqRepositoryImpl(
    private val firestore: FirebaseFirestore
) : FaqRepository {

    override suspend fun load() = withContext(Dispatchers.IO) {
        runCatching {
            val faq = firestore
                .collection("faq")
                .orderBy("id")
                .get()
                .documents.map {
                    it.data<FaqDTO>()
                }
            ResultOf.Success(
                faq.map {
                    FAQ(
                        question = it.question,
                        answer = it.answer.replace("\\n", "\n")
                    )
                }
            )

        }.getOrElse {
            ResultOf.Failure(it)
        }
    }
}