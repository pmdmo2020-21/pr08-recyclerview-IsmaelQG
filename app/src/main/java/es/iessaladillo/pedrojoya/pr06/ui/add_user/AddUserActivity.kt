package es.iessaladillo.pedrojoya.pr06.ui.add_user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.renderscript.ScriptGroup
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import es.iessaladillo.pedrojoya.pr06.R
import es.iessaladillo.pedrojoya.pr06.data.Database
import es.iessaladillo.pedrojoya.pr06.data.model.User
import es.iessaladillo.pedrojoya.pr06.databinding.UserActivityBinding
import es.iessaladillo.pedrojoya.pr06.ui.users.UsersActivityViewModel
import es.iessaladillo.pedrojoya.pr06.utils.loadUrl

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
        listeners()
    }

    private fun listeners(){
        binding.txtName?.addTextChangedListener ( object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.name = binding.txtName?.text.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        binding.txtEmail?.addTextChangedListener ( object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.email = binding.txtEmail?.text.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        binding.txtPhonenumber?.addTextChangedListener ( object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.tlf = binding.txtPhonenumber?.text.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        binding.txtAdress?.addTextChangedListener ( object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.adress = binding.txtAdress?.text.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        binding.txtWeb?.addTextChangedListener ( object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.web = binding.txtWeb?.text.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        binding.imgUser?.setOnClickListener{ binding.imgUser?.loadUrl("https://picsum.photos/id/122/400/300") }
    }

    private fun onSave() {
        if(viewModel.name == "" || viewModel.email == "" || viewModel.tlf == ""){

        }
        else{
            viewModel.user = User(viewModel.id, viewModel.name, viewModel.email, viewModel.tlf, viewModel.adress, viewModel.web, "https://picsum.photos/id/122/400/300")
            setResult(RESULT_OK, Intent().putExtras(bundleOf(EXTRA_USER to viewModel.user)))
            super.onBackPressed()
        }

    }

}