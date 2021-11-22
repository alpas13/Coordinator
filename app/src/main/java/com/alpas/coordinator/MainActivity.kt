package com.alpas.coordinator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Checkable
import com.alpas.coordinator.databinding.ActivityMainBinding
import com.alpas.coordinator.databinding.LayoutBottombarBinding
import com.alpas.coordinator.databinding.LayoutSubmenuBinding
import com.google.android.material.snackbar.Snackbar

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