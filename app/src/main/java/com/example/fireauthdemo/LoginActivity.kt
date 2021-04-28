package com.example.fireauthdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Btn to register page
        register_page_btn.setOnClickListener{

            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finish()
        }

        login_btn.setOnClickListener {

            // this checks if an email or password has been entered for login
            when{
                TextUtils.isEmpty(login_email.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "please enter email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(login_Password.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "please enter password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }else ->{
                        val email: String = login_email.text.toString().trim { it <= ' '}
                        val password: String = login_Password.text.toString().trim { it <= ' ' }

                        //create an instance and login with a email and password.
                        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(
                            OnCompleteListener { task ->

                                // If login was successfully done
                                if(task.isSuccessful){

                                    Toast.makeText(
                                    this@LoginActivity,
                                    "You were Logged in Successfully",
                                    Toast.LENGTH_SHORT
                                    ).show()

                                    /**
                                     * Here the new user is  logged in so we sign them out
                                     * and send them to the main screen with user ID and Email that user have used for
                                    */

                                    val intent =
                                    Intent(this@LoginActivity, MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
                                    intent.putExtra("email_id", email)
                                    startActivity(intent)
                                    finish()
                                }else{
                                    // If login is not successfully then show error message
                                    Toast.makeText(
                                        this@LoginActivity,
                                        task.exception!!.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        )
                }
            }
        }
    }
}