package com.github.caoddx.easyadapter.example

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.github.caoddx.easyadapter.datasource.MutableListDataSource
import com.github.caoddx.easyadapter.dsl.easyAdapter
import com.github.caoddx.easyadapter.dsl.list
import com.github.caoddx.easyadapter.dsl.single
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_action.view.*
import kotlinx.android.synthetic.main.item_body.view.*
import java.util.concurrent.ThreadLocalRandom

class DslActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dsl)

        val ds = MutableListDataSource<String>()

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = easyAdapter(empty) {
            single {
                layout(R.layout.item_action)

                bindView { itemView ->
                    itemView.button.text = "random"
                    itemView.button.setOnClickListener {
                        rand(ds)
                    }
                }
            }

            single {
                layout(R.layout.item_action)

                bindView { itemView ->
                    itemView.button.text = "remove first"
                    itemView.button.setOnClickListener {
                        ds.removeFirst()
                    }
                }
            }

            list(ds) {
                layout(R.layout.item_body)

                bindView { itemView, item, position ->
                    itemView.textViewIndex.text = position.toString()
                    itemView.textViewContent.text = item
                }
            }

            single {
                layout(R.layout.item_action)

                bindView { itemView ->
                    itemView.button.text = "add last"
                    itemView.button.setOnClickListener {
                        ds.addLast("${ThreadLocalRandom.current().nextLong()}")
                    }
                }
            }
        }

        ds.replace(listOf("first", "second", "third"))
    }

    private fun rand(ds: MutableListDataSource<String>) {
        val random = ThreadLocalRandom.current()
        val list = (0..random.nextInt(1, 10)).map { random.nextLong().toString() }
        ds.replace(list)
    }
}
