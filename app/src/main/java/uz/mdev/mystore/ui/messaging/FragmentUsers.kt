package uz.mdev.mystore.ui.messaging

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.*
import uz.mdev.mystore.R
import uz.mdev.mystore.adapters.UserAdapter
import uz.mdev.mystore.databinding.FragmentUsersBinding
import uz.mdev.mystore.db.entities.product.Product
import uz.mdev.mystore.helpers.hide
import uz.mdev.mystore.models.User

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentUsers.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentUsers : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentUsersBinding
    lateinit var userAdapter: UserAdapter
    lateinit var list_users: ArrayList<User>

    //realtime firebase
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var reference: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUsersBinding.inflate(inflater, container, false)
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("users").child("shop_boys")
        list_users = ArrayList()


        setAdapter()
        setList()
        return binding.root
    }

    private fun setList() {
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list_users.clear()
                for (child in snapshot.children) {
                    val value = child.getValue(User::class.java)
                    if (value != null) {
                        list_users.add(value)
                    }
                }
                userAdapter.notifyDataSetChanged()
                binding.progress.hide()
            }

            override fun onCancelled(error: DatabaseError) {
                binding.progress.hide()
            }
        })
    }

    private fun setAdapter() {
        list_users = ArrayList()
        userAdapter = UserAdapter(list_users, object : UserAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, user: User) {

            }

            override fun onPhoneClick(position: Int, user: User) {

            }

        })
        binding.apply {
            rv.adapter = userAdapter
        }
    }

}