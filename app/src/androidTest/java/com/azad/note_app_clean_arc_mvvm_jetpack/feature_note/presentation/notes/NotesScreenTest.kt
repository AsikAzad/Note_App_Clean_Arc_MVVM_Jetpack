package com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.presentation.notes

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import com.azad.note_app_clean_arc_mvvm_jetpack.di.AppModule
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.presentation.MainActivity
import com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.presentation.util.Screen
import com.azad.note_app_clean_arc_mvvm_jetpack.ui.theme.Note_App_Clean_Arc_MVVM_JetpackTheme
import com.azad.note_app_clean_arc_mvvm_jetpack.util.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
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
//        composeRule.setContent {
//            Note_App_Clean_Arc_MVVM_JetpackTheme {
//                val navController = rememberNavController()
//                NavHost(
//                    navController = navController,
//                    startDestination = Screen.NotesScreen.route
//                ){
//                    composable(route = Screen.NotesScreen.route){
//                        NotesScreen(navController = navController)
//                    }
//                }
//            }
//        }
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
        composeRule.onNodeWithTag(TestTags.TITLE_RADIO_BUTTON).assertIsNotSelected()

        //Click title selection
        composeRule.onNodeWithTag(TestTags.TITLE_RADIO_BUTTON).performClick()

        //Check for title selection is selected
        composeRule.onNodeWithTag(TestTags.TITLE_RADIO_BUTTON).assertIsSelected()
    }

    @Test
    fun clickColorSortingRadioButton_isCheckedTrue() {
        //Find and click sort icon
        composeRule.onNodeWithContentDescription("Sort").performClick()

        //Check for color selection not selected initially
        composeRule.onNodeWithTag(TestTags.COLOR_RADIO_BUTTON).assertIsNotSelected()

        //Click color selection
        composeRule.onNodeWithTag(TestTags.COLOR_RADIO_BUTTON).performClick()

        //Check for color selection is selected
        composeRule.onNodeWithTag(TestTags.COLOR_RADIO_BUTTON).assertIsSelected()
    }

    @Test
    fun clickAscSortRadioButton_isCheckedTrue() {
        //Find and click sort icon
        composeRule.onNodeWithContentDescription("Sort").performClick()

        //Check for asc selection not selected initially
        composeRule.onNodeWithTag(TestTags.ASC_RADIO_BUTTON).assertIsNotSelected()

        //Click asc selection
        composeRule.onNodeWithTag(TestTags.ASC_RADIO_BUTTON).performClick()

        //Check for asc selection is selected
        composeRule.onNodeWithTag(TestTags.ASC_RADIO_BUTTON).assertIsSelected()
    }

    @Test
    fun clickOnAlreadySelectedRadioButton_isUncheckedFalse() {
        //Find and click sort icon
        composeRule.onNodeWithContentDescription("Sort").performClick()

        //Check for date selection already selected
        composeRule.onNodeWithTag(TestTags.DATE_RADIO_BUTTON).assertIsSelected()

        //Click date selection
        composeRule.onNodeWithTag(TestTags.DATE_RADIO_BUTTON).performClick()

        //Check for date selection still selected
        composeRule.onNodeWithTag(TestTags.DATE_RADIO_BUTTON).assertIsSelected()
    }

    @Test
    fun checkUnselectedRadioButtonState_isUncheckedTrue() {
        //Find and click sort icon
        composeRule.onNodeWithContentDescription("Sort").performClick()

        //Check for desc selection already selected
        composeRule.onNodeWithTag(TestTags.DESC_RADIO_BUTTON).assertIsSelected()

        //Click asc selection
        composeRule.onNodeWithTag(TestTags.ASC_RADIO_BUTTON).performClick()

        //Check for desc selection not selected
        composeRule.onNodeWithTag(TestTags.DESC_RADIO_BUTTON).assertIsNotSelected()
    }




}