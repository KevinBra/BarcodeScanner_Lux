package fr.epf.min.barcodescannerlux

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fr.epf.min.barcodescannerlux.repository.Repository

class MainViewModelFactory(private val repository: Repository)
    : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClasse: Class<T>): T {
            return MainViewModel(repository) as T
        }
    }