package com.github.caoddx.easyadapter.example

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.github.caoddx.easyadapter.EasyAdapter
import com.github.caoddx.easyadapter.datasource.ImmutableSingleDataSource
import com.github.caoddx.easyadapter.datasource.MutableListDataSource
import com.github.caoddx.easyadapter.plain.ListGroup
import com.github.caoddx.easyadapter.plain.SingleGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_action.view.*
import kotlinx.android.synthetic.main.item_body.view.*
import java.util.concurrent.ThreadLocalRandom

class NoDslActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_dsl)

        val ds = MutableListDataSource<String>()

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = EasyAdapter(
                SingleGroup(R.layout.item_action, ImmutableSingleDataSource("random")) { itemView, item ->
                    itemView.button.text = item
                    itemView.button.setOnClickListener {
                        rand(ds)
                    }
                },
                SingleGroup(R.layout.item_action, ImmutableSingleDataSource(Unit)) { itemView, item ->
                    itemView.button.text = "remove first"
                    itemView.button.setOnClickListener {
                        ds.removeFirst()
                    }
                },
                ListGroup(R.layout.item_body, ds) { itemView, item, position ->
                    itemView.textViewIndex.text = position.toString()
                    itemView.textViewContent.text = item
                },
                SingleGroup(R.layout.item_action, ImmutableSingleDataSource(Unit)) { itemView, item ->
                    itemView.button.text = "add last"
                    itemView.button.setOnClickListener {
                        ds.addLast("${ThreadLocalRandom.current().nextLong()}")
                    }
                },
                emptyView = empty)

        ds.replace(listOf("first", "second", "third"))
    }

    private fun rand(ds: MutableListDataSource<String>) {
        val random = ThreadLocalRandom.current()
        val list = (0..random.nextInt(1, 10)).map { random.nextLong().toString() }
        ds.replace(list)
    }
}
