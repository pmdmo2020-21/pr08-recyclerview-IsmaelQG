package es.iessaladillo.pedrojoya.pr06.ui.edit_user

import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.pr06.data.DataSource
import es.iessaladillo.pedrojoya.pr06.data.model.User
import java.util.*


// TODO:
//  Crear la clase EditUserViewModel. Ten en cuenta que la url de la photo
//  deberá ser preservada por si la actividad es destruida por falta de recursos.

class EditUserViewModel(private val dataSource : DataSource) : ViewModel(){

    // Para obtener un URL de foto de forma aleatoria (tendrás que definir
    // e inicializar el random a nivel de clase.
    lateinit var user : User
    private var random : Random = Random()

    lateinit var img : String
    var randomImg = getRandomPhotoUrl()

    fun setRandomImg(){
        randomImg = getRandomPhotoUrl()
        img = randomImg
    }

    fun edit(user : User){
        dataSource.updateUser(user)
    }

    private fun getRandomPhotoUrl(): String =
            "https://picsum.photos/id/${random.nextInt(100)}/400/300"


}
