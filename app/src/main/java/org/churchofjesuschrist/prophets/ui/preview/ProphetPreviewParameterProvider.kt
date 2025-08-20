package org.churchofjesuschrist.prophets.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import org.churchofjesuschrist.prophets.data.local.entity.ProphetEntity

/**
 * [ProphetPreviewParameterProvider] supplies a single [ProphetEntity] for use in Compose previews.
 *
 * This provider retrieves the first entity from [ProphetListPreviewParameterProvider].
 * It is intended for UI preview purposes only and does not reflect dynamic or production data.
 */
class ProphetPreviewParameterProvider : PreviewParameterProvider<ProphetEntity> {
    /**
     * Sequence containing a single [ProphetEntity] for Compose previews.
     */
    override val values = sequenceOf(ProphetListPreviewParameterProvider().values.first())
}