package com.egoriku.grodnoroads.settings.faq.domain.component

import com.egoriku.grodnoroads.coroutines.flow.CStateFlow
import com.egoriku.grodnoroads.coroutines.flow.stateFlowOf
import com.egoriku.grodnoroads.extensions.LoremIpsum
import com.egoriku.grodnoroads.settings.faq.domain.model.FAQ
import com.egoriku.grodnoroads.settings.faq.domain.store.FaqStore
import kotlinx.collections.immutable.persistentListOf

class FaqComponentPreview : FaqComponent {

    override val state: CStateFlow<FaqStore.State>
        get() = stateFlowOf {
            FaqStore.State(
                isLoading = false,
                faq = persistentListOf(
                    FAQ(
                        question = LoremIpsum.generateLoremIpsum(5),
                        answer = LoremIpsum.generateLoremIpsum(40)
                    ),
                    FAQ(
                        question = LoremIpsum.generateLoremIpsum(8),
                        answer = LoremIpsum.generateLoremIpsum(30)
                    )
                )
            )
        }
}