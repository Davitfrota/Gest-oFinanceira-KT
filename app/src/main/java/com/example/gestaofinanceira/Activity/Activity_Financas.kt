package com.example.gestaofinanceira.Activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestaofinanceira.Activity_Adcionar
import com.example.gestaofinanceira.R
import com.example.gestaofinanceira.adapter.TaskAdapter
import com.example.gestaofinanceira.model.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Activity_Financas : AppCompatActivity(), View.OnClickListener {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mAddlucro: Button
    private lateinit var mAddEndiv: Button
    private lateinit var mRecycleLucro: RecyclerView
    private lateinit var mRecycleEndividado: RecyclerView
    private var mDespesaLista = mutableListOf<Task>()
    private var mLucroLista = mutableListOf<Task>()
    private  val handler = Handler(Looper.getMainLooper())
    private  lateinit var TaskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_financas)
        mDatabase = FirebaseDatabase.getInstance()
        mAuth = FirebaseAuth.getInstance()

        mAddlucro = findViewById(R.id.add_btn_financasL)
        mAddEndiv= findViewById(R.id.add_btn_financasEN)

        mRecycleEndividado = findViewById(R.id.Recycle_view_Endividado)
        mRecycleLucro = findViewById(R.id.Recycle_view_Lucro)

        mAddlucro.setOnClickListener(this)
        mAddEndiv.setOnClickListener(this)



    }


    override fun onStart() {
        super.onStart()

        val ListaDespesa= mDatabase.reference.child("/users/${mAuth.uid}/Despesa").orderByKey().limitToLast(5)
        val ListaLucro = mDatabase.reference.child("/users/${mAuth.uid}/Lucros").orderByKey().limitToLast(5)
        ListaDespesa.addValueEventListener(object:ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                var DespesaLista  = mutableListOf<Task>()
                snapshot.children.forEach{
                    val despesa1 = it.getValue(Task::class.java)
                    DespesaLista.add(despesa1!!)

                }
                mDespesaLista.clear()
                mDespesaLista.addAll(DespesaLista)
                handler.post{
                    TaskAdapter = TaskAdapter(mDespesaLista)
                    val llm = LinearLayoutManager(applicationContext)

                    llm.stackFromEnd = true
                    llm.reverseLayout = true
                    mRecycleEndividado.apply {
                        adapter = TaskAdapter
                        layoutManager = llm
                    }

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        ListaLucro.addValueEventListener(object:ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                var LucroLista  = mutableListOf<Task>()
                snapshot.children.forEach{
                    val Lucro = it.getValue(Task::class.java)
                    LucroLista.add(Lucro!!)

                }

                mLucroLista.clear()
                mLucroLista.addAll(LucroLista)
                handler.post{
                    TaskAdapter = TaskAdapter(mLucroLista)
                    val llm = LinearLayoutManager(applicationContext)

                    llm.stackFromEnd = true
                    llm.reverseLayout = true
                    mRecycleLucro.apply {
                        adapter = TaskAdapter
                        layoutManager = llm
                    }

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.add_btn_financasL->{

                val it = Intent(applicationContext,Activity_Adcionar::class.java)
                it.putExtra("transacao", 1)
                startActivity(it)

            }

            R.id.add_btn_financasEN->{

                val it = Intent(applicationContext,Activity_Adcionar::class.java)
                it.putExtra("transacao", 0)
                startActivity(it)


            }
        }
    }
}