package org.churchofjesuschrist.prophets.data.repository

import co.touchlab.kermit.Logger
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.churchofjesuschrist.prophets.data.local.MainDatabase
import org.churchofjesuschrist.prophets.data.local.entity.ProphetEntity
import org.churchofjesuschrist.prophets.util.toEntity
import org.churchofjesuschrist.prophets.webservice.Cdn
import org.churchofjesuschrist.prophets.webservice.Result

/**
 * Repository for managing Prophet data from both remote CDN and local database.
 *
 * This class fetches prophet data from a remote source (CDN) and stores it in the local database.
 * It exposes flows for observing the list of prophets and individual prophet details by name.
 *
 * @property cdn The remote data source for fetching prophet information.
 * @property mainDatabase The local database for storing prophet entities.
 */
class ProphetRepository @Inject constructor(
    private val cdn: Cdn,
    private val mainDatabase: MainDatabase,
) {
    private val prophetsDao = mainDatabase.prophetsDao()

    /**
     * Returns a flow of all prophets from the local database.
     *
     * Also launches a coroutine to fetch prophets from the CDN and upserts them into the database.
     *
     * @param scope The coroutine scope in which to launch the network fetch and database update.
     * @return A flow emitting the list of [ProphetEntity] from the local database.
     */
    fun getProphetsFlow(scope: CoroutineScope): Flow<List<ProphetEntity>> {
        scope.launch {
            when (val result = cdn.getProphets()) {
                is Result.Success -> {
                    val dto = result.data
                    dto.forEach { prophetDto ->
                        val entity = prophetDto.toEntity()
                        prophetsDao.upsert(entity)
                    }
                }

                is Result.Error -> {
                    Logger.e(result.exception, message = { "Error fetching prophets: ${result.exception.message}" })
                }
            }
        }

        return prophetsDao.getAllProphetsFlow()
    }

    /**
     * Returns a flow of a single prophet entity by name from the local database.
     *
     * @param name The name of the prophet to retrieve.
     * @return A flow emitting the [ProphetEntity] with the given name, or null if not found.
     */
    fun getProphetByNameFlow(name: String): Flow<ProphetEntity?> = prophetsDao.getProphetByNameFlow(name)
}
