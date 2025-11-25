package org.churchofjesuschrist.prophets.ux.prophetdetail

import androidx.compose.foundation.clickable
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import org.churchofjesuschrist.prophets.data.local.entity.ProphetEntity
import org.churchofjesuschrist.prophets.ui.preview.ProphetPreviewParameterProvider
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

@Composable
fun ProphetDetailScreen(
    viewModel: ProphetDetailViewModel,
    onNavigateBack: () -> Unit,
    onImageClick: (prophetName: String) -> Unit
) {
    ProphetDetailContent(uiState = viewModel.uiState, onNavigateBack = onNavigateBack, onImageClick = onImageClick)
}

@Composable
private fun ProphetDetailContent(
    uiState: ProphetDetailUiState,
    onNavigateBack: () -> Unit = { /* Default no-op */ },
    onImageClick: (prophetName: String) -> Unit = { }
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
        prophet?.let { prophet ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = prophet.imageUrl,
                    contentDescription = "Prophet Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .clickable { onImageClick(prophet.name) },
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.height(16.dp))
                // Middle section: prophetCalled, born, apostleCalled, died (born after prophetCalled)
                InfoRow(label = "Born", value = prophet.born)
                InfoRow(label = "Apostle Called", value = prophet.apostleCalled)
                InfoRow(label = "Prophet Called", value = prophet.prophetCalled)
                InfoRow(label = "Died", value = prophet.died)
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Notable Quotes",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Column(modifier = Modifier.fillMaxWidth()) {
                    prophet.notableQuotes.forEach { quote ->
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

private fun formatDateForLocale(dateString: String?): String {
    if (dateString.isNullOrBlank()) return ""
    return try {
        val date = LocalDate.parse(dateString)
        val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(Locale.getDefault())
        date.toJavaLocalDate().format(formatter)
    } catch (e: Exception) {
        dateString // fallback to original if parsing fails
    }
}

@Composable
private fun InfoRow(label: String, value: String?) {
    val formattedValue = formatDateForLocale(value)
    if (formattedValue.isNotBlank()) {
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
                text = formattedValue,
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