package com.example.tp1kotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import coil.load
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.util.Observer


class CompteFragment : Fragment() {
    private lateinit var user: FirebaseUser
    private lateinit var txtName: TextView
    private lateinit var txtMail: TextView
    private lateinit var profilePic: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root=inflater.inflate(R.layout.fragment_compte, container, false);
        if(FirebaseAuth.getInstance().currentUser!=null)
            user= FirebaseAuth.getInstance().currentUser!!

        txtName=root.findViewById(R.id.txtName)
        txtMail=root.findViewById(R.id.txtMail)
        profilePic=root.findViewById(R.id.profilePic)

        (activity as AppCompatActivity).supportActionBar?.title ="Compte"

        var name=user.displayName
        if(name==null||name.isEmpty())
            name="Impossible de récuperer le nom"
        txtName.text=name
        txtMail.text=user.email

        profilePic.load(user.photoUrl)
        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() = CompteFragment()
    }
}