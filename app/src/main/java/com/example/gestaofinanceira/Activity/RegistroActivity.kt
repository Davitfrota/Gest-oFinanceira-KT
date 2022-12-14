package com.example.gestaofinanceira.Activity

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.gestaofinanceira.R
import com.example.gestaofinanceira.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistroActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var mFirstName: EditText
    private lateinit var mLastName: EditText
    private lateinit var mTelefone: EditText
    private lateinit var mEmail: EditText
    private lateinit var mPassword: EditText
    private lateinit var mPasswordConfirm: EditText
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var logar: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)


        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance()

        mFirstName = findViewById(R.id.register_PersonNome)
        mLastName = findViewById(R.id.register_PersonSobrenome)
        mTelefone = findViewById(R.id.register_Telefone)
        mEmail = findViewById(R.id.register_tEmailAddress)
        mPassword = findViewById(R.id.register_Password)
        mPasswordConfirm = findViewById(R.id.register_PasswordConfirm)
        logar = findViewById(R.id.register_btn_login)
        logar.setOnClickListener(this)

    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.register_btn_login ->{
                val firstname = mFirstName.text.toString()
                val lastname = mLastName.text.toString()
                val telefone = mTelefone.text.toString()
                val email = mEmail.text.toString()
                val password = mPassword.text.toString()
                val passwordConfirm = mPasswordConfirm.text.toString()

                var isFormFilled = true

                if (firstname.isEmpty()) {
                    mFirstName.error = "este campo n??o pode estar vazio"
                    isFormFilled = false
                }
                if (lastname.isEmpty()) {
                    mLastName.error = "este campo n??o pode estar vazio"
                    isFormFilled = false
                }


                if (telefone.isEmpty()) {
                    mTelefone.error = "este campo n??o pode estar vazio"
                    isFormFilled = false
                }

                if (email.isEmpty()) {
                    mEmail.error = "este campo n??o pode estar vazio"
                    isFormFilled = false
                }

                if (password.isEmpty()) {
                    mPassword.error = "este campo n??o pode estar vazio"
                    isFormFilled = false
                }

                if (passwordConfirm.isEmpty()) {
                    mPasswordConfirm.error = "este campo n??o pode estar vazio"
                    isFormFilled = false
                }

                if (isFormFilled) {
                    if (password != passwordConfirm) {
                        mPasswordConfirm.error = "As senhas n??o coincidem"
                        return
                    }
                    val dialog = ProgressDialog(this)
                    dialog.setTitle("ToDoList")
                    dialog.isIndeterminate = true
                    dialog.show()

                    mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            dialog.dismiss()
                            val handler = Handler(Looper.getMainLooper())
                            if (it.isSuccessful) {
                                val user = User(firstname,lastname,email, telefone)

                                val ref = mDatabase.getReference("users/${mAuth.uid!!}")
                                ref.setValue(user)
                                handler.post{
                                    Toast.makeText(applicationContext,
                                        "Usu??rio cadastrado com sucesso",
                                        Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                            }
                            else{
                                handler.post{
                                    Toast.makeText(applicationContext,
                                        it.exception?.message,
                                        Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                }

            }
        }
    }
    }




