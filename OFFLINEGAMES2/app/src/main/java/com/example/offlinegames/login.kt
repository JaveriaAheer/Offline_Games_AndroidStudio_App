package com.example.offlinegames


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.offlinegames.databinding.LoginBinding
import com.example.sem_project.models.registerModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class login : AppCompatActivity() {

    private lateinit var databaseReference : DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var binding: LoginBinding
    private lateinit var login_btn: Button
    private lateinit var i:Intent
    var flag1 = 0
    var flag2 = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email=binding.editTextTextEmailAddress
        val password=binding.editTextTextPassword
        login_btn=binding.button5

        login_btn.setEnabled(false)

        firebaseDatabase =FirebaseDatabase.getInstance()
        databaseReference=firebaseDatabase.reference.child("Users")

        //for email
        email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
            {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {}

            override fun afterTextChanged(s: Editable?) {
                if(email.text !=null)
                {
                    flag1 = 1
                }
                else {
                    flag1 = 0
                }
                check_validity()
            }

        })


        //for password
        password.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
            {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {}

            override fun afterTextChanged(s: Editable?) {
                if (password.text !=null)
                {
                    flag2 = 1
                }
                else {
                    flag2 = 0
                }
                check_validity()
            }
        })

        //login button

        login_btn.setOnClickListener {
            if (flag1 == 0) {
                Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show()
            } else if (flag2 == 0) {
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show()
            } else {
                // Directly go to next screen without Firebase check
                val intent = Intent(this, startscreen::class.java)
                startActivity(intent)
                finish()
            }
        }






        //already have an account
        val logintext=findViewById<TextView>(R.id.signin)
        logintext.setOnClickListener{
            val intent=Intent(this, Sign_In::class.java)
            startActivity(intent)
            finish()
        }


    }

    //CHECK VALIDITY
    private fun check_validity() {
        if(flag1 == 1 && flag2 == 1 ){
            login_btn.setEnabled(true)
        }
    }


//login validity
    private fun loginCheck(email:String,password:String){
        databaseReference.orderByChild("email").equalTo(email)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        snapshot.key
                        for(usersnapshot in snapshot.children){
                            val userData=usersnapshot.getValue(registerModel::class.java)
                            if( userData!=null && userData.email == email && userData.password == password )
                            {
                                Toast.makeText(this@login, "Login Successful", Toast.LENGTH_SHORT).show()

                                    i= Intent(this@login,startscreen::class.java)
                                    startActivity(i)

                            }
                            else{
                                Toast.makeText(this@login, "Enter Valid Information", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@login, "Failed "+error.message, Toast.LENGTH_SHORT).show()
                }

            })
    }
}