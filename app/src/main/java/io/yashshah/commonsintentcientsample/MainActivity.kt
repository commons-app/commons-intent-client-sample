package io.yashshah.commonsintentcientsample

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.descriptionEditText
import kotlinx.android.synthetic.main.activity_main.imageView
import kotlinx.android.synthetic.main.activity_main.selectImageButton
import kotlinx.android.synthetic.main.activity_main.shareButton
import kotlinx.android.synthetic.main.activity_main.titleEditText

class MainActivity : AppCompatActivity() {

    val IMAGE_PICK_REQUEST_CODE = 777;

    var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        selectImageButton.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                    Intent.createChooser(intent, "Select Image To Share"), IMAGE_PICK_REQUEST_CODE
            );
        }

        shareButton.setOnClickListener {
            uri?.let {
                val title = titleEditText.text.toString().trim()
                val description = descriptionEditText.text.toString().trim()

                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_STREAM, uri)
                intent.putExtra("title", title)
                intent.putExtra("description", description)
                intent.type = "image/*"
                startActivity(Intent.createChooser(intent, "Send Image To"))
            }
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMAGE_PICK_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            uri = data?.data
            uri?.let {
                imageView.setImageBitmap(MediaStore.Images.Media.getBitmap(contentResolver, uri))
            }
        }
    }
}
