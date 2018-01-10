package com.example.developer.firstapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var db = FirebaseFirestore.getInstance()
        var user = HashMap<String,Any>()
        user.put("first","Aarón")
        user.put("last","Negrete")
        user.put("born",1996)
        db.collection("users")
                .add(user)
                .addOnSuccessListener({
                    reference ->
                    Log.e("id: ",reference.id)
                })
                .addOnFailureListener({
                    exception ->
                    Log.e("TAG","Error en documento",exception)
                })
        var user2 = HashMap<String, Any>()
        user2.put("first","jose")
        user2.put("middle","pérez")
        user2.put("last","sánchez")
        user2.put("born",1990)

        db.collection("users")
                .add(user2)
                .addOnSuccessListener { success ->
                    Log.e("tag id: ",success.id)
                }
                .addOnFailureListener { exception ->
                    Log.e("Error","no se insertó",exception)
                }
        db.collection("users")
                .get()
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        for(document in task.result){
                            var textView = TextView(this)
                            textView.setText(document.id + " => " + document.data)
                            ln_main.addView(textView)
                            Log.e("respuesta: ",document.id + " => " + document.data)
                        }
                    }else{
                        Log.e("ERROR", "error obteniendo documentos", task.exception)
                    }
                }
    }
}
