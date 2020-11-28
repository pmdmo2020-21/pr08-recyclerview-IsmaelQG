package es.iessaladillo.pedrojoya.pr06.ui.users

import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.pr06.data.Database
import es.iessaladillo.pedrojoya.pr06.data.model.User

// TODO:
//  Crear clase UsersActivityViewModel

class UsersActivityViewModel(private val dataSource: Database) : ViewModel(){

    var users : List<User>? = dataSource.getAllUsersOrderedByName().value
    lateinit var preUserEdited : User
    lateinit var userEdited : User

    fun setUsers(){
        users = dataSource.getAllUsersOrderedByName().value
    }

    fun delete(user : User){
        dataSource.deleteUser(user)
    }

    fun edit(user : User){
        dataSource.updateUser(user)
    }

}