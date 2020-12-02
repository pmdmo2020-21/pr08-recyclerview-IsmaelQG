package es.iessaladillo.pedrojoya.pr06.ui.edit_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.pr06.data.DataSource
import es.iessaladillo.pedrojoya.pr06.data.model.User
import java.util.*


// TODO:
//  Crear la clase EditUserViewModel. Ten en cuenta que la url de la photo
//  deberá ser preservada por si la actividad es destruida por falta de recursos.

class EditUserViewModel(private val dataSource : DataSource, userAssigned : User) : ViewModel(){

    // Para obtener un URL de foto de forma aleatoria (tendrás que definir
    // e inicializar el random a nivel de clase.
    private var random : Random = Random()

    private val _user : MutableLiveData<User> = MutableLiveData(userAssigned)
    val user : LiveData<User>
        get() = _user
    private val _img : MutableLiveData<String> = MutableLiveData(userAssigned.photoUrl)
    val img : LiveData<String>
        get() = _img

    fun actImg(){
        _img.value = getRandomPhotoUrl()
    }

    fun edit(user : User){
        dataSource.updateUser(user)
    }

    private fun getRandomPhotoUrl(): String =
            "https://picsum.photos/id/${random.nextInt(100)}/400/300"


}
