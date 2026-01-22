package com.vitalstraker.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.vitalstraker.R
import com.vitalstraker.data.local.model.VitalItem
import com.vitalstraker.presentation.ui.theme.Card_dark_background
import com.vitalstraker.presentation.ui.theme.Card_light_background
import com.vitalstraker.presentation.ui.theme.Text_color
import com.vitalstraker.presentation.ui.theme.Text_heading
import com.vitalstraker.presentation.utils.getCurrentFormattedDateTime
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: VitalViewModel = koinViewModel()
) {
    var showDialog by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getVitals()
    }

    val vitalList = viewModel.vitalList.collectAsState().value

    Scaffold(
        topBar = { PregnancyTopBar() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = Card_dark_background
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
            }
        }
    ) { innerPadding ->

        if (vitalList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No vitals recorded yet.\nTap + to add your first entry.",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(vitalList) {
                    VitalCard(it)
                }
            }
        }


        if (showDialog) {
            AddVitalDialog(
                onDismiss = { showDialog = false },
                onSubmit = { sys, dia, weight, kicks ->
                    viewModel.addVital(
                        VitalItem(
                            heartRate = sys,
                            bp = dia,
                            weight = weight,
                            kicks = kicks,
                            dateTime = getCurrentFormattedDateTime()
                        )
                    )
                }
            )
        }

    }
}

@Composable
fun VitalCard(vital: VitalItem) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Card_light_background
        ),
        elevation = CardDefaults.cardElevation(6.dp),
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.SpaceBetween
                )
                {
                    VitalInfo(painterResource(R.drawable.heart_rate_1), "${vital.heartRate} bpm")
                    Spacer(modifier = Modifier.height(10.dp))
                    VitalInfo(painterResource(R.drawable.scale_1), "${vital.weight} kg")

                }

                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.SpaceBetween
                )
                {
                    VitalInfo(painterResource(R.drawable.blood_pressure_1), vital.bp)
                    Spacer(modifier = Modifier.height(10.dp))
                    VitalInfo(painterResource(R.drawable.newborn_2), "${vital.kicks} kicks")
                }

            }

            Spacer(Modifier.weight(1f))

            // Footer
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Card_dark_background)
                    .padding(8.dp),
                contentAlignment = Alignment.CenterEnd,
            )
            {
                Text(
                    text = vital.dateTime,
                    fontSize = 16.sp,
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun VitalInfo(icon: Painter, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(icon, contentDescription = null, modifier = Modifier.size(22.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = value, fontWeight = FontWeight.Medium, color = Text_color)
    }
}


@Composable
fun PregnancyTopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFD7B6FF))
            .statusBarsPadding()
            .padding(start = 16.dp, bottom = 16.dp)
    ) {
        Text(
            text = "Track My Pregnancy",
            color = Text_heading,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun AddVitalDialog(
    onDismiss: () -> Unit,
    onSubmit: (sys: String, dia: String, weight: String, kicks: String) -> Unit
) {
    var sysBP by rememberSaveable { mutableStateOf("") }
    var diaBP by rememberSaveable { mutableStateOf("") }
    var weight by rememberSaveable { mutableStateOf("") }
    var kicks by rememberSaveable { mutableStateOf("") }
    val isValid = sysBP.isNotBlank() &&
            diaBP.isNotBlank() &&
            weight.isNotBlank() &&
            kicks.isNotBlank()



    Dialog(onDismissRequest = onDismiss) {

        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = "Add Vitals",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF5B1D9B)
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = sysBP,
                        onValueChange = { sysBP = it },
                        label = { Text("Sys BP") },
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedTextField(
                        value = diaBP,
                        onValueChange = { diaBP = it },
                        label = { Text("Dia BP") },
                        modifier = Modifier.weight(1f)
                    )
                }

                OutlinedTextField(
                    value = weight,
                    onValueChange = { weight = it },
                    label = { Text("Weight (in kg)") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = kicks,
                    onValueChange = { kicks = it },
                    label = { Text("Baby Kicks") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        onSubmit(sysBP, diaBP, weight, kicks)
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isValid,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF9C4DCC)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Submit", color = Color.White)
                }
            }
        }
    }
}



