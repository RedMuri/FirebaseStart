package com.example.firebasestart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User

class SignInActivity : AppCompatActivity() {
    private lateinit var editName: EditText
    private lateinit var editLastName: EditText
    private lateinit var editType: EditText
    private lateinit var editAge: EditText
    private lateinit var buttonSignIn: Button

    private lateinit var db: FirebaseFirestore
    private lateinit var users: Map<String, Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_activity)
        db = FirebaseFirestore.getInstance()
        users = mutableMapOf()

        editName = findViewById(R.id.editTextName)
        editLastName = findViewById(R.id.editTextLastName)
        editType = findViewById(R.id.editTextType)
        editAge = findViewById(R.id.editTextAge)
        buttonSignIn =
            findViewById<Button?>(R.id.buttonSignIn).apply { setOnClickListener { signIn() } }
    }

    private fun signIn() {
        if (isCorrect(editAge, editName, editLastName, editType)) {
            val name = editName.text.toString()
            val lastName = editLastName.text.toString()
            val type = editType.text.toString()
            val age = editAge.text.toString()
            val user = User(name,lastName, type, age)
            db.collection("Users").add(user).addOnSuccessListener {
                Log.i("muri","added")
            }.addOnFailureListener{
                Log.i("muri",it.message.toString())
            }
            val intent = Intent(this,UserListActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Fill all fields!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isCorrect(vararg editText: EditText): Boolean =
        editText.all { it.text.toString().trim() != "" }
}