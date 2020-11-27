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
import es.iessaladillo.pedrojoya.pr06.R
import es.iessaladillo.pedrojoya.pr06.data.model.User
import es.iessaladillo.pedrojoya.pr06.databinding.UserActivityBinding
import es.iessaladillo.pedrojoya.pr06.ui.add_user.AddUserActivity
import es.iessaladillo.pedrojoya.pr06.utils.loadUrl

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
    private val viewModel : EditUserViewModel by viewModels()

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
        viewModel.user = intent.getParcelableExtra(EXTRA_USER)
        viewModel.name = viewModel.user?.name ?: ""
        viewModel.email = viewModel.user?.email ?: ""
        viewModel.tlf = viewModel.user?.tlf ?: ""
        viewModel.adress = viewModel.user?.adress ?: ""
        viewModel.web = viewModel.user?.web ?: ""
    }

    private fun setAllText(){
        binding.txtName?.setText(viewModel.user?.name)
        binding.txtEmail?.setText(viewModel.user?.email)
        binding.txtPhonenumber?.setText(viewModel.user?.tlf)
        if(viewModel.user?.adress != null){
            binding.txtAdress?.setText(viewModel.user?.adress)
        }
        if(viewModel.user?.web != null){
            binding.txtWeb?.setText(viewModel.user?.web)
        }
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
            setResult(RESULT_OK, Intent().putExtras(bundleOf(AddUserActivity.EXTRA_USER to viewModel.user)))
            super.onBackPressed()
        }
    }

}