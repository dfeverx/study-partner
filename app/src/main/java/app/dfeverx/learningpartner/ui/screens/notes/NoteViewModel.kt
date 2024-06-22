package app.dfeverx.learningpartner.ui.screens.notes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.dfeverx.learningpartner.models.local.StudyNote
import app.dfeverx.learningpartner.repos.StudyNoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val noteRepository: StudyNoteRepository
) : ViewModel() {
    private val studyNoteId: String = checkNotNull(savedStateHandle["noteId"])

    private val _studyNote: MutableStateFlow<StudyNote> = MutableStateFlow(StudyNote("", ""))
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