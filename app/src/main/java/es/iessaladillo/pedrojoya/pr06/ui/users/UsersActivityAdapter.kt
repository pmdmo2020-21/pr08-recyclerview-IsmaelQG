package es.iessaladillo.pedrojoya.pr06.ui.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.pr06.R
import es.iessaladillo.pedrojoya.pr06.data.model.User
import es.iessaladillo.pedrojoya.pr06.utils.loadUrl
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.user_activity.view.*
import kotlinx.android.synthetic.main.users_activity_item.*
import kotlinx.android.synthetic.main.users_activity_item.view.*
import kotlinx.android.synthetic.main.users_activity_item.view.lblEmail
import kotlinx.android.synthetic.main.users_activity_item.view.lblName

class UsersActivityAdapter : RecyclerView.Adapter<UsersActivityAdapter.ViewHolder>() {

    private var data : List<User> = emptyList()
    var onEditListener : ((Int) -> Unit)? = null
    var onDeleteListener : ((Int) -> Unit)? = null

    init{
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return data[position].id
    }

    inner class ViewHolder(override val containerView : View) : RecyclerView.ViewHolder(containerView) , LayoutContainer{

        init{
            btnEdit.setOnClickListener{
                onEditListener?.invoke(adapterPosition)
            }
            btnDelete.setOnClickListener{
                onDeleteListener?.invoke(adapterPosition)
            }
        }

        fun bind(user: User) {
            user.run {
                containerView.lblName.text = name
                containerView.lblEmail.text = email
                containerView.lblTlf.text = tlf
                containerView.lblAdress?.text = adress
                containerView.lblWeb?.text = email
                containerView.imgCard.loadUrl(photoUrl)
            }
        }
    }

    fun submitList(newList: List<User>){
        data = newList
        notifyDataSetChanged()
    }

    fun getItem(position : Int) : User = data[position]

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