package es.iessaladillo.pedrojoya.pr06.ui.users

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.pr06.data.DataSource
import es.iessaladillo.pedrojoya.pr06.data.Database
import es.iessaladillo.pedrojoya.pr06.data.model.User

// TODO:
//  Crear clase UsersActivityViewModel

class UsersActivityViewModel(private val dataSource: DataSource) : ViewModel(){

    var users : LiveData<List<User>> = dataSource.getAllUsersOrderedByName()

    fun delete(user : User){
        dataSource.deleteUser(user)
    }

}