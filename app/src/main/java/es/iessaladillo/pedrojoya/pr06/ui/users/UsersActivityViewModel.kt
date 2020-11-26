package es.iessaladillo.pedrojoya.pr06.ui.users

import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.pr06.data.Database
import es.iessaladillo.pedrojoya.pr06.data.model.User
import javax.sql.DataSource

// TODO:
//  Crear clase UsersActivityViewModel

class UsersActivityViewModel() : ViewModel(){

    lateinit var users : List<User>

    fun setUsers(database: Database){
        users = database.getAllUsersOrderedByName().value!!
    }

}