package com.example.projetreveil.ui.theme.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetreveil.data.GroupRepository
import com.example.projetreveil.data.UsersRepository
import com.example.projetreveil.ui.theme.data.Objet.Group
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class GroupeScreenViewModel(name: String)  : ViewModel() {

    val repository = GroupRepository();

    /*
    val groupes: StateFlow<List<Group>> = repository.getGroupesFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    */
}