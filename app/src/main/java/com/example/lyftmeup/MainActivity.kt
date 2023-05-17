package com.example.lyftmeup

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.lyftmeup.data.BuildingData
import com.example.lyftmeup.adapter.BuildingAdapter
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    private val SHARED_PREFERENCE_FULLNAME = "full_name"
    private val SHARED_PREFERENCE_EMAIL = "email"
    private val SHARED_PREFERENCE_PHONE = "phone"

    private lateinit var etName: EditText;
    private lateinit var etEmail: EditText;
    private lateinit var etPhone: EditText;
    private lateinit var etButton: Button;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkIfUserIsNew();

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etButton = findViewById(R.id.etButton);

        etButton.setOnClickListener {
            val name = etName.text.toString().trim();
            val email = etEmail.text.toString().trim();
            val phone = etPhone.text.toString().trim();

            if(name.isEmpty()) {
                etName.error = "Full Name is Required"
                return@setOnClickListener;
            }
            else if(email.isEmpty()) {
                etEmail.error = "Email Address is Required"
                return@setOnClickListener
            }
            else if(!emailIsValid(email)) {
                etEmail.error = "Enter a valid Email Address"
                return@setOnClickListener
            }
            else if(phone.isEmpty()) {
                etPhone.error = "Phone Number is Required"
                return@setOnClickListener
            }
            else if(!phoneIsValid(phone)) {
                etEmail.error = "Enter a valid Phone Number"
                return@setOnClickListener
            }
            else {
                Toast.makeText(this, "User Information stored successfully.", Toast.LENGTH_SHORT)
                saveUserData();
                enterApplication();
            }
        }
    }

    fun emailIsValid(email:String): Boolean {
        val pattern = Pattern.compile(
            "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"
        )
        val matcher = pattern.matcher(email);
        return matcher.matches()
    }

    fun phoneIsValid(email:String): Boolean {
        val pattern = Pattern.compile(
            "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}\$"
        )
        val matcher = pattern.matcher(email);
        return matcher.matches()
    }

    fun enterApplication() {
        val intent: Intent = Intent(this, BuildingList::class.java)
        this.startActivity(intent)
    }

    fun checkIfUserIsNew() {
        val sharedPreference by lazy {
            getSharedPreferences(
                "${BuildConfig.APPLICATION_ID}_SHARED_PREFERENCES",
                Context.MODE_PRIVATE
            )
        }

        var name = sharedPreference.getString(SHARED_PREFERENCE_FULLNAME, "")
        var email = sharedPreference.getString(SHARED_PREFERENCE_EMAIL, "")
        var phone = sharedPreference.getString(SHARED_PREFERENCE_PHONE, "")

        //  Skip step and go to building list if data is already stored
        if (name != null && email != null && phone != null) {
            if(name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty()) {
                enterApplication()
            }
        }
    }

    fun saveUserData() {
        val sharedPreference by lazy {
            getSharedPreferences(
                "${BuildConfig.APPLICATION_ID}_SHARED_PREFERENCES",
                Context.MODE_PRIVATE
            )
        }

        sharedPreference.edit().putString(SHARED_PREFERENCE_FULLNAME, etName.text.toString()).apply()
        sharedPreference.edit().putString(SHARED_PREFERENCE_EMAIL, etEmail.text.toString()).apply()
        sharedPreference.edit().putString(SHARED_PREFERENCE_PHONE, etPhone.text.toString()).apply()

    }
}