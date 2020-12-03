package es.iessaladillo.pedrojoya.pr06.ui.edit_user

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import es.iessaladillo.pedrojoya.pr06.utils.SoftInputUtils.hideSoftKeyboard
import es.iessaladillo.pedrojoya.pr06.utils.loadUrl
import es.iessaladillo.pedrojoya.pr06.utils.observeEvent
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

    companion object {

        const val EXTRA_USER = "EXTRA_USER"

        fun newIntent(context: Context, user : User) =
                Intent(context, EditUserActivity::class.java).putExtras(bundleOf(EXTRA_USER to user))
    }

    private val binding : UserActivityBinding by lazy{
        UserActivityBinding.inflate(layoutInflater)
    }
    private val viewModel : EditUserViewModel by viewModels(){
        EditUserViewModelFactory(Database, intent.getParcelableExtra(EXTRA_USER)!!, application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViews()
    }

    private fun setupViews(){
        getIntentData()
        observeUser()
        observeRandomImg()
        observeError()
        listeners()
    }

    private fun getIntentData() {
        if (intent == null || !intent.hasExtra(EXTRA_USER)) {
            throw RuntimeException()
        }
    }

    private fun observeUser(){
        viewModel.user.observe(this) {
            setAllText(it)
        }
    }

    private fun observeRandomImg(){
        viewModel.img.observe(this){
            changeImg(it)
        }
    }

    private fun observeError(){
        viewModel.errorMsg.observeEvent(this){
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun setAllText(user : User){
        binding.txtName.setText(user.name)
        binding.txtEmail.setText(user.email)
        binding.txtPhonenumber.setText(user.tlf)
        binding.txtAdress.setText(user.adress)
        binding.txtWeb.setText(user.web)
        binding.imgUser.loadUrl(user.photoUrl)
    }

    private fun changeImg(img : String){
        binding.imgUser.loadUrl(img)
    }

    private fun listeners(){
        binding.txtWeb.setOnEditorActionListener { v, actionId, event ->
            onSave()
            true
        }
        binding.imgUser.setOnClickListener{
            viewModel.actImg()
        }
    }

    private fun onSave(){
        hideSoftKeyboard(binding.root)
        val name = binding.txtName.text.toString()
        val email = binding.txtEmail.text.toString()
        val tlf = binding.txtPhonenumber.text.toString()

        if(viewModel.check(name, email, tlf)){
            if(viewModel.tlfFormat(tlf)){
                val adress = binding.txtAdress.text.toString()
                val web = binding.txtWeb.text.toString()
                val photoUrl = viewModel.user.value?.photoUrl ?: ""
                val user = User(viewModel.user.value?.id ?: 0, name, email, tlf, adress, web, photoUrl)
                viewModel.edit(user)
                finish()
            }
        }
    }

}