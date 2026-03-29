package com.taqwa.journal.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taqwa.journal.data.preferences.ShieldPlan
import com.taqwa.journal.ui.components.TaqwaAccentCard
import com.taqwa.journal.ui.components.TaqwaCard
import com.taqwa.journal.ui.components.TaqwaTopBar
import com.taqwa.journal.ui.theme.*

@Composable
fun ShieldPlanScreen(
    plans: List<ShieldPlan>,
    onEditPlan: (ShieldPlan) -> Unit,
    onAddCustomPlan: () -> Unit,
    onDeletePlan: (String) -> Unit,
    onBack: () -> Unit
) {
    var planToDelete by remember { mutableStateOf<ShieldPlan?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        TaqwaTopBar(
            title = "Shield Plans",
            onBack = onBack
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                horizontal = TaqwaDimens.screenPaddingHorizontal,
                vertical = TaqwaDimens.spaceM
            ),
            verticalArrangement = Arrangement.spacedBy(TaqwaDimens.spaceM)
        ) {
            // Header explanation
            item {
                TaqwaCard {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "🛡️", fontSize = 36.sp)
                        Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                        Text(
                            text = "Your Pre-Written Defense",
                            style = TaqwaType.sectionTitle,
                            color = VanillaCustard,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                        Text(
                            text = "When a trigger hits, your brain goes offline.\nThese plans think FOR you.\n\nPrepare now. Fight later.",
                            style = TaqwaType.bodySmall.copy(lineHeight = 22.sp),
                            color = TextGray,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // Plan cards
            itemsIndexed(plans, key = { _, plan -> plan.triggerId }) { _, plan ->
                ShieldPlanCard(
                    plan = plan,
                    onEdit = { onEditPlan(plan) },
                    onDelete = if (plan.isCustom) {
                        { planToDelete = plan }
                    } else null
                )
            }

            // Add custom trigger
            item {
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))
                OutlinedButton(
                    onClick = onAddCustomPlan,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = PrimaryLight),
                    shape = RoundedCornerShape(TaqwaDimens.buttonCornerRadius)
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(TaqwaDimens.spaceS))
                    Text("Add Custom Trigger", style = TaqwaType.button, color = PrimaryLight)
                }
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }

    // Delete confirmation
    if (planToDelete != null) {
        AlertDialog(
            onDismissRequest = { planToDelete = null },
            title = { Text("Delete Shield Plan?", style = TaqwaType.sectionTitle, color = TextWhite) },
            text = {
                Text(
                    "Remove \"${planToDelete?.triggerName}\" from your shield plans?",
                    style = TaqwaType.body, color = TextGray
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    planToDelete?.let { onDeletePlan(it.triggerId) }
                    planToDelete = null
                }) { Text("Delete", color = AccentRed) }
            },
            dismissButton = {
                TextButton(onClick = { planToDelete = null }) {
                    Text("Keep", color = TextLight)
                }
            },
            containerColor = BackgroundCard
        )
    }
}

@Composable
private fun ShieldPlanCard(
    plan: ShieldPlan,
    onEdit: () -> Unit,
    onDelete: (() -> Unit)?
) {
    val hasPersonalNote = plan.personalNote.isNotBlank()
    val stepCount = plan.steps.size

    TaqwaAccentCard(accentColor = PrimaryMedium, alpha = 0.08f) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = plan.emoji, fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(TaqwaDimens.spaceM))
                    Column {
                        Text(
                            text = plan.triggerName,
                            style = TaqwaType.cardTitle,
                            color = VanillaCustard
                        )
                        if (plan.triggerNameAr.isNotBlank()) {
                            Text(
                                text = plan.triggerNameAr,
                                style = TaqwaType.captionSmall,
                                color = TextMuted
                            )
                        }
                    }
                }
                Row {
                    IconButton(
                        onClick = onEdit,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = PrimaryLight,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    if (onDelete != null) {
                        IconButton(
                            onClick = onDelete,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = TextMuted,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceS))

            Text(
                text = plan.description,
                style = TaqwaType.captionSmall,
                color = TextGray,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(TaqwaDimens.spaceL))

            // Steps preview
            plan.steps.forEachIndexed { index, step ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "${index + 1}.",
                        style = TaqwaType.captionSmall.copy(fontWeight = FontWeight.Bold),
                        color = PrimaryLight,
                        modifier = Modifier.width(20.dp)
                    )
                    Text(
                        text = step,
                        style = TaqwaType.bodySmall,
                        color = TextLight,
                        lineHeight = 20.sp
                    )
                }
            }

            // Personal note
            if (hasPersonalNote) {
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                HorizontalDivider(color = DividerColor, thickness = TaqwaDimens.dividerThickness)
                Spacer(modifier = Modifier.height(TaqwaDimens.spaceM))
                Text(
                    text = "💭 \"${plan.personalNote}\"",
                    style = TaqwaType.bodySmall.copy(fontWeight = FontWeight.Light),
                    color = TextGray,
                    lineHeight = 20.sp
                )
            }
        }
    }
}