package com.example.tp1kotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity(), View.OnClickListener {
   private lateinit var mAuth:FirebaseAuth
    lateinit var name:EditText
    lateinit var editTextPassword:EditText
    lateinit var mGoogleSignInClient:GoogleSignInClient
    val RC_SIGN_IN:Int=262

    override fun onCreate(savedInstanceState: Bundle?) {
        mAuth=FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar!!.setTitle("Connexion")
        if(mAuth.currentUser!=null){
            updateUI(mAuth.currentUser!!)
        }
        val signInButton:SignInButton=findViewById(R.id.sign_in_button)
        signInButton.setSize(SignInButton.SIZE_STANDARD)
        signInButton.setOnClickListener(this)
        val gso=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient=GoogleSignIn.getClient(this, gso)
        name=findViewById(R.id.editTextTextPersonName)
        editTextPassword=findViewById(R.id.editTextTextPassword)
        val actionCodeSettings=ActionCodeSettings.newBuilder()
            .setUrl("https://www.example.com/finishSignUp?cartId=1234")
            // This must be true
            .setHandleCodeInApp(true)
            .setIOSBundleId("com.example.ios")
            .setAndroidPackageName(
                "com.example.td1_jav",
                true, /* installIfNotAvailable */
                "12"    /* minimumVersion */
            )
            .build();
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d("AUTH", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("AUTH", "Google sign in failed", e)
                // ...
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("AUTH", "signInWithCredential:success")
                    val user = mAuth.currentUser
                    if (user != null) {
                        updateUI(user)
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("AUTH", "signInWithCredential:failure", task.exception)
                    // ...
                    Snackbar.make(
                        findViewById(R.id.rootLayout),
                        "Authentication Failed.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }

                // ...
            }
    }

    private fun googleSignIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    fun signIn(email: String?, password: String?) {
        mAuth.signInWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("AUTH", "signInWithEmail:success")
                    val user = mAuth.currentUser
                    if (!user!!.isEmailVerified) {
                        Snackbar.make(
                            findViewById(R.id.rootLayout),
                            "Veuillez d'abord confirmer votre mail.",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        user.sendEmailVerification()
                        FirebaseAuth.getInstance().signOut()
                    } else {
                        Snackbar.make(
                            findViewById(R.id.rootLayout),
                            "Connexion réussie.",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        updateUI(user)
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("AUTH", "signInWithEmail:failure", task.exception)
                    Snackbar.make(
                        findViewById(R.id.rootLayout),
                        "Authentication Failed.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }

            }
    }

    private fun checkInfo(title: String):Boolean{
        if(name.text.toString().isEmpty()||editTextPassword.text.isEmpty()){
            val alertDialog=AlertDialog.Builder(this).setTitle(title)
                .setMessage("Un des deux champs n'a pas été renseigné")
                .setPositiveButton(android.R.string.yes, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
            return false
        }
        return true
    }


    fun updateUI(user: FirebaseUser?){
        if(user!=null){
            intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }

    fun createAccount(email: String?, password: String?) {
        mAuth.createUserWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("AUTH", "createUserWithEmail:success")
                    Snackbar.make(
                        findViewById(R.id.rootLayout),
                        "Inscription réussie, veuillez confirmer votre mail.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    val user = mAuth.currentUser
                    user!!.sendEmailVerification()
                    mAuth.signOut()
                    name.setText("")
                    editTextPassword.setText("")
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("AUTH", "createUserWithEmail:failure", task.exception)
                    Snackbar.make(
                        findViewById(R.id.rootLayout),
                        "Problème à l'inscription.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }

                // ...
            }
    }


    fun connexion(view: View) {
        if(checkInfo("Connexion"))
            signIn(name.text.toString(), editTextPassword.getText().toString())
    }
    fun inscription(view: View) {
        if(checkInfo("inscription"))
            createAccount(name.text.toString(), editTextPassword.text.toString())
    }
    fun mdpOublie(view: View) {}
    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id){
                R.id.sign_in_button -> googleSignIn()
            }
        }
    }
}