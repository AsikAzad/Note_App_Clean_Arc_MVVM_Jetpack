package com.azad.note_app_clean_arc_mvvm_jetpack.feature_note.presentation.notes

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
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











}