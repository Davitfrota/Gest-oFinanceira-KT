package com.example.gestaofinanceira.Activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.gestaofinanceira.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {
    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var mAuth: FirebaseAuth
    private lateinit var entrar: Button
    private lateinit var registrar: TextView
    private lateinit var mEmail: EditText
    private lateinit var mPassword: EditText



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_main)




            entrar = findViewById(R.id.login_entrar_main)
            registrar = findViewById(R.id.login_text_register )
            mEmail = findViewById(R.id.register_tEmailAddress)
            mPassword = findViewById(R.id.Password_main)


            mAuth = FirebaseAuth.getInstance()
            mDatabase = FirebaseDatabase.getInstance()


            entrar.setOnClickListener{
                val email = mEmail.text.toString()
                val password = mPassword.text.toString()

                var isLoginFilled = true

                if (email.isEmpty()) {
                    mEmail.error = "Este campo não pode estar vazio"
                    isLoginFilled = false

                }

                if (password.isEmpty()) {
                    mPassword.error = "Este campo não pode estar vazio"
                    isLoginFilled = false

                }

                if (isLoginFilled) {
                    val dialog = ProgressDialog(MainActivity@ this)
                    dialog.setTitle("Validando usuário")
                    dialog.isIndeterminate = true
                    dialog.show()

                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                        dialog.dismiss()
                        if (it.isSuccessful) {
                            goToMain()
                            finish()
                        } else {
                            showToastMessage("Usuário ou senha incorretos")
                        }

                    }


                }



            }
            registrar.setOnClickListener{
                goToResgister()
            }

        }

        private fun goToMain(){
            val man = Intent(this,Activity_Financas::class.java)
            startActivity(man)
        }
        private fun goToResgister(){
            val res = Intent(this,RegistroActivity::class.java)
            startActivity(res)
        }

        private fun showToastMessage(text: String) {
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
            }
        }

    }


