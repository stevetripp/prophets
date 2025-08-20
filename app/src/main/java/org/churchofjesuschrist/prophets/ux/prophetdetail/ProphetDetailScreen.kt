package org.churchofjesuschrist.prophets.ux.prophetdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.MutableStateFlow
import org.churchofjesuschrist.prophets.data.local.entity.ProphetEntity
import org.churchofjesuschrist.prophets.ui.preview.ProphetPreviewParameterProvider

@Composable
fun ProphetDetailScreen(
    viewModel: ProphetDetailViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    ProphetDetailContent(viewModel.uiState, onNavigateBack = onNavigateBack)
}

@Composable
private fun ProphetDetailContent(
    uiState: ProphetDetailUiState,
    onNavigateBack: () -> Unit = { /* Default no-op */ },
) {
    val prophet by uiState.prophetFlow.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = prophet?.name ?: "") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        prophet?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = it.imageUrl,
                    contentDescription = "Prophet Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.height(16.dp))
                // Middle section: prophetCalled, born, apostleCalled, died (born after prophetCalled)
                InfoRow(label = "Born", value = it.born)
                InfoRow(label = "Apostle Called", value = it.apostleCalled)
                InfoRow(label = "Prophet Called", value = it.prophetCalled)
                InfoRow(label = "Died", value = it.died)
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Notable Quotes",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Column(modifier = Modifier.fillMaxWidth()) {
                    it.notableQuotes.forEach { quote ->
                        Text(
                            text = "\u2022 $quote",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String?) {
    if (!value.isNullOrBlank()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "$label:",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.width(120.dp)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProphetDetailScreenPreview(
    @PreviewParameter(ProphetPreviewParameterProvider::class) prophet: ProphetEntity
) {
    ProphetDetailContent(ProphetDetailUiState(MutableStateFlow(prophet)))
}