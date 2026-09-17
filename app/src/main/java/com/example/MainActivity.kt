package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.DirectionsTransit
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.MainTab
import com.example.ui.TripViewModel
import com.example.ui.dialogs.AddChecklistDialog
import com.example.ui.dialogs.AddEmergencyContactDialog
import com.example.ui.dialogs.EditScheduleDialog
import com.example.ui.dialogs.ShowPhraseFlashCardDialog
import com.example.ui.screens.ChecklistScreen
import com.example.ui.screens.ContingencyScreen
import com.example.ui.screens.KidsGourmetScreen
import com.example.ui.screens.ScheduleScreen
import com.example.ui.screens.TransitPassScreen
import com.example.ui.theme.CrimsonAccent
import com.example.ui.theme.IndigoPrimary
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    private val viewModel: TripViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                TripMainApp(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripMainApp(viewModel: TripViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val filteredSchedules by viewModel.filteredSchedules.collectAsStateWithLifecycle()
    val allChecklists by viewModel.allChecklists.collectAsStateWithLifecycle()
    val allEmergencyContacts by viewModel.allEmergencyContacts.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag("trip_main_scaffold"),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.FlightTakeoff,
                                contentDescription = null,
                                tint = Color(0xFFFDE68A),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "간사이·키타큐슈 여행",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color.White.copy(alpha = 0.2f),
                            modifier = Modifier.padding(top = 2.dp)
                        ) {
                            Text(
                                text = "9/20 - 9/25 (5박 6일) 가족 플래너",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color(0xFFE2E8F0),
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = IndigoPrimary
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 6.dp,
                modifier = Modifier.testTag("bottom_nav_bar")
            ) {
                MainTab.values().forEach { tab ->
                    val isSelected = uiState.selectedTab == tab
                    val (icon, label) = when (tab) {
                        MainTab.SCHEDULE -> Pair(Icons.Default.CalendarMonth, "일정표")
                        MainTab.TRANSIT_PASS -> Pair(Icons.Default.DirectionsTransit, "교통&패스")
                        MainTab.GOURMET -> Pair(Icons.Default.Restaurant, "현지맛집")
                        MainTab.CONTINGENCY -> Pair(Icons.Default.Security, "돌발·SOS")
                        MainTab.CHECKLIST -> Pair(Icons.Default.Checklist, "체크리스트")
                    }
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { viewModel.selectTab(tab) },
                        icon = { Icon(icon, contentDescription = label) },
                        label = {
                            Text(
                                text = label,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = IndigoPrimary,
                            selectedTextColor = IndigoPrimary,
                            indicatorColor = Color(0xFFDBEAFE),
                            unselectedIconColor = MaterialTheme.colorScheme.outline,
                            unselectedTextColor = MaterialTheme.colorScheme.outline
                        ),
                        modifier = Modifier.testTag("nav_tab_${tab.name}")
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState.selectedTab) {
                MainTab.SCHEDULE -> ScheduleScreen(
                    viewModel = viewModel,
                    uiState = uiState,
                    schedules = filteredSchedules
                )
                MainTab.TRANSIT_PASS -> TransitPassScreen()
                MainTab.GOURMET -> KidsGourmetScreen()
                MainTab.CONTINGENCY -> ContingencyScreen(
                    viewModel = viewModel,
                    uiState = uiState,
                    emergencyContacts = allEmergencyContacts
                )
                MainTab.CHECKLIST -> ChecklistScreen(
                    viewModel = viewModel,
                    uiState = uiState,
                    checklists = allChecklists
                )
            }
        }
    }

    // Add / Edit Schedule Dialog
    if (uiState.isAddScheduleDialogOpen) {
        EditScheduleDialog(
            initialSchedule = uiState.editingSchedule,
            defaultDay = uiState.selectedDay,
            onDismiss = { viewModel.closeScheduleDialog() },
            onSave = { updated -> viewModel.saveSchedule(updated) },
            onDelete = { target -> viewModel.deleteSchedule(target) }
        )
    }

    // Add Checklist Item Dialog
    if (uiState.isAddChecklistDialogOpen) {
        AddChecklistDialog(
            onDismiss = { viewModel.closeAddChecklistDialog() },
            onAdd = { title, cat -> viewModel.addChecklist(title, cat) }
        )
    }

    // Add / Edit Emergency Contact Dialog
    if (uiState.isAddContactDialogOpen) {
        AddEmergencyContactDialog(
            initialContact = uiState.editingContact,
            onDismiss = { viewModel.closeContactDialog() },
            onSave = { contact -> viewModel.saveEmergencyContact(contact) },
            onDelete = { contact -> viewModel.deleteEmergencyContact(contact) }
        )
    }

    // Japanese Phrase High-visibility Flash Card Dialog
    uiState.selectedPhraseForFlashCard?.let { phrase ->
        ShowPhraseFlashCardDialog(
            phrase = phrase,
            onSpeak = { viewModel.speakJapanese(it) },
            onDismiss = { viewModel.showPhraseFlashCard(null) }
        )
    }
}
