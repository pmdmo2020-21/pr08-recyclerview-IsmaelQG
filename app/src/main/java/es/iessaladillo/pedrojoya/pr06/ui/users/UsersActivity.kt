package es.iessaladillo.pedrojoya.pr06.ui.users

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.GridLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*
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
                }
            }

    private val editUserActivityCall=
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
                val resultIntent = result.data
                if (result.resultCode == Activity.RESULT_OK && resultIntent != null) {
                }
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViews()
        observeUsers()
    }

    private fun setupViews(){
        setupRecyclerView()
        listeners()
    }

    private fun listeners(){
        binding.imgAdd.setOnClickListener { onAddUser() }
    }

    private fun observeUsers(){
        viewModel.users.observe(this){
            updateList(it)
        }
    }

    private fun updateList(newList : List<User>){
        listAdapter.submitList(newList)
        binding.imgAdd.visibility = if (newList.isEmpty()) VISIBLE else GONE
    }

    private fun setupRecyclerView() {
        binding.lstUsers.run {
            setHasFixedSize(true)
            adapter = listAdapter
            layoutManager = GridLayoutManager(this@UsersActivity, resources.getInteger(R.integer.users_grid_columns))
            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun onAddUser() {
        val intent = AddUserActivity.newIntent(this)
        addUserActivityCall.launch(intent)
    }

    private fun editUser(position : Int){
        val user : User = listAdapter.currentList[position]
        Toast.makeText(this, "Edit ${user.name}", Toast.LENGTH_SHORT).show()
        val intent = EditUserActivity.newIntent(this, user)
        editUserActivityCall.launch(intent)
    }

    private fun deleteUser(position : Int){
        val user : User = listAdapter.currentList[position]
        Toast.makeText(this, "Delete ${user.name}", Toast.LENGTH_SHORT).show()
        viewModel.delete(user)
    }

}