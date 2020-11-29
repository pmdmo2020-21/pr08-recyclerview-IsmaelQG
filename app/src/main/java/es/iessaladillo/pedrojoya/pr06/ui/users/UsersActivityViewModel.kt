package es.iessaladillo.pedrojoya.pr06.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.pr06.data.Database
import es.iessaladillo.pedrojoya.pr06.data.model.User

// TODO:
//  Crear clase UsersActivityViewModel

class UsersActivityViewModel(private val dataSource: Database) : ViewModel(){

    var users : LiveData<List<User>> = dataSource.getAllUsersOrderedByName()

    fun insert(user : User){
        dataSource.insertUser(user)
    }

    fun delete(user : User){
        dataSource.deleteUser(user)
    }

    fun edit(user : User){
        dataSource.updateUser(user)
    }

}