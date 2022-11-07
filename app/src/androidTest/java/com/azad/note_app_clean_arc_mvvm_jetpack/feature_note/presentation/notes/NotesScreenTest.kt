package com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.presentation.notes

import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.azad.note_app_clean_arc_mvvm_jetpack.di.AppModule
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.presentation.MainActivity
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
class NotesScreenTest{

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
            Note_App_Clean_Arc_MVVM_JetpackTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.NotesScreen.route
                ){
                    composable(route = Screen.NotesScreen.route){
                        NotesScreen(navController = navController)
                    }
                }
            }
        }
    }

    @Test
    fun clickToggleOrderSection_isVisible(){
        //Check for order section not visible initially
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertDoesNotExist()

        //Find and click sort icon
        composeRule.onNodeWithContentDescription("Sort").performClick()

        //Check for order section visibility
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertIsDisplayed()
    }

    @Test
    fun clickTitleSortingRadioButton_isCheckedTrue() {
        //Find and click sort icon
        composeRule.onNodeWithContentDescription("Sort").performClick()

        //Check for title selection not selected initially
        composeRule.onNodeWithContentDescription("Title").assertIsNotSelected()

        //Click title selection
        composeRule.onNodeWithContentDescription("Title").performClick()

        //Check for title selection is selected
        composeRule.onNodeWithContentDescription("Title").assertIsSelected()
    }

    @Test
    fun clickColorSortingRadioButton_isCheckedTrue() {
        //Find and click sort icon
        composeRule.onNodeWithContentDescription("Sort").performClick()

        //Check for color selection not selected initially
        composeRule.onNodeWithContentDescription("Color").assertIsNotSelected()

        //Click color selection
        composeRule.onNodeWithContentDescription("Color").performClick()

        //Check for color selection is selected
        composeRule.onNodeWithContentDescription("Color").assertIsSelected()
    }

    @Test
    fun clickAscSortRadioButton_isCheckedTrue() {
        //Find and click sort icon
        composeRule.onNodeWithContentDescription("Sort").performClick()

        //Check for asc selection not selected initially
        composeRule.onNodeWithContentDescription("Ascending").assertIsNotSelected()

        //Click asc selection
        composeRule.onNodeWithContentDescription("Ascending").performClick()

        //Check for asc selection is selected
        composeRule.onNodeWithContentDescription("Ascending").assertIsSelected()
    }

    @Test
    fun clickOnAlreadySelectedRadioButton_isUncheckedFalse() {
        //Find and click sort icon
        composeRule.onNodeWithContentDescription("Sort").performClick()

        //Check for date selection already selected
        composeRule.onNodeWithContentDescription("Date").assertIsSelected()

        //Click date selection
        composeRule.onNodeWithContentDescription("Date").performClick()

        //Check for date selection still selected
        composeRule.onNodeWithContentDescription("Date").assertIsSelected()
    }

    @Test
    fun checkUnselectedRadioButtonState_isUncheckedTrue() {
        //Find and click sort icon
        composeRule.onNodeWithContentDescription("Sort").performClick()

        //Check for desc selection already selected
        composeRule.onNodeWithContentDescription("Descending").assertIsSelected()

        //Click asc selection
        composeRule.onNodeWithContentDescription("Ascending").performClick()

        //Check for desc selection not selected
        composeRule.onNodeWithContentDescription("Descending").assertIsNotSelected()
    }
}