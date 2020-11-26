package es.iessaladillo.pedrojoya.pr06.ui.users

import android.app.Activity
import android.content.Intent
import android.database.DatabaseUtils
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.pr06.R
import es.iessaladillo.pedrojoya.pr06.data.Database
import es.iessaladillo.pedrojoya.pr06.data.model.User
import es.iessaladillo.pedrojoya.pr06.databinding.UsersActivityBinding
import es.iessaladillo.pedrojoya.pr06.ui.add_user.AddUserActivity
import es.iessaladillo.pedrojoya.pr06.ui.add_user.AddUserActivity.Companion.newIntent

class UsersActivity : AppCompatActivity() {

    // TODO: Código de la actividad.
    //  Ten en cuenta que el recyclerview se muestra en forma de grid,
    //  donde el número de columnas está gestionado
    //  por R.integer.users_grid_columns
    //  ...

    // NO TOCAR: Estos métodos gestionan el menú y su gestión

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.users, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mnuAdd) {
            onAddUser()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    // FIN NO TOCAR

    private lateinit var binding : UsersActivityBinding
    private val listAdapter : UsersActivityAdapter = UsersActivityAdapter()
    private var database = Database()
    private val viewModel : UsersActivityViewModel by viewModels()

    private val addUserActivityCall=
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
                val resultIntent = result.data
                if (result.resultCode == Activity.RESULT_OK && resultIntent != null) {
                    extractResult(resultIntent)
                }
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UsersActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
    }

    private fun setupViews(){
        setupRecyclerView()
        imageVisibility()
        binding.imgAdd.setOnClickListener { onAddUser() }
    }

    private fun imageVisibility(){
        if(listAdapter.itemCount > 0){
            binding.imgAdd.visibility = GONE
        }
        else{
            binding.imgAdd.visibility = VISIBLE
        }
    }

    private fun setupRecyclerView() {
        binding.lstUsers.run {
            setHasFixedSize(true)
            adapter = listAdapter
            layoutManager = LinearLayoutManager(this@UsersActivity)
            addItemDecoration(DividerItemDecoration(this@UsersActivity, RecyclerView.VERTICAL))
            itemAnimator = DefaultItemAnimator()

        }
    }

    fun onAddUser() {
        val intent = newIntent(this)
        addUserActivityCall.launch(intent)
    }

    private fun extractResult(intent: Intent) {
        if (!intent.hasExtra(AddUserActivity.EXTRA_USER)) {
            throw RuntimeException()
        }
        var user : User = intent.getParcelableExtra(AddUserActivity.EXTRA_USER)!!
        database.insertUser(user)
        listAdapter.submitList(database.getAllUsersOrderedByName().value!!)
        listAdapter.onCreateViewHolder(binding.lstUsers, database.id.toInt())
    }

}