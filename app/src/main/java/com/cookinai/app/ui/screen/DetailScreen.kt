package com.cookinai.app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.cookinai.app.ui.theme.*
import com.cookinai.app.ui.viewmodel.DetailViewModel
import com.cookinai.app.ui.viewmodel.GameDetailUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    gameId: Int,
    onBack: () -> Unit,
    viewModel: DetailViewModel = viewModel()
) {
    LaunchedEffect(gameId) {
        viewModel.fetchGameDetail(gameId)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Game Detail",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = OnPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = OnPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SoftPink
                )
            )
        },
        containerColor = LightBlue
    ) { paddingValues ->
        when (val state = uiState) {
            is GameDetailUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(
                            color = SoftPinkDark,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Memuat detail game...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = OnSurface.copy(alpha = 0.6f)
                        )
                    }
                }
            }

            is GameDetailUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Text(text = "😢", fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Gagal memuat detail game.",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = state.message,
                            style = MaterialTheme.typography.bodyMedium,
                            color = OnSurface.copy(alpha = 0.6f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.fetchGameDetail(gameId) },
                            colors = ButtonDefaults.buttonColors(containerColor = SoftPinkDark)
                        ) {
                            Text("Coba Lagi", color = Color.White)
                        }
                    }
                }
            }

            is GameDetailUiState.Success -> {
                val game = state.game
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Hero Image
                    AsyncImage(
                        model = game.backgroundImage,
                        contentDescription = game.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                            .background(LightBlueDark)
                    )

                    // Content Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = CardBackground),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {
                            // Genre chips
                            val genreText = game.genres?.take(3)?.joinToString(" · ") { it.name }
                            if (!genreText.isNullOrEmpty()) {
                                Text(
                                    text = genreText,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = OnSurface.copy(alpha = 0.5f)
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                            }

                            // Game Title
                            Text(
                                text = game.name,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = OnSurface
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            // Rating and Release Date Row
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Rating Badge
                                Box(
                                    modifier = Modifier
                                        .background(
                                            color = PastelYellow,
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                        .padding(horizontal = 12.dp, vertical = 8.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Filled.Star,
                                            contentDescription = "Rating",
                                            tint = Color(0xFFFFA000),
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = String.format("%.1f", game.rating),
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF5D4037)
                                        )
                                        if ((game.ratingsCount ?: 0) > 0) {
                                            Text(
                                                text = " (${formatCount(game.ratingsCount!!)})",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = Color(0xFF5D4037).copy(alpha = 0.7f)
                                            )
                                        }
                                    }
                                }

                                // Release Date Badge
                                if (!game.released.isNullOrEmpty()) {
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                color = LightBlue,
                                                shape = RoundedCornerShape(12.dp)
                                            )
                                            .padding(horizontal = 12.dp, vertical = 8.dp)
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = Icons.Default.DateRange,
                                                contentDescription = "Release Date",
                                                tint = Color(0xFF0097A7),
                                                modifier = Modifier.size(14.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = "Released: ${game.released}",
                                                style = MaterialTheme.typography.labelMedium,
                                                fontWeight = FontWeight.Medium,
                                                color = Color(0xFF006064)
                                            )
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            // Divider
                            Divider(color = Outline.copy(alpha = 0.4f))

                            Spacer(modifier = Modifier.height(16.dp))

                            // About Section
                            Text(
                                text = "About the Game",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = OnSurface
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            val description = game.descriptionRaw?.trim()
                            if (!description.isNullOrEmpty()) {
                                Text(
                                    text = description,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = OnSurface.copy(alpha = 0.8f),
                                    lineHeight = 22.sp
                                )
                            } else {
                                Text(
                                    text = "Tidak ada deskripsi tersedia untuk game ini.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = OnSurface.copy(alpha = 0.45f)
                                )
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            // Platforms
                            val platformText = game.platforms?.take(4)?.joinToString(", ") {
                                it.platform.name
                            }
                            if (!platformText.isNullOrEmpty()) {
                                Text(
                                    text = "Platform",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = OnSurface.copy(alpha = 0.6f)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = platformText,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = OnSurface.copy(alpha = 0.75f)
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}

private fun formatCount(count: Int): String {
    return when {
        count >= 1000 -> "${count / 1000}k"
        else -> count.toString()
    }
}
