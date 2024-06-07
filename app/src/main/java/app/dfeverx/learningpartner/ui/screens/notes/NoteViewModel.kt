package app.dfeverx.learningpartner.ui.screens.notes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.EnergySavingsLeaf
import androidx.compose.material.icons.outlined.LibraryBooks
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Moving
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.dfeverx.learningpartner.models.local.StudyNote
import app.dfeverx.learningpartner.repos.NoteRepository
import app.dfeverx.learningpartner.ui.screens.home.TextIcon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val noteRepository: NoteRepository
) : ViewModel() {
    private val studyNoteId: Long = checkNotNull(savedStateHandle["noteId"])
    val note = noteRepository.studyNoteById(studyNoteId)

    private val _studyNote: MutableStateFlow<StudyNote> = MutableStateFlow(StudyNote(0))
    val studyNote: StateFlow<StudyNote>
        get() = _studyNote


    init {
        viewModelScope.launch {
            noteRepository.studyNoteById(studyNoteId).collect { studyNote ->
                _studyNote.value = studyNote
            }
        }
    }

}