package com.alpas.coordinator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Checkable
import com.alpas.coordinator.databinding.ActivityMainBinding
import com.alpas.coordinator.databinding.LayoutBottombarBinding
import com.alpas.coordinator.databinding.LayoutSubmenuBinding
import com.alpas.coordinator.extensions.init
import com.google.android.material.snackbar.Snackbar
import com.alpas.coordinator.extensions.dpToPx

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var btmBarBinding: LayoutBottombarBinding
    private lateinit var subMenuBinding: LayoutSubmenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        btmBarBinding = LayoutBottombarBinding.bind(binding.root)
        subMenuBinding = LayoutSubmenuBinding.bind(binding.root)
        setContentView(binding.root)
        setupBottombar()
        setupFabs()
    }

    private fun setupFabs() {
        binding.mini1.init(dpToPx(-48), dpToPx(-48))
        binding.mini2.init(dpToPx(0), dpToPx(-64))
        binding.mini3.init(dpToPx(48), dpToPx(-48))

        binding.fab.setOnClickListener {
            if (binding.mini1.isOrWillBeShown) binding.mini1.hide() else binding.mini1.show()
            if (binding.mini2.isOrWillBeShown) binding.mini2.hide() else binding.mini2.show()
            if (binding.mini3.isOrWillBeShown) binding.mini3.hide() else binding.mini3.show()
        }
    }

    private fun setupBottombar() {
        btmBarBinding.btnLike.setOnClickListener {
            it as Checkable
            it.toggle()
            Snackbar.make(it, if (it.isChecked) "set like" else "unset like" , Snackbar.LENGTH_LONG)
                .setAnchorView(binding.bottombar)
                .show()
        }

        btmBarBinding.btnSettings.setOnClickListener {
            it as Checkable
            it.toggle()
            if (it.isChecked) binding.submenu.open() else binding.submenu.close()
        }
    }
}