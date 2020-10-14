package com.example.tp1kotlin

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_presentation.*

class PresentationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_presentation)

        val container=findViewById<FrameLayout>(R.id.FrameLayout)

        val transaction=supportFragmentManager.beginTransaction()
        transaction.add(R.id.FrameLayout, CocktailFragment.newInstance()).commit()
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.action_cocktails -> {
                val fragment = CocktailFragment()
                supportFragmentManager.beginTransaction().replace(
                    R.id.FrameLayout,
                    fragment,
                    fragment.javaClass.getSimpleName()
                )
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_compte -> {
                val fragment = CompteFragment()
                supportFragmentManager.beginTransaction().replace(
                    R.id.FrameLayout,
                    fragment,
                    fragment.javaClass.getSimpleName()
                )
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menulogout,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id=item.itemId
        if(id==R.id.btn_logout){
            FirebaseAuth.getInstance().signOut()
            val intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}
