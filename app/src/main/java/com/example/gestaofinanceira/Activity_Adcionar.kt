package com.example.gestaofinanceira

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.gestaofinanceira.model.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Activity_Adcionar : AppCompatActivity(), View.OnClickListener {

    private var mtipo = -1
    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mAdcionar : TextView
    //luxo
    private lateinit var mCor1 : TextView
    private lateinit var mCor2 : TextView
    private lateinit var mTitulo: TextView

    private lateinit var mOrigem: TextView
    private lateinit var mValor : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adcionar)

        mDatabase = FirebaseDatabase.getInstance()
        mAuth = FirebaseAuth.getInstance()

        mtipo = intent.getIntExtra("transacao", -1)


        mValor = findViewById(R.id.adicionar_valor)
        mOrigem = findViewById(R.id.adicionar_Origem)
        mCor1 = findViewById(R.id.adicionar_cor1)
        mCor2 = findViewById(R.id.adicionar_cor2)
        mTitulo = findViewById(R.id.Titulo_adicionar)

        mAdcionar = findViewById(R.id.Adcionar_btn_lucrodiv)
        mAdcionar.setOnClickListener(this)


        if (mtipo == 1){

            mTitulo.setText("Novo Lucro")
            mCor1.setBackgroundColor(Color.parseColor("#0FEF18"));
            mCor2.setBackgroundColor(Color.parseColor("#0FEF18"));

        }
        else {

            mTitulo.setText("Nova Despesa")
            mCor1.setBackgroundColor(Color.parseColor("#EF1730"));
            mCor2.setBackgroundColor(Color.parseColor("#EF1730"));

        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.Adcionar_btn_lucrodiv ->{

                var name = mOrigem.text.toString()
                var valor = mValor.text.toString()

                if (name.isEmpty()) {
                   mOrigem.error = "Este campo não pode estar vazio"
                    return
                }

                if (valor.isEmpty()) {
                   mValor.error = "Este campo não pode estar vazio"
                    return
                }

                if (mtipo == 1){
                    val LucroId = mDatabase.reference.child("/users/${mAuth.uid}/Lucros").push().key
                    val ref = mDatabase.getReference("/users/${mAuth.uid}/Lucros/${LucroId}")
                    val taskk = Task(name,valor)
                    ref.setValue(taskk)
                    Toast.makeText(applicationContext, "Lucro adicionado com sucesso", Toast.LENGTH_SHORT).show()
                }
                else {
                    val DespesaId = mDatabase.reference.child("/users/${mAuth.uid}/Despesa").push().key
                    val ref = mDatabase.getReference("/users/${mAuth.uid}/Despesa/${DespesaId}")
                    val taskk = Task(name,valor)
                    ref.setValue(taskk)
                    Toast.makeText(applicationContext, "Despesa adicionada com sucesso", Toast.LENGTH_SHORT).show()

                }
                finish()

            }



        }

    }

}
