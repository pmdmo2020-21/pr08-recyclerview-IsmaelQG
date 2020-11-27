package es.iessaladillo.pedrojoya.pr06.ui.users

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
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
import es.iessaladillo.pedrojoya.pr06.ui.edit_user.EditUserActivity

class UsersActivity : AppCompatActivity(){

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

    private val binding : UsersActivityBinding by lazy {
        UsersActivityBinding.inflate(layoutInflater)
    }
    private val listAdapter : UsersActivityAdapter = UsersActivityAdapter().also {
        it.onEditListener = { position -> editUser(position) }
        it.onDeleteListener = { position -> deleteUser(position) }
    }
    private val viewModel : UsersActivityViewModel by viewModels{
        UsersActivityViewModelFactory(Database)
    }

    private val addUserActivityCall=
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
                val resultIntent = result.data
                if (result.resultCode == Activity.RESULT_OK && resultIntent != null) {
                    extractResultAdd(resultIntent)
                    imageVisibility()
                }
            }

    private val editUserActivityCall=
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
                val resultIntent = result.data
                if (result.resultCode == Activity.RESULT_OK && resultIntent != null) {
                    extractResultEdit(resultIntent)
                    imageVisibility()
                }
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViews()
    }

    private fun setupViews(){
        setupRecyclerView()
        imageVisibility()
        listeners()
    }

    private fun listeners(){
        binding.imgAdd.setOnClickListener { onAddUser() }
    }

    private fun imageVisibility(){
        listAdapter.submitList(Database.getAllUsersOrderedByName().value!!)
        if(listAdapter.itemCount == 0){
            binding.imgAdd.visibility = VISIBLE
        }
        else{
            binding.imgAdd.visibility = GONE
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
        val intent = AddUserActivity.newIntent(this)
        addUserActivityCall.launch(intent)
    }

    private fun extractResultAdd(intent: Intent) {
        if (!intent.hasExtra(AddUserActivity.EXTRA_USER)) {
            throw RuntimeException()
        }
        val user : User = intent.getParcelableExtra(AddUserActivity.EXTRA_USER)!!
        Database.insertUser(user)
        viewModel.setUsers()
        listAdapter.submitList(viewModel.users!!)
        listAdapter.onCreateViewHolder(binding.lstUsers, Database.id.toInt())
    }

    private fun extractResultEdit(intent: Intent) {
        if (!intent.hasExtra(AddUserActivity.EXTRA_USER)) {
            throw RuntimeException()
        }
        val user : User = intent.getParcelableExtra(AddUserActivity.EXTRA_USER)!!
        Database.insertUser(user)
        Database.deleteUser(viewModel.preUserEdited)
        viewModel.userEdited = user
        viewModel.setUsers()
        listAdapter.submitList(viewModel.users!!)
        listAdapter.onCreateViewHolder(binding.lstUsers, Database.id.toInt())
        viewModel.edit(viewModel.userEdited)
    }

    private fun editUser(position : Int){
        val user : User = listAdapter.getItem(position)
        viewModel.preUserEdited = user
        Toast.makeText(this, "Edit ${user.name}", Toast.LENGTH_SHORT).show()
        val intent = EditUserActivity.newIntent(this, user)
        editUserActivityCall.launch(intent)
    }

    private fun deleteUser(position : Int){
        val user : User = listAdapter.getItem(position)
        Toast.makeText(this, "Delete ${user.name}", Toast.LENGTH_SHORT).show()
        viewModel.delete(user)
        listAdapter.submitList(Database.getAllUsersOrderedByName().value!!)
    }

}