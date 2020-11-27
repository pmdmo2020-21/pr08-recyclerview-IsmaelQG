package es.iessaladillo.pedrojoya.pr06.ui.add_user

import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.pr06.data.model.User
import java.util.*

// TODO:
//  Crear la clase EditUserViewModel. Ten en cuenta que la url de la photo
//  deberá ser preservada por si la actividad es destruida por falta de recursos.

class AddUserViewModel : ViewModel(){

    var id : Long = 0
    var name : String = ""
    var email : String = ""
    var tlf : String = ""
    var adress : String = ""
    var web : String = ""
    var user : User? = null

    private val random : Random = Random()

    private fun getRandomPhotoUrl(): String =
            "https://picsum.photos/id/${random.nextInt(100)}/400/300"


}
