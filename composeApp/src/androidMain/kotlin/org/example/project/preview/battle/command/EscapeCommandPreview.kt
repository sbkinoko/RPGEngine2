package org.example.project.preview.battle.command

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import gamescreen.battle.command.escape.EscapeCommand
import gamescreen.battle.command.escape.EscapeViewModel

@Composable
@Preview
fun EscapeCommandPreview() {
    EscapeCommand(
        modifier = Modifier,
        escapeViewModel = EscapeViewModel()
    )
}
