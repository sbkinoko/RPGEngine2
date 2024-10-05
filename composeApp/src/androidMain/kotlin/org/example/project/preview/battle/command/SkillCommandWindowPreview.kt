package org.example.project.preview.battle.command

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import gamescreen.battle.command.skill.SkillCommandViewModel
import gamescreen.battle.command.skill.SkillCommandWindow

@Composable
@Preview
fun SkillCommandWindowPreview() {
    SkillCommandWindow(
        skillCommandViewModel = SkillCommandViewModel(),
    )
}
