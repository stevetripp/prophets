package org.churchofjesuschrist.prophets.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import org.churchofjesuschrist.prophets.data.local.entity.ProphetEntity
import org.churchofjesuschrist.prophets.webservice.dto.ProphetDto

/**
 * [ProphetListPreviewParameterProvider] supplies a sequence of [ProphetEntity] objects for use in Compose previews.
 *
 * This provider uses a hardcoded list of sample [ProphetDto] data, which is mapped to [ProphetEntity].
 * It is intended for UI preview purposes only and does not reflect dynamic or production data.
 */
class ProphetListPreviewParameterProvider : PreviewParameterProvider<ProphetEntity> {
    /**
     * Lazily initialized list of [ProphetEntity] objects for previewing UI components.
     *
     * The data is constructed from a static list of [ProphetDto]s and mapped to [ProphetEntity].
     */
    private val prophets: List<ProphetEntity> by lazy {
        // Hardcoded ProphetDto list for preview purposes
        val prophetDtos: List<ProphetDto> = listOf(
            ProphetDto(
                prophetCalled = "1830-04-06",
                born = "1805-12-23",
                apostleCalled = null,
                notableQuotes = listOf(
                    "I told the brethren that the Book of Mormon was the most correct of any book on earth, and the keystone of our religion, and a man would get nearer to God by abiding by its precepts, than by any other book.",
                    "The fundamental principles of our religion are the testimony of the apostles and prophets, concerning Jesus Christ, that He died, was buried, and rose again the third day, and ascended into heaven; and all other things which pertain to our religion are only appendages to it.",
                    "If we have a religion that will not bear the test of all things, we do not want it; if it will not bear the test of earth, and heaven, and angels, we do not want it."
                ),
                name = "Joseph Smith Jr.",
                died = "1844-06-27",
                imageUrl = "https://churchofjesuschrist.org/imgs/cbace218fc690ed250c875ed08a667e006439191/full/640%2C/0/default"
            ),
            ProphetDto(
                prophetCalled = "1847-12-27",
                born = "1801-06-01",
                apostleCalled = "1835-02-14",
                notableQuotes = listOf(
                    "This is the place.",
                    "My faith is that the God of heaven will not suffer a people who are doing his will to be destroyed.",
                    "The greatest fear I have is that the people of this Church will get rich in this country, forget God and his people, wax fat, and kick themselves out of the Church and go to hell."
                ),
                name = "Brigham Young",
                died = "1877-08-29",
                imageUrl = "https://churchofjesuschrist.org/imgs/c5ad66094c574f122610595c368e09bfab5d4a06/full/640%2C/0/default"
            ),
            ProphetDto(
                prophetCalled = "1880-10-10",
                born = "1808-11-01",
                apostleCalled = "1838-12-19",
                notableQuotes = listOf(
                    "I am as happy as the Lord can make me in my present condition, and I know that God is with me and will sustain me.",
                    "We should be true to God, true to our families, true to our country, true to our friends, and true to ourselves.",
                    "The kingdom of God is a government, and it is a government of principles, and the principles are the laws of righteousness."
                ),
                name = "John Taylor",
                died = "1887-07-25",
                imageUrl = "https://churchofjesuschrist.org/imgs/df24d8e09721888a1827b680a05d893552ab33ba/full/640%2C/0/default"
            ),
            ProphetDto(
                prophetCalled = "1889-04-07",
                born = "1807-03-01",
                apostleCalled = "1839-04-26",
                notableQuotes = listOf(
                    "I have had a great many revelations, and I have had a great many manifestations, and I have had a great many dreams and visions.",
                    "The Lord will not suffer His people to be led astray if they will only listen to the counsel of the inspired leaders of the Church.",
                    "The world is going to have to humble itself before the Lord, and it will have to repent of its sins, and it will have to obey the commandments of God."
                ),
                name = "Wilford Woodruff",
                died = "1898-09-02",
                imageUrl = "https://churchofjesuschrist.org/imgs/df24d8e09721888a1827b680a05d893552ab33ba/full/640%2C/0/default"
            )
        )
        prophetDtos.map { dto ->
            ProphetEntity(
                prophetCalled = dto.prophetCalled,
                born = dto.born,
                apostleCalled = dto.apostleCalled,
                notableQuotes = dto.notableQuotes,
                name = dto.name,
                died = dto.died,
                imageUrl = dto.imageUrl
            )
        }
    }

    /**
     * Sequence of [ProphetEntity] objects exposed for Compose previews.
     */
    override val values = prophets.asSequence()
}