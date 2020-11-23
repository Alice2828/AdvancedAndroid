package com.example.newsapp.view.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.Likes
import com.example.newsapp.R
import com.example.newsapp.utils.RequestConstants
import com.example.newsapp.view.activities.LoginActivity
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.File
import java.io.IOException

class ProfileFragment : Fragment() {
    private lateinit var preferences: SharedPreferences
    private var photoPath: String? = null
    private val REQUEST_TAKE_PHOTO = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_profile, container, false) as ViewGroup
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        logout.setOnClickListener {
            logout()
        }

        changePhoto.setOnClickListener {
            getPermissions()
        }
        saved.setOnClickListener{
            openSaved()
        }
    }

    private fun logout() {
        preferences = context?.getSharedPreferences("my_preferences", 0) as SharedPreferences
        preferences.edit().clear().commit()
        preferences = context?.getSharedPreferences("Avatar", 0) as SharedPreferences
        preferences.edit().clear().commit()
        val intent = Intent(activity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        activity?.finish()
    }


    private fun initViews() {
        name.text =(this.activity as Context).getSharedPreferences("my_preferences", 0).getString("username", "null")
        try {
            preferences = (this.activity as Context).getSharedPreferences("Avatar", 0)
            val pathPhotoAvatar = preferences.getString("uri", null)
            avatarIm.setImageURI(Uri.parse(pathPhotoAvatar))
        } catch (e: Exception) {

        }
    }

    private fun getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val cameraGranted = ContextCompat.checkSelfPermission(
                activity as Context,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
            val galleryGranted = ContextCompat.checkSelfPermission(
                activity as Context,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
            if (cameraGranted && galleryGranted) {
                imageChooserDialog()
            } else {
                requestPermissions(
                    arrayOf(
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    RequestConstants.AVATAR_PERMISSION_REQUEST
                )
            }
        } else {
            imageChooserDialog()
        }
    }

    private fun imageChooserDialog() {
        val adapter: ArrayAdapter<String> =
            ArrayAdapter(activity as Context, android.R.layout.simple_list_item_1)
        adapter.add("Camera")
        adapter.add("Gallery")
        AlertDialog.Builder(activity as Context)
            .setTitle("Change avatar")
            .setAdapter(adapter) { dialog, which ->
                if (which == 0) {
                    openCamera()
                } else {
                    openGallery()
                }
            }
            .create()
            .show()
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity((this.activity as Context).packageManager) != null) {
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (e: IOException) {
            }
            if (photoFile != null) {
                val photoUri = FileProvider.getUriForFile(
                    activity as Context,
                    "${context?.packageName}.provider",
                    photoFile
                )
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                startActivityForResult(intent, REQUEST_TAKE_PHOTO)
            }
        }
    }

    private fun createImageFile(): File? {
        val filename = "MyAvatars"
        val storageDir = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            filename,
            ".jpg",
            storageDir
        )
        photoPath = image.absolutePath
        return image
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        preferences = (this.activity as Context).getSharedPreferences("Avatar", 0)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_TAKE_PHOTO) {
            preferences.edit().clear().commit()
            preferences.edit().putString("uri", photoPath).commit()
            avatarIm.setImageURI(Uri.parse(photoPath))
            Toast.makeText(activity as Context, "Your photo is changed", Toast.LENGTH_SHORT)
                .show()

        } else if (requestCode == RequestConstants.GALLERY) {
            if (data?.data == null) {
                preferences = (this.activity as Context).getSharedPreferences("Avatar", 0)
                val pathPhotoAvatar = preferences.getString("uri", null)
                avatarIm.setImageURI(Uri.parse(pathPhotoAvatar))
                Toast.makeText(
                    activity as Context,
                    "You don't change your photo",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val image = data.data
                preferences.edit().clear().commit()
                preferences.edit().putString("uri", image.toString()).commit()
                avatarIm.setImageURI(image)
                Toast.makeText(activity as Context, "Your photo is changed", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RequestConstants.AVATAR_CAMERA_PERMISSION_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            }
            return
        } else if (requestCode == RequestConstants.AVATAR_GALLERY_PERMISSION_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            }
            return
        } else if (requestCode == RequestConstants.AVATAR_PERMISSION_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                imageChooserDialog()
            }
        }
    }

    private fun openGallery() {
        val intent =
            Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        startActivityForResult(intent, RequestConstants.GALLERY)
    }
    private fun openSaved(){
        Toast.makeText(context, Likes.getList()?.get(0)?.author, Toast.LENGTH_LONG).show()
    }
}