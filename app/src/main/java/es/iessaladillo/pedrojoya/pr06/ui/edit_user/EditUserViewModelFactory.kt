package es.iessaladillo.pedrojoya.pr06.ui.edit_user

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.pr06.data.DataSource
import es.iessaladillo.pedrojoya.pr06.data.model.User

class EditUserViewModelFactory(private val dataSource: DataSource, private val userAssigned : User, private val application: Application) : ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T  = EditUserViewModel(dataSource, userAssigned, application) as T

}