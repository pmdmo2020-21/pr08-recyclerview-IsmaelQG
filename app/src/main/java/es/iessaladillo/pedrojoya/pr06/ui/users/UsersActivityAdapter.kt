package es.iessaladillo.pedrojoya.pr06.ui.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.pr06.R
import es.iessaladillo.pedrojoya.pr06.data.model.User
import es.iessaladillo.pedrojoya.pr06.utils.loadUrl

class UsersActivityAdapter : RecyclerView.Adapter<UsersActivityAdapter.ViewHolder>() {

    private var data : List<User> = emptyList()

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bind(user: User) {
            user.run {
                lblName.text = name
                lblEmail.text = email
                lblTlf.text = tlf
                imgCard.loadUrl(photoUrl)
            }
        }

        private val lblName : TextView = itemView.findViewById(R.id.lblName)
        private val lblEmail : TextView = itemView.findViewById(R.id.lblEmail)
        private val lblTlf : TextView = itemView.findViewById(R.id.lblTlf)
        private val imgCard : ImageView = itemView.findViewById(R.id.imgCard)
    }

    fun submitList(newList: List<User>){
        data = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.users_activity_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = data[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = data.size

}