package com.egoriku.grodnoroads.settings.changelog.data.repository

import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.settings.changelog.data.dto.ChangelogDTO
import com.egoriku.grodnoroads.settings.changelog.domain.model.ReleaseNotes
import com.egoriku.grodnoroads.settings.changelog.domain.repository.ChangelogRepository
import com.egoriku.grodnoroads.settings.changelog.domain.util.DateFormatter
import dev.gitlive.firebase.firestore.Direction
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.orderBy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

internal class ChangelogRepositoryImpl(
    private val firestore: FirebaseFirestore
) : ChangelogRepository {

    private val formatter = DateFormatter()

    override suspend fun load() = withContext(Dispatchers.IO) {
        runCatching {
            val changelog = firestore
                .collection("whats_new")
                .orderBy("code", Direction.DESCENDING)
                .get()
                .documents.map {
                    it.data<ChangelogDTO>()
                }

            ResultOf.Success(
                changelog.map {
                    ReleaseNotes(
                        versionCode = it.code,
                        versionName = it.name,
                        notes = it.notes.replace("\\n", "\n"),
                        releaseDate = formatter.formatTime(it.releaseDate.seconds)
                    )
                }
            )
        }.getOrElse {
            ResultOf.Failure(it)
        }
    }
}

