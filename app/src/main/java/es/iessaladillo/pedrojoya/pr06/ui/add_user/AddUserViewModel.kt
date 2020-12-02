package es.iessaladillo.pedrojoya.pr06.ui.add_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.pr06.data.DataSource
import es.iessaladillo.pedrojoya.pr06.data.model.User
import java.util.*

// TODO:
//  Crear la clase EditUserViewModel. Ten en cuenta que la url de la photo
//  deberá ser preservada por si la actividad es destruida por falta de recursos.

class AddUserViewModel(val dataSource : DataSource) : ViewModel(){

    private var random : Random = Random()

    private val _img : MutableLiveData<String> = MutableLiveData(getRandomPhotoUrl())
    val img : LiveData<String>
        get() = _img

    fun actImg(){
        _img.value = getRandomPhotoUrl()
    }

    fun insert(user : User){
        dataSource.insertUser(user)
    }

    private fun getRandomPhotoUrl(): String =
            "https://picsum.photos/id/${random.nextInt(100)}/400/300"


}
