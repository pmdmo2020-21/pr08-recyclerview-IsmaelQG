package es.iessaladillo.pedrojoya.pr06.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.iessaladillo.pedrojoya.pr06.data.model.User

class Database : DataSource{
    private val list : MutableList<User> = mutableListOf()
    private val listData : MutableLiveData<List<User>> = MutableLiveData<List<User>>()
    private var id : Long = 0

    init {
        orderList()
    }

    override fun getAllUsersOrderedByName(): LiveData<List<User>> {
        return listData
    }

    private fun orderList(){
        listData.value = list.sortedBy { it.name }
    }

    private fun actData(){
        listData.value = list
    }

    override fun insertUser(user: User) {
        user.id = ++id
        list.add(user)
        actData()
    }

    override fun updateUser(user: User) {
        val indexUser = list.indexOfFirst {it.id == user.id}
        if(indexUser != -1){
            list[indexUser] = user
            actData()
        }
    }

    override fun deleteUser(user: User) {
        val indexUser = list.indexOfFirst {it.id == user.id}
        if(indexUser != -1){
            list.removeAt(indexUser)
            actData()
        }
    }

}
// TODO:
//  Crear una singleton Database que implemente la interfaz DataSource.
//  Al insertar un usuario, se le asignará un id autonumérico
//  (primer valor válido será el 1) que deberá ser gestionado por la Database.
//  La base de datos debe ser reactiva, de manera que cuando se agrege,
//  actualice o borre un usuario, los observadores de la lista deberán
//  recibir la nueva lista.
//  Al consultar los usuario se deberá retornar un LiveData con la lista
//  de usuarios ordenada por nombre

