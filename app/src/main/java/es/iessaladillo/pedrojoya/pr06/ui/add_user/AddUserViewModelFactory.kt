package es.iessaladillo.pedrojoya.pr06.ui.add_user

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.pr06.data.DataSource

class AddUserViewModelFactory(private val dataSource : DataSource, private val application: Application) : ViewModelProvider.Factory{
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T  = AddUserViewModel(dataSource, application) as T

}