package app.dfeverx.learningpartner.ui.screens.home

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.EnergySavingsLeaf
import androidx.compose.material.icons.outlined.LibraryBooks
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Moving
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.dfeverx.learningpartner.models.local.StudyNote
import app.dfeverx.learningpartner.repos.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ocpsoft.prettytime.PrettyTime
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val prettyTime: PrettyTime
) : ViewModel() {
    private val TAG = "HomeViewModel"
    private val _studyNotes: MutableStateFlow<List<StudyNote>> = MutableStateFlow(emptyList())
    val studyNotes: StateFlow<List<StudyNote>>
        get() = _studyNotes

    private val _categories =
        MutableStateFlow(
            listOf(
                TextIcon(Icons.Outlined.LibraryBooks, "All", "all"),
                TextIcon(Icons.Outlined.MenuBook, "Learning", "all"),
                TextIcon(Icons.Outlined.Archive, "Archive", "all"),
                TextIcon(Icons.Outlined.Moving, "Laws of motion", "all"),
                TextIcon(Icons.Outlined.EnergySavingsLeaf, "Biology", "all"),
            )
        )
    val categories = _categories.asStateFlow()

    init {
        viewModelScope.launch {
            homeRepository.studyNotes.collect { messages ->
                _studyNotes.value = messages
            }
        }
    }

    fun formatTime(time: Long): String {
        val date = Date(time )
       /* val currentDate = Date()
        val dateFormat = SimpleDateFormat("yyyyMMdd")
        val isSameDate = dateFormat.format(date) == dateFormat.format(currentDate)*/
        return prettyTime.format(date)
    }

    fun handleCategorySelection(value: String) {
        TODO("Not yet implemented")
    }

    fun addNote(pdfUri: String) {
        Log.d(TAG, "addNote: $pdfUri")
        viewModelScope.launch(Dispatchers.Main) {
            val r = homeRepository.addNote(pdfUri)
            Log.d(TAG, "addNote: $r")
        }

    }
}