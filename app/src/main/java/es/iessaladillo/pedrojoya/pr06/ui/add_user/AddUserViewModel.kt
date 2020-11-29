package es.iessaladillo.pedrojoya.pr06.ui.add_user

import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.pr06.data.model.User
import java.util.*

// TODO:
//  Crear la clase EditUserViewModel. Ten en cuenta que la url de la photo
//  deberá ser preservada por si la actividad es destruida por falta de recursos.

class AddUserViewModel : ViewModel(){

    private var random : Random = Random()

    lateinit var img : String
    var randomImg = getRandomPhotoUrl()
    var id : Long = 0
    var name = ""
    var email = ""
    var tlf = ""
    var adress = ""
    var web = ""
    lateinit var user : User

    fun setRandomImg(){
        randomImg = getRandomPhotoUrl()
        img = randomImg
    }

    fun checkIfEmpty() : Boolean{
        return (name == "" || email == "" || tlf == "")
    }

    private fun getRandomPhotoUrl(): String =
            "https://picsum.photos/id/${random.nextInt(100)}/400/300"


}
