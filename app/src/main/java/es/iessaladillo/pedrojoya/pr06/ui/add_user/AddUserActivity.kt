package es.iessaladillo.pedrojoya.pr06.ui.add_user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.text.isDigitsOnly
import com.google.android.material.snackbar.Snackbar
import es.iessaladillo.pedrojoya.pr06.R
import es.iessaladillo.pedrojoya.pr06.data.Database
import es.iessaladillo.pedrojoya.pr06.data.model.User
import es.iessaladillo.pedrojoya.pr06.databinding.UserActivityBinding
import es.iessaladillo.pedrojoya.pr06.utils.loadUrl
import java.lang.Double.parseDouble

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
    private val viewModel : AddUserViewModel by viewModels(){
        AddUserViewModelFactory(Database)
    }

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
        viewModel.setRandomImg()
        binding.imgUser.loadUrl(viewModel.img)
    }

    private fun listeners(){
        binding.txtWeb.setOnEditorActionListener { v, actionId, event ->
            onSave()
            true
        }
        binding.imgUser.setOnClickListener{
            viewModel.setRandomImg()
            binding.imgUser.loadUrl(viewModel.img)
        }
    }

    private fun onSave(){
        val name = binding.txtName.text.toString()
        val email = binding.txtEmail.text.toString()
        val tlf = binding.txtPhonenumber.text.toString()

        if(name.isBlank() || email.isBlank() || tlf.isBlank()){
            Snackbar.make(binding.root, getString(R.string.user_invalid_data), Snackbar.LENGTH_LONG).show()
        }
        else{
            val adress = binding.txtAdress.text.toString()
            val web = binding.txtWeb.text.toString()
            val photoUrl = viewModel.img
            val user = User(0, name, email, tlf, adress, web, photoUrl)
            viewModel.insert(user)
            finish()
        }

    }

}