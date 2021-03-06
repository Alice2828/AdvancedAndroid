package com.example.newsapp.view.fragments

import android.annotation.SuppressLint
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
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.utils.CommonUtils
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
        return inflater.inflate(R.layout.fragment_profile, container, false) as ViewGroup
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        checkMode()
        setListeners()
    }

    private fun checkMode() {
        nightMode.isChecked = CommonUtils.isNightModeEnabled(requireContext())
        if (CommonUtils.isNightModeEnabled(requireContext()))
            nightMode.text = "DISABLE DARK MODE "
        else
            nightMode.text = " ENABLE DARK MODE "
    }

    private fun setListeners() {
        logout.setOnClickListener {
            logout()
        }
        avatarIm.setOnClickListener {
            getPermissions()
        }
        changePhoto.setOnClickListener {
            getPermissions()
        }

        nightMode.setOnClickListener {
            if (CommonUtils.isNightModeEnabled(requireContext())) {
                CommonUtils.setIsNightModeEnabled(requireContext(), false)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                activity?.finish()

            } else if (!CommonUtils.isNightModeEnabled(requireContext())) {
                CommonUtils.setIsNightModeEnabled(requireContext(), true)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                activity?.finish()
            }
        }
    }

    private fun logout() {
        preferences = context?.getSharedPreferences("my_preferences", 0) as SharedPreferences
        preferences.edit().clear().commit()
        val intent = Intent(activity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        activity?.finish()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initViews() {
        val loggedSharedPreferences = context?.getSharedPreferences("my_preferences", 0)
        name.text = loggedSharedPreferences?.getString("username", "null")
        email.text = loggedSharedPreferences?.getString("emailName", "null")
        try {
            val currentUser = (activity as Context).getSharedPreferences("my_preferences", 0)
                ?.getString("emailName", "")
            preferences = context?.getSharedPreferences(currentUser, 0) as SharedPreferences
            try {
                val pathPhotoAvatar = preferences.getString("uri", null)
                if (pathPhotoAvatar == null)
                    avatarIm.setImageDrawable(
                        resources.getDrawable(
                            R.drawable.ic_account_circle_black_24dp,
                            null
                        )
                    )
                else
                    avatarIm.setImageURI(Uri.parse(pathPhotoAvatar))

            } catch (e: Exception) {
                avatarIm.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_account_circle_black_24dp,
                        null
                    )
                )
            }
        } catch (e: Exception) {
            avatarIm.setImageDrawable(
                resources.getDrawable(
                    R.drawable.ic_account_circle_black_24dp,
                    null
                )
            )
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
        adapter.add("Delete")
        AlertDialog.Builder(activity as Context)
            .setTitle("Change avatar")
            .setAdapter(adapter) { _, which ->
                if (which == 0) {
                    openCamera()
                } else if (which == 1) {
                    openGallery()
                } else {
                    deletePhoto()
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
        val currentUser = (activity as Context).getSharedPreferences("my_preferences", 0)
            ?.getString("emailName", "")
        preferences = (this.activity as Context).getSharedPreferences(currentUser, 0)

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_TAKE_PHOTO) {
            preferences.edit().clear().commit()
            preferences.edit().putString("uri", photoPath).commit()
            avatarIm.setImageURI(Uri.parse(photoPath))
            Toast.makeText(activity as Context, "Your photo is changed", Toast.LENGTH_SHORT)
                .show()

        } else if (requestCode == RequestConstants.GALLERY) {
            if (data?.data == null) {
                preferences = (this.activity as Context).getSharedPreferences(currentUser, 0)
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

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun deletePhoto() {
        if (photoPath != null) {
            val fDelete = File(photoPath)
            if (fDelete.exists()) {
                if (fDelete.delete()) {

                    avatarIm.setImageDrawable(
                        resources.getDrawable(
                            R.drawable.ic_account_circle_black_24dp,
                            null
                        )
                    )
                    val currentUser =
                        (activity as Context).getSharedPreferences("my_preferences", 0)
                            ?.getString("emailName", "")
                    (this.activity as Context).getSharedPreferences(currentUser, 0).edit()
                        .remove("uri").commit()
                    Toast.makeText(context, "Photo was deleted", Toast.LENGTH_LONG).show()

                } else {
                    Toast.makeText(context, "Something is wrong", Toast.LENGTH_LONG).show()

                }
            }
        } else {
            Toast.makeText(context, "No photo", Toast.LENGTH_LONG).show()
        }
    }
}