package com.example.praktapkmenu

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import com.google.android.material.card.MaterialCardView

class MainActivity : AppCompatActivity() {

    private lateinit var cardQuote: MaterialCardView
    private lateinit var tvQuoteBody: TextView
    private lateinit var btnAction: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cardQuote = findViewById(R.id.cardQuote)
        tvQuoteBody = findViewById(R.id.tvQuoteBody)
        btnAction = findViewById(R.id.btnAction)

        registerForContextMenu(cardQuote)

        btnAction.setOnClickListener { view ->
            showPopupMenu(view)
        }
    }

    // OPTIONS MENU
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_about_app -> {
                Toast.makeText(this, "Buka About App", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_settings -> {
                Toast.makeText(this, "Buka Settings", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_quotes -> {
                Toast.makeText(this, "Buka Quotes", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_feedback -> {
                Toast.makeText(this, "Buka Feedback", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_help -> {
                Toast.makeText(this, "Buka Help", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // CONTEXT MENU
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu?.setHeaderTitle(getString(R.string.context_title))
        menuInflater.inflate(R.menu.menu_context, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.ctx_copy -> {
                copyTextToClipboard(tvQuoteBody.text.toString())
                Toast.makeText(this, "Teks disalin", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.ctx_save -> {
                Toast.makeText(this, "Tersimpan ke favorit", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.ctx_share -> {
                shareText(tvQuoteBody.text.toString())
                true
            }
            R.id.ctx_edit -> {
                Toast.makeText(this, "Mode edit dibuka", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    // POPUP MENU
    private fun showPopupMenu(view: View) {
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.menu_popup, popup.menu)

        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.pop_download -> {
                    Toast.makeText(this, "Lagi unduh kutipan...", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.pop_share -> {
                    shareText(tvQuoteBody.text.toString())
                    true
                }
                else -> false
            }
        }
        popup.show()
    }


    // FUNGSI BANTUAN
    private fun copyTextToClipboard(text: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Quote", text)
        clipboard.setPrimaryClip(clip)
    }

    private fun shareText(text: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(sendIntent, "Bagikan Kutipan"))
    }
}