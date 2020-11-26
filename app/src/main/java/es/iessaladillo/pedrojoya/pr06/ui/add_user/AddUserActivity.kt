package es.iessaladillo.pedrojoya.pr06.ui.add_user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.renderscript.ScriptGroup
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import es.iessaladillo.pedrojoya.pr06.R
import es.iessaladillo.pedrojoya.pr06.data.model.User
import es.iessaladillo.pedrojoya.pr06.databinding.UserActivityBinding
import es.iessaladillo.pedrojoya.pr06.ui.users.UsersActivityViewModel

class AddUserActivity : AppCompatActivity() {

    // TODO: Código de la actividad.
    //  ...

    // NO TOCAR: Estos métodos gestionan el menú y su gestión

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.user, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mnuSave) {
            onSave()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    // FIN NO TOCAR

    private lateinit var binding : UserActivityBinding
    private lateinit var user : User
    private val viewModel : AddUserViewModel by viewModels()

    companion object {

        const val EXTRA_USER = "EXTRA_USER"

        fun newIntent(context: Context) =
                Intent(context, AddUserActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UserActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
    }

    private fun setupViews(){

            user = User(
                    0,
                    "binding.txtName?.text.toString()",
                    "binding.txtEmail?.text.toString()",
                    "binding.txtPhonenumber?.text.toString()",
                    "binding.txtAdress?.text.toString()",
                    "binding.txtWeb?.text.toString()",
                    "https://picsum.photos/id/20/400/300"
            )

    }

    private fun onSave() {
        setResult(RESULT_OK, Intent().putExtras(bundleOf(EXTRA_USER to user)))
        super.onBackPressed()
    }

}