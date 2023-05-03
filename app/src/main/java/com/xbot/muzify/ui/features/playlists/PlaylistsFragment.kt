package com.xbot.muzify.ui.features.playlists

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.xbot.muzify.R
import com.xbot.muzify.databinding.FragmentPlaylistsBinding
import com.xbot.muzify.ui.extensions.viewBinding

class PlaylistsFragment : Fragment(R.layout.fragment_playlists) {

    private val binding: FragmentPlaylistsBinding by viewBinding(FragmentPlaylistsBinding::bind)
    private val viewModel: PlaylistsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO:
    }
}