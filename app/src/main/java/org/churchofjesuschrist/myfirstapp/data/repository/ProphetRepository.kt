package org.churchofjesuschrist.myfirstapp.data.repository

import co.touchlab.kermit.Logger
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.churchofjesuschrist.myfirstapp.data.local.MainDatabase
import org.churchofjesuschrist.myfirstapp.data.local.entity.ProphetEntity
import org.churchofjesuschrist.myfirstapp.util.toEntity
import org.churchofjesuschrist.myfirstapp.webservice.LocalCdn
import org.churchofjesuschrist.myfirstapp.webservice.Result

class ProphetRepository @Inject constructor(
    private val localCdn: LocalCdn,
    private val mainDatabase: MainDatabase,
) {
    private val prophetsDao = mainDatabase.prophetsDao()

    fun getProphetsFlow(scope: CoroutineScope): Flow<List<ProphetEntity>> {
        scope.launch {
            when (val result = localCdn.getProphets()) {
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
}

