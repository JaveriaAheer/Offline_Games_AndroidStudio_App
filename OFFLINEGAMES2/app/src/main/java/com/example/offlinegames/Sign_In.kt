package com.example.offlinegames

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.offlinegames.databinding.SignInLayoutBinding
import com.example.sem_project.models.registerModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class Sign_In : AppCompatActivity() {

    private lateinit var binding: SignInLayoutBinding
    private lateinit var database : DatabaseReference
    private lateinit var register_btn: Button
    var flag1 = 0
    var flag2 = 0
    var flag3 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignInLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name=binding.editTextText
        val email=binding.editTextTextEmailAddress2
        val password=binding.editTextTextPassword2
        register_btn=binding.button7

        register_btn.setEnabled(false)

        name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
            {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {}

            override fun afterTextChanged(s: Editable?)
            {
                if (name.text !=null)
                {
                    flag1 = 1
                }
                else {
                    flag1 = 0
                }
                check_validity()
            }
        })

        email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
            {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {}

            override fun afterTextChanged(s: Editable?)
            {
                if (email.text !=null)
                {
                    flag2 = 1
                }
                else {
                    flag2 = 0
                }
                check_validity()
            }
        })


        password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
            {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {}

            override fun afterTextChanged(s: Editable?)
            {
                if (password.text !=null)
                {
                    flag3 = 1
                }
                else {
                    flag3 = 0
                }
                check_validity()
            }
        })

        register_btn.setOnClickListener{

            val name2 = name.text.toString()
            val email2 = email.text.toString()
            val password2 = password.text.toString()

            database = FirebaseDatabase.getInstance().getReference("Users").push()

            val player = registerModel(name2,email2,password2)
            database.setValue(player).addOnSuccessListener {

                email.text.clear()
                password.text.clear()
                name.text.clear()


                Toast.makeText(this@Sign_In, "Successfully Saved", Toast.LENGTH_SHORT)
                    .show()
                val i= Intent(this@Sign_In,login::class.java)
                startActivity(i)

            }.addOnFailureListener {

                Toast.makeText(this@Sign_In, "Failed" + it.message, Toast.LENGTH_SHORT).show()


            }


        }


        //Already account text
        val alreadytext=findViewById<TextView>(R.id.alreadyaccount)
        alreadytext.setOnClickListener{
            val intent= Intent(this, login::class.java)
            startActivity(intent)
        }

        }

    private fun check_validity() {

        if(flag1 == 1 && flag2 == 1 && flag3 == 1 ){
            register_btn.setEnabled(true)
        }

    }
    }
