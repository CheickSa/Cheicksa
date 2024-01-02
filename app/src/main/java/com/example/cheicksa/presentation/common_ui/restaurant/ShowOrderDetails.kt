package com.example.cheicksa.presentation.common_ui.restaurant

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.maxkeppeker.sheets.core.CoreDialog
import com.maxkeppeker.sheets.core.models.CoreSelection
import com.maxkeppeker.sheets.core.models.base.ButtonStyle
import com.maxkeppeker.sheets.core.models.base.Header
import com.maxkeppeker.sheets.core.models.base.SelectionButton
import com.maxkeppeker.sheets.core.models.base.UseCaseState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowOrderDetails(
    state: UseCaseState,
    onPositiveClick: () -> Unit = {},
    onNegativeClick: () -> Unit = {},
    negativeText: String = "annuler",
    positiveText: String = "envoyer",
    title: String = "Votre avis",
    body: @Composable () -> Unit = {},
    extraButton: SelectionButton? = null,
    onExtraButtonClick: () -> Unit = {},

    ) {


    CoreDialog(
        state = state,
        selection = CoreSelection(
            withButtonView = true,
            negativeButton = SelectionButton(
                text = negativeText,
                //IconSource(Icons.Rounded.Notifications),
                type = ButtonStyle.FILLED
            ),
            positiveButton = SelectionButton(
                text = positiveText,
                //IconSource(Icons.Rounded.Add),
                type= ButtonStyle.ELEVATED
            ),
            onNegativeClick = { onNegativeClick() },
            onPositiveClick = { onPositiveClick() },
            extraButton = extraButton,
            onExtraButtonClick = onExtraButtonClick
        ),
        header = Header.Default(
            title = title,
        ),
        onPositiveValid = true,
        body = {
            body.invoke()
        },

        )
}