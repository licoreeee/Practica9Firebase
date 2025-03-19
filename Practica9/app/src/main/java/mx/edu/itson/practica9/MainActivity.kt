package mx.edu.itson.practica9

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private val userRef= FirebaseDatabase.getInstance().getReference("Users")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        var btnSave: Button =findViewById(R.id.btGuardar) as Button
        btnSave.setOnClickListener { saveMarkFromForm() }

        userRef.addChildEventListener(object: ChildEventListener{
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}

            override fun onChildAdded(dataSnapshot: DataSnapshot, p1:String?) {
                val value = dataSnapshot.getValue()

                if (value is String) {
                } else if (value is User) {
                    val usuario = value

                    if(usuario!=null){writeMark(usuario)}
                }
            }
        })

    }

    private fun saveMarkFromForm(){
        var name:EditText=findViewById(R.id.etNombre) as EditText
        var lastName:EditText=findViewById(R.id.etApellido) as EditText
        var age:EditText=findViewById(R.id.etEdad) as EditText

        val usuario = User(
            name.text.toString(),
            lastName.text.toString(),
            age.text.toString()
        )
        userRef.push().setValue(usuario)
    }

    private fun writeMark(mark: User){
        var listV:TextView=findViewById(R.id.tvLista) as TextView
        val text = listV.text.toString() + mark.toString() + "\n"
        listV.text = text
    }
}