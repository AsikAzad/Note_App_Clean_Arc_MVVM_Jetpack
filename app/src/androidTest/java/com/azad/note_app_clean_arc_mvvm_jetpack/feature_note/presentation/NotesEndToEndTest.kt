package com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.presentation

import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.azad.note_app_clean_arc_mvvm_jetpack.di.AppModule
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.presentation.notes.NotesScreen
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.presentation.util.Screen
import com.azad.note_app_clean_arc_mvvm_jetpack.ui.theme.Note_App_Clean_Arc_MVVM_JetpackTheme
import com.azad.note_app_clean_arc_mvvm_jetpack.util.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class) //To ignore prod db in test case
class NotesEndToEndTest {
    //For injecting hilt dependency
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    //For simulate UI components
    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @ExperimentalAnimationApi
    @Before
    fun setUp(){
        hiltRule.inject()
        composeRule.activity.setContent {
            //Copied from main activity
            Note_App_Clean_Arc_MVVM_JetpackTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.NotesScreen.route
                ){
                    composable(route = Screen.NotesScreen.route) {
                        NotesScreen(navController = navController)
                    }
                    composable(
                        route = Screen.AddEditNoteScreen.route + "?noteId={noteId}&noteColor={noteColor}",
                        arguments = listOf(
                            navArgument(
                                name = "noteId"
                            ){
                                type = NavType.IntType
                                defaultValue = -1
                            },
                            navArgument(
                                name = "noteColor"
                            ){
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ) {
                        val color = it.arguments?.getInt("noteColor") ?: -1
                        AddEditNoteScreen(
                            navController = navController,
                            noteColor = color
                        )
                    }
                }
            }
        }
    }

    @Test
    fun saveNewNoteAndEditAfterwards(){
        //On Note Screen
        //Click on add button
        composeRule.onNodeWithContentDescription("Add note").performClick()

        //On Add Edit Screen
        //Input title text
        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD).performTextInput("Test Title")

        //Input content text
        composeRule.onNodeWithTag(TestTags.CONTENT_TEXT_FIELD).performTextInput("Test Content")

        //Click on save button
        composeRule.onNodeWithContentDescription("Save note").performClick()

        //On Note Screen
        //Check for new note created
        composeRule.onNodeWithText("Test Title").assertIsDisplayed()

        //Click on note title to edit
        composeRule.onNodeWithText("Test Title").performClick()

        //On Add Edit Screen
        //Check for a particular note open
        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD).assertTextEquals("Test Title")
        composeRule.onNodeWithTag(TestTags.CONTENT_TEXT_FIELD).assertTextEquals("Test Content")

        //Edit note title
        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD).performTextReplacement("Test Title 2")

        //Click on save button to update
        composeRule.onNodeWithContentDescription("Save note").performClick()

        //On Note Screen
        //Check for note title updated
        composeRule.onNodeWithText("Test Title 2").assertIsDisplayed()
    }

    @Test
    fun saveNewNotes_orderByTitleDesc(){
        //Create 3 notes
        for(i in 1..3){
            //On Note Screen
            //Click on add button
            composeRule.onNodeWithContentDescription("Add note").performClick()

            //On Add Edit Screen
            //Input title text
            composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD).performTextInput("Test Title $i")

            //Input content text
            composeRule.onNodeWithTag(TestTags.CONTENT_TEXT_FIELD).performTextInput("Test Content $i")

            //Click on save button
            composeRule.onNodeWithContentDescription("Save note").performClick()
        }

        //On Note Screen
        //Check for new notes created
        composeRule.onNodeWithText("Test Title 1").assertIsDisplayed()
        composeRule.onNodeWithText("Test Title 2").assertIsDisplayed()
        composeRule.onNodeWithText("Test Title 3").assertIsDisplayed()

        //Click on sort icon
        composeRule.onNodeWithContentDescription("Sort").performClick()

        //Click on title radio button
        composeRule.onNodeWithContentDescription("Title").performClick()

        //Click on descending radio button
        composeRule.onNodeWithContentDescription("Descending").performClick()

        //Check for sort by title desc
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[0].assertTextContains("Test Title 3")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[1].assertTextContains("Test Title 2")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[2].assertTextContains("Test Title 1")
    }
}