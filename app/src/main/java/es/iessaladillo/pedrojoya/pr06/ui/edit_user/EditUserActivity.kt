package es.iessaladillo.pedrojoya.pr06.ui.edit_user

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
import com.google.android.material.snackbar.Snackbar
import es.iessaladillo.pedrojoya.pr06.R
import es.iessaladillo.pedrojoya.pr06.data.Database
import es.iessaladillo.pedrojoya.pr06.data.model.User
import es.iessaladillo.pedrojoya.pr06.databinding.UserActivityBinding
import es.iessaladillo.pedrojoya.pr06.ui.add_user.AddUserActivity
import es.iessaladillo.pedrojoya.pr06.utils.loadUrl
import java.lang.Double.parseDouble

class EditUserActivity : AppCompatActivity() {

    // TODO: Código de la actividad.
    //  Ten en cuenta que la actividad debe recibir a través del intent
    //  con el que es llamado el objeto User corresondiente
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
    private val viewModel : EditUserViewModel by viewModels(){
        EditUserViewModelFactory(Database)
    }

    companion object {

        const val EXTRA_USER = "EXTRA_USER"

        fun newIntent(context: Context, user : User) =
                Intent(context, EditUserActivity::class.java).putExtras(bundleOf(EXTRA_USER to user))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UserActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
    }

    private fun setupViews(){
        getIntentData()
        setAllText()
        listeners()
    }

    private fun getIntentData() {
        if (intent == null || !intent.hasExtra(EXTRA_USER)) {
            throw RuntimeException()
        }
        viewModel.user = intent.getParcelableExtra(EXTRA_USER)!!
    }

    private fun setAllText(){
        binding.txtName.setText(viewModel.user.name)
        binding.txtEmail.setText(viewModel.user.email)
        binding.txtPhonenumber.setText(viewModel.user.tlf)
        binding.txtAdress.setText(viewModel.user.adress)
        binding.txtWeb.setText(viewModel.user.web)
        binding.imgUser.loadUrl(viewModel.user.photoUrl)
    }

    private fun listeners(){
        binding.txtWeb.setOnEditorActionListener { v, actionId, event ->
            onSave()
            true
        }
        binding.imgUser.setOnClickListener{
            viewModel.setRandomImg()
            binding.imgUser.loadUrl(viewModel.img)
            viewModel.user.photoUrl = viewModel.img
        }
    }

    private fun onSave() {
        val name = binding.txtName.text.toString()
        val email = binding.txtEmail.text.toString()
        val tlf = binding.txtPhonenumber.text.toString()

        if(name.isBlank() || email.isBlank() || tlf.isBlank()){
            Snackbar.make(binding.root, getString(R.string.user_invalid_data), Snackbar.LENGTH_LONG).show()
        }
        else{
            val adress = binding.txtAdress.text.toString()
            val web = binding.txtWeb.text.toString()
            val photoUrl = viewModel.user.photoUrl
            val user = User(viewModel.user.id, name, email, tlf, adress, web, photoUrl)
            viewModel.edit(user)
            finish()
        }
    }

}