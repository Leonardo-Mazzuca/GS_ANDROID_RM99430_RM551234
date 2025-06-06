package leonardomazzuca.com.github

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class RmActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rm_layout)

        val backBtn = findViewById<Button>(R.id.back_btn)
        backBtn.setOnClickListener {
            finish()
        }
    }

}