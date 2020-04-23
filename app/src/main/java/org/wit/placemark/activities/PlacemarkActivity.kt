package org.wit.placemark.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_placemark.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.placemark.R
import org.wit.placemark.helpers.readImage
import org.wit.placemark.helpers.readImageFromPath
import org.wit.placemark.helpers.showImagePicker
import org.wit.placemark.main.MainApp
import org.wit.placemark.models.Location
import org.wit.placemark.models.PlacemarkModel
import android.widget.Button
import android.widget.Toast


class PlacemarkActivity : AppCompatActivity(), AnkoLogger {

  private lateinit var auth: FirebaseAuth

  private lateinit var logoutBtn: Button
  private lateinit var updatePass: Button

  var placemark = PlacemarkModel()
  var edit = false
  lateinit var app: MainApp
  val IMAGE_REQUEST = 1
  val LOCATION_REQUEST = 2
  //var location = Location(52.245696, -7.139102, 15f)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_placemark)
    auth = FirebaseAuth.getInstance()

    if(auth.currentUser == null){
      val intent = Intent(this, LoginActivity::class.java)
      startActivity(intent)
      finish()
    }else{
      Toast.makeText(this, "Already logged in", Toast.LENGTH_LONG).show()
    }

    setContentView(R.layout.activity_placemark)

    logoutBtn = findViewById(R.id.logout_btn)
    updatePass = findViewById(R.id.update_pass_btn)

    logoutBtn.setOnClickListener{
      FirebaseAuth.getInstance().signOut()
      val intent = Intent(this, LoginActivity::class.java)
      startActivity(intent)
      finish()
    }

    updatePass.setOnClickListener{
      val intent = Intent(this, UpdatePassword::class.java)
      startActivity(intent)
    }

    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)
    info("Placemark Activity started..")

    chooseImage.setOnClickListener {
      showImagePicker(this, IMAGE_REQUEST)
    }

    app = application as MainApp

//    toolbar.title = title
//    setSupportActionBar(toolbar)

    if (intent.hasExtra("placemark_edit")) {
      edit = true
      btnAdd.setText(R.string.save_placemark)
      placemark = intent.extras?.getParcelable<PlacemarkModel>("placemark_edit")!!
      placemarkTitle.setText(placemark.title)
      description.setText(placemark.description)
      placemarkImage.setImageBitmap(readImageFromPath(this, placemark.image))

      if (placemark.image != null) {
        chooseImage.setText(R.string.change_placemark_image)
      }
    }

    btnAdd.setOnClickListener() {
      placemark.title = placemarkTitle.text.toString()
      placemark.description = description.text.toString()
      if (placemark.title.isEmpty()) {
        toast(R.string.enter_placemark_title)
      } else {
        if (edit) {
          app.placemarks.update(placemark.copy())
        } else {
          app.placemarks.create(placemark.copy())
        }
      }
      info("add Button Pressed: $placemarkTitle")
      setResult(AppCompatActivity.RESULT_OK)
      finish()
    }

    placemarkLocation.setOnClickListener {
      val location = Location(52.245696, -7.139102, 15f)
      if (placemark.zoom != 0f) {
        location.lat =  placemark.lat
        location.lng = placemark.lng
        location.zoom = placemark.zoom
      }
      startActivityForResult(intentFor<MapsActivity>().putExtra("location", location), LOCATION_REQUEST)
    }

  }
  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_placemark, menu)
    return super.onCreateOptionsMenu(menu)
  }
  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_cancel -> {
        finish()
      }
      R.id.item_delete -> {
        app.placemarks.delete(placemark)
        finish()
      }
    }
    return super.onOptionsItemSelected(item)
  }
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
      IMAGE_REQUEST -> {
        if (data != null) {
          placemark.image = data.getData().toString()
          placemarkImage.setImageBitmap(readImage(this, resultCode, data))
          chooseImage.setText(R.string.change_placemark_image)
        }
      }
      LOCATION_REQUEST -> {
        if (data != null) {
          val location = data.extras?.getParcelable<Location>("location")
          if (location != null) {
            placemark.lat = location.lat
          }
          if (location != null) {
            placemark.lng = location.lng
          }
          if (location != null) {
            placemark.zoom = location.zoom
          }
        }
      }
    }
  }

}

