package info.kulikov.logistica

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import info.kulikov.logistica.database.AppDao
import info.kulikov.logistica.database.DatabaseManager
import io.reactivex.disposables.Disposable

class MainViewModel: ViewModel() {

    lateinit var appDao: AppDao
    private val itemsLiveData = MutableLiveData<ArrayList<Somewhat>>()
    val items: LiveData<ArrayList<Somewhat>> = itemsLiveData
    var tabIndex = MutableLiveData<Int>()

    fun loadDatabaseRepositories(): Disposable {
        return DatabaseManager.loadSomewhatRepos(appDao)
            .subscribe(
                { values ->
                    itemsLiveData.postValue(values as ArrayList<Somewhat>?)
                },
                { error -> println("Error: $error") },    // onError
                {
                    println("Completed!")                 // onComplete
                }
            )
    }

    fun createTestData() {
        // генерим код, потом можно отключить или оставить так
        if (itemsLiveData.value == null) {
            itemsLiveData.value = ArrayList();
        }

        itemsLiveData.value?.add(Somewhat(1, 89.55, "image_1.png"))
        itemsLiveData.value?.add(Somewhat(2, 895.5, "image_2.png"))
        itemsLiveData.value?.add(Somewhat(3, 8955.00, "image_3.png"))

        itemsLiveData.value?.let {
            DatabaseManager.saveSomewhatRepos(appDao, it)
                .subscribe()
        }
    }

    fun getFieldData(fieldName: String): ArrayList<String> {
        val list: ArrayList<String> = arrayListOf()
        if (itemsLiveData.value != null) {
            for (item in itemsLiveData.value!!) {
                when (fieldName) {
                    "id" -> {
                        list.add(item.id.toString())
                    }
                    "value" -> {
                        list.add(item.value.toString())
                    }
                    "image" -> {
                        list.add(item.image)
                    }
                }
            }
        }
        return list
    }

}