package org.churchofjesuschrist.prophets.ux.prophetdetail

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlinx.coroutines.flow.MutableStateFlow
import org.churchofjesuschrist.prophets.data.local.entity.ProphetEntity

class ProphetDetailUiStatePreviewParameterProvider : PreviewParameterProvider<ProphetDetailUiState> {
    override val values = sequenceOf(
        ProphetDetailUiState(
            prophetFlow = MutableStateFlow(
                ProphetEntity(
                    name = "Joseph Smith Jr.",
                    born = "December 23, 1805",
                    died = "June 27, 1844",
                    apostleCalled = null,
                    prophetCalled = "April 6, 1830",
                    notableQuotes = listOf(
                        "I told the brethren that the Book of Mormon was the most correct of any book on earth, and the keystone of our religion, and a man would get nearer to God by abiding by its precepts, than by any other book.",
                        "The fundamental principles of our religion are the testimony of the apostles and prophets, concerning Jesus Christ, that He died, was buried, and rose again the third day, and ascended into heaven; and all other things which pertain to our religion are only appendages to it.",
                        "If we have a religion that will not bear the test of all things, we do not want it; if it will not bear the test of earth, and heaven, and angels, we do not want it."
                    ),
                    imageUrl = "https://churchofjesuschrist.org/imgs/cbace218fc690ed250c875ed08a667e006439191/full/640%2C/0/default"
                )
            )
        ),
        ProphetDetailUiState(
            prophetFlow = MutableStateFlow(
                ProphetEntity(
                    name = "Brigham Young",
                    born = "June 1, 1801",
                    died = "August 29, 1877",
                    apostleCalled = "February 14, 1835",
                    prophetCalled = "December 27, 1847",
                    notableQuotes = listOf(
                        "This is the place.",
                        "My faith is that the God of heaven will not suffer a people who are doing his will to be destroyed.",
                        "The greatest fear I have is that the people of this Church will get rich in this country, forget God and his people, wax fat, and kick themselves out of the Church and go to hell."
                    ),
                    imageUrl = "https://churchofjesuschrist.org/imgs/c5ad66094c574f122610595c368e09bfab5d4a06/full/640%2C/0/default"
                )
            )
        ),
        ProphetDetailUiState(
            prophetFlow = MutableStateFlow(
                ProphetEntity(
                    name = "John Taylor",
                    born = "November 1, 1808",
                    died = "July 25, 1887",
                    apostleCalled = "December 19, 1838",
                    prophetCalled = "October 10, 1880",
                    notableQuotes = listOf(
                        "I am as happy as the Lord can make me in my present condition, and I know that God is with me and will sustain me.",
                        "We should be true to God, true to our families, true to our country, true to our friends, and true to ourselves.",
                        "The kingdom of God is a government, and it is a government of principles, and the principles are the laws of righteousness."
                    ),
                    imageUrl = "https://churchofjesuschrist.org/imgs/df24d8e09721888a1827b680a05d893552ab33ba/full/640%2C/0/default"
                )
            )
        )
    )
}
