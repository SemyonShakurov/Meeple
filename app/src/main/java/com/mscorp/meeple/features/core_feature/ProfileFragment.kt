package com.mscorp.meeple.features.core_feature

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mscorp.meeple.R
import com.mscorp.meeple.core.MeepleFragment
import com.mscorp.meeple.databinding.FragmentProfileBinding
import com.mscorp.meeple.features.core_feature.login.LoginActivity
import com.mscorp.meeple.features.core_feature.view_models.UserViewModel
import com.mscorp.meeple.model.BoardGames
import com.mscorp.meeple.model.TypeOfGameList
import com.mscorp.meeple.model.User
import com.mscorp.meeple.model.UserFriends
import com.mscorp.meeple.ui.adapters.SmallFiendsAdapter
import com.mscorp.meeple.ui.adapters.SmallGamesAdapter
import com.squareup.picasso.Picasso
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

internal class ProfileFragment : MeepleFragment<UserViewModel>() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            viewModel.user = requireArguments().get("user") as User
            viewModel.userFriends = requireArguments().get("friends") as UserFriends
            viewModel.games = (requireArguments().get("games") as BoardGames).games
        } catch (ignored: Exception) { }

        setupViews()
        setupAdapters()
        setupOnCLickListeners()
    }

    private fun setupViews() {
        var name = viewModel.user.name
        name = name.substring(0, name.indexOf(' ')) + "\n" + name.substring(
            name.indexOf(' '),
            name.length
        )
        binding.textViewUsername.text = name
        binding.textViewNickname.text = viewModel.user.nickname
        Picasso.get().load(viewModel.user.photoUrl).into(binding.roundedImageViewAvatarProfile)
    }

    private fun setupOnCLickListeners() {

        binding.imageViewGamesInfo.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_profile_to_myGamesFragment)
        }

        binding.imageViewFriendsInfo.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_profile_to_myFriendsFragment)
        }

        binding.imageViewSignOut.setOnClickListener {
            // TODO!!!!!
//            val preferences =
//                SecurePreferences(context, "my-preferences", "SometopSecretKey1235", true)
//            preferences.removeValue("userId")
//            preferences.removeValue("pass")
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.roundedImageViewAvatarProfile.setOnClickListener {
            openImageChooser()
        }
    }

    private fun setupAdapters() {
        if (viewModel.userFriends.friends.isEmpty())
            binding.textViewFriendsNotFound.visibility = View.VISIBLE
        else
            binding.textViewFriendsNotFound.visibility = View.INVISIBLE

        binding.textViewGamesNotFound.visibility = View.VISIBLE

        val adapterFriends = SmallFiendsAdapter(viewModel.userFriends.friends.take(3))
        val itemDecor = DividerItemDecoration(context, 1)
        binding.recyclerFiends.addItemDecoration(itemDecor)
        binding.recyclerFiends.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerFiends.adapter = adapterFriends


        //Игры
        if (viewModel.user.games.isNullOrEmpty())
            binding.textViewGamesNotFound.visibility = View.VISIBLE
        else {
            binding.textViewGamesNotFound.visibility = View.INVISIBLE
        }

        val adapterGames = SmallGamesAdapter(
            viewModel.getUsersGames().take(3),
            TypeOfGameList.SMALL,
            findNavController(),
            viewModel
        )

        binding.recyclerGames.addItemDecoration(itemDecor)
        binding.recyclerGames.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerGames.adapter = adapterGames
    }

    private fun openImageChooser() {
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(intent, REQUEST_CODE_IMAGE_PICKER)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_IMAGE_PICKER -> {
                    val selectedImage = data?.data
                    binding.roundedImageViewAvatarProfile.setImageURI(selectedImage)
                    uploadImage(selectedImage)
                }
            }
        }
    }

    private fun uploadImage(selectedImage: Uri?) {
        if (selectedImage == null)
            return

        val activity = requireActivity()
        val parcelFileDescriptor =
            activity.contentResolver.openFileDescriptor(selectedImage, "r", null) ?: return
        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(activity.cacheDir, getFileName(activity.contentResolver, selectedImage))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)

        viewModel.uploadAvatar(file)
    }

    private fun getFileName(contentResolver: ContentResolver, uri: Uri): String {
        var name = ""
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            it.moveToFirst()
            val column = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            name = cursor.getString(column)
        }
        return name
    }

    companion object {
        private const val REQUEST_CODE_IMAGE_PICKER = 100
    }
}