package com.devsudal.sdk.ph.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devsudal.sdk.addon.connection.buzzvil.BuzzProfile
import com.devsudal.sdk.addon.factory.AddOnFactory
import com.devsudal.sdk.log.LogTracer
import com.devsudal.sdk.ph.databinding.ActivityPointhomeMainBinding
import com.devsudal.sdk.ph.databinding.ItemMenuBinding

internal class PointHomeMainActivity : AppCompatActivity() {

    lateinit var binding: ActivityPointhomeMainBinding

    enum class Menu(val title: String, val activityClass: Class<out AppCompatActivity>? = null) {
        FEED("피드적립"),
        LOCK_SCREEN("잠금화면", LockScreenActivity::class.java),
        NOTIFICATION("노티바", NotificationActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPointhomeMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val linearLayoutManager = LinearLayoutManager(this)

        with(binding.rvMemu) {
            setHasFixedSize(true)
            adapter = MenuListAdapter(list = Menu.entries)
            layoutManager = linearLayoutManager
        }
    }


    inner class MenuListAdapter(private val list: List<Menu>) :
        RecyclerView.Adapter<MenuListAdapter.ItemViewHolder>() {

        private val menuActivityMap: Map<String, Class<out AppCompatActivity>> = mapOf(
            "잠금화면" to LockScreenActivity::class.java,
            "노티바" to NotificationActivity::class.java
        )

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val itemViewBinding = ItemMenuBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ItemViewHolder(itemViewBinding)
        }

        override fun getItemCount(): Int {

            LogTracer.i { "list.size: ${list.size}" }
            return list.size
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val menuItem = list[position]
            holder.bindView(menuItem.title)
            holder.itemView.setOnClickListener {
                when (menuItem) {
                    Menu.FEED -> {
                        AddOnFactory.Buzzvil.setUserProfile(
                            BuzzProfile(
                                userId = "asdefq22",
                                gender = BuzzProfile.Gender.MALE,
                                birth = 1979
                            )
                        )
                        AddOnFactory.Buzzvil.load(activity = this@PointHomeMainActivity)
                    }
                    else -> {
                        menuItem.activityClass?.let {
                            startActivity(Intent(this@PointHomeMainActivity, it))
                        }
                    }
                }
            }
        }

        inner class ItemViewHolder(private val itemBinding: ItemMenuBinding) : RecyclerView.ViewHolder(itemBinding.root) {
            fun bindView(menu: String) {
                itemBinding.itemMenuName.text = menu
            }
        }
    }
}