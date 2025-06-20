package leonardomazzuca.com.github


import EventoViewModel
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class ItemsViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EventoViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}