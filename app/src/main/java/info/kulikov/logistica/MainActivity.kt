package info.kulikov.logistica

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import info.kulikov.logistica.database.AppRoomDatabase


class MainActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel

    private var currentValueField: String = "id"
    private var currentSpinnerImagePosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel =
            ViewModelProvider(this).get(MainViewModel::class.java) // подключили ViewModel

        mainViewModel.appDao = AppRoomDatabase.getDatabase(
            this.applicationContext)
            .appDao()

        mainViewModel.createTestData()
        if (mainViewModel.tabIndex.value == null) {
            mainViewModel.tabIndex.value = 0
        }

        prepareTabs()

        val adapterImage = prepareSecondTabContent()
        val adapterValue = prepareFirstTabContent()


        // подписались на изменние списка пользователей userListLiveData
        mainViewModel.items.observe(this@MainActivity, {
            adapterValue.clear()
            adapterValue.addAll(
                mainViewModel.getFieldData(currentValueField)
            )

            adapterImage.setItems(it)
            adapterImage.notifyDataSetChanged()

        })

        mainViewModel.loadDatabaseRepositories()
    }

    private fun prepareTabs() {
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                mainViewModel.tabIndex.value = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        mainViewModel.tabIndex.observe(this@MainActivity, {
            layerVisible(it)
            tabs.getTabAt(it)?.select();
        })
    }

    private fun prepareSecondTabContent() : CustomAdapter {
        // -------------------- combobox с картинками ------------------------
        val ivImage: ImageView = findViewById(R.id.ivImage)
        val spinnerImage: Spinner = findViewById(R.id.spinnerImage)
        val adapterImage = CustomAdapter(this, mainViewModel.items.value)

        spinnerImage.adapter = adapterImage
        spinnerImage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected( parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                currentSpinnerImagePosition = position
                changeImageView(position, ivImage)

            }
        }
        return adapterImage;
    }

    private fun changeImageView(position: Int, ivImage: ImageView) {
        when (mainViewModel.items.value?.get(position)?.image) {
            "image_1.png" -> {
                ivImage.setImageResource(R.drawable.image_1)
            }
            "image_2.png" -> {
                ivImage.setImageResource(R.drawable.image_2)
            }
            "image_3.png" -> {
                ivImage.setImageResource(R.drawable.image_3)
            }
        }
    }

    private fun prepareFirstTabContent() : ArrayAdapter<String> {
        // -------------------- список значений полей --------------------
        val listView: ListView = findViewById(R.id.listView)
        val listValues = mainViewModel.getFieldData(currentValueField)

        val adapterValue: ArrayAdapter<String> = ArrayAdapter(
            this, android.R.layout.simple_list_item_1, listValues
        )

        listView.setAdapter(adapterValue)

        // -------------------- combobox с полями --------------------------
        val spinner: Spinner = findViewById(R.id.spinner)
        val adapter: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            this, R.array.db_fields,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // полeчам значения по имени поля
                // currentSpinnerValuePosition = position
                currentValueField = spinner.getItemAtPosition(position).toString()
                adapterValue.clear()
                adapterValue.addAll(
                    mainViewModel.getFieldData(currentValueField)
                )
            }
        }
        return adapterValue;
    }


    private fun layerVisible(folder: Int) {
        if (folder == 0) {
            findViewById<LinearLayout>(R.id.llFirst).visibility = View.VISIBLE
            findViewById<LinearLayout>(R.id.llSecond).visibility = View.GONE
        } else {
            findViewById<LinearLayout>(R.id.llFirst).visibility = View.GONE
            findViewById<LinearLayout>(R.id.llSecond).visibility = View.VISIBLE
        }
    }

}