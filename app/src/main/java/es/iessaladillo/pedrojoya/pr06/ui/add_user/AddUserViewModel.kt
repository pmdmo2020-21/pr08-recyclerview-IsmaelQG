package es.iessaladillo.pedrojoya.pr06.ui.add_user

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import es.iessaladillo.pedrojoya.pr06.R
import es.iessaladillo.pedrojoya.pr06.data.DataSource
import es.iessaladillo.pedrojoya.pr06.data.model.User
import es.iessaladillo.pedrojoya.pr06.utils.Event
import java.util.*

// TODO:
//  Crear la clase EditUserViewModel. Ten en cuenta que la url de la photo
//  deberá ser preservada por si la actividad es destruida por falta de recursos.

class AddUserViewModel(private val dataSource : DataSource, private val application: Application) : ViewModel(){

    private var random : Random = Random()

    private val _img : MutableLiveData<String> = MutableLiveData(getRandomPhotoUrl())
    val img : LiveData<String>
        get() = _img
    private val _errorMsg : MutableLiveData<Event<String>> = MutableLiveData()
    val errorMsg : LiveData<Event<String>>
        get() = _errorMsg

    fun actImg(){
        _img.value = getRandomPhotoUrl()
    }

    fun insert(user : User){
        dataSource.insertUser(user)
    }

    fun check(vararg fields : String) : Boolean{
        for(f in fields){
            if(f.isBlank()){
                _errorMsg.value = Event(application.getString(R.string.user_invalid_data))
                return false;
            }
        }
        return true;
    }

    private fun getRandomPhotoUrl(): String =
            "https://picsum.photos/id/${random.nextInt(100)}/400/300"


}
